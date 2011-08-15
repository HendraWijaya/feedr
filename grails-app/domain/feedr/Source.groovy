/**
 * 
 */
package feedr

/**
 * @author Hendra
 *
 */
class Source {
   String name
   String url
   Integer totalFeeds = 0
   Integer totalOnFeeds = 0

   static hasMany = [feeds: Feed]

   static constraints = {
      name (blank: false)
      url (blank: false, unique: true)
      feeds (nullable: true)
   }
}
