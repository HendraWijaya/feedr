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
}
