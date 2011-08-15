package feedr

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.sun.syndication.feed.synd.SyndEntry
import com.sun.syndication.feed.synd.SyndFeed

import feedr.fetcher.SyndFeedReceiver

class ItemService implements SyndFeedReceiver {
    private static Logger logger = LoggerFactory.getLogger(ItemService.class)
    
    void onReceiveSyndFeed(Feed feed, SyndFeed syndFeed) {
        syndFeed?.entries.collect { SyndEntry syndEntry ->
            processSyndEntry(feed, syndEntry)
        }
    }

    private void processSyndEntry(Feed feed, SyndEntry syndEntry) {
        Item.withTransaction {
            if(Item.findByLink(syndEntry.link)) {
                logger.debug "Found and going to ignore feed item: {}", syndEntry.link
            } else {
                logger.trace "New synd entry:\n${}", syndEntry
                
                Item item = ItemFactory.build(feed, syndEntry)
                
                logger.debug "Saving new feed item:\n{}", item
                
                if(!item.save()) {
                    logger.error "Unable to save the entry:\n{}", item.errors
                }
            }
        }
    }
}
