package com.katlex.vitrina.domain

class User {
    @Override
	public boolean equals(Object obj) {
		 obj instanceof User
 		 User user = (User)obj
		return user.id == this.id;
	}

	String username
    String passwordHash
	Boolean blocked = false

	
    static hasMany = [ roles: Role, permissions: String ]
   	static mapping = {
    	roles lazy: false
		permissions lazy: false
    }
    

    static constraints = {
        username nullable: false, blank: false
    }
}
