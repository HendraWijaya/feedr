import javax.servlet.http.HttpServletRequest

import grails.util.Environment

import feedr.source.*

class BootStrap {

   def init = { servletContext ->
      HttpServletRequest.metaClass.isXhr = { ->
         'XMLHttpRequest' == delegate.getHeader('X-Requested-With')
      }

      switch (Environment.current) {
         case Environment.DEVELOPMENT:
            loadSources()

         // Startup the HSQLDB browser
         //org.hsqldb.util.DatabaseManagerSwing.main( ['--url', 'jdbc:hsqldb:mem:devDB'] as String[] )
            break
         case Environment.PRODUCTION:
            println "No special configuration required"
            break
      }
   }
   def destroy = {
   }

   void loadSources() {
      new TechCrunchSource().load()
      //new ReutersSource().load()
   }
}
