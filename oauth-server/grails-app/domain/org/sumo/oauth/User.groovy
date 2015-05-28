package org.sumo.oauth

import org.bson.types.ObjectId

class User {

	transient springSecurityService

    ObjectId id
	String username
	String password
    String email
    String firstName
    String lastName
    boolean enabled = true
    boolean accountExpired
	boolean accountLocked
	boolean credentialsExpired

    Set<Role> authorities
    static embedded = ['authorities']

    static transients = ['springSecurityService']

	static constraints = {
        username    blank: false, unique: true, size: 2..32,matches: "[a-zA-Z0-9_]+"
        password    blank: false, size: 6..64
        firstName   blank: false, nullable: false
        lastName    blank: false, nullable: false
        email       blank: false, nullable: false, unique: true, email: true
	}

	static mapping = {
		password column: '`password`'
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		log.info '***encodePassword***'
		//password = springSecurityService.encodePassword(password)
	}
}
