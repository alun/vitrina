
package com.katlex.grails.menu

import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject 

import com.katlex.vitrina.domain.Menu
import com.katlex.vitrina.domain.MenuItemType


class MenuTagLib {
	
	def menuViewabilityService
			
	def isItemViewable = { attrs, body ->
		
		Menu item = attrs.menuItem
		log.trace "Checking menu item ${item} for view ability"

		boolean viewPermitted =
			menuViewabilityService.checkViewable(item)
		
		log.debug "User ${SecurityUtils.subject.principal}. Menu ${item} view permitted ${viewPermitted}" 
		
		if( viewPermitted ) {
			out << body()
		}
	}
	
	def isItemCurrent = { attrs, body ->
		
		Menu item = attrs.menuItem
		
		if( isCurrentOrParentOfCurrent( item )  ){
			out << body()
		}
	}
	
	def isCurrentOrParentOfCurrent( Menu item ) {
		def currentItem = session.currentMenuItem
		return item == currentItem || isParentOfCurrent( item )
	}
	
	def isParentOfCurrent( Menu item ) {
		def currentItem = session.currentMenuItem
		return item.type == MenuItemType.SUBMENU && Menu.findAllByParent( item.id ).contains( currentItem )
	}
	
	def menuLink = { attrs, body ->
		
		def item = attrs.menuItem
		
		out << '<a href="'
		
		switch( item.type ) {
			case MenuItemType.SUBMENU:
				out << "?unfoldMenu=${item.id}"
			break;
			
			case MenuItemType.ACTION:
			
				def contollerAndAction = item.command.split( /\./ )
				def contoller = contollerAndAction[0]
				def action = contollerAndAction[1]
							
				out << createLink( controller:contoller ,action:action )
			break;
			
			case MenuItemType.URL:
			
				out << menu.command
			break;
		}
		
		out << '">'
		out << message( code: "menu.${item.path}" )
		out << '</a>'
	}
	
	def renderMenu = { attrs, body ->
		def list = attrs.list ?: Menu.findAllByParent( 0 )
		out << render( template:"/menu/menu",model:[ itemsList: list ])
	}
	
	def renderSubmenu = { attrs, body ->
		
		def item = attrs.menuItem
		def unfoldItem = session.additionalUnfoldMenu
		
		def needRender = isParentOfCurrent( item ) || unfoldItem == item
		
		if( needRender )
			out << renderMenu ( list: Menu.findAllByParent( item.id ) )
			
	}
}
