package com.katlex.vitrina.domain

class Goods {
 
    static constraints = {
		name blank: false, nullable: false
		imageBytes nullable: false
		imageType nullable: false
		owner nullable: false
		description maxSize: 10000
    }

    static mapping = {
    	owner lazy: false
    }
	
	User owner
	
	byte [] imageBytes
    String imageType
	Boolean asserted = true
//	String file
	String description
	String name
	
}

