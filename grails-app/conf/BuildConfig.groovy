grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
   // inherit Grails' default dependencies
   inherits("global") {
      // uncomment to disable ehcache
      // excludes 'ehcache'
   }
   log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
   repositories {
      grailsPlugins()
      grailsHome()
      grailsCentral()

      // uncomment the below to enable remote dependency resolution
      // from public Maven repositories
      //mavenLocal()
      mavenCentral()
      //mavenRepo "http://snapshots.repository.codehaus.org"
      mavenRepo "http://repository.codehaus.org"
      mavenRepo "http://download.java.net/maven/2/"
      //mavenRepo "http://repository.jboss.com/maven2/"
   }
   dependencies {
      // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

      // runtime 'mysql:mysql-connector-java:5.1.13'

      //build 'org.rometools:rome-fetcher:1.2'
      build 'rome:rome:1.0'
      build 'org.rometools:rome-modules:1.0'
      build ('org.codehaus.groovy.modules.http-builder:http-builder:0.5.1') { excludes 'xml-apis', 'xercesImpl' }

      runtime 'quartz:quartz:1.5.2'
      //runtime 'postgresql:postgresql:9.0-801.jdbc4'
   }

   plugins {
      runtime ":pretty-time:0.3"

      compile ":jquery:1.6.1.1"
      compile ":resources:1.0"
      compile ":spock:0.5-groovy-1.7"
   }
}
