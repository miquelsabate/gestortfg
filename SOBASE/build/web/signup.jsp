<%@page import="cat.urv.deim.sob.User"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*" %>
<%@ include file="includes/top.jsp" %>
<%@ page session="true" %>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/estils.css">
	<title>Gestor de TFGs - SOB</title>
    </head>
    <body>
       <% if (request.getParameter("registrar") == null) { %>
       <p class="contenidor">Formulari de registre</p>
        <form class="login_form" method="post" action="signup.do">
            <input type="text" placeholder="Usuari" name="user">
            <input type="password" placeholder="Contrasenya" name="pass">
            <input type="password" placeholder="Repetir contrasenya" name="pass2">
            <input type="text" placeholder="Nom complet" name="nom_complet">
            <select name="tipus" style="padding: 1rem 1rem 0;vertical-align:middle;border-color: #c8c8c8;background-color: #efefef">
                <option value="Estudiant">ESTUDIANT</option>
                <option value="Professor">PROFESSOR</option>
            </select>
            <input class="btn_submit" type="submit" name="registrar" value="REGISTRAR">
        </form>
        
        <% } else {
                    String msg = (String) request.getAttribute("msg");
            
                    out.println("<div class='contenidor'>");
                    out.println(msg);
                    out.println("</div>");
                   }
            %>
    </body>
</html>