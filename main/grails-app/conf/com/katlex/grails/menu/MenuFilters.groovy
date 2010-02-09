package com.katlex.grails.menu

import com.katlex.vitrina.domain.Menu 

public class MenuFilters {
	
	def filters = {
		all(uri: "/**") {
			before = {
				
				def currentMenuItem = null
				def additionalUnfoldMenu = null
								
				if( controllerName && actionName )
					currentMenuItem = Menu.findByCommand( "${controllerName}.${actionName}" )
				
				if( currentMenuItem ) {
					log.debug "Current menu detemoined from target action: {currentMenuItem.path}"
				} 
				
				def unfoldId = params.unfoldMenu
				if( unfoldId ) {
					additionalUnfoldMenu = Menu.get( unfoldId )

					// Cancel request if unfoldMenu is bad
					if( ! additionalUnfoldMenu ) return false
					
					log.debug "Additional unfold menu: ${additionalUnfoldMenu.path}"
				}
				
				session.currentMenuItem = currentMenuItem
				session.additionalUnfoldMenu = additionalUnfoldMenu
								
			}
			
		}
	}
}
