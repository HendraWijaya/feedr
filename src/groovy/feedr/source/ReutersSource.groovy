package feedr.source

class ReutersSource extends SourceDef {
   def sourceDef() {
      [name: "Reuters", url: "http://www.reuters.com"]
   }

   def feedsDef() {
      [
         [
            name: "Top News", url: "http://feeds.reuters.com/reuters/topNews"
         ]
      ]
   }
}
