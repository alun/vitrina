import java.awt.Font
import java.awt.Color

import com.octo.captcha.service.multitype.GenericManageableCaptchaService
import com.octo.captcha.engine.GenericCaptchaEngine
import com.octo.captcha.image.gimpy.GimpyFactory
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator
import com.octo.captcha.component.image.color.SingleColorGenerator
import com.octo.captcha.component.image.textpaster.NonLinearTextPaster

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

//grails.config.locations = [ "classpath:SecurityConfig.groovy" ]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
import java.awt.Font
import java.awt.Color

import com.octo.captcha.service.multitype.GenericManageableCaptchaService
import com.octo.captcha.engine.GenericCaptchaEngine
import com.octo.captcha.image.gimpy.GimpyFactory
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator
import com.octo.captcha.component.image.color.SingleColorGenerator
import com.octo.captcha.component.image.textpaster.NonLinearTextPaster

grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// set per-environment serverURL stem for creating absolute links
environments {
	production {
		grails.serverURL = "http://www.changeme.com"
	}
	development {
		grails.serverURL = "http://localhost:8080/${appName}"
		com.katlex.vitrina.bootstrap.images.dir = "/images/goods"
	}
	test {
		grails.serverURL = "http://localhost:8080/${appName}"
		com.katlex.vitrina.bootstrap.images.dir = "/images/goods"
	}
	
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
        console name:'stderr', layout:pattern(conversionPattern: '%c %m%n'), target: "System.err"
    }

	root {
		debug 'stdout'
		additivity = true
	}	
	
	error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
	       'org.codehaus.groovy.grails.web.pages', //  GSP
	       'org.codehaus.groovy.grails.web.sitemesh', //  layouts
	       'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
	       'org.codehaus.groovy.grails.web.mapping', // URL mapping
	       'org.codehaus.groovy.grails.commons', // core / classloading
	       'org.codehaus.groovy.grails.plugins', // plugins
	       'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
	       'org.springframework',
	       'org.hibernate'

    warn   'org.mortbay.log'
	
	debug  'com.katlex.vitrina.goods',"grails-app.taglib"
	       

}


jcaptchas {
	image = new GenericManageableCaptchaService(
	new GenericCaptchaEngine(
	new GimpyFactory(
	new RandomWordGenerator(
	"abcdefghijklmnopqrstuvwxyz1234567890"
	),
	new ComposedWordToImage(
	new RandomFontGenerator(
	20, // min font size
	30, // max font size
	[new Font("Arial", 0, 10)] as Font[]
	),
	new GradientBackgroundGenerator(
	140, // width
	35, // height
	new SingleColorGenerator(new Color(0, 60, 0)),
	new SingleColorGenerator(new Color(20, 20, 20))
	),
	new NonLinearTextPaster(
	6, // minimal length of text
	6, // maximal length of text
	new Color(0, 255, 0)
	)
	)
	)
	),
	180, // minGuarantedStorageDelayInSeconds
	180000 // maxCaptchaStoreSize
	)
	
	
}
