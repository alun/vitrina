<ul>
         
        <g:each in="${itemsList}" var="item">
        
        
	       <g:isItemViewable menuItem="${item}">
	       
	       <li>
	       	<g:isItemCurrent menuItem="${item}">
	       		<i>
	       	</g:isItemCurrent>
	       	
	       	<g:menuLink menuItem="${item}"/>
	       	
	       	<g:isItemCurrent menuItem="${item}">
	       		</i>
	       	</g:isItemCurrent>
	       	
            <g:renderSubmenu menuItem="${item}"/>
	       	
	       </li>
	       
	       </g:isItemViewable>
	       
        </g:each>
            
</ul>


