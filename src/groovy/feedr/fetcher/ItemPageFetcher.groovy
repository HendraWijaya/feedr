package feedr.fetcher

// It seems there is conflict if we put this in BuildConfig.grooy
@Grab(group='org.codehaus.gpars', module='gpars', version='0.11')
import static groovyx.gpars.GParsPool.withPool

import org.apache.http.impl.cookie.DateUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import feedr.ItemPage
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.Method

class ItemPageFetcher extends HttpFetcher {
    private static Logger logger = LoggerFactory.getLogger(ItemPageFetcher.class)
    
   /*
	* This method attempts to retrieve all pages that have not been retrieved yet.
	* At the moment, it will keep retrying in the next cycle if the current fetch fails.
	*
	* TODO: Add a retry feature to handle HTTP failure
	*/
	void run() {
		def pages

		ItemPage.withTransaction {
			pages = ItemPage.findAllByFetchStatusIsNull()
		}

		withPool {
			pages.eachParallel { ItemPage page ->
				
				// New thread here so no session bound here
				ItemPage.withTransaction {
					retrieve(page.load(page.id))
				}
			}
		}
	}
	
	/*
	 * Assuming 'page' is bound to a hibernate session
	 */
    void retrieve(ItemPage page) {
		// Set the thread name for logging purposes
		Thread t = Thread.currentThread()
		t.setName(page.url)
		
		long start = System.currentTimeMillis()
		
		new HTTPBuilder(page.url).request(Method.GET) { req ->
			response.success = { resp ->
				onHttp200(start, page, resp)
			}
	
			response.failure = {resp ->
				onFailure(start, page, resp)
			}
		}
    }
    
    private void onHttp200(long start, ItemPage page, HttpResponseDecorator response) {
        byte[] bytes = getInputBytes(response)
        String charset = getCharset(response)

        page.type = getContentType(response)
        page.charset = charset
		page.size = bytes.length
		page.content = new String(bytes, charset)
		if(response.headers.'Last-Modified') {
			page.lastModified = DateUtils.parseDate(response.headers.'Last-Modified')
		}
          
	    updateStatus(start, page, response)
    }
	
	private void onFailure(long start, ItemPage page, HttpResponseDecorator response) {
		updateStatus(start, page, response)
		onFailure(page.url, response)
	}
	
	private void updateStatus(long start, ItemPage page, HttpResponseDecorator response) {
		page.fetchStatus = getFetchStatus(start, response)
		
		if(!page.save()) {
			logger.error "Unable to save the page:\n{}", page.errors
		}
	}
}
