package feedr.fetcher

import org.apache.http.impl.cookie.DateUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import feedr.Feed
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.Method

class HttpFeedFetcher extends HttpFetcher implements FeedFetcher {
    private static Logger logger = LoggerFactory.getLogger(HttpFeedFetcher.class)
    
    HttpFeedReceiver httpFeedReceiver
  
	@Transactional
	void retrieve(Feed feed) {
		// Load the feed from the persistent context
		feed = Feed.load(feed.id)
		
		Thread t = Thread.currentThread()
		t.setName(feed.url)
		
		logger.debug "Start fetching feed: {}", feed.url
		
		try {
			retrieveByHttp(feed)
		} catch (Exception e) {
			logger.error e.toString(), e
		}
		
		logger.debug "Finish fetching feed: {}", feed.url
	}
	
    void retrieveByHttp(Feed feed) {
        def http = new HTTPBuilder( feed.url )
        http.request(Method.GET, ContentType.XML) { req ->
  
          headers.'User-Agent' = 'Mozilla/5.0'
        
          // Setting up for conditional get with last modified and etag to get only
          // if it has been changed
          if(feed.lastModified) {
              // If the requested feed has not been modified since the time specified in this field,
              // the feed will not be returned from the server; instead, a 304 (not modified) response
              // will be returned without any message-body.
              headers.'If-Modified-Since' = DateUtils.formatDate(feed.lastModified)
          }
          
          if(feed.eTag) {
               headers.'If-None-Match' = feed.eTag
          }
          
          response.'200' = { resp ->
              onHttp200(feed, resp)
          }
           
          // Not modified for web supporting conditional get
          response.'304' = { resp ->
              // The feed has not changed
              onHttp304(feed, resp)
          }
          
          response.failure = { resp ->
              onFailure(feed.url, resp)
          }
        }
    }
    
    private void onHttp200(Feed feed, HttpResponseDecorator response) {
        httpFeedReceiver.onHttp200(feed, response)
    }
    
    /*
     * 304 means there is no change from the server. We get the feed if this is a new feed. Otherwise,
     * we ignore it because it has not changed since the last fetched.
     */
    private void onHttp304(Feed feed, HttpResponseDecorator response) {
        httpFeedReceiver.onHttp304(feed, response)
    }
}
