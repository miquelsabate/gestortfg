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
       <% if (request.getParameter("proposar") == null) { %>
       <p class="contenidor">Formulari de registre</p>
        <form class="login_form" method="post" action="proposar.do">
            <input type="text" placeholder="Títol" name="titol">
            <input type="text" placeholder="Professors (separats per comes sense espais)" name="professors">
            <input type="text" placeholder="Estudis (separats per comes sense espais)" name="estudis">
            <input class="btn_submit" type="submit" name="proposar" value="PROPOSAR">
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