/**
 * 
 */
package feedr

import java.util.Date;

/**
 * @author Hendra
 *
 */
class Item {
    String url
    String link
    String uri
    String title
    String description
    String content
    
    Date publishedDate
    
    // Not from RSS
    Date dateCreated
    
    static transients = ['url']
    
    static belongsTo = [feed: Feed]
    
    static hasOne = [page: ItemPage]
    
    static hasMany = [
        authors: ItemAuthor,
        images: ItemImage,
        categories: ItemCategory]
    
    static constraints = {
        link(unique: true)
        uri(unique: true)
        description(nullable: true, blank: true, maxSize: 10240) // 10KB
        content(nullable: true, blank: true, maxSize: 102400) // 100KB
        authors(nullable: true)
        images(nullable: true)
        categories(nullable: true)
        page(nullable: true)
    }
    
	static mapping = {
		version false
	}
	
    String toString() {
        "publishedDate: ${publishedDate}\nuri: ${uri}\ntitle: ${title}\ndescription: ${description}\ncontent:${content}"
    }
    
    /**
     * 
     * @return Return the URI if it exists, otherwise return the link
     */
    String getUrl() {
        uri ?: link
    }
}
