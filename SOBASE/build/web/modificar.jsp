<%@page import="cat.urv.deim.sob.User"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*" %>
<%@ include file="includes/top.jsp" %>
<%@ page session="true" %>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/estils.css">
        <title>Gestor de TFGs - SOB</title>
        <script type="text/javascript">
            function habilitar(obj) {
                var hab;
                var msg = "L'estat seleccionat no requereix aquesta acció"
                var msg2 = " (Existent)";
                frm = obj.form;
                num = obj.selectedIndex;
                if (num === 1) { //ASSIGNAT
                    frm.descripcio.disabled = false;
                    frm.descripcio.placeholder = "Descripció";
                    frm.estudiants.disabled = false;
                    frm.estudiants.placeholder = "Estudiants (separats per comes sense espais)";
                    frm.recursos.disabled = true;
                    frm.recursos.placeholder = msg;
                    frm.dataDef.disabled = true;
                    frm.dataDef.placeholder = msg;
                    frm.qualificacio.disabled = true;
                    frm.qualificacio.placeholder = msg;
                } else if (num === 2) { //ACABAT
                    frm.descripcio.disabled = true;
                    frm.descripcio.placeholder = "Descripció"+msg2;
                    frm.estudiants.disabled = true;
                    frm.estudiants.placeholder = "Estudiants (separats per comes sense espais)"+msg2;
                    frm.recursos.disabled = false;
                    frm.recursos.placeholder = "Recursos";
                    frm.dataDef.disabled = true;
                    frm.dataDef.placeholder = msg;
                    frm.qualificacio.disabled = true;
                    frm.qualificacio.placeholder = msg;
                } else if (num === 3) { //PENDENT DE DEF
                    frm.descripcio.disabled = true;
                    frm.descripcio.placeholder = "Descripció"+msg2;
                    frm.estudiants.disabled = true;
                    frm.estudiants.placeholder = "Estudiants (separats per comes sense espais)"+msg2;
                    frm.recursos.disabled = true;
                    frm.recursos.placeholder = "Recursos"+msg2;
                    frm.dataDef.disabled = false;
                    frm.dataDef.placeholder = "Data de defensa (dd-MM-yyyy)";
                    frm.qualificacio.disabled = true;
                    frm.qualificacio.placeholder = msg;
                } else if (num === 4) { //DEFENSAT
                    frm.descripcio.disabled = true;
                    frm.descripcio.placeholder = "Descripció"+msg2;
                    frm.estudiants.disabled = true;
                    frm.estudiants.placeholder = "Estudiants (separats per comes sense espais)"+msg2;
                    frm.recursos.disabled = true;
                    frm.recursos.placeholder = "Recursos"+msg2;
                    frm.dataDef.disabled = true;
                    frm.dataDef.placeholder = "Data de defensa (dd-MM-yyyy)"+msg2;
                    frm.qualificacio.disabled = false;
                    frm.qualificacio.placeholder = "Qualificació";
                }
            }
        </script> 
    </head>
    <body>
        <% if (request.getParameter("modificar") == null) { %>
        <p class="contenidor">Formulari de registre</p>
        <form class="login_form" method="post" action="modificar.do">
            <input type="text" placeholder="Títol" name="titol">
            <select onchange="habilitar(this)" name="estat" style="padding: 1rem 1rem 0;vertical-align:middle;border-color: #c8c8c8;background-color: #efefef">
                <option value="NoEstat">SELECCIONAR ESTAT</option>
                <option value="Assignat">ASSIGNAT</option>
                <option value="Acabat">ACABAT</option>
                <option value="Pendent de defensa">PENDENT DE DEFENSA</option>
                <option value="Defensat">DEFENSAT</option>
            </select>
            <input type="text" placeholder="Descripció" name="descripcio">
            <input type="text" placeholder="Estudiants (separats per comes sense espais)" name="estudiants">
            <input type="text" placeholder="Recursos" name="recursos">
            <input type="text" placeholder="Data de defensa (dd-MM-yyyy)" name="dataDef">
            <input type="text" placeholder="Qualificació" name="qualificacio">
            <input class="btn_submit" type="submit" name="modificar" value="MODIFICAR">
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