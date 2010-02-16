
package com.katlex.vitrina.goods
import org.mortbay.log.Log;


import com.katlex.vitrina.goods.GoodsNavigationService;
class GoodsHelpTagLib {   
	
	GoodsNavigationService goodsNavigationService
	def SecurityService
	
	def goodsOwner = { attrs, body ->
	 def current = goodsNavigationService.currentGoods

	 if (SecurityService.currentUser().equals(current.owner)) {
			out << body()

		}
	}
	
}
