/**
 * 
 */
package feedr


/**
 * @author Hendra
 *
 */
class Feed {
    String name
    String url
    Status status = Status.On

    Date publishedDate
    Date lastModified
    String eTag
    
    Date lastChecked
    
    static belongsTo = [source: Source]
    static hasMany = [items: Item, snapshots: FeedSnapshot]
    
    static constraints = {
        name (blank: false)
        url (blank: false, unique: true)
        publishedDate(nullable: true)
        lastModified(nullable: true)
        lastChecked(nullable: true)
        eTag(nullable: true)
    }
    
    static enum Status {
        On,
        Off
    }
}
