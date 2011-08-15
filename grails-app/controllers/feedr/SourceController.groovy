package feedr

import javax.servlet.http.HttpServletResponse

class SourceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [sources: Source.list(params), totalSources: Source.count()]
    }

    def create = {
        def source = new Source()
        source.properties = params
        return [source: source]
    }

    def save = {
        def source = new Source(params)
        if (source.save(flush: true)) {
            flash.message = "${message(code: 'created.message', args: [message(code: 'source.label', default: 'source'), source.name])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [source: source])
        }
    }

    def show = {
        def source = Source.get(params.id)
        if (!source) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }
        else {
            [source: source]
        }
    }

    def edit = {
        def source = Source.get(params.id)
        if (!source) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'source.label', default: 'source'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [source: source]
        }
    }

    def update = {
        def source = Source.get(params.id)
        if (source) {
            if (params.version) {
                def version = params.version.toLong()
                if (source.version > version) {
                    
                    source.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'source.label', default: 'source')] as Object[], "Another user has updated this source while you were editing")
                    render(view: "edit", model: [sourceInstance: source])
                    return
                }
            }
            source.properties = params
            if (!source.hasErrors() && source.save(flush: true)) {
                flash.message = "${message(code: 'updated.message', args: [message(code: 'source.label', default: 'Source'), source.name])}"
                redirect(action: "edit", id: source.id)
            }
            else {
                render(view: "edit", model: [sourceInstance: source])
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }
    }

    def delete = {
        def source = Source.get(params.id)
        if (source) {
            try {
                source.delete(flush: true)
                if(request.xhr) {
                    render ""
                } else {
                    flash.message = "${message(code: 'deleted.message', args: [message(code: 'source.label', default: 'Source'), source.name])}"
                    redirect(action: "list")
                }
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                log.error "Error when deleting source: $source\n$e"
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }
    }
}