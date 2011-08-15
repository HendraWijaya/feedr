package feedr

import java.util.Date;

import feedr.fetcher.FetchStatus;

class ItemPage {
    String url
    String type
    String charset
    Integer size // in bytes
    String content
    Date lastModified
    
    Date lastUpdated
    Date dateCreated
    
    FetchStatus fetchStatus
    
    Item item
    
    static embedded = ['fetchStatus']
    
    static constraints = {
		type(nullable: true)
		charset(nullable: true)
		size(nullable: true)
        content(nullable: true, maxSize: 102400) // 100KB
        fetchStatus(nullable: true)
        lastModified(nullable: true)
    }
	
	static mapping = {
		version false
	}
}
