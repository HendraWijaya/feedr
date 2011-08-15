package feedr.fetcher

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.sun.syndication.feed.synd.SyndEntry
import com.sun.syndication.feed.synd.SyndFeed

import feedr.Feed
import feedr.FeedSnapshot
import feedr.Item
import feedr.ItemFactory

interface SyndFeedReceiver {
   void onReceiveSyndFeed(Feed feed, SyndFeed syndFeed)
}