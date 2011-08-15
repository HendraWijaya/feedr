package feedr.source

class TempoInteraktifSource extends SourceDef {
   def sourceDef() {
      [name: "TempoInteraktif", url: "http://www.tempointeraktif.com"]
   }

   def feedsDef() {
      [
         [
            name: "Headline", url: "http://rss.tempointeraktif.com/index.xml"
         ],
         [
            name: "Fokus", url: "http://rss.tempointeraktif.com/fokus.xml"
         ],
         [
            name: "Nasional", url: "http://rss.tempointeraktif.com/nasional.xml"
         ],
         [
            name: "Internasional", url: "http://rss.tempointeraktif.com/internasional.xml"
         ],
         [
            name: "Seni Hiburan", url: "http://rss.tempointeraktif.com/senihiburan.xml"
         ],
         [
            name: "Teknologi", url: "http://rss.tempointeraktif.com/teknologi.xml"
         ],
         [
            name: "Olahraga", url: "http://rss.tempointeraktif.com/olahraga.xml"
         ]
      ]
   }
}

