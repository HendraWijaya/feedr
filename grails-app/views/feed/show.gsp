<html>
    <head>
        <title>${feed.source.name} | ${feed.name}</title>
        <meta name="layout" content="main" />
        <r:require modules="list, item"/>
    </head>
    <body>
        <div class="span-16">
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            
            <f:breadcrumb>
                <g:link controller="source" action="list"><g:message code="source.list.label" default="Sources"/></g:link>
                <g:link controller="source" action="show" id="${feed.source.id}">${feed.source.name}</g:link>
                ${feed.name}
            </f:breadcrumb>
            
            <div class="title">
	            <h1>${feed.name}</h1>
	            <p>
	               ${feed.url}<br/>
	               <strong><g:message code="feed.totalItems.label"/>:</strong> ${feed.items.size()}
	            </p>
	            <div class="actions">
	               <g:link controller="feed" action="edit" id="${feed.id}"><g:message code="edit.label" default="Edit"/></g:link> <g:link controller="feed" action="delete" id="${feed.id}"><g:message code="delete.label" default="Delete"/></g:link>
	            </div>
            </div>
            
            <div class="feed-items list span-16 last">
                
	            <g:each in="${items}" var="item">
	               <div class="item clearfix">
	                    <h2><a href="${item.link}">${fieldValue(bean: item, field: "title")}</a></h2>  
	                    <p class="small">
	                        <span class="quiet"><prettytime:display date="${item.dateCreated}" /> &middot; <a class="source" href="${item?.feed?.source.url}">${item?.feed?.source.name}</a> &middot; </span><a href="${createLink(controller: "item", action: "delete", id: item.id)}"><g:message code="delete.label"/></a>
	                    </p>
	     
	                    <dl>
	                       <dt><g:message code="item.description.label"/></dt>
	                       <dd>${fieldValue(bean: item, field: "description")}</dd>
	                       <g:if test="${item.content}">
	                           <dt>Content</dt>
	                           <dd>${fieldValue(bean: item, field: "content")}</dd>
	                       </g:if>
	                       <dt><g:message code="item.link.label"/></dt>
	                       <dd><a href="${item.link}">${item.link}</a></dd>
	                       
	                       <dt><g:message code="item.uri.label"/></dt>
	                       <dd><a href="${item.uri}">${item.uri}</a></dd>
	                       
	                       <dt><g:message code="item.publishedDate.label"/></dt>
	                       <dd><g:formatDate date="${item.publishedDate}" type="datetime" style="FULL"/></dd>
	                       
	                       <dt><g:message code="item.dateCreated.label"/></dt>
	                       <dd><g:formatDate date="${item.dateCreated}" type="datetime" style="FULL"/></dd>
	                       
	                       <g:if test="${item.authors || item.authors.size() > 0}">
		                       <dt><g:message code="item.authors.label"/></dt>
		                       <dd>
		                           <g:join in="${item.authors.collect{it.name}}"/>
		                       </dd>
	                       </g:if>
	                       <g:if test="${item.images || item.images.size() > 0}">
	                           <dt><g:message code="item.images.label"/></dt>
	                           <dd>
	                                <g:each in="${item.images}" var="image">
	                                   <div class="img-frame">
	                                       <a style="display:block" href="${createLink(controller: 'itemImage', action: 'show', id: image.id)}"><img src="${createLink(controller: 'itemImage', action: 'show', id: image.id)}"/></a>
	                                   </div>
	                                </g:each>
	                           </dd>
	                       </g:if>
	                    </dl>
	               </div>
	            </g:each>
            </div>
            <div class="paginate">
                <g:paginate id="${feed.id}" total="${feed.items.size()}" />
            </div>
        </div>
    </body>
</html>
