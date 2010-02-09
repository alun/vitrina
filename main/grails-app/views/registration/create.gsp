

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title><g:message code="Account creation"/></title>         
    </head>
    <body>
        
        <div class="body">
            <h1><g:message code="Account creation"/></h1>
            <g:if test="${flash.error}">
            	<div class="errors">${flash.error}</div>
            </g:if>
            <g:hasErrors bean="${user}">
	            <div class="errors">
	                <g:renderErrors bean="${user}" as="list" />
	            </div>
            </g:hasErrors>
            
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="Login"/></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:RegistrationInstance,field:'nameof','errors')}">
                                    <input type="text" id="username"  name="username" value="${fieldValue(bean:RegistrationInstance,field:'nameof')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password"><g:message code="Password"/></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:RegistrationInstance,field:'passwordof','errors')}">
                                    <input type="text" id="password" name="password" value="${fieldValue(bean:RegistrationInstance,field:'passwordof')}"/>
                                </td>
                            </tr> 
                            
                            <tr class="prop">
								<td colspan="2" style="text-align:center">
									<jcaptcha:jpeg name="image" height="35" width="140" />
								</td>
                            </tr>
                            
                            <tr class="prop">
                                <td class="name">
                                    <label for="code"><g:message code="Image code"/></label>
                                </td>
                                <td class="value ${hasErrors(bean:RegistrationInstance,field:'code','errors')}">
                                    <input type="text" id="code" name="code" value="${fieldValue(bean:RegistrationInstance,field:'code')}"/>
                                </td>
                            </tr>                            
                        </tbody>
                    </table>
                </div>                
                <div class="buttons">
                    <span class="button">
                    	<input class="save" type="submit" value="${g.message(code:"Create")}" />
                    </span>
                </div>
            </g:form>
        </div>
    </body>
</html>

