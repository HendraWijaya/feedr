package feedr.fetcher

// It seems there is a conflict if we put this in BuildConfig.grooy
@Grab(group='org.codehaus.gpars', module='gpars', version='0.11')
import static groovyx.gpars.GParsPool.withPool

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import feedr.Item


/**
 * @author Hendra
 *
 */
class ItemBot {
    private static final Logger log = LoggerFactory.getLogger(ItemBot.class)

    ItemFetcher itemFetcher

    void run() {
    	itemFetcher.run()
    }
}
