<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:message code="site.title" default="FeedR"/> | <g:layoutTitle default="Home" /></title>
		
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		
		<r:layoutResources/>
	</head>
	<body>
		<div class="container">
            <div class="span-16">
                <div id="header">
                    <a id="logo" href="">FeedR</a>
                    <!--  <div id="logo" style="float:left"><a href=""><img src="${resource(dir:'images',file:'logo.png')}" alt="Timeline" border="0" /></a></div> -->
                    <div id="search-box">
                       <input id="search-key" type="text" value="Search is not working yet"/> <g:submitButton class="search button green" name="search" value="Search" />
                    </div> 
                </div>   
            </div>
            <g:layoutBody />
        </div>
        <r:layoutResources/>
	</body>
</html>