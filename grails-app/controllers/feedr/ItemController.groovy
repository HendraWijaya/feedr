package feedr

class ItemController {
   static allowedMethods = [delete: "POST"]

   def delete = {
      def feedEntryInstance = FeedEntry.get(params.id)
      if (feedEntryInstance) {
         try {
            feedEntryInstance.delete(flush: true)
            flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'feedEntry.label', default: 'FeedEntry'), params.id])}"
            redirect(action: "list")
         }
         catch (org.springframework.dao.DataIntegrityViolationException e) {
            flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'feedEntry.label', default: 'FeedEntry'), params.id])}"
            redirect(action: "show", id: params.id)
         }
      }
      else {
         flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedEntry.label', default: 'FeedEntry'), params.id])}"
         redirect(action: "list")
      }
   }
}
