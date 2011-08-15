package feedr.source

class FoxNewsSource extends SourceDef {
   def sourceDef() {
      [name: "Fox News", url: "http://www.foxnews.com"]
   }

   def feedsDef() {
      [
         [
            name: "National", url: "http://feeds.foxnews.com/foxnews/national?format=xml"
         ],
         [
            name: "World", url: "http://feeds.foxnews.com/foxnews/world?format=xml"
         ]
      ]
   }
}
