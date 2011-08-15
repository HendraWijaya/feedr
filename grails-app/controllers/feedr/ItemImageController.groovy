package feedr

class ItemImageController {
    def defaultAction ='show'
    
    def show = {
        def image = ItemImage.get( params.id )
        
        if(image) {
            response.setHeader('Content-length', image.size.toString())
            if(image.type)
                response.setContentType( image.type )
            response.outputStream << image.bytes
            response.outputStream.flush()
        } else {
            response.sendError(404)
        }
    }
}
