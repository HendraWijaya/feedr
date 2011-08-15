package feedr.source

class HuffingtonPostSource extends SourceDef {
    def sourceDef() {
        [name: "The Huffington Post", url: "http://www.huffingtonpost.com"]
    }
    
    def feedsDef() {
        [
            [
                name: "Latest News", url: "http://feeds.huffingtonpost.com/huffingtonpost/LatestNews"
            ]
        ]
    }
}
