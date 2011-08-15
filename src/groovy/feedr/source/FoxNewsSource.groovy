package feedr.source

class FoxNewsSource {
   def source() {
      [name: "Fox News", url: "http://www.foxnews.com"]
   }

   def feeds() {
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
