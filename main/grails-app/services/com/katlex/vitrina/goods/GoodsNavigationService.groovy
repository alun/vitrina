package com.katlex.vitrina.goods;
import com.katlex.vitrina.GoodsNavigationException;
import com.katlex.vitrina.domain.User;
import com.katlex.vitrina.domain.Goods;
import com.katlex.vitrina.goods.ListViewStateService;
import org.apache.shiro.SecurityUtils;

public class GoodsNavigationService implements IGoodsNavigationService {

	ListViewStateService listViewStateService	
		
	
	
    def setCurrentGoods(Long id){
		return listViewStateService.currentGoodsList?.find { it.id == id}
	}
	
	def getCurrentGoods(){
        return listViewStateService.currentGoodsList?.find { it.id == currentGoodsId}		
	}
	@Override
	public Long getCurrentGoodsId() {
		return listViewStateService.currentGoodsId
	}

	@Override
	public void setCurrentGoodsId(Long value) {
		
		if(listViewStateService.currentGoodsList.find { it.id == value})
		 listViewStateService.currentGoodsId=value
		
	
		
	}

	@Override
	public Long goodsCurrentPosition() {
		def list = listViewStateService.currentGoodsList
        def selectedGood = list.find{it.id == currentGoodsId}
		def lastIndex
		lastIndex = list.indexOf(selectedGood)
    		return lastIndex
	
			
		
		
	}

	@Override
	public Long goodsGetIdAt(Long pos) {
		def list = listViewStateService.currentGoodsList
		return list.find{list.indexOf(it) == pos}.id
		
	}

	@Override
	public Long goodsTotal() {
		def list = listViewStateService.currentGoodsList
		if (list==null) return list.size(0) 

        return list.size()
		
		
	}

	@Override
	public Long nextGoodsId() {
		if (goodsCurrentPosition() >= goodsTotal()) throw new GoodsNavigationException("No next goods")
		def index = ( goodsCurrentPosition() + 1 ) % goodsTotal()
        setCurrentGoodsId(goodsGetIdAt(index))
        return goodsGetIdAt(index)
		
	}

	@Override
	public Long prevGoodsId() {
		def index
		if (goodsCurrentPosition() < 0) throw new GoodsNavigationException("No prev goods")
			
		index = ( goodsCurrentPosition() - 1 ) % goodsTotal()
		if(!(index <= 0)){
        setCurrentGoodsId(goodsGetIdAt(index))
		return goodsGetIdAt(index)	
		}
		else{
			setCurrentGoodsId(goodsGetIdAt(0))
			return goodsGetIdAt(0)
           
		}
	}

	@Override
	public Long removeCurrentGoodsFromList() {
		
		
		if(goodsCurrentPosition()){
             
	         listViewStateService.currentGoodsList.remove(currentGoods)
		}	
	    else{
			throw new GoodsNavigationException("Goods not selected")
		}
		return null
	}

	@Override
	public void removeGoodsFromList(Long goodsId) {
		
		
		if(currentGoods.find{it.id == goodsId}) {
			
			currentGoods.remove(currentGoods)
		}	
		
		else{
			throw new GoodsNavigationException("Goods not selected")
		}
		

		
		
	}

}
