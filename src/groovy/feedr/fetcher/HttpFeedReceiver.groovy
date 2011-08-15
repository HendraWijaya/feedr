/**
 * 
 */
package feedr.fetcher

import java.util.Date

import org.apache.http.HttpResponse
import org.apache.http.impl.cookie.DateUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import feedr.Feed
import feedr.FeedSnapshot
import groovyx.net.http.HttpResponseDecorator

/**
 * @author Hendra
 *
 */
abstract 
class HttpFeedReceiver extends HttpReceiver {
    private static Logger logger = LoggerFactory.getLogger(HttpFeedReceiver.class)
    
    protected String createRawFeed(HttpResponse response, byte[] bytes) {
        new String(bytes, getCharset(response))
    }
    
    protected void updateFeedStatus(Feed feed, HttpResponseDecorator response, Date publishedDate) {
        if(response.headers.'Last-Modified') {
            feed.lastModified = DateUtils.parseDate(response.headers.'Last-Modified')
        } else if (response.headers.'Etag') {
            feed.eTag = response.headers.'Etag'
        }
        
        feed.publishedDate = publishedDate
        feed.lastChecked = new Date()
        feed.save()
    }
    
    protected boolean isNewFeed(Feed feed) {
        if(!feed.lastModified) {
            return true
        }
    }
    
    protected boolean isNewPublishedDate(Feed feed, Date publishedDate) {
        !feed.publishedDate || publishedDate.after(feed.publishedDate)
    }
   
    protected void takeSnapshot(Feed feed, HttpResponseDecorator response, byte[] feedBytes, Date publishedDate) {
        logger.debug "Taking new snapshot {} for feed {}", publishedDate, feed.url
                
        FeedSnapshot snapshot =
            new FeedSnapshot(
                lastModified: response.headers.'Last-Modified',
                eTag: response.headers.'Etag',
                publishedDate: publishedDate,
                content: createRawFeed(response, feedBytes),
                size: feedBytes.length,
                feed: feed
            )
          
		if(!snapshot.save()) {
			logger.error "Unable to save the feed snapshot:\n{}", snapshot.errors
		}
    }
	
	abstract
	void onHttpResponse(Feed feed, HttpResponseDecorator response)
	
	abstract
	void onHttp304(Feed feed, HttpResponseDecorator response)
	
	abstract
	void onHttp200(Feed feed, HttpResponseDecorator response)
}
