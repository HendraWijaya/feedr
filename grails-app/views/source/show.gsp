<html>
    <head>
        <title>${source.name}</title>
        <meta name="layout" content="main" />
        <r:require modules="list, dialog"/>
        <r:script>
            $(function(){
                $('.list .item a.delete').live('click', function(e) {
                    e.preventDefault();
                    var $a = $(this);

                    var $confirmDialog = $(
                            '<div class="dialog" title="Delete the feed">' +
                                '<p>wow The feed will be permanently deleted and cannot be recovered. Are you sure?</p>' +
                            '</div>'
                            );

                    var $errorDialog = $(
                    		'<div class="dialog" title="Oops">' +
                    	        '<p>Sorry, we are unable to perform your request at the moment.</p>' +
                            '</div>'
                            );
                    
                    $confirmDialog.dialog({
                        resizable: false,
                        modal: true,
                        buttons: {
                            ${message(code: 'delete.label', default: "Delete")}: function() {
                                var $dialog = $( this );
                                $.post($a.attr('href'), function() {
                                	$('#total-feeds').html(parseInt($('#total-feeds').text()) - 1);
                                	$a.closest('.item').slideUp();
                                  })
                                  .error( function() { 
                                	  $errorDialog.dialog({
                                          resizable: false,
                                          modal: true,
                                          buttons: {
                                        	  ${message(code: 'ok.label', default: "Ok")}: function() {
                                                  $(this).dialog("close");    
                                              }
                                          }
                                      }); 
                                  })
                                  .complete( function() {
                                	  $dialog.dialog("close");
                                  });
                            },
                            ${message(code: 'cancel.label', default: "Cancel")}: function() {
                                $( this ).dialog( "close" );
                            }
                        }
                    });
                });
 

                $('.title .actions a.delete').live('click', function(e) {
                    e.preventDefault();

                    alert('delete source');
                });
            });
        </r:script>
        
    </head>
    <body>
        <div class="span-16">
            <g:if test="${flash.message}">
                <div class="info">${flash.message}</div>
            </g:if>
            
            <f:breadcrumb>
                <g:link controller="source" action="list"><g:message code="source.list.label" default="Sources"/></g:link>
                ${source.name}
            </f:breadcrumb>
            <div class="title clearfix">
                <h1>${source.name}</h1>
	            <p>
	               ${source.url}<br/>
	               <strong><g:message code="source.totalFeeds.label"/>:</strong> <span id="total-feeds">${source.feeds.size()}</span>
	            </p>
	            <div class="actions">
                    <g:link controller="source" action="edit" id="${source.id}"><g:message code="edit.label" default="Edit"/></g:link> <g:link class="delete" controller="source" action="delete" id="${source.id}"><g:message code="delete.label" default="Delete"/></g:link> <g:link controller="feed" action="create" params="[sourceId: source.id]"><g:message code="feed.create.label" default="New Feed"/></g:link>
                </div>
            </div>
                       
            <div class="list span-16 last">
                
	            <g:each in="${source.feeds}" status="i" var="feed">
	               <div class="item">
		               <h2><g:link controller="feed" action="show" id="${feed.id}">${fieldValue(bean: feed, field: "name")}</g:link></h2>             
		               <dl>
		                    <dt><g:message code="url.label" default="URL"/></dt>
		                    <dd>${fieldValue(bean: feed, field: "url")}</dd>
		                    
		                    <dt><g:message code="status.label" default="Status"/></dt>
		                    <dd>${fieldValue(bean: feed, field: "status")}</dd>
		               </dl>
		               
		               <span class="actions"><g:link controller="feed" action="edit" id="${feed.id}"><g:message code="edit.label" default="Edit"/></g:link> &middot; <g:link controller="feed" action="delete" id="${feed.id}" class="delete"><g:message code="delete.label" default="Delete"/></g:link></span>
		           </div>
	            </g:each>
	            
	            <div class="paginate">
	                <g:paginate total="${source.feeds.size()}" />
	            </div>
            </div>
            
        </div>
       
    </body>
</html>
