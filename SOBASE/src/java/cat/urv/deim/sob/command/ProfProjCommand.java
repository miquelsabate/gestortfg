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
            String query = "SELECT titol FROM TFGDB.Relacio WHERE professor = '"+request.getParameter("professor")+"'";
            String query2;
            String titol;
            Statement stmt2 = con.createStatement();
            ResultSet rs2;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                titol = rs.getString("titol");
                query2 ="SELECT titol, estat FROM TFGDB.Projecte WHERE titol = '"+rs.getString("titol")+"'";
                rs2 = stmt2.executeQuery(query2);
                while(rs2.next()){
                    p = new Projecte(titol,rs2.getString("estat"),request.getParameter("professor"));
                    llista.add(p);
                }
            }
            con.close();
        }catch(SQLException | ClassNotFoundException e){ }
        request.setAttribute("llistat", llista);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/proj-professor.jsp").forward(request, response);
    }
}
