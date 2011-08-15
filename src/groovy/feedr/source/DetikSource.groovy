package feedr.source

class DetikSource extends SourceDef {
    def sourceDef() {
        [name: "Detik", url: "http://www.detik.com/"]
    }
    
    def feedsDef() {
        [
            [
                name: "All", url: "http://rss.detik.com/"
            ],
        
            [
                name: "Surabaya", url: "http://feeds.feedburner.com/detik/Zgvz?format=xml"
            ]
        ]
    }
}
