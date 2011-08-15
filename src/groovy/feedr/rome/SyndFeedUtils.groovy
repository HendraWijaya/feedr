package feedr.rome

import com.sun.syndication.feed.synd.SyndFeed
import com.sun.syndication.feed.synd.SyndEntry

class SyndFeedUtils {
	static Date getLatestEntryDate(SyndFeed syndFeed) {
		Date latestDate = null
		for(SyndEntry entry : syndFeed.entries) {
			if(!latestDate || entry.publishedDate.after(latestDate)) {
				latestDate = entry.publishedDate
			}
		}
		
		return latestDate
	}
}
