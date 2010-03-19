package com.katlex.vitrina.goods;

import com.katlex.vitrina.domain.Goods;
import com.katlex.vitrina.goods.GoodsController
public class GoodsOperationsService implements IGoodsOperationsService{

	@Override
	public Goods saveGoods(Goods goods) {
	return goods.save()
	}

	@Override
	public void deleteGoods(Goods goods) {
		
		
	}




}
