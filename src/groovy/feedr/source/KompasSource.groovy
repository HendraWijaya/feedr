/**
 * 
 */
package feedr.source

/**
 * @author Hendra
 *
 */
class KompasSource extends SourceDef {

   def sourceDef() {
      [name: "Kompas", url: "http://www.kompas.com"]
   }

   def feedsDef() {
      [
         [
            name: "Nasional",
            url: "http://www.kompas.com/getrss/nasional"
         ],
         [
            name: "Regional",
            url: "http://www.kompas.com/getrss/regional"
         ]
      ]
   }
}
