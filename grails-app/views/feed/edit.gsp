<html>
    <head>
        <title><g:message code="edit.label" args="[feed.name]"/></title>
        <meta name="layout" content="main" />
        <r:require modules="jquery, form"/>
        <r:script>
           $('#cancel').live('click', function(e) {
               e.preventDefault();
               
               window.location.replace("${createLink(controller: "feed", action: "show", id: feed.id)}");
           });
        </r:script>
    </head>
    <body>

        <div class="span-16">
            <f:breadcrumb>
                <g:link controller="source" action="list"><g:message code="source.list.label" default="Sources"/></g:link>
                <g:link controller="source" action="show" id="${feed.source.id}">${feed.source.name}</g:link>
                <g:link action="show" id="${feed.id}">${feed.name}</g:link>
                <g:message code="edit.label" default="Edit"/>
            </f:breadcrumb>
            
            <div class="editor box">
                <g:form>
                   <g:hasErrors bean="${feed}">
                      <div class="field-errors">
                          <g:renderErrors bean="${feed}" as="list" />
                      </div>
                   </g:hasErrors>
                   
                   <g:hiddenField name="id" value="${feed?.id}" />
                   <g:hiddenField name="version" value="${feed?.version}" />
                
                   <label for="name"><g:message code="feed.name.label" default="Name" />:</label>
                   <div class="value ${hasErrors(bean: feed, field: 'name', 'field-error')}">
                       <g:textField class="text" name="name" value="${feed?.name}" />
                   </div>
                   
                   <label for="url"><g:message code="feed.url.label" default="URL" />:</label>
                   <div class="value ${hasErrors(bean: feed, field: 'url', 'field-error')}">
                       <g:textField class="text" name="url" value="${feed?.url}" />
                   </div>
                   
                   <label for="status"><g:message code="feed.status.label" default="Status" />:</label>
                   <div class="value ${hasErrors(bean: feed, field: 'status', 'field-error')}">
                       <g:select name="status" from="${feedr.Feed.Status.values()}" keys="${feedr.Feed.Status.values()*.name()}" value="${feed?.status?.name()}"  />
                   </div>
                   
                   <label><g:message code="source.label" default="Source" />:</label>
                   <div class="value">
                       <a href="">${feed.source.name}</a>
                   </div>
                   
                   <div class="buttons">
                        <g:actionSubmit class="button green" action="update" value="${message(code: 'update.label', default: 'Update')}" />
                        <g:actionSubmit class="button red" action="delete" value="${message(code: 'delete.label', default: 'Delete')}" />
                        <button id="cancel" class="button white">${message(code: 'cancel.label', default: 'Cancel')}</button>
                   </div>
               </g:form>
            </div>
        </div>   
    </body>
</html>
