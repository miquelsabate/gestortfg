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
        <%
            out.println("<div id='errorlogin' class='contenidor'>");
            out.println("Usuari i/o contrasenya incorrectes");
            out.println("</div>"); %>
        <script>
            document.getElementById("errorlogin").style.display = "none";
        </script>
        <form class="login_form" method="post" action="login.do">
            <select name="tipus" style="padding: 1rem 1rem 0;vertical-align:middle;border-color: #c8c8c8;background-color: #efefef">
                <option value="Estudiant">ESTUDIANT</option>
                <option value="Professor">PROFESSOR</option>
            </select>
            <input type="text" placeholder="&#128100; Usuari" name="user">
            <input type="password" placeholder="&#x1F512; Contrasenya" name="pass">
            <input class="btn_submit" type="submit" name="entrar" value="ENTRAR">
        </form>

        <%  if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if (user.getNomUsuari().equals("") || user.getPass().equals("") || user.getNomComplet().equals("")) {
                session.setAttribute("user", null);%>
        <script>
            document.getElementById("errorlogin").style.display = "block";
        </script >
            <%} else {
                        response.sendRedirect("user.do");
                    }
                }
            %>
    </body>
        </html>