package feedr

import feedr.fetcher.FetchStatus;

abstract class ItemMedia {
   String url
   String type
   String text
   byte[] bytes
   Integer size = 0

   FetchStatus fetchStatus

   static embedded = ['fetchStatus']

   static constraints = {
      url(blank: false)
      bytes(nullable: true, size: 0..5120000) // 5MB
      type(nullable: true)
      text(nullable: true)
      fetchStatus(nullable: true)
   }

   static mapping = {
      version false
   }
}
