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
        <table>
            <thead>
                <tr>
                    <th>Títol TFG</th>
                    <th>Estat</th>
                    <th>Professor/a</th>
                    <th>Estudi/s</th>
                </tr>
            </thead>
            <tbody>
                <%
                    LinkedList<Projecte> llista = (LinkedList<Projecte>) request.getAttribute("llistat");
                    out.println("<tr>");
                    for (Projecte proj : llista) {
                        out.print("<tr><td>" + proj.getTitol() + "</td><td>" + proj.getEstat() + "</td><td>" + proj.getProfessor() + "</td><td>" + proj.getEstudi() + "</td></tr>");
                    }
                %>
            </tbody>
        </table>
    </body>
</html>