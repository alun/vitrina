package com.katlex.vitrina.goods;

/**
 * Сервис навигации по списку товаров
 */
interface IGoodsNavigationService {

 /**
  * Возвращает id текущего просматриваемого товара
  */
 Long getCurrentGoodsId();

 /**
  * Делает текущим товар с id == value.
  * Если такой товар с таким id отсутствует в списке,
  * то вызов возвращает ошибку и при этом текущий товар
  * остается прежним.
  */
 void setCurrentGoodsId(Long value);

 /**
  * Получение id следующего товара в списке
  */
 Long nextGoodsId(); 

 /**
  * Получение id предыдущего товара в списке
  */
 Long prevGoodsId();

 /**
  * Получение полного колличества товаров в списке
  */
 Long goodsTotal();
 /**
  * Получение номера позициции текущего товара в списке
  */
 Long goodsCurrentPosition();

 /**
  * Получение id товара, находящегося на указанной позиции в списке
  */
 Long goodsGetIdAt( Long pos );

 /**
  * Удаляет текущий товар, либо товар goodsId из списка товаров
  */
 void removeGoodsFromList(Long goodsId);
 
 Long removeCurrentGoodsFromList();
}

