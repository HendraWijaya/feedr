/**
 * 
 */
package feedr

/**
 * @author Hendra
 *
 */
class ItemCategory {
    String name
    
    static belongsTo = [item: Item]
    
    static constraints = {
		name(blank: false)
    }
	
	static mapping = {
		version false
	}
}
