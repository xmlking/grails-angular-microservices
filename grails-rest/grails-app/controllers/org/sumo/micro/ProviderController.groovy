package org.sumo.micro

class ProviderController extends PagedRestfulController<Provider> {

    ProviderController( ) {
        super(Provider)
    }

    def show(Provider provider) {
        respond provider,
                [includes: getIncludeFields(), excludes: ['class', 'errors', 'version'],
                 formats:['json','xml']]
    }

    def patch() {
        log.error 'todo'
    }


}