package com.katlex.vitrina.security

class SecurityTagLib {
	
	def securityService
	
	def notAnonymous = { attrs, body ->
		if (!securityService.isAnonymous()) {
			out << body()
		}
	}
		
	def anonymous = { attrs, body ->
		if (securityService.isAnonymous()) {
			out << body()
		}
	}
	
	
}
