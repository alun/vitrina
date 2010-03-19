package com.katlex.vitrina.domain

import com.katlex.vitrina.security.Permission;

class Role {
	
	static final Role ANONYMOUS = new Role(
		name:"ANONYMOUS",
		permissions:[
		    "goods:show,showAllGoods,prev,next,index,list,image",
			"auth:login",
			"registration:*",
			"jcaptcha:*","menu:*" 
		] )
	
	static final Role REGISTERED = new Role(
		name:"REGISTERED",
		permissions:[
		    "goods:create,save,update,delete,showMine",
			"auth:signOut",
		    Permission.NOT_ANONYMOUS
		] )
	
	static final Role VALIDATED  = new Role(
	    name:"VALIDATED",
	    permissions:[] )
	
	static final Role MODERATOR  = new Role(
	    name:"MODERATOR",
		permissions:["goods:moderate,assertGoods,unAssertGoods","user:list,moderateUsers"] )
	
	
    String name

    static hasMany = [ users: User, permissions: String ]
    static belongsTo = User

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
}
