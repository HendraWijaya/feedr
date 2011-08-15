package feedr

import java.util.List

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.sun.syndication.feed.module.mediarss.MediaEntryModule
import com.sun.syndication.feed.module.mediarss.MediaModule
import com.sun.syndication.feed.module.mediarss.types.MediaContent
import com.sun.syndication.feed.synd.SyndCategory
import com.sun.syndication.feed.synd.SyndContent
import com.sun.syndication.feed.synd.SyndEnclosure
import com.sun.syndication.feed.synd.SyndEntry
import com.sun.syndication.feed.synd.SyndPerson

class ItemFactory {
    private static Logger log = LoggerFactory.getLogger(ItemFactory.class)
    
    static Item build(Feed feed, SyndEntry syndEntry) {
        Item item = 
            new Item(
                feed: feed,
                link: syndEntry.link,
                uri: syndEntry.uri,
                title: syndEntry.title,
                description: syndEntry.description?.value?.trim(),
                content: joinContents(syndEntry.contents),
                publishedDate: syndEntry.publishedDate)
		
	    // Init the 'empty' page now as metadata so it can be fetched later
		item.page =
			new ItemPage(url: item.url, item: item)
			
        processCategories(item, syndEntry.categories)
        processEnclosures(item, syndEntry.enclosures)
        processAuthors(item, syndEntry)
        processModules(item, syndEntry)
        
        return item
    }
    
    static private void processCategories(Item item, List<SyndCategory> syndCategories) {
        for(SyndCategory syndCategory : syndCategories) {
            if(syndCategory.name) {
                item.addToCategories(new ItemCategory(name: syndCategory.name))
            }
        }
    }
    
    static private void processModules(Item item, SyndEntry syndEntry) {
        processMediaModule(item, syndEntry)
    }
    
    static private void processMediaModule(Item item, SyndEntry syndEntry) {
        MediaModule mediaModule = (MediaModule) syndEntry.getModule( MediaModule.URI )

        if (mediaModule != null && mediaModule instanceof MediaEntryModule ) {
            MediaEntryModule mediaEntry = (MediaEntryModule ) mediaModule;
            for (MediaContent mediaContent : mediaEntry.getMediaContents()) {
                processMediaContent(item, mediaContent)
            }
        }
    }
    
//    static private void processMediaContent(Entry entry, MediaContent mediaContent) {
//        String medium = mediaContent.getMedium()
//        String reference = mediaContent.getReference()
//        
//        if(medium != null && reference != null) {
//            
//            byte[] bytes
//            
//            try {
//                bytes = reference.toURL().bytes
//            } catch (FileNotFoundException e) {
//                // do nothing
//            }
//        
//            if(bytes) {
//                EntryMedia entryMedia = new EntryMedia()
//                entryMedia.medium = medium
//                entryMedia.url = reference
//                entryMedia.bytes = bytes
//                
//                entry.addToMedias(entryMedia)
//            }
//        }
//    }
    
    static private void processMediaContent(Item item, MediaContent mediaContent) {
        String medium = mediaContent.getMedium()
        String reference = mediaContent.getReference()
        
        if(medium != null && reference != null) {
            
            switch(medium) {
                case 'image':
                    ItemImage image = new ItemImage()
                    image.url = reference
                    
                    item.addToImages(image)
                break
            }
        }
    }
    
    static private void processAuthors(Item item, SyndEntry syndEntry) {
        if(syndEntry.author) {
            item.addToAuthors(new ItemAuthor(name: syndEntry.author))
        }
        
        if(syndEntry.authors) {
            syndEntry.authors.each {SyndPerson person ->
                item.addToAuthors(new ItemAuthor(name: person.name))
            }
        }
    }
    
    static private void processEnclosures(Item item, List<SyndEnclosure> enclosures) {
        if(enclosures != null && enclosures.size() > 0) {
            enclosures.each { SyndEnclosure enclosure ->
                processEnclosure(item, enclosure)
            }
        }
    }
    
    static private void processEnclosure(Item item, SyndEnclosure enclosure) {
        switch (enclosure.type) {
            case 'image/jpeg':
            case 'image/jpg':
                processImageEnclosure(item, enclosure)
                break
        }
    }
    
//    static private void processImageEnclosure(Entry entry, SyndEnclosure enclosure) {
//        byte[] bytes
//        
//        try {
//            bytes = enclosure.url.toURL().bytes
//        } catch (FileNotFoundException e) {
//            // do nothing
//        }
//    
//        if(bytes) {
//            def image = new EntryImage()
//            image.url = enclosure.url
//            image.bytes = bytes
//            image.size = bytes.size()
//            image.mime = enclosure.type
//            
//            entry.addToImages(image)
//        }
//    }
    
	/*
	 * To allow high concurrency and low-latency process, this method only add the image metadata 
	 * without actually fetching the image now because ItemImageFetcher will do the fetching later
	 */
    static private void processImageEnclosure(Item item, SyndEnclosure enclosure) {
        def image = new ItemImage()
        image.url = enclosure.url
        image.type = enclosure.type
        
        item.addToImages(image)
    }
    
//    private List<SyndEntry> fetch() {
//        String url = feed.url
//        log.debug ("Retrieving... {}", url)
//        return fetch(new XmlReader(url.toURL()))
//    }
//
//    private List<SyndEntry> fetch(Reader reader) {
//        syndFeed = new SyndFeedInput().build(reader)
//        return syndFeed?.entries
//    }
    
    static private String joinContents(List<SyndContent> contents) {
        if(contents == null || contents.size() == 0) {
            return null
        }
        
        def out = new StringBuffer()
        
        contents.each { SyndContent content ->
            out << content.value << "\n"
        }
        
        return out.toString().trim()
    }
}
