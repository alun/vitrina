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
		
		 <g:goodsOwner goods="${goods}">
	
			  <div class="principal">
	          	Это ваш товар,  
	          	<shiro:principal/>
	          </div>
	          
		
		Вы можете произвести следующие действия:
		<li><g:link controller="goods" action="delete">удалить товар</g:link></li>
		<li><g:link controller="goods" action="edit">редактировать данные</g:link></li>
		<li><g:link controller="goods" action="update">обновить</g:link></li>
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