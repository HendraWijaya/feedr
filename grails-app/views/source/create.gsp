<html>
    <head>
        <title><g:message code="source.create.label"/></title>
        <meta name="layout" content="main" />
        <r:require modules="jquery, form"/>
        <r:script>
           $('#cancel').live('click', function(e) {
               e.preventDefault();
               // simulates similar behavior as an HTTP redirect
               window.location.replace("${createLink(controller: "source", action: "list")}");

               // simulates similar behavior as clicking on a link
               // window.location.href = "http://stackoverflow.com";
           });
        </r:script>
    </head>
    <body>
        <div class="span-16">
            <f:breadcrumb>
                <g:link controller="source" action="list"><g:message code="source.list.label" default="Sources"/></g:link>
                <g:message code="source.create.label" default="New Source"/>
            </f:breadcrumb>
            
            <div class="editor box">
                <g:form action="save">
                   <g:hasErrors bean="${source}">
                      <div class="error">
                          <g:renderErrors bean="${source}" as="list" />
                      </div>
                   </g:hasErrors>
                   
                   <label for="name"><g:message code="source.name.label" default="Name" />:</label>
                   <div class="value ${hasErrors(bean: source, field: 'name', 'field-error')}">
                       <g:textField class="text" name="name" value="${source?.name}" />
                   </div>
                   
                   <label for="url"><g:message code="source.url.label" default="URL" />:</label>
                   <div class="value ${hasErrors(bean: source, field: 'url', 'field-error')}">
                       <g:textField class="text" name="url" value="${source?.url}" />
                   </div>
                   
                   <div class="buttons">
                        <g:submitButton name="create" class="button green" value="${message(code: 'create.label', default: 'Create')}" />
                        <button id="cancel" class="button white">${message(code: 'cancel.label', default: 'Cancel')}</button>
                   </div>
               </g:form>
            </div>
         </div>    
    </body>
</html>
