<html>
<head>
<meta name="layout" content="main" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Просмотр доступных товаров</title>
</head>
<body>

	<div id="goodsNavigation">
		<g:link action="prev">&lt;</g:link> | 
		<g:link action="next">&gt;</g:link>
		<g:notAnonymous>
		<g:moderator>
	    
                  
              
		  <ul><span class="button"><input class="AssertGoods" type="submit" value="assert" /></span></ul>
		 <g:form action="unAssertGoods" method="post" >
		  <ul><span class="button"><input class="UnAssertGoods" type="submit" value="send reason" /></span></ul>
		 
                             <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="reason">reason:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:GoodsInstance,field:'reason','errors')}">
                                    <input type="text" id="reason" name="reason" value="${fieldValue(bean:reasonText,field:'reason')}"/>
                                </td>
                            </tr>                
           </g:form>
		   
		</g:moderator>
		 <g:goodsOwner>
	
			  <div class="principal">
	          	Это ваш товар,  
	          	<shiro:principal/>
	          </div>
	          
		
		Вы можете произвести следующие действия:
		<li><g:link controller="goods" action="delete">удалить товар</g:link></li>
		<li><g:link controller="goods" action="edit">редактировать данные</g:link></li>
		<li><g:link controller="goods" action="update">обновить</g:link></li>
		<g:notAsserted/>
		
	     </g:goodsOwner>
		</g:notAnonymous>
	</div>
	
	<div id="goods">
		<div class="title">${goods.name}</div>
		<img src="${g.createLink (action:"image", id:goods.id)}"/>
		<div class="description">${goods.description}</div>

	</div>
	
</body>
</html>