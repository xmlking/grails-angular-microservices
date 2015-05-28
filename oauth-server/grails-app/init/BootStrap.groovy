import org.sumo.oauth.Role
import org.sumo.oauth.User

class BootStrap {

    def init = { servletContext ->
        // Check whether the test data already exists.
        if (!User.count()) {
            createUsers()
        }
    }
    def destroy = {
    }

    private void createUsers() {
        def superAdminRole =    new Role(authority: 'ROLE_SUPER_ADMIN')
        def switchUserRole =    new Role(authority: 'ROLE_SWITCH_USER')
        def businessAdminRole = new Role(authority: 'ROLE_BUSINESS_ADMIN')
        def itAdminRole =       new Role(authority: 'ROLE_IT_ADMIN')
        def dataAdminRole =     new Role(authority: 'ROLE_DATA_ADMIN')
        def userRole =          new Role(authority: 'ROLE_USER')

        def superUser =         new User(username: 'sumo',          enabled: true, password: 'sumodemo',    firstName:'Sumanth', lastName:'Chintha', email:'sumo@demo.com',   authorities:[superAdminRole, userRole] ).save(failOnError: true)
        def businessAdminUser = new User(username: 'businessadmin', enabled: true, password: 'sumodemo',    firstName:'Sumanth', lastName:'Chintha', email:'badmin@demo.com', authorities:[businessAdminRole, userRole] ).save(failOnError: true)
        def dataAdminUser =     new User(username: 'dataadmin',     enabled: true, password: 'sumodemo',    firstName:'Sumanth', lastName:'Chintha', email:'dadmin@demo.com', authorities:[dataAdminRole, userRole] ).save(failOnError: true)
        def itAdminUser =       new User(username: 'itadmin',       enabled: true, password: 'sumodemo',    firstName:'Sumanth', lastName:'Chintha', email:'iadmin@demo.com', authorities:[itAdminRole, userRole] ).save(failOnError: true)
        def basicUser =         new User(username: 'user',          enabled: true, password: 'sumodemo',    firstName:'Jeson',   lastName:'Chang',   email:'jeson@demo.com',  authorities:[userRole] ).save(failOnError: true)
    }
}
