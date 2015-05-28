import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.sumo.oauth.CustomUserDetailsService
import org.sumo.oauth.SecurityConfiguration

beans = {
    webSecurityConfiguration(SecurityConfiguration)
    userDetailsService(CustomUserDetailsService)
    passwordEncoder(BCryptPasswordEncoder)
}
