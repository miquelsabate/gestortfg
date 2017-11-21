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
        <a style="text-decoration:none;" href="<%=request.getContextPath() %>/actius.do"><div class="buttonMain">Projectes actius</div></a>
        <a style="text-decoration:none;" href="<%=request.getContextPath() %>/anteriors.do"><div class="buttonMain">Projectes anteriors</div></a>
        <a style="text-decoration:none;" href="<%=request.getContextPath() %>/tots.do"><div class="buttonMain">Tots els projectes</div></a>
        <!--<a style="text-decoration:none;" href="<%=request.getContextPath() %>/proj-professor.do"><div class="buttonMain">Projectes per professor</div></a>-->
        <img class="headerimg" src="<%=request.getContextPath() %>/css/logo.png" alt=""/>
        <a style="text-decoration:none; float:right;" href="<%=request.getContextPath() %>/signup.jsp"><div class="buttonMain">Registrar-se</div></a>
        <a style="text-decoration:none; float:right;" href="<%=request.getContextPath() %>/login.jsp"><div class="buttonMain">Login</div></a>
    </div>
  </body>
</html>
