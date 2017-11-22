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
                out.println("<div class='infoContenidor'>"+proj.getTitol()+"</div><br>");
                out.println("Estat<br>");
                out.println("<div class='infoContenidor'>"+proj.getEstat()+"</div><br>");
                out.println("Professor/s: "+proj.getProfessor()+"<br><br>");
                out.println("Estudiant/s: "+proj.getEstudiant()+"<br><br>");
                out.println("Estudi/s: "+proj.getEstudi()+"<br><br>");
                if(proj.getDescripcio() != null) out.println("Descripció: "+proj.getDescripcio()+"<br><br>");
                if(proj.getRecursos() != null) out.println("Recursos: "+proj.getRecursos()+"<br><br>");
                if(proj.getData_crea() != null) out.println("Data de creació: "+proj.getData_crea()+"<br><br>");
                if(proj.getData_mod() != null) out.println("Data de modificació: "+proj.getData_mod()+"<br><br>");
                if(proj.getData_def() != null) out.println("Data de defensa: "+proj.getData_def()+"<br><br>");
                if(proj.getQualificacio() >= 0) out.println("Qualificació: "+proj.getQualificacio()+"<br><br>");
                out.println("</div>");
                
                
                out.print("<br><br><br>"+proj.getTitol() + " | " + proj.getEstat() + " | "+ proj.getProfessor() +" | "
                    +proj.getDescripcio()+" | "+proj.getRecursos()+" | "+proj.getData_crea()+" | "+proj.getData_def()+" | "
                    +proj.getData_mod()+" | "+proj.getQualificacio());
            }
            %>
    </body>
    </html>