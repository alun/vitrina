        <g:menuDrilldownReset/>
        <g:menuCriteriaReset/>

<div class="body">
 
    <div class="options">
        <ul>
         
        <g:each in="${optionList}" var="option">
        <g:if test="${securityService.isAnonymous()}">
          <g:if test="${((option.Anonymous==true))}">
            <g:if test="${option.type=='submenu'}">
           
               <li><a href="?unfoldMenu=${option.id}"><g:message code="option.${option.path}" default="${option.title}" encodeAs="HTML" /></a></li>
            
          
               <g:if test="${session.currentMenuItem!=null}">
                   <g:if test="${option.path == session.currentMenuItem.path}">
           
                        <g:selected/>
                   </g:if>
               </g:if>
            </g:if> 
             
            <g:if test="${option.type!='submenu'}">
               <li><g:link controller ='menu' action='execute' id="${option.id}"><g:message code="option.${option.path}" default="${option.title}" encodeAs="HTML" /></g:link></li>
            </g:if>
          </g:if>  
        </g:if>
        <g:if test="${!securityService.isAnonymous()}">
          <g:if test="${option.Anonymous==false}">
            <g:if test="${option.type=='submenu'}">
           
               <li><a href="?unfoldMenu=${option.id}"><g:message code="option.${option.path}" default="${option.title}" encodeAs="HTML" /></a></li>
            
          
               <g:if test="${session.currentMenuItem!=null}">
                   <g:if test="${option.path == session.currentMenuItem.path}">
           
                        <g:selected/>
                   </g:if>
               </g:if>
            </g:if> 
             
            <g:if test="${option.type!='submenu'}">
               <li><g:link controller ='menu' action='execute' id="${option.id}"><g:message code="option.${option.path}" default="${option.title}" encodeAs="HTML" /></g:link></li>
            </g:if>
          </g:if>
        </g:if>         
        </g:each>
            
        </ul>
    </div>
</div>

