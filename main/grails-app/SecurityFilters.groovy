package com.katlex.vitrina.security

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * This filters class protects all URLs
 * via access control by convention.
 * 
 * User logs in as "anonymous" automatically if he is not authenticated
 */

class SecurityFilters {
	
	def securityService
	
	
	def filters = {
        all(uri: "/**") {
            before = {
				if( ! SecurityUtils.subject.authenticated ) {
					securityService.becomeAnonymous()
				}
				
				if( ! controllerName ) return true
				
                // Access control by convention.
                accessControl(auth:false)
            }
			
        }
    }	
}
