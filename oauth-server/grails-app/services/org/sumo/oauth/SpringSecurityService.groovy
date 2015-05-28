class SpringSecurityService {

    /** dependency injection for authenticationTrustResolver */
    def authenticationTrustResolver

    /** dependency injection for grailsApplication */
    def grailsApplication

    /** dependency injection for the password encoder */
    def passwordEncoder

    /** dependency injection for userDetailsService */
    def userDetailsService

    /** dependency injection for userCache */
    def userCache

    String encodePassword(String password, salt = null) {
        passwordEncoder.encode password
    }

}