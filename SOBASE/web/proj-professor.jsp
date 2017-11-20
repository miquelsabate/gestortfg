<%@page import="java.util.LinkedList"%>
<%@page import="cat.urv.deim.sob.Projecte"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*" %>
<%@ include file="includes/top.jsp" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/estils.css">
	<title>Gestor de TFGs - SOB</title>
    </head>
    <body>
    <% if (request.getParameter("cercar") == null) { %>
        <form class="login_form" method="post" action="proj-professor.do">
            <input type="text" placeholder="Usuari o nom complert professor/a" name="id_prof">
            <input class="btn_submit" type="submit" name="cercar" value="Cercar">
        </form>
    <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>T�tol TFG</th>
                    <th>Estat</th>
                    <th>Professor/a</th>
                </tr>
            </thead>
            <tbody>
            <%
            LinkedList<Projecte> llista = (LinkedList<Projecte>) request.getAttribute("llistat");
            out.println("<tr>");
            for (Projecte proj : llista) {
                    out.print("<tr><td>"+ proj.getTitol() + "</td><td>" + proj.getEstat() + "</td><td>"+ proj.getProfessor() +"</td></tr>");
            }
            }
            %>
            </tbody>
        </table>
    </body>
    </html>