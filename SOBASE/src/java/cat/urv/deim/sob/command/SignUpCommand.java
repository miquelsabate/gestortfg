package cat.urv.deim.sob.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;

public class SignUpCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String msg;
        try {
            if ((request.getParameter("user").equals("")) || (request.getParameter("pass").equals(""))
                    || (request.getParameter("pass2").equals("")) || (request.getParameter("nom_complet").equals(""))) {
                msg = "Has d'omplir tots els camps";
            } else if (!request.getParameter("user").equals(request.getParameter("pass2"))) {
                msg = "Les contrasenyes no coincideixen";
            } else {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
                Statement stmt = con.createStatement();
                String query = "INSERT INTO TFGDB." + request.getParameter("tipus") + " (nom_usuari, contrasenya, nom_complert) VALUES ('" + request.getParameter("user") + "', '" + request.getParameter("pass") + "', '" + request.getParameter("nom_complet") + "')";
                stmt.executeUpdate(query);
                msg = "S'ha afegit l'usuari " + request.getParameter("user") + " correctament";
            }
        } catch (SQLException | ClassNotFoundException e) {
            msg = "" + e;
        }
        request.setAttribute("msg", msg);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/signup.jsp").forward(request, response);
    }
}
