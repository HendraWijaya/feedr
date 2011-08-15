/**
 * 
 */
package feedr

/**
 * @author Hendra
 *
 */
class ItemAuthor {
    String name
    
    static belongsTo = [item: Item]
	
	static constraints = {
		name(blank: false)
	}
	
	static mapping = {
		version false
	}
}
