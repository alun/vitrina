package com.katlex.vitrina.security

import com.octo.captcha.service.CaptchaServiceException;

import com.katlex.vitrina.domain.Role;


import com.katlex.vitrina.domain.User 
import org.apache.shiro.crypto.hash.Sha1Hash

class RegistrationController {
    
	def jcaptchaService
	static defaultAction = "create"
	
	// the delete, save and update actions only accept POST requests
	static allowedMethods = [save:'POST']

	def create = {
	}
	
	def save = {
		
		def user
		
		try {
			if( ! jcaptchaService.validateResponse("image", session.id, params.code) )
				throw new CaptchaServiceException()
			
			user = new User(
					username: params.username,
					passwordHash: new Sha1Hash( params.password ).toHex(),
					roles: [ Role.ANONYMOUS, Role.REGISTERED ] )
			
			if( !user.save( flush: true ) ) {
				throw new Exception()
			}

			// TODO сделать проверку пароля на качество и скрыть пароль
			// TODO проверка username на уникальность
			
			forward controller:'auth', action:'signIn', params:params
		} catch( CaptchaServiceException ex ) {
			flash.error = "Ivalid human check code"
			render view:'create'
		} catch( Exception ex ) {
			render view:'create',model:[user:user]
		}
	}
}
