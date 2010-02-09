<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />

        
    </head>
    <body>

        <g:menuDrilldownReset/>
        <g:menuCriteriaReset/>
 
        <div class="body">
 
            
            <g:if test="${flash.message}">
            <div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" /></div>
            </g:if>
            <div class="crumbs">
 
                <g:menuCrumbs default="Main"/>
            </div>
            <div class="options">
                <ul>
                    <g:each in="${optionList}" var="option">

                        <li><g:link action="${option.type == 'submenu' ? 'display' : 'execute'}" id="${option.id}" params=[unfoldMenuId:1]><g:message code="option.${option.path}" default="${option.title}" encodeAs="HTML" /></g:link></li>
                    </g:each>
                </ul>
            </div>
        </div>
    </body>
</html>
