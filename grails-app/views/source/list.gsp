<html>
    <head>
        <title><g:message code="source.list.label"/></title>
        <meta name="layout" content="main" />
        <r:require modules="list, form, dialog"/>
        <r:script>
            $(function(){
                $('.list .item a.delete').live('click', function(e) {
                    e.preventDefault();
                    var $a = $(this);
                    
                    $( "#dialog-delete-confirm" ).dialog({
                        resizable: false,
                        modal: true,
                        buttons: {
                            ${message(code: 'delete.label', default: "Delete")}: function() {
                                var $dialog = $( this );
                                $.post($a.attr('href'), function() {
                                    $('#total-sources').html(parseInt($('#total-sources').text()) - 1);
                                    $a.closest('.item').slideUp();
                                  })
                                  .error( function() { 
                                      $("#dialog-error").dialog({
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
        <div class="dialog" id="dialog-delete-confirm" title="Delete the source?">
            <p>The source will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>
        <div class="dialog" id="dialog-error" title="Oops">
            <p>Sorry, we are unable to perform your request at the moment.</p>
        </div>
        
        <div class="span-16">
            <g:if test="${flash.message}">
                <div class="info">${flash.message}</div>
            </g:if>
            <div class="title">
                <h1><g:message code="source.list.label" default="Sources"/></h1>
                <p>
                    <strong>Total Sources:</strong> <span id="total-sources">${totalSources}</span>
	            </p>
	            <div class="actions">
	                <g:link action="create"><g:message code="source.create.label" default="New Source"/></g:link>
	            </div>
            </div>
            
            <div class="list span-16 last">
	            <g:each in="${sources}" status="i" var="source">
	               <div class="item">
                       <h2>
                        <g:link action="show" id="${source.id}">${fieldValue(bean: source, field: "name")}</g:link>
                        
                        </h2>             
                        <p>
                            <a href="${fieldValue(bean: source, field: "url")}" target="_blank">${fieldValue(bean: source, field: "url")}</a><br/>
                            <g:message code="source.totalFeeds.label" default="Feeds"/>: ${fieldValue(bean: source, field: "totalFeeds")}
                        </p>
                        <span class="actions"><g:link action="edit" id="${source.id}"><g:message code="edit.label" default="Edit"/></g:link> &middot; <g:link class="delete" action="delete" id="${source.id}"><g:message code="delete.label" default="Delete"/></g:link></span>
                   </div>
                </g:each>
                
                <div class="paginate">
	                <g:paginate total="${totalSources}" />
	            </div>
            </div>
            
        </div>
    </body>
</html>
