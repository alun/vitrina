package com.katlex.vitrina.domain

class User {
    String username
    String passwordHash
	
    static hasMany = [ roles: Role, permissions: String ]
   	static mapping = {
    	roles lazy: false
		permissions lazy: false
    }
    

    static constraints = {
        username nullable: false, blank: false
    }
}
