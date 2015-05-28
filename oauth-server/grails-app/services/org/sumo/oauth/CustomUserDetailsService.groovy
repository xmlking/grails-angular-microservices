package org.sumo.oauth

import grails.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Transactional
class CustomUserDetailsService implements UserDetailsService {

    protected Logger log = LoggerFactory.getLogger(getClass())

    static final GrantedAuthority NO_ROLE = new SimpleGrantedAuthority("ROLE_NO_ROLES")

    @Transactional(readOnly = true, noRollbackFor = [IllegalArgumentException, UsernameNotFoundException])
    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {

        def user = User.findWhere(username: username)
        if (!user) {
            log.warn "User not found: $username"
            throw new UsernameNotFoundException('User not found')
        }

        Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
        createUserDetails user, authorities
    }

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        loadUserByUsername username, true
    }

    protected Collection<GrantedAuthority> loadAuthorities(user, String username, boolean loadRoles) {
        if (!loadRoles) {
            return []
        }
        Collection<?> userAuthorities = user.authorities
        def authorities = userAuthorities.collect { new SimpleGrantedAuthority(it.authority) }
        return authorities ?: [NO_ROLE]
    }

    protected UserDetails createUserDetails(user, Collection<GrantedAuthority> authorities) {

        String username = user.username
        String password = user.password
        boolean enabled = user.enabled ?: true
        boolean accountExpired = user.accountExpired ?: false
        boolean credentialsExpired = user.credentialsExpired ?: false
        boolean accountLocked = user.accountLocked ?: false

        return new CustomUserDetails(
                username, password, enabled, !accountExpired, !credentialsExpired, !accountLocked,
                authorities ?: [NO_ROLE], user.id,
                "${user.lastName}, ${user.firstName}", user.email, "NO_DEP", "55555", user.username)
    }
}