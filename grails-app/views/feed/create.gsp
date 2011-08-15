<html>
    <head>
        <title>${source.name} | <g:message code="feed.create.label"/></title>
        <meta name="layout" content="main" />
        <r:require modules="jquery, form"/>
        <r:script>
           $('#cancel').live('click', function(e) {
               e.preventDefault();
 
               window.location.replace("${createLink(controller: "source", action: "show", id: source.id)}");
           });
        </r:script>
    </head>
    <body>
        <div class="span-16">
            <f:breadcrumb>
                <g:link controller="source" action="list"><g:message code="source.list.label" default="Sources"/></g:link>
                <g:link controller="source" action="show" id="${source.id}">${source.name}</g:link>
                <g:message code="feed.create.label" default="New Feed"/>
            </f:breadcrumb>
            <div class="editor box">
                <g:form action="save">
                   <g:hasErrors bean="${feed}">
                      <div class="error">
                          <g:renderErrors bean="${feed}" as="list" />
                      </div>
                   </g:hasErrors>
                   
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
                       <g:select name="status" from="${feedr.Feed.Status?.values()}" keys="${feedr.Feed.Status?.values()*.name()}" value="${feed?.status?.name()}"  />
                   </div>
                   
                   <label><g:message code="source.label" default="Source" />:</label>
                   <div class="value">
                       <g:link controller="source" action="show" id="${source.id}">${source.name}</g:link>
                   </div>
                   <g:hiddenField name="sourceId" value="${source?.id}" />
                   
                   <div class="buttons">
                        <g:submitButton name="create" class="button green" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                        <button id="cancel" class="button white">${message(code: 'cancel.label', default: 'Cancel')}</button>
                   </div>
               </g:form>
            </div>
        </div>   
    </body>
</html>
