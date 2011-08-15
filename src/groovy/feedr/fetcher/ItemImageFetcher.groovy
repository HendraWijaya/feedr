package feedr.fetcher

// It seems there is conflict if we put this in BuildConfig.grooy
@Grab(group='org.codehaus.gpars', module='gpars', version='0.11')
import static groovyx.gpars.GParsPool.withPool

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import feedr.ItemImage
import feedr.ItemMedia
import groovyx.net.http.HttpResponseDecorator

class ItemImageFetcher extends ItemMediaFetcher {
   private static Logger logger = LoggerFactory.getLogger(ItemImageFetcher.class)

   /*
    * This method attempts to retrieve all images that have not been retrieved yet.
    * At the moment, it will keep retrying in the next cycle if the current fetch fails.
    *
    * TODO: Add a retry feature to handle HTTP failure
    */
   void run() {
      def images

      ItemImage.withTransaction {
         images = ItemImage.findAllByFetchStatusIsNull()
      }

      withPool {
         images.eachParallel { ItemImage image ->
            // New thread here so no session bound here
            ItemImage.withTransaction {
               // Reload the image first to bound it to the hibernate session
               retrieve(image.load(image.id))
            }
         }
      }
   }

   protected void updateStatus(long start, ItemMedia media, HttpResponseDecorator response) {
      super.updateStatus(start, media, response)

      def image = media as ItemImage
      if(!image.save()) {
         logger.error "Unable to save the item image:\n{}", image.errors
      }
   }
}
