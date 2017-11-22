package cat.urv.deim.sob.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModificarProjCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String msg = "";
        try {
            String[] estudiants = null;

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            String query;
            ResultSet rs;
            int i;
            Boolean seguir = true;
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
            query = "SELECT titol FROM TFGDB.Projecte WHERE titol = '" + request.getParameter("titol") + "'";
            ResultSet rs3 = stmt.executeQuery(query);
            String existeix = "";

            while (rs3.next()) {
                existeix = rs3.getString("titol");
            }
            if (request.getParameter("titol").equals("")) {
                msg = "Has d'introduïr un títol de projecte";
            } else if ((request.getParameter("estat").equals("NoEstat"))) {
                msg = "Has de seleccionar un estat";
            } else if (existeix.equals("")) {
                msg = "El projecte " + request.getParameter("titol") + " no existeix a la base de dades.";
            } else {
                String estatActual = "";
                String q = "SELECT estat FROM TFGDB.Projecte WHERE titol='" + request.getParameter("titol") + "'";
                ResultSet rs2 = stmt.executeQuery(q);
                while (rs2.next()) {
                    estatActual = rs2.getString("estat");
                }

                switch (request.getParameter("estat")) {
                    case "Assignat":
                        if ((request.getParameter("descripcio").equals("")) || (request.getParameter("estudiants").equals(""))) {
                            msg = "Has d'omplir tots els camps necessaris";
                        } else if (!estatActual.equals("Proposat")) {
                            msg = "No es pot passar de " + estatActual + " a Assignat";
                        } else {
                            estudiants = request.getParameter("estudiants").split(",");
                            for (i = 0; (i < estudiants.length) && (seguir); i++) {
                                query = "SELECT nom_usuari FROM TFGDB.Estudiant WHERE nom_usuari = '" + estudiants[i] + "'";
                                rs = stmt.executeQuery(query);
                                if (rs.next()) {
                                    // Existeix l'usuari especificat
                                } else {
                                    msg = "Hi ha hagut un error (estudiant/s no existeixen)";
                                    seguir = false;
                                }
                            }
                            if (seguir) {
                                query = "UPDATE TFGDB.Projecte SET estat='Assignat', descripcio='" + request.getParameter("descripcio") + "', data_mod='" + timeStamp + "' WHERE titol = '" + request.getParameter("titol") + "'";
                                stmt.executeUpdate(query);
                                for (String est : estudiants) {
                                    query = "UPDATE TFGDB.Relacio SET estudiant='" + est + "' WHERE titol = '" + request.getParameter("titol") + "'";
                                    stmt.executeUpdate(query);
                                }
                                msg = "S'ha modificat el projecte '" + request.getParameter("titol") + "' correctament";
                            }
                        }
                        break;

                    case "Acabat":
                        if (request.getParameter("recursos").equals("")) {
                            msg = "Has d'omplir tots els camps necessaris";
                        } else if (!estatActual.equals("Assignat")) {
                            msg = "No es pot passar de " + estatActual + " a Acabat";
                        } else {
                            query = "UPDATE TFGDB.Projecte SET estat='Acabat', recursos='" + request.getParameter("recursos") + "', data_mod='" + timeStamp + "' WHERE titol = '" + request.getParameter("titol") + "'";
                            stmt.executeUpdate(query);
                            msg = "S'ha modificat el projecte '" + request.getParameter("titol") + "' correctament";
                        }
                        break;

                    case "Pendent de defensa":
                        if (request.getParameter("dataDef").equals("")) {
                            msg = "Has d'omplir tots els camps necessaris";
                        } else if (!estatActual.equals("Acabat")) {
                            msg = "No es pot passar de " + estatActual + " a Pendent de defensa";
                        } else {
                            query = "UPDATE TFGDB.Projecte SET estat='Pendent de defensa', data_def='" + request.getParameter("dataDef") + "', data_mod='" + timeStamp + "' WHERE titol = '" + request.getParameter("titol") + "'";
                            stmt.executeUpdate(query);
                            msg = "S'ha modificat el projecte '" + request.getParameter("titol") + "' correctament";
                        }
                        break;
                    case "Defensat":
                        if (request.getParameter("qualificacio").equals("")) {
                            msg = "Has d'omplir tots els camps necessaris";
                        } else if (!estatActual.equals("Pendent de defensa")) {
                            msg = "No es pot passar de " + estatActual + " a Defensat";
                        } else {
                            query = "UPDATE TFGDB.Projecte SET estat='Defensat', qualificacio='" + request.getParameter("qualificacio") + "', data_mod='" + timeStamp + "' WHERE titol = '" + request.getParameter("titol") + "'";
                            stmt.executeUpdate(query);
                            msg = "S'ha modificat el projecte '" + request.getParameter("titol") + "' correctament";
                        }
                        break;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            msg += "" + e;
        }
        request.setAttribute("msg", msg);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/modificar.jsp").forward(request, response);
    }
}
