package feedr.fetcher

import feedr.Feed

interface FeedFetcher {
   void retrieve(Feed feed)
}
