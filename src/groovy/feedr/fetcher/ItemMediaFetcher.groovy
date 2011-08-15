package feedr.fetcher

import java.util.Set

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import feedr.ItemMedia
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.Method

abstract
class ItemMediaFetcher extends HttpFetcher {
    private static Logger logger = LoggerFactory.getLogger(ItemMediaFetcher.class)
	
    void retrieve(Set<? extends ItemMedia> medias) {
        for(ItemMedia media : medias) {
            retrieve(media)
        }
    }
    
	/*
	 * Assuming that 'media' is bound to a hibernate session
	 */
    void retrieve(ItemMedia media) {
		// Set the thread name for logging purposes
		Thread t = Thread.currentThread()
		t.setName(media.url)
		
        long start = System.currentTimeMillis()
        
        new HTTPBuilder(media.url).request(Method.GET, ContentType.BINARY) { req ->
            headers.'User-Agent' = 'Mozilla/5.0'
            response.success = { resp ->
				onHttp200(start, media, resp)
            }

            // handler for any failure
            response.failure = { resp ->
				onFailure(start, media, resp)
            }
        }
    }
   
    private void onHttp200(long start, ItemMedia media, HttpResponseDecorator response) {
		media.type = response.headers.'Content-Type'
		media.bytes = getInputStream(response).bytes
		if(response.headers.'Content-Length') {
			media.size = response.headers.'Content-Length'.toInteger()
		} else {
			media.size = media.bytes.length
		}
		
		updateStatus(start, media, response)
    }
	
	private void onFailure(long start, ItemMedia media, HttpResponseDecorator response) {
		updateStatus(start, media, response)
		onFailure(media.url, response)
	}

	protected void updateStatus(long start, ItemMedia media, HttpResponseDecorator response) {
		media.fetchStatus = getFetchStatus(start, response)
	}
}
