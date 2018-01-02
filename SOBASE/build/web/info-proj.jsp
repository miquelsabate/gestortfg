<%@page import="java.util.LinkedList"%>
<%@page import="cat.urv.deim.sob.Projecte"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*" %>
<%@ include file="includes/top.jsp" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/estils.css">
        <link href="https://fonts.googleapis.com/css?family=Julius+Sans+One" rel="stylesheet">
        <title>Gestor de TFGs - SOB</title>
    </head>
    <body>
        <%
            LinkedList<Projecte> llista = (LinkedList<Projecte>) request.getAttribute("llistat");
            out.println("<div class='contenidor2' style='width: 40%'>Informació del projecte</div><br>");
            for (Projecte proj : llista) {
                out.println("<div class='infoProj'>");
                out.println("Títol<br>");
                out.println("<div class='infoContenidor'>" + proj.getTitol() + "</div><br>");
                out.println("Estat<br>");
                out.println("<div class='infoContenidor'>" + proj.getEstat() + "</div><br>");
                out.println("Professor/s<br>");
                out.println("<div class='infoContenidor'>" + proj.getProfessor() + "</div><br>");
                out.println("Estudi<br>");
                proj.setEstudi(proj.getEstudi().replace("null", ""));
                out.println("<div class='infoContenidor'>" + proj.getEstudi() + "</div><br>");
                if (proj.getEstudiant() != null) {
                    proj.setEstudiant(proj.getEstudiant().replace("null", ""));
                    out.println("Estudiant/s<br>");
                    out.println("<div class='infoContenidor'>" + proj.getEstudiant() + "</div><br>");
                }
                if (proj.getDescripcio() != null) {
                    out.println("Descripció<br>");
                    out.println("<div class='infoContenidor'>" + proj.getDescripcio() + "</div><br>");
                }
                if (proj.getRecursos() != null) {
                    out.println("Recursos<br>");
                    out.println("<div class='infoContenidor'>" + proj.getRecursos() + "</div><br>");
                }
                if (proj.getData_crea() != null) {
                    out.println("Data de creació:<br>");
                    out.println("<div class='infoContenidor'>" + proj.getData_crea() + "</div><br>");
                }
                if (proj.getData_mod() != null) {
                    out.println("Data de modificació<br>");
                    out.println("<div class='infoContenidor'>" + proj.getData_mod() + "</div><br>");
                }
                if (proj.getData_def() != null) {
                    out.println("Data de defensa<br>");
                    out.println("<div class='infoContenidor'>" + proj.getData_def() + "</div><br>");
                }
                if (proj.getQualificacio() != null) {
                    out.println("Qualificació<br>");
                    out.println("<div class='infoContenidor'>" + proj.getQualificacio() + "</div><br>");
                }
                out.println("</div>");
            }
        %>
    </body>
</html>