package com.katlex.vitrina.goods

import com.katlex.vitrina.domain.Goods;

class GoodsNavigationServiceOld {

	static scope = "session"

	
	private def _goodsList
	
	private def _currentGoodsId = -1
	
	Goods getCurrentGoods() {
		if( _currentGoodsId == -1 ) return null
		
		goodsList.find { it.id == _currentGoodsId }
	}

	void setCurrentGoods(Goods goods) {
		_currentGoodsId = goods.id
	}
	
	

	def setGoodsList(List<Goods> list) {
		_goodsList = list
	}
	
	
	def getGoodsList() {
		
		if( !_goodsList ) {
			_goodsList = Goods.list()
			// TODO : оставить товары которые принадлежат пользователям с ролью VALIDATED
			// и те, что не лежат в таблице UncheckedGoods
		}
		
		return _goodsList
	}
		

    def doStep(direction = null) {
		
		if( currentGoods ) {
			def lastIndex = goodsList.indexOf(currentGoods)
			lastIndex = lastIndex == -1 ? 0 : lastIndex
			log.debug "The cg is ${currentGoods}"
			switch( direction ) {
				
				case ~/forward|fwd|f/:
					lastIndex = ( lastIndex + 1 ) % goodsList.size()
                    log.debug "The lastindex is ${lastIndex}"
					break
				
				case ~/backward|bkwd|b/:
					lastIndex = ( lastIndex - 1 ) % goodsList.size()
					if( lastIndex < 0 ) lastIndex += goodsList.size()
                    log.debug "The lastindex is ${lastIndex}"
					break
			} 
			
			currentGoods = goodsList.get( lastIndex )
			
		} else {
			currentGoods = goodsList.get( 0 )
		}
    }
}
