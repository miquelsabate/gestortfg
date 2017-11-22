<%@page import="cat.urv.deim.sob.User"%>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="css/estils.css">
    <link href="https://fonts.googleapis.com/css?family=Julius+Sans+One" rel="stylesheet">
    <title>
      Gestor de TFGs - SOB 17/18
    </title>
  </head>
  <body>
    <div class="heading">
        <a href="<%=request.getContextPath() %>/actius.do"><div class="buttonMain">Projectes actius</div></a>
        <a href="<%=request.getContextPath() %>/anteriors.do"><div class="buttonMain">Projectes anteriors</div></a>
        <a href="<%=request.getContextPath() %>/tots.do"><div class="buttonMain">Tots els projectes</div></a>
        <!--<img class="headerimg" src="<%=request.getContextPath() %>/css/logo.png" alt=""/>-->
        <% if(session.getAttribute("user") == null) {  %>
        <a style="float:right;" href="<%=request.getContextPath() %>/signup.jsp"><div class="buttonMain">Registrar-se</div></a>
        <a style="float:right;" href="<%=request.getContextPath() %>/login.jsp"><div class="buttonMain">Login</div></a>
        <% }else { 
            User u = (User) session.getAttribute("user"); %>
        <a style="float:right;" href="<%=request.getContextPath() %>/signout.do"><div class="buttonMain">Tancar sessió</div></a>
        <a class="msgbenvinguda" style="float:right;" href="<%=request.getContextPath() %>/user.do"><%=u.getNomComplet()%></a>
        <% } %>
    </div>
  </body>
</html>
