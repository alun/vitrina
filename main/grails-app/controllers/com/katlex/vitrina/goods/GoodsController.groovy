package com.katlex.vitrina.goods

import org.apache.shiro.SecurityUtils;
import org.hibernate.classic.Session;
import org.mortbay.log.Log;

import java.awt.image.renderable.RenderableImage;
import java.util.Set;
import com.katlex.vitrina.domain.Goods;
import com.katlex.vitrina.domain.User;
import com.sun.xml.internal.ws.handler.HandlerProcessor.Direction;

class GoodsController {
	
	def goodsNavigationService
	
	
    def index = { 
		redirect(action:"show")
	}
	def list = {
		params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
		[ GoodsInstanceList: Goods.list( params ), GoodsInstanceTotal: Goods.count() ]
	}
	def showMine = {
		if( ! goodsNavigationService.currentGoods ) {
			forward action:"next"}
		
		
		def ownedGoods = Goods.findAllByOwner(User.findByUsername( SecurityUtils.subject.principal) )
		log.debug "GOODS ==============================================${Goods.list().name}"
		
		log.debug "MINEGOODS ==============================================${ownedGoods}"
		goodsNavigationService.goodsList = ownedGoods
		
		redirect(action:show)
		
		
		
		
	}
		
	def show = {
		if( ! goodsNavigationService.currentGoods ) {
			forward action:"next"
		}
		
		[ goods : goodsNavigationService.currentGoods ]
	}
	
	def delete = {
		def goodsInstance = goodsNavigationService.currentGoods
		if(goodsInstance) {
			try {
				goodsInstance.delete(flush:true)
                log.debug "good ${params.id} deleted"
				redirect(action:show)
			}
			catch(org.springframework.dao.DataIntegrityViolationException e) {
				log.debug "this goood ${params.id} could not be deleted"
				redirect(action:show,id:params.id)
			}
		}
		else {
			log.debug "Good not found with id ${params.id}"
			redirect(action:show)
		}
	}
	
	def edit = {
		def GoodsInstance = goodsNavigationService.currentGoods
		
		if(!GoodsInstance) {
			flash.message = "Good not found with id ${params.id}"
			redirect(action:show)
		}
		else {
			return [ GoodsInstance : GoodsInstance ]
		}
	}
	
	def update = {
		def GoodsInstance = Goods.get( params.id )
		if(GoodsInstance) {
			if(params.version) {
				def version = params.version.toLong()
				if(GoodsInstance.version > version) {
					
					GoodsInstance.errors.rejectValue("version", "Goods.optimistic.locking.failure", "Another user has updated this Good while you were editing.")
					render(view:'edit',model:[GoodsInstance:GoodsInstance])
					return
				}
			}
			GoodsInstance.properties = params
			if(!GoodsInstance.hasErrors() && GoodsInstance.save()) {
				flash.message = "Good ${params.id} updated"
				redirect(action:show,id:GoodsInstance.id)
			}
			else {
				render(view:'edit',model:[RegistrationInstance:RegistrationInstance])
			}
		}
		else {
			flash.message = "Good not found with id ${params.id}"
			redirect(action:list)
		}
	}
	def next = {
		goodsNavigationService.doStep "forward"
		redirect action:"show"
	}
	
	def prev = {
		goodsNavigationService.doStep "backward"
		redirect action:"show"
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
		
		if( ! goods.save() ) {
			render ""
		} else {
			log.debug "The id is ${goods.id}"
			goodsNavigationService.currentGoods = goods
			redirect (action:show)
		}
	}
}
