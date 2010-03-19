<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title><g:message code="Account creation"/></title>         
    </head>
    <body>
  <table>
   <td> 
    <tr><g:sortableColumn property="username" title="name"/></tr>
                       <tr>
                   	        
                      
                      <g:each in="${users}">
                    
                        
                         ${it.username}
                        
                       
                      </g:each>
                      

                      </tr> 
   </td>
   <td> 
    <tr><g:sortableColumn property="blocked" title="status"/></tr>
            <tr>                      
                   	        

                         <g:each in="${users}">
                    
                        
                            ${it.blocked}<g:checkBox name="block" value="${true}" />
                        
                       
                         </g:each>
                         
                      </tr>
   </td>
   
  </table>
                
                  
    
    
  
    
 
      
       <g:paginate next="Forward" prev="Back"
            maxsteps="0" controller="user"
            action="list" total="${users.count()}" />
    </body>
</html>