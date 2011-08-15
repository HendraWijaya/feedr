/**
 * 
 */
package feedr

/**
 * @author Hendra
 *
 */
class ItemImage extends ItemMedia {
    static belongsTo = [item: Item]
}
