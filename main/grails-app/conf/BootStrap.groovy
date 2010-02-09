import java.util.Date;

import org.apache.shiro.crypto.hash.Sha1Hash;

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.codehaus.groovy.grails.commons.ApplicationHolder

import com.katlex.vitrina.domain.Goods;
import com.katlex.vitrina.domain.Menu;
import com.katlex.vitrina.domain.MenuItemType;
import com.katlex.vitrina.domain.Role;
import com.katlex.vitrina.domain.User;
import com.katlex.vitrina.security.Permission;
import com.katlex.vitrina.security.SecurityService;


class BootStrap {
    def init = { servletContext ->
		
		createRoles()
		createUsers()
		createMenus()

		def imgDirPath = servletContext.getAttribute('grailsApplication').config.
			com.katlex.vitrina.bootstrap.images.dir
		
		if( imgDirPath ) {
		
			
			def uri = servletContext.getResource(imgDirPath)?.toURI();
			if( uri ) {
				
				def dir = new File( uri )
				if( dir.isDirectory() ) {
					
					log.info("Папка с изображениями товара: $imgDirPath")
					
					def files = dir.listFiles().toList().grep { it.isFile() }
					
					def fileIdx = 0
					def totalCreated = 0
					def totalFiles = files.size()
					def user = User.findByUsername('alun')
					

					log.debug("Всего файлов $totalFiles")
					
					
					if( totalFiles > 0 ) {
						[
							[name:"Пылесос",description:"Производитель: Samsung",owner:User.findByUsername('alun')],
							[name:"ПК",description:"Pentium III",owner:User.findByUsername('tork01')],
							[name:"ПК",description:"Pentium II",owner:User.findByUsername('alun')],
							[name:"ПК",description:"Pentium I",owner:User.findByUsername('tork01')]
								
						].each {
							
							def file = files[fileIdx]
							
							def stream = new FileInputStream(file)
							def fileSize = stream.available()
							byte [] data = new byte[fileSize]
							
							stream.read( data, 0, fileSize )

							it.imageBytes = data
							it.imageType = servletContext.getMimeType(file.absolutePath)

							def goods = new Goods(it)
							
							goods.save()
							
							fileIdx = ( fileIdx + 1 ) % totalFiles
							totalCreated ++
							
						}

						log.debug( "В базу добавлено $totalCreated товаров" )
					}

					
				}
			}
			
		}
		
     }
     def destroy = {
     }
	
	def createUsers() {

    	def user

    	
		user = new User(username: "alun", passwordHash: new Sha1Hash("lex").toHex())
		[ Role.ANONYMOUS, Role.REGISTERED, Role.MODERATOR, Role.VALIDATED ].each {
    		user.addToRoles( it )
		}
		user.save()
		
		user = new User(username: "tork01", passwordHash: new Sha1Hash("gerodot").toHex())
		[ Role.ANONYMOUS, Role.REGISTERED, Role.MODERATOR, Role.VALIDATED ].each {
			user.addToRoles( it )
		}
		user.save()		
		
		// создает анонимного пользователя для гетерогенной среды распределения доступа
		
		user = new User(
				username: SecurityService.ANONYMOUS_NAME,
				passwordHash: new Sha1Hash(SecurityService.ANONYMOUS_PASSWORD).toHex()
		)
		user.addToPermissions( Permission.ANONYMOUS )
		user.addToRoles( Role.ANONYMOUS )
		user.save()		
	}

    def createRoles() {
		
		[ Role.ANONYMOUS, Role.REGISTERED, Role.VALIDATED, Role.MODERATOR ]
		  	.each { it.save() }
	}
	
	def createMenu( params ) {
		def menu = new Menu( params )
		
		if ( !menu.save() ) {
			def fields = ""
			menu.errors.allErrors.each {
				fields += it.field + ", "
			}
			log.debug "Menu ${menu.title} saving failure. Bad fields ${fields} "
			
		}
				
	}
	def createMenus() {
		
		createMenu( path: "goods",
			sequencer: 0,
			type: MenuItemType.SUBMENU,
		)
		
				
		createMenu( path: "goods.view",
				sequencer: 1,
				type: MenuItemType.ACTION,
				command: "goods.show",

		)
				
		createMenu( path: "mygoods",
				sequencer: 2,
				type: MenuItemType.SUBMENU,
		)
		
				
		createMenu( path: "mygoods.create",
				sequencer: 3,
				type: MenuItemType.ACTION,
				command:"goods.create"
		)
		
		createMenu( path: "mygoods.showMine",
                sequencer: 3,
                type: MenuItemType.ACTION,
                command:"goods.showMine"
		)		
				
		createMenu( path: "registration",
				sequencer: 4,
				type: MenuItemType.ACTION,
				command:"registration.create",
				permissions: [Permission.ANONYMOUS]
		)
						
		createMenu( path: "login",
				sequencer: 5,
				type: MenuItemType.ACTION,
				command:"auth.login",
				permissions: [Permission.ANONYMOUS]
		)
						
		createMenu( path: "logout",
				sequencer: 6,
				type: MenuItemType.ACTION,
				command:"auth.signOut",
				permissions: [Permission.NOT_ANONYMOUS]
		)
				

		
																
	}
		
} 