
package com.katlex.vitrina.goods

import com.katlex.vitrina.domain.Role
import com.katlex.vitrina.domain.User
import com.katlex.vitrina.domain.Reason;
import com.katlex.vitrina.goods.GoodsNavigationService;
import org.apache.shiro.SecurityUtils;
import com.katlex.vitrina.security.SecurityService;
class GoodsHelpTagLib {   
	
	GoodsNavigationService goodsNavigationService
	def SecurityService
	
	def goodsOwner = { attrs, body ->
	 def current = goodsNavigationService.currentGoods

	 if (SecurityService.currentUser().equals(current.owner)) {
			out << body()

		}
	}
	def moderator = { attrs, body ->
		def current = goodsNavigationService.currentGoods
		
		if (SecurityUtils.subject.hasRole(Role.MODERATOR.name)) {
			
			out << body()
		
		}
	}
	def notAsserted ={ attrs, body ->
		def cu = goodsNavigationService.currentGoods	
     if(cu.asserted == false){
	 
	   if (!Reason.find{it.goods.name == cu.name}){
	   
	    
			out << 'товар в стадии проверки'
		}
	   else{
			out << "товар отклонен : ${Reason.reason}"
			}
	   }
	}
	
}
