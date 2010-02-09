
<ul>
<g:each in="${optionSelected}" var="option">

 
<li><g:link controller= 'menu' action='execute' id="${option.id}"><g:message code="option.${option.path}" default="${option.title}" encodeAs="HTML" /></g:link></li>

</g:each>
</ul>

