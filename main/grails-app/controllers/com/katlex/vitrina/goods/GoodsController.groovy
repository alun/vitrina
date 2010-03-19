package com.katlex.vitrina.goods

import org.apache.shiro.SecurityUtils;
import org.hibernate.classic.Session;
import org.mortbay.log.Log;
import com.katlex.vitrina.goods.GoodsOperationsService;
import java.awt.image.renderable.RenderableImage;
import java.util.Set;
import com.katlex.vitrina.domain.Goods;
import com.katlex.vitrina.domain.Reason;
import com.katlex.vitrina.goods.GoodsNavigationService;
import com.katlex.vitrina.goods.GoodsListService;
import com.katlex.vitrina.domain.User;
import com.sun.xml.internal.ws.handler.HandlerProcessor.Direction;

class GoodsController {
	GoodsOperationsService goodsOperationsService
	GoodsNavigationService goodsNavigationService
	GoodsListService goodsListService
	
    def index = { 
		
		redirect(action:"show")
	}
	def list = {
		params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
		[ GoodsInstanceList: Goods.list( params ), GoodsInstanceTotal: Goods.count() ]
	}
	def showMine = {
		goodsListService.myGoods()
		redirect action:"show"
		
		
	

	}
	
	def assertGoods = {
			def goods = goodsNavigationService.currentGoods
			goods.asserted = true
			goods.save()
		    goodsListService.unAssertedGoods()
			log.debug ""
		    redirect action:"show"		    
		}
	def unAssertGoods ={
		def reas = new Reason() 
		   
		    reas.reason = params.reason
			log.debug "tttttttttttttttttttttttttttttt${params.reason}"
		    reas.goods = goodsNavigationService.currentGoods
			reas.goods.id = goodsNavigationService.currentGoodsId
			
			log.debug "IYIAWETVIUAEYTIUAYVTIAVBIATV${reas.goods}"
			reas.save()


		
			
			
			redirect action:"show"			    
			
		}
	
	
	def moderate ={
		goodsListService.unAssertedGoods()
		
		redirect action:"show"
	}
    def showAllGoods ={
		goodsListService.allGoods()
		redirect action:"show"
	}
	def show = {

		
		goodsNavigationService.goodsCurrentPosition()
		def cu = goodsNavigationService.currentGoods
        
	
		[ goods : cu ]
	}
	
	def delete = {
		goodsNavigationService.removeCurrentGoodsFromList()
        redirect(action:"show", id:goodsNavigationService.nextGoodsId())

	}
	
	def edit = {
		def goods = goodsNavigationService.currentGoods
		
		if(!goods) {
			flash.message = "Good not found with id ${params.id}"
			redirect(action:show)
		}
		else {
			return [ GoodsInstance : goods ]
		}
	}
	
	def update = {
		def goods = Goods.get( params.id )
		
		if(goods) {
			if(params.version) {
				def version = params.version.toLong()
				if(goods.version > version) {
					
					goods.errors.rejectValue("version", "Goods.optimistic.locking.failure", "Another user has updated this Good while you were editing.")
					render(view:'edit',model:[GoodsInstance:goods])
					
					return
				}
			}
			goods.properties = params
			if(!goods.hasErrors() && goodsOperationsService.saveGoods(goods)) {
				flash.message = "Good ${params.id} updated"
				goods.asserted = false
				redirect(action:show,id:goods.id)
			}
			else {
				render(view:'edit',model:[GoodsInstance:goods])
			}
		}
		else {
			flash.message = "Good not found with id ${params.id}"
			redirect(action:list)
		}
	}
	def next = {
		
		redirect(action:"show", id:goodsNavigationService.nextGoodsId())
	}
	
	def prev = {
		
		redirect(action:"show", id:goodsNavigationService.prevGoodsId() )
	}
	
	def image = {
		
		def goods = Goods.get( params.id )

		if( goods ) { 
		
			response.setContentType( goods.imageType )
			response.setContentLength( goods.imageBytes.size() )

			def out = response.outputStream
			out << goods.imageBytes
			out.close()
			
		} else {
			response.sendError(404)
		}
		
	}
	def create = {
		def goods = new Goods()
		goods.properties = params
		return ['goods':goods]
		
	}
	
	def save = {
		//def multiPartFile = request.getFile('file')
		//def stream = multiPartFile.inputStream
	//	def fileSize = stream.available()
	//	byte [] data = new byte[fileSize]
		
	//	stream.read( data, 0, fileSize )
		def uri = servletContext.getResource("/images/goods/vc.jpg")?.toURI();
		def file = new File( uri )
		def stream = new FileInputStream(file)
		
		def fileSize = stream.available()
		byte [] data = new byte[fileSize]
		stream.read( data, 0, fileSize )


		
//		log.debug "The class is ${currentuser.class.name}"
//		log.debug "Authenticdated ${SecurityUtils.subject.authenticated}"
	    def goods = new Goods(
		                      name: params.name,
		                      description: params.description,
		                      owner: User.findByUsername( SecurityUtils.subject.principal ),
		                      imageBytes: data,
		                      imageType: "image/jpeg"
		                      )



        goodsOperationsService.saveGoods(goods)
        
		if( !goodsOperationsService.saveGoods(goods)) {
			render ""
		} else {
			log.debug "The id is ${goods.id}"
			GoodsNavigationService.currentGoods = goods
			redirect (action:show)
		}
	}
}
