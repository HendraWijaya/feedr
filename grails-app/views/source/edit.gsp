<html>
    <head>
        <title><g:message code="edit.label"/> ${source.name}</title>
        <meta name="layout" content="main" />
        
        <r:require modules="jquery, form"/>
        <r:script>
           $('#cancel').live('click', function(e) {
               e.preventDefault();
               
               window.location.replace("${createLink(controller: "source", action: "list")}");
           });
        </r:script>
    </head>
    <body>
        <div class="span-16">
            <f:breadcrumb>
                <g:link controller="source" action="list"><g:message code="source.list.label" default="Sources"/></g:link>
                <g:link controller="source" action="show" id="${source.id}">${source.name}</g:link>
                <g:message code="edit.label" default="Edit"/>
            </f:breadcrumb>
            
            <div class="editor box">
                <g:if test="${flash.message}">
	                <div class="info">${flash.message}</div>
	            </g:if>
                <g:form action="update">
                   <g:hasErrors bean="${source}">
                      <div class="error">
                          <g:renderErrors bean="${source}" as="list" />
                      </div>
                   </g:hasErrors>
                   
                   <g:hiddenField name="id" value="${source?.id}" />
                   <g:hiddenField name="version" value="${source?.version}" />
                
                   <label for="name"><g:message code="source.name.label" default="Name" />:</label>
                   <div class="value ${hasErrors(bean: source, field: 'name', 'field-error')}">
                       <g:textField class="text" name="name" value="${source?.name}" />
                   </div>
                   
                   <label for="url"><g:message code="source.url.label" default="URL" />:</label>
                   <div class="value ${hasErrors(bean: source, field: 'url', 'field-error')}">
                       <g:textField class="text" name="url" value="${source?.url}" />
                   </div>
                   
                   <div class="buttons">
                        <g:actionSubmit name="update" class="button green" value="${message(code: 'update.label', default: 'Update')}" />
                        <g:actionSubmit class="button red" action="delete" value="${message(code: 'delete.label', default: 'Delete')}" />
                        <button id="cancel" class="button white">${message(code: 'cancel.label', default: 'Cancel')}</button>
                   </div>
               </g:form>
            </div>
         </div>    

    </body>
</html>
