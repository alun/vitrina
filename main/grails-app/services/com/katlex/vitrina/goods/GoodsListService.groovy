package com.katlex.vitrina.goods;

import java.util.List;
import org.apache.shiro.SecurityUtils;
import com.katlex.vitrina.security.SecurityService;
import com.katlex.vitrina.domain.User;
import com.katlex.vitrina.domain.Role;
import com.katlex.vitrina.domain.Goods;
import com.katlex.vitrina.domain.Reason;
import com.katlex.vitrina.GoodsNavigationException;
import com.katlex.vitrina.domain.Vitrina;
import org.mortbay.log.Log;
import com.katlex.vitrina.goods.GoodsNavigationService;
import com.katlex.vitrina.goods.ListViewStateService;
public class GoodsListService implements IGoodsListService {
	SecurityService securityService
	static final String CURRENT_LIST_FIELD = "currentList"
        GoodsNavigationService goodsNavigationService
		ListViewStateService listViewStateService
	public void unAssertedGoods(){
		def unAsserted = []
		Set denied
		if (SecurityUtils.subject.hasRole(Role.MODERATOR.name)){
			
			unAsserted = Goods.withCriteria {
				eq("asserted",false)

			}
			
			denied = Reason.list().goods.id as Set
			unAsserted = unAsserted.findAll { ! denied.contains (it.id) }
			
	
		}
		log.debug "=======================================${unAsserted}"
		
		listViewStateService.currentGoodsList = unAsserted
		listViewStateService.currentGoodsId = unAsserted[0].id
				
	}

	@Override
	
	public void allGoods() {
		def publicGoods = [] 
		

		
	    if (SecurityUtils.subject.hasRole(Role.ANONYMOUS.name)){
			publicGoods = Goods.withCriteria {
			 eq("asserted",true)
			 owner {
				 roles
				          {
					eq("name",Role.VALIDATED.name)
				          }
				}
			}
				
	   
		}
		
		listViewStateService.currentGoodsList = publicGoods
		listViewStateService.currentGoodsId = publicGoods[0].id 
	   }

	@Override
	public Boolean listNameExists(String listName) {
		
		return null;
	}

	@Override
	public List<String> listNames(String startWith, Boolean open) {
		def list=[] as Set
		def ownedList=[] as Set
		if(!open){
          ownedList = Vitrina.withCriteria {
		    like("name","${startWith}%")
            eq("owner",securityService.currentUser)
		}
		}
		else{
		  list = Vitrina.withCriteria {
			eq("opened","true")
			like("name","${startWith}%")
          }
		}
		return (list+ownedList).collect{it.name}
	}

	@Override
	public void loadList(String listName) {
	 def vitrina = Vitrina.findByName(listName)
	 if(!vitrina) throw new GoodsNavigationException("this list does not exists")
 	}

	@Override
	public void myGoods() {
		def cu = securityService.currentUser()
        def myGoods =[]
		if (SecurityUtils.subject.hasRole( Role.REGISTERED.name )){
		  myGoods = Goods.withCriteria{
			eq("owner",cu)
		}
		}
		else {
			throw new SecurityException("You have no permission to get this list")
		}
		listViewStateService.currentGoodsList = myGoods
		listViewStateService.currentGoodsId = myGoods[0].id
	}

	@Override
	public void saveList(String listName, Boolean force) {
	 def vitrina = Vitrina.findByName(listName)
	 if( vitrina && ! force ) throw new GoodsNavigationException("this name already exists")
	 else new Vitrina(name:listName, goods:listViewStateService.currentGoodsList).save()
	}

	@Override
	public void setListWorldOpen(String listName, Boolean open) {
	 def vitrina = GoodsListService.loadList(listName)
	 if(!open) vitrina.opened = false
	 else vitrina.opened = true
		
	
	}

	@Override
	public void unApprovedGoods() {
		if (SecurityUtils.subject.hasRole( Role.findByName("MODERATOR") )){	
		unApprovedGoods = Goods.withCriteria{
			eq("asserted",false)
			}
		}
		else
		{   
			throw new SecurityException("No have no permission to get this list")
		}
		
	}

	
}
