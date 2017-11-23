package cat.urv.deim.sob.command;

import cat.urv.deim.sob.Projecte;
import cat.urv.deim.sob.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        User u = new User("", "", "");
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<>();
        try {
            if ((request.getParameter("user").equals("")) || (request.getParameter("pass").equals(""))) {

            } else {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root")) {
                    Statement stmt = con.createStatement();
                    String query = "SELECT contrasenya, nom_complert FROM TFGDB." + request.getParameter("tipus") + " WHERE nom_usuari = '" + request.getParameter("user") + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        if (rs.getString("contrasenya").equals(request.getParameter("pass"))) {
                            u = new User(request.getParameter("user"), request.getParameter("pass"), rs.getString("nom_complert"));
                            u.setTipus(request.getParameter("tipus"));
                        }
                    }
                    String query2 = "SELECT titol, estat FROM TFGDB.Projecte ORDER BY titol";
                    ResultSet rs2 = stmt.executeQuery(query2);
                    while (rs2.next()) {
                        p = new Projecte(rs.getString("titol"), rs.getString("estat"), "");
                        llista.add(p);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
        }
        request.setAttribute("llistat", llista);
        HttpSession session = request.getSession();
        session.setAttribute("user", u);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
