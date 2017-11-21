<%@page import="java.util.LinkedList"%>
<%@page import="cat.urv.deim.sob.Projecte"%>
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
        <%  LinkedList<Projecte> llista = (LinkedList<Projecte>) request.getAttribute("llistat");
            User user = (User) session.getAttribute("user");
            if (user.getNomUsuari().equals("")||user.getPass().equals("")||user.getNomComplet().equals("")){
                out.println("<div class='contenidor'>");
                out.println("Usuari i/o contrasenya incorrectes");
                out.println("</div>");
            }else{
                out.println("<div class='contenidor'>");
                out.println("Benvingut "+user.getNomComplet()+"!");
                out.println("<br>Tipus: "+user.getTipus());
                out.println("<br>Usuari: "+user.getNomUsuari());
                out.println("</div>");
                
                /* --- OPCIONS DE PROFESSOR O ALUMNE -- */
                
                if (user.getTipus().equals("Estudiant")){
                    out.println("<br>");
                    out.println("<div class='contenidor' style='background-color:blue'>");
                    out.println("Els usuaris de tipus 'Alumne' no tenen cap eina disponible actualment");
                    out.println("</div>");
                }
                else if(user.getTipus().equals("Professor")){
                    out.println("<br>");
                    
                    out.println("<div class='contenidor2'");
                    out.println("<br><br>");
                    out.println("EINES DISPONIBLES<br><br>");
                    out.println("<a style='text-decoration:none;' href='proposar.jsp'><div class='button'>Proposar TFG</div></a>");
                    out.println("<a style='text-decoration:none;' href='proposar.jsp'><div class='button'>Modificar estat TFG</div></a>");
                    out.println("<br>");
                    out.println("</div>");
                    
                    out.println("<br>");
                    out.println("<div class='contenidor'>");
                    out.println("Els teus projectes");
                    out.println("</div><br>"); %>
                    <table>
                        <thead>
                            <tr>
                                <th>Títol TFG</th>
                                <th>Estat</th>
                            </tr>
                        </thead>
                        <tbody>
                <%  out.println("<tr>");
                    for (Projecte proj : llista) {
                        out.print("<tr><td>" + proj.getTitol() + "</td><td>" + proj.getEstat() + "</td><td>" + proj.getProfessor() + "</td></tr>");
                    }
                %>
            </tbody>
        </table>
                <% }               
            }
        %>
    </body>
</html>