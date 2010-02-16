package com.katlex.vitrina.goods;

import java.util.List;
import org.apache.shiro.SecurityUtils;
import com.katlex.vitrina.security.SecurityService;
import com.katlex.vitrina.domain.User;
import com.katlex.vitrina.domain.Role;
import com.katlex.vitrina.domain.Goods;
import com.katlex.vitrina.GoodsNavigationException;
import com.katlex.vitrina.domain.Vitrina;
import org.mortbay.log.Log;
import com.katlex.vitrina.goods.ListViewStateService;
public class GoodsListService implements IGoodsListService {
	def securityService
	static final String CURRENT_LIST_FIELD = "currentList"

		ListViewStateService listViewStateService
	
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
	    if (SecurityUtils.subject.hasRole(Role.REGISTERED.name)){
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
	    if (SecurityUtils.subject.hasRole(Role.VALIDATED.name)){
		
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
		if (SecurityUtils.subject.hasRole(Role.MODERATOR.name)){
				
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
            eq("owner",secutiryService.currentUser)
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
		if (SecurityUtils.subject.hasRole( Role.findByName("REGISTERED") )){
		myGoods = Goods.withCriteria{
			eq("owner",secutiryService.currentUser)
		}
		}
		if (SecurityUtils.subject.hasRole( Role.findByName("ANONYMOUS") )){
			throw new SecurityException("No have no permission to get this list")
		}
	}

	@Override
	public void saveList(String listName, Boolean force) {
	 def vitrina = Vitrina.findByName(listName)
	 if( vitrina && ! force ) throw new GoodsNavigationException("this name already exists")
	 else new Vitrina(name:listName, goods:GoodsListService.currentList).save()
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
