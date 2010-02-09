<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><g:layoutTitle default="Grails" /></title>
<style type="text/css">
  UL {
    list-style-type: none;
     }
</style>
<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
<link rel="shortcut icon"
	href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
<g:layoutHead />
<g:javascript library="application" />
</head>
<body>
 
<div id="spinner" class="spinner" style="display: none;"><img
	src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" /></div>

<div id="main">

<div id="header" class="basic">

<div class="site">
<div class="logo"><a href="/">Витрина</a></div>


<ul class="mainMenu">
	

<g:renderMenu/>
<%-- 
 --%>
</ul>

</div>
</div>
<!-- header --> <g:layoutBody /></div>
<!-- main -->

</body>
</html>