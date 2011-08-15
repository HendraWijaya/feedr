// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
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

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// log4j
def log4jConsoleLogLevel = org.apache.log4j.Level.WARN
def log4jFileLogLevel = org.apache.log4j.Level.INFO
def log4jLayoutPattern = new org.apache.log4j.PatternLayout("%d [%t] %-5p %c - %m%n")
def log4jLogsDirectory = './logs'

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
        
        // log4j
        log4jConsoleLogLevel = org.apache.log4j.Level.DEBUG
        log4jFileLogLevel = org.apache.log4j.Level.DEBUG
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    appenders {
        console(
			name: "stdout", 
			threshold: log4jConsoleLogLevel, 
			layout: log4jLayoutPattern)
			// Making sure it doesn't output any level above info such as warn and error
			// because stderr will handle those levels. 
			.addFilter(
				new org.apache.log4j.varia.LevelRangeFilter(
					levelMin: org.apache.log4j.Level.DEBUG, 
					levelMax: org.apache.log4j.Level.INFO))
		console(
			name: "stderr", 
			threshold: org.apache.log4j.Level.WARN, 
			layout: log4jLayoutPattern, 
			target: "System.err")
			.addFilter(
				new org.apache.log4j.varia.LevelRangeFilter(
					levelMin: org.apache.log4j.Level.WARN,
					levelMax: org.apache.log4j.Level.FATAL))
        rollingFile(
			name:"file", 
			file: "${log4jLogsDirectory}/${appName}.log", 
			threshold: log4jFileLogLevel, 
			maxFileSize:"1MB", 
			maxBackupIndex: 10, 
			layout: log4jLayoutPattern, 
			'append': true)
        rollingFile(
			name:"error", 
			file: "${log4jLogsDirectory}/${appName}-error.log", 
			threshold: org.apache.log4j.Level.ERROR, 
			maxFileSize:"1MB", 
			maxBackupIndex: 10, 
			layout: log4jLayoutPattern, 
			'append':true)
        rollingFile(
			name: "stacktrace", 
			file: "${log4jLogsDirectory}/${appName}-stacktrace.log",
			maxFileSize: "1MB", 
			maxBackupIndex: 10)
    }
    
    error  'org.grails.plugin', // Resource plugin
           'grails.app.service.org.grails.plugin.resource', // Resource plugin
           'org.codehaus',
           'org.apache',
           'org.springframework',
           'org.hibernate',
           'org.quartz',
           'net.sf',
           'feedr',
           'feedr.fetcher.SnapshotSyndFeedReceiver'
           
    warn   'org.mortbay.log',
           'grails.spring'
    
    debug  'feedr.fetcher',
           // HttpBuilder
           'org.apache.http.headers'
           //'org.apache.http.wire'
    
    root {
        debug 'stdout', 'stderr', 'file', 'error'
        additivity = true
    }
}
