package com.katlex.vitrina.domain

class UncheckedGoods {

    static constraints = {
		goods nullable:false
    }
	
	Goods goods;
}