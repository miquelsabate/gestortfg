package cat.urv.deim.sob.command;

import cat.urv.deim.sob.Projecte;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

public class ProfProjCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<Projecte>();
        /* -- REQUERIMENTS -- */
        
        /* ------------------ */
        try{
        Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            String query2 = "SELECT DISTINCT nom_usuari FROM TFGDB.Professor WHERE nom_usuari='"+request.getParameter("professor")+"' OR nom_complert='"+request.getParameter("professor")+"'";
            String query = "SELECT DISTINCT P.titol, P.estat, R.professor FROM TFGDB.Projecte P, TFGDB.Relacio R WHERE R.professor = ("+query2+")";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                    p = new Projecte(rs.getString("titol"),rs.getString("estat"),rs.getString("professor"));
                    llista.add(p);
            }
            con.close();
        }catch(SQLException | ClassNotFoundException e){ }
        request.setAttribute("llistat", llista);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/proj-professor.jsp").forward(request, response);
    }
}
