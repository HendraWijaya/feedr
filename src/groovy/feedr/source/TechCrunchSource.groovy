package feedr.source

class TechCrunchSource extends SourceDef {

   def sourceDef() {
      [name: "TechCrunch", url: "http://techcrunch.com/"]
   }

   def feedsDef() {
      [
         [
            name: "All", url: "http://feeds.feedburner.com/TechCrunch"
         ]
      ]
   }
}
