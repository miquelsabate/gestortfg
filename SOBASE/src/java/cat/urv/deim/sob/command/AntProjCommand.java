package cat.urv.deim.sob.command;

import cat.urv.deim.sob.Projecte;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

public class AntProjCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<Projecte>();
        try{
        Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            //String query = "SELECT P.titol, P.estat, Pr.nom_complert, P.qualificacio FROM TFGDB.Projecte P, TFGDB.Relacio R, TFGDB.Professor Pr WHERE P.estat = 'Defensat' AND Pr.nom_usuari = (SELECT Professor FROM TFGDB.Relacio) ORDER BY P.titol";
            String query = "SELECT DISTINCT P.titol, P.estat, R.professor, P.qualificacio FROM TFGDB.Projecte P,  TFGDB.Relacio R WHERE P.estat = 'Defensat' AND P.titol = R.titol ORDER BY P.titol";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                    p = new Projecte(rs.getString("titol"),rs.getString("estat"),rs.getString("professor"));
                    p.setQualificacio(rs.getFloat("qualificacio"));
                    llista.add(p);
            }
            con.close();
        }catch(SQLException | ClassNotFoundException e){ }
        request.setAttribute("llistat", llista);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/anteriors.jsp").forward(request, response);
    }
}
