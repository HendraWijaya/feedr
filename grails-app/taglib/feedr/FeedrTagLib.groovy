package feedr

class FeedrTagLib {
   static namespace = "f"

   def breadcrumb = { attrs, body ->
      def clazz = attrs.'class' ?: "breadcrumb"
      def links = body.call().trim()
      out << "<div class='${clazz}'>"
      links.eachLine {
         out << "&rsaquo; " << it << " "
      }

      out << "&rsaquo;</div>"
   }
}
