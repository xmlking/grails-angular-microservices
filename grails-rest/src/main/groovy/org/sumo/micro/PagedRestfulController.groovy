package org.sumo.micro

import grails.gorm.PagedResultList
import grails.rest.RestfulController
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import static org.springframework.http.HttpStatus.*

class PagedRestfulController<T> extends RestfulController<T> {

    static responseFormats = ['json']

    PagedRestfulController(Class<T> resource) {
        super(resource)
    }

    def index(Integer pageSize) {
        //FIXME but pageSize should have value
        def max = params.int('pageSize')
        params.page = params.int('page') ?: 1
        params.max = Math.min(max ?: grailsApplication.config.getProperty('angular.defaultPageSize', int), 100)
        params.offset = ((params.page - 1) * params.max)

        def results = loadPagedResults(params)

        response.setHeader('Accept-Ranges', resourceClassName)
        //response.setHeader('Content-Range', resourceClassName +' '+ getContentRange((int)results.totalCount, params.offset, params.max))
        response.setHeader('Content-Range', getContentRange((int)results.totalCount, params.offset, params.max))
        def status = request.getHeader('Range')? PARTIAL_CONTENT : OK
        respond results, [ status: status, includes: getIncludeFields(),
                           excludes: ['class', 'errors', 'version'],
                           formats:['json', 'xml']]
    }

    protected getIncludeFields() {
        params.includes?.tokenize(',')
    }
    protected getExcludeFields() {
        params.excludes?.tokenize(',')
    }

    protected PagedResultList loadPagedResults(params) {
        resource.createCriteria().list(max: params.max, offset: params.offset) {

            params.filter?.each { String name, String value ->
                setDefaultCriteria(delegate, name, value)
            }

            if (params.sort) {
                order(params.sort, params.order == "asc" ? "asc" : "desc")
            }
        }
    }

    protected void setDefaultCriteria(criteria, String propertyName, String propertyValue) {
        String declaredPropertyName = propertyName - 'Id'
        Class propertyClass = GrailsClassUtils.getPropertyType(resource, declaredPropertyName)

        if (!propertyClass) {
            return
        }
        else if (propertyName.endsWith('Id')) {
            criteria."${declaredPropertyName}" {
                eq('id', propertyValue.toLong())
            }
        }
        else {
            switch (propertyClass) {
                case String:
                    criteria.ilike(propertyName, "%${propertyValue}%")
                    break

                case [Float, Integer, BigDecimal]:
                    if (propertyValue.isNumber()) {
                        criteria.eq(propertyName, propertyValue.asType(propertyClass))
                    }
                    else {
                        criteria.eq(propertyName, null)
                    }
                    break

                case Date:
                    def dateFormats = grailsApplication.config.grails.databinding?.dateFormats
                    def dateProperty = params.date("filter.${propertyName}", dateFormats)

                    if (dateProperty) {
                        criteria.ge(propertyName, dateProperty)
                    }
                    else {
                        criteria.eq(propertyName, null)
                    }
                    break

                default:
                    criteria.eq(propertyName, propertyValue.asType(propertyClass))
            }
        }
    }

    protected String getContentRange(int totalCount, int offset, int max) {
        int startRange = (totalCount == 0) ? 0 : offset + 1;
        int endRange = Math.min(offset + max, totalCount)

        "${startRange}-${endRange}/${totalCount}"
    }
}