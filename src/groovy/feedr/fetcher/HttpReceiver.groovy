/**
 * 
 */
package feedr.fetcher

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.ParserRegistry

import java.io.InputStream

import org.apache.http.HttpResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Hendra
 *
 */
abstract
class HttpReceiver {
   private static Logger logger = LoggerFactory.getLogger(HttpReceiver.class)

   protected InputStream getInputStream(HttpResponse response) {
      response.entity.content
   }

   protected byte[] getInputBytes(HttpResponse response) {
      getInputStream(response).bytes
   }

   // Content-Type: text/html; charset=UTF-8
   protected String getCharset(HttpResponse response) {
      ParserRegistry.getCharset(response)
   }

   protected String getContentType(HttpResponse response) {
      ParserRegistry.getContentType(response)
   }

   protected void onFailure(String url, HttpResponseDecorator response) {
      logger.error "Unable to get {}: {} - {}", url, response.status, response.statusLine.reasonPhrase
   }
}
