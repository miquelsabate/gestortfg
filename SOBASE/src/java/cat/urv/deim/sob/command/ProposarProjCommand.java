package cat.urv.deim.sob.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpSession;

public class ProposarProjCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        HttpSession session = request.getSession();
        try {
            if ((request.getParameter("titol").equals("")) || (request.getParameter("professors").equals(""))
                    || (request.getParameter("estudis").equals(""))) {
                msg = "Has d'omplir tots els camps";
            } else if (session.getAttribute("user") == null) {
                msg = "Accés denegat";
            } else {
                String[] professors = request.getParameter("professors").split(",");
                String[] estudis = request.getParameter("estudis").split(",");

                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
                Statement stmt = con.createStatement();
                String query;
                ResultSet rs;
                /* ------- REQUISITS (professors i/o estudis existeixen?) --------- */
                int i;
                Boolean seguir = true;
                for (i = 0; (i < professors.length) && (seguir); i++) {
                    query = "SELECT nom_usuari FROM TFGDB.Professor WHERE nom_usuari = '" + professors[i] + "'";
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        // Existeix l'usuari especificat
                    } else {
                        msg = "Hi ha hagut un error (professor/s no existeixen)";
                        seguir = false;
                    }
                }
                for (i = 0; (i < estudis.length) && (seguir); i++) {
                    query = "SELECT id FROM TFGDB.Estudi WHERE id = '" + estudis[i] + "'";
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        // Existeix l'estudi especificat
                    } else {
                        msg = "Hi ha hagut un error (estudi/s no existeixen)";
                        seguir = false;
                    }
                }
                /* -------------------------------------------------------------- */
                if (seguir) {
                    String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                    query = "INSERT INTO TFGDB.Projecte (titol, estat, data_crea) VALUES ('" + request.getParameter("titol") + "', 'Proposat', '" + timeStamp + "')";
                    stmt.executeUpdate(query);
                    for (String prof : professors) {
                        for (String est : estudis) {
                            query = "INSERT INTO TFGDB.Relacio (titol, professor, estudi) VALUES ('" + request.getParameter("titol") + "', '" + prof + "', '" + est + "')";
                            stmt.executeUpdate(query);
                        }
                    }
                    msg = "S'ha afegit el projecte '" + request.getParameter("titol") + "' correctament";
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            msg += "" + e;
        }
        request.setAttribute("msg", msg);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/proposar.jsp").forward(request, response);
    }
}
