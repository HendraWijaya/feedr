package feedr.fetcher

import org.apache.http.HttpResponse;

import java.io.InputStream

import org.apache.http.HttpResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.sun.syndication.feed.synd.SyndFeed
import com.sun.syndication.io.SyndFeedInput
import com.sun.syndication.io.XmlReader

import feedr.Feed
import feedr.rome.SyndFeedUtils
import groovyx.net.http.HttpResponseDecorator

/**
 * @author Hendra
 *
 */
class HttpSyndFeedReceiver extends HttpFeedReceiver {
    private static Logger logger = LoggerFactory.getLogger(HttpSyndFeedReceiver.class)
    
    SyndFeedReceiver syndFeedReceiver
    
    void onHttpResponse(Feed feed, HttpResponseDecorator response) {
        byte[] bytes = getInputBytes(response)
        
        SyndFeed syndFeed = createSyndFeed(response, bytes)
        
        // In addition to checking for conditional HTTP get, we also check for the published date
        if (isSyndFeedUpdated(feed, syndFeed)) {
            logger.debug "New published date {} for feed {}", syndFeed.publishedDate, feed.url
            onSyndFeedUpdated(feed, syndFeed, response, bytes)
        }
        
        updateFeedStatus(feed, response, syndFeed.publishedDate)
    }
    
    void onHttp200(Feed feed, HttpResponseDecorator response) {
        logger.debug "HTTP 200 - New update from the server for feed {}", feed.url
        onHttpResponse(feed, response)
    }
    
    /*
     * 304 means there is no change from the server. We get the feed if this is a new feed. Otherwise,
     * we ignore it because it has not changed since the last fetched.
     */
    void onHttp304(Feed feed, HttpResponseDecorator response) {
        if(isNewFeed(feed)) {
            logger.debug "HTTP 304 - It's a new feed so fetch it regardless of 304 {}", feed.url
            onHttpResponse(feed, response)
        } else {
            logger.debug "HTTP 304 - No update from the server for feed {}", feed.url
            feed.lastChecked = new Date()
			feed.save()
        }
    }
    
	/*
	 * Sometimes syndFeed.publishedDate is null because it's not available in the 
	 * original RSS feed. If it's not null then use this date to determine whether
	 * the feed is newly published or updated. Otherwise, if it's null, then we need
	 * to 'emulate' the published date by picking the latest date in an entry.
	 */
	private boolean isSyndFeedUpdated(Feed feed, SyndFeed syndFeed) {
		if(syndFeed.publishedDate) {
			isNewPublishedDate(feed, syndFeed.publishedDate)
		} else {
			
			syndFeed.publishedDate = SyndFeedUtils.getLatestEntryDate(syndFeed)
			
			if(syndFeed.publishedDate) {
				logger.debug "Feed {} has no published date. Gonna use latest date from entries {}", feed.url, syndFeed.publishedDate
				isNewPublishedDate(feed, syndFeed.publishedDate)
			} else {
				logger.debug "Feed {} has no published date so we're gonna ignore it", feed.url
				// Assume it's updated if there's no way to tell
				false
			}
		}
	}
	
	private SyndFeed createSyndFeed(HttpResponseDecorator response, byte[] bytes) {
		String contentType = response.headers.'Content-Type'
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes)
		XmlReader xmlReader = null
		if (contentType) {
			xmlReader = new XmlReader(byteStream, contentType, true);
		} else {
			xmlReader = new XmlReader(byteStream, true);
		}
		
		return new SyndFeedInput().build(xmlReader);
	}
	
    private void onSyndFeedUpdated(Feed feed, SyndFeed syndFeed, HttpResponseDecorator response, byte[] bytes) {
        syndFeedReceiver.onReceiveSyndFeed(feed, syndFeed)
        takeSnapshot(feed, response, bytes, syndFeed.publishedDate)
    }
}
