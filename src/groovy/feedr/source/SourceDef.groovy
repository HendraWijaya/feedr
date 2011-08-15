package feedr.source

import java.util.List;
import java.util.Map;

import feedr.Source;

abstract class SourceDef {
   void load() {
      def properties = sourceDef()

      Source source = Source.findByName(properties.name)

      if(!source) {
         source = new Source(sourceDef())
         feedsDef().each { source.addToFeeds(it) }
         source.totalOnFeeds = source.feeds.size()
         source.totalFeeds = source.feeds.size()
         source.save(failOnError: true)
      }
   }

   abstract def sourceDef()
   abstract def feedsDef()
}
