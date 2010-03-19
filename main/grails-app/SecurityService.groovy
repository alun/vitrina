package com.katlex.vitrina.security

import com.katlex.vitrina.domain.User 
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

class SecurityService {

    boolean transactional = true
	
	final static ANONYMOUS_NAME = "anonymous"
    final static ANONYMOUS_PASSWORD = "anonymous"

    def currentUser() {
		User.findByUsername( SecurityUtils.subject.principal )
    }

    def isAnonymous() {
		def p = SecurityUtils.subject.principal
		return p == null || p == ANONYMOUS_NAME
    }
	
	def becomeAnonymous() {
		try {
			SecurityUtils.subject.login new UsernamePasswordToken(ANONYMOUS_NAME,ANONYMOUS_PASSWORD)
		} catch(AuthenticationException ex) {
			log.error "Не могу залогиниться как анонимус"
		}
	}
}
