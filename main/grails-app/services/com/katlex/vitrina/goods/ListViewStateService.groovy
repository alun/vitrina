

package com.katlex.vitrina.goods;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.mortbay.log.Log;
public class ListViewStateService {
	
	static final String CURRENT_GOODS_ID = "currentId"
	static final String CURRENT_LIST = "currentList"
		static final String MOD_LIST = "moderateList"

	Session getCurrentSession() {
		SecurityUtils.subject.session
	}

		def clearListFromDuplicates(List list) { 
			def duplicatesmap = [:]
			
			list.each { 
				elem ->  def elemDuplicates = [] 
				elemDuplicates << list.collect { anotherElem -> elem.id == anotherElem.id }  
				elemDuplicates -= [ elem ]  
				duplicatesmap << [ elem : elemDuplicates ] } 
			duplicatesmap.values.each { 
				duplicates ->  list.removeAll( duplicates ) }  
			return list
			}
	def getModerateList(){
		return currentSession.getAttribute(MOD_LIST);
	}
	def setModerateList(List list){
		return currentSession.setAttribute(MOD_LIST, list);
	}
	def getCurrentGoodsId() {
		return currentSession.getAttribute( CURRENT_GOODS_ID );
	}
	
	def setCurrentGoodsId(Long value) {
		
		return currentSession.setAttribute( CURRENT_GOODS_ID, value );
	}
	def getCurrentGoodsList() {
		return currentSession.getAttribute( CURRENT_LIST );
	}
	def setCurrentGoodsList(List list) {
		return currentSession.setAttribute( CURRENT_LIST, list);
	}	

	
}
