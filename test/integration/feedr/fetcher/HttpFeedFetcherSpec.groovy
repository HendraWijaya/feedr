package feedr.fetcher

import spock.lang.Shared
import feedr.Feed
import feedr.FeedSnapshot
import feedr.Item
import feedr.Source
import grails.plugin.spock.IntegrationSpec

class HttpFeedFetcherSpec extends IntegrationSpec {
   @Shared def httpFeedFetcher
   @Shared int port = 1234
   @Shared TesRssServer server
   @Shared Source source

   def setupSpec() {
      source = new Source(name: "Foo Source", url: "http://foosource.com")
      assert source.save()

      server = new TesRssServer(port)
      server.start()
   }

   def cleanupSpec() {
      server.stop()
   }

   def "fetching a regular feed" () {
      given:
      server.setResponseHeaders(200, ["Content-Type": "text/xml; charset=UTF-8"])
      def rss = "regular.rss"
      when:
      Feed feed = createFeed(rss)
      httpFeedFetcher.retrieve(feed)
      then:
      assert Item.count() == 10
      assert FeedSnapshot.count() == 1

      verifyItem (
            title: 'Murdochs bow to pressure to attend UK parliament',
            link: 'http://feeds.reuters.com/~r/reuters/topNews/~3/beOXtUy_w-4/us-newscorp-hacking-idUSTRE7641IO20110714',
            uri: 'http://www.reuters.com/article/2011/07/14/us-newscorp-hacking-idUSTRE7641IO20110714?feedType=RSS&feedName=topNews',
            publishedDate: parseDate("Thu, 14 Jul 2011 13:49:56 -0400")
            )

      verifyItem (
            title: 'Jobless claims, sales show recovery still weak',
            link: 'http://feeds.reuters.com/~r/reuters/topNews/~3/Q8ga1mTbaKM/us-usa-economy-idUSTRE7662I420110714',
            uri: 'http://www.reuters.com/article/2011/07/14/us-usa-economy-idUSTRE7662I420110714?feedType=RSS&feedName=topNews',
            publishedDate: parseDate("Thu, 14 Jul 2011 14:23:09 -0400")
            )

      verifyItem (
            title: 'FBI to probe News Corp 9/11 hacking allegations',
            link: 'http://feeds.reuters.com/~r/reuters/topNews/~3/rtbSFUuaVxA/us-newscorp-fbi-idUSTRE76D5O220110714',
            uri: 'http://www.reuters.com/article/2011/07/14/us-newscorp-fbi-idUSTRE76D5O220110714?feedType=RSS&feedName=topNews',
            publishedDate: parseDate("Thu, 14 Jul 2011 14:46:05 -0400")
            )
   }

   def "interaction in fetching a feed with conditional HTTP GET support" () {
      setup: 'save the original HttpFeedReceiver before mocking it'
         HttpFeedReceiver originalHttpFeedReceiver = httpFeedFetcher.httpFeedReceiver
      and: 'assign a mock HttpFeedReceiver to HttpFeedFetcher'
         HttpFeedReceiver mockHttpFeedReceiver = Mock()
         httpFeedFetcher.httpFeedReceiver = mockHttpFeedReceiver

      and: 'create a Feed'
         Feed feed = createFeed("regular.rss")

      when: 'the server returns HTTP 200'
         server.setResponseHeaders(200, ["Content-Type": "text/xml; charset=UTF-8"])
         httpFeedFetcher.retrieve(feed)
      then: 'HttpFeedReceiver should handle HTTP 200 response'
         1 * mockHttpFeedReceiver.onHttp200(!null, !null)

      when: 'the server returns HTTP 304'
         server.setResponseHeaders(304, ["Content-Type": "text/xml; charset=UTF-8"])
         httpFeedFetcher.retrieve(feed)
      then: 'HttpFeedReceiver should handle HTTP 304 response'
         1 * mockHttpFeedReceiver.onHttp304(!null, !null)

      cleanup: 'assign back the original HttpFeedReceiver to HttpFeedFetcher'
         httpFeedFetcher.httpFeedReceiver = originalHttpFeedReceiver
   }

   //	def "feed status in fetching a feed with conditional HTTP GET support" {
   //
   //	}

   // ---------- Helper Methods ---------- //

   private void verifyItem(Map expected) {
      Item item = Item.findByUri ( expected.uri )
      assert item != null
      assert item.link == expected.link
      assert item.title == expected.title
      assert item.publishedDate != null
      assert item.description != null
      assert item.content == null
   }

   private Feed createFeed(String filename) {
      Feed feed = new Feed(
            name: "foo",
            url: "http://localhost:${port}/${filename}",
            source: source)

      assert source != null
      assert feed.save()

      return feed
   }

   private Date parseDate(String dateString) {
      Date.parse("EEE, d MMM yyyy HH:mm:ss Z", dateString)
   }
}
