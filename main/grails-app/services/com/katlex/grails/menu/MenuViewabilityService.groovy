

package com.katlex.grails.menu;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.katlex.vitrina.domain.Menu;
import com.katlex.vitrina.domain.MenuItemType;


class MenuViewabilityService {

	static transactional = true
	
	def checkViewable( Menu item ) {
		
		def user = SecurityUtils.subject
		
		switch( item.type ) {
			case MenuItemType.ACTION:
				def actionPermission = item.command.replace(".", ":")
				def result = user.isPermitted( actionPermission )
				
				if( item.permissions ) {
					result &= user.isPermittedAll( item.permissions.toArray(new String[0]) )
				}
				
				return result
			case MenuItemType.SUBMENU:
				return Menu.findByParent( item.id ).any { checkViewable it }
			case MenuItemType.URL:
				return true
		}
		
		return false
	}
}
