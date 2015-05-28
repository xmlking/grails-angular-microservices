package org.sumo.oauth

import groovy.transform.ToString
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@ToString(includeNames=true, includeSuper=true)
class CustomUserDetails extends User {

    private static final long serialVersionUID = 1;

    private final Object id;

    // LDAP attributes
    final String displayName
    final String email
    final String departmentNumber
    final String postalCode
    final String uid

    CustomUserDetails(String username,
                      String password,
                      boolean enabled,
                      boolean accountNonExpired,
                      boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<GrantedAuthority> authorities,
                      Object id,
                      String displayName,
                      String email,
                      String departmentNumber,
                      String postalCode,
                      String uid) {

        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);

        this.id = id

        this.displayName = displayName
        this.email = email
        this.departmentNumber = departmentNumber
        this.postalCode = postalCode
        this.uid = uid
    }

    public Object getId() {
        return id
    }
}
