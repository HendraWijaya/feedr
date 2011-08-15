package feedr

import javax.servlet.http.HttpServletResponse

import org.hibernate.FetchMode

class FeedController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create = {
        def source = Source.get(params.sourceId)
        
        if(!source) {
            log.debug "Unable to get source with id: $params.sourceId"
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        } else {
            def feed = new Feed()
            feed.properties = params
            return [source: source, feed: feed]
        }
    }

    def save = {
        def source = Source.lock(params.sourceId)
        
        if(!source) {
            log.debug "Unable to get source with id: $params.sourceId"
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
            return
        }
        
        def feed = new Feed(params)
        
        if(feed.status == Feed.Status.On) {
            source.totalOnFeeds += 1
        } 
        source.totalFeeds += 1
        source.save()
        
        feed.source = source
        
        if (feed.save(flush: true)) {
            flash.message = "${message(code: 'created.message', args: [message(code: 'feed.label', default: 'Feed'), feed.name])}"
            redirect(controller: "source", action: "show", id: source.id)
        } else {
            render(view: "create", model: [source: source, feed: feed])
        }
    }

    def show = {
		params.offset = params.offset ? params.int('offset') : 0
		params.id = params.long('id')
		
		def feed = Feed.get(params.id)
		
		def items = Item.createCriteria().list(max: 10, offset: params.offset) {
			eq "feed.id", feed.id
			fetchMode "authors", FetchMode.SELECT
			fetchMode "images", FetchMode.SELECT
			fetchMode "categories", FetchMode.SELECT
			order("dateCreated", "desc")
		}
		
        if (!feed) {
            log.debug "Unable to get feed with id: $params.id"
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }
        else {
            [feed: feed, items: items]
        }
    }

    def edit = {
        def feed = Feed.findById(params.id, [fetch:[source:'join']])
        
        if (!feed) {
            log.debug "Unable to get feed with id: $params.id"
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        } else {
            return [feed: feed]
        }
    }

    def update = {
        def feed = Feed.findById(params.id, [fetch:[source:'join']])
        if (feed) {
            if (params.version) {
                def version = params.version.toLong()
                if (feed.version > version) {
                    
                    feed.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'feed.label', default: 'Feed')] as Object[], "Another user has updated this Feed while you were editing")
                    render(view: "edit", model: [feed: feed])
                    return
                }
            }
            feed.properties = params
            if (!feed.hasErrors() && feed.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'feed.label', default: 'Feed'), feed.name])}"
                redirect(controller: "source", action: "show", id: feed.source.id)
            }
            else {
                render(view: "edit", model: [feed: feed])
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }
    }
    
//    response.status = 500
//    render myGormObject.errors.allErrors.collect {
//        message(error:it,encodeAs:'HTML')
//    } as JSON

    def delete = {
        def feed = Feed.findById(params.id, [fetch:[source:'join']])
        if (feed) {
            try {
                feed.delete(flush: true)
                if(request.xhr) {
                    render ""
                } else {
                    flash.message = "${message(code: 'deleted.message', args: [message(code: 'feed.label', default: 'Feed'), feed.name])}"
                    redirect(controller: "source", action: "show", id: feed.source.id)
                }
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                log.error "Error when deleting feed: $feed\n$e"
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }
    }
}
