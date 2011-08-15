package feedr.fetcher

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import feedr.Item

/**
 * @author Hendra
 *
 */
class ItemFetcher {
    private static Logger logger = LoggerFactory.getLogger(ItemFetcher.class);
    
    ItemImageFetcher imageFetcher
    ItemPageFetcher pageFetcher
 
	void run() {
		try {
			imageFetcher.run()
		    pageFetcher.run()
		} catch (Exception e) {
			logger.error e.toString(), e
		}
	}
	
//	@Transactional
//    void retrieve(Item item) {
//		Thread t = Thread.currentThread()
//		t.setName(item.url)
//		
//		logger.debug "Start fetching item: {}", item.url
//		try {
//			imageFetcher.retrieve(item.images)
//			pageFetcher.retrieve(item.page)
//		} catch (Exception e) {
//			logger.error e.toString(), e
//		}
//		
//		logger.debug "Finish fetching item: {}", item.url
//	}
}
