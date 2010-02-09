package com.katlex.vitrina.goods

class GoodsHelpTagLib {   
	
	def SecurityService
	
	def goodsOwner = { attrs, body ->
		
		if (attrs.goods?.owner ==  SecurityService.currentUser() ) {
			out << body()
		}
	}
	
}
