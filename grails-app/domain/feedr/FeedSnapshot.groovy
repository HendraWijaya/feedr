package feedr

import java.util.Date;

class FeedSnapshot {
   Date publishedDate
   Date lastModified
   String eTag

   String content
   String size // in bytes
   Date dateCreated

   static belongsTo = [feed: Feed]

   static constraints = {
      publishedDate(nullable: true) // Some feed doesn't support publishedDate
      lastModified(nullable: true) // Some page doesn't support lastModified
      eTag(nullable: true) // Some page doesn't support eTag
      content(maxSize: 512000) // 500KB
   }
}
