package feedr.fetcher

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.ParserRegistry

import java.io.InputStream

import org.apache.http.HttpResponse

abstract
class HttpFetcher extends HttpReceiver {

   protected FetchStatus getFetchStatus(long start, HttpResponseDecorator response) {
      new FetchStatus(
            code: response.status,
            description: response.statusLine?.reasonPhrase,
            time: System.currentTimeMillis() - start
      )
   }
}
