package feedr.fetcher

// It seems there is conflict if we put this in BuildConfig.grooy
@Grab(group='org.codehaus.gpars', module='gpars', version='0.11')
import static groovyx.gpars.GParsPool.withPool

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import feedr.Feed
import feedr.Feed.Status

class FeedBot {
    private static final Logger log = LoggerFactory.getLogger(FeedBot.class)

    FeedFetcher feedFetcher

    void run() {
        def feeds
        
        Feed.withTransaction {
            feeds = Feed.findAllByStatus(Feed.Status.On)
        }

        withPool {
            feeds.eachParallel { Feed feed ->
				// This is a new thread and feed has been detached from the session.
				// It loses its persistent context.
				// We should re-attach it to a session before making changes.
				// It's like a new HTTP request with form data of an feed
                feedFetcher.retrieve(feed)
            }
        }
    }
}
