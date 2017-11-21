package cat.urv.deim.sob.command;

import cat.urv.deim.sob.Projecte;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

public class ActiusProjCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Projecte p;
        String prof = "";
        LinkedList<String> prova = new LinkedList<String>();
        LinkedList<Projecte> llista = new LinkedList<Projecte>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            //String query = "SELECT P.titol, P.estat, Pr.nom_complert FROM TFGDB.Projecte P, TFGDB.Relacio R, TFGDB.Professor Pr WHERE P.estat != 'Defensat' AND Pr.nom_usuari = (SELECT Professor FROM TFGDB.Relacio) ORDER BY P.titol";
<<<<<<< HEAD
            String query = "SELECT titol, estat FROM TFGDB.Projecte WHERE estat !='Defensat' ORDER BY titol";
=======

            String query = "SELECT titol, estat FROM TFGDB.Projecte ORDER BY titol";
>>>>>>> 8139df0b950e58e63d3e11b0fdc23fbbe0530170
            String query2;
            ResultSet rs2;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
<<<<<<< HEAD
                    prof = "";
                    query2 = "SELECT professor FROM TFGDB.Relacio WHERE titol='"+rs.getString("titol")+"'";
                    rs2 = stmt2.executeQuery(query2);
                    while (rs2.next()){
                       prof += "<a href='proj-professor.do?professor="+rs2.getString("professor")+"'>"+rs2.getString("professor")+"</a> ";
                       //if (!(rs2.isLast())) prof += ", ";
                    }
                    p = new Projecte(rs.getString("titol"),rs.getString("estat"), prof);
                    llista.add(p);
=======
                prof = "";
                prova.add(rs.getString("titol"));
                //prova += rs.getString("titol");
                query2 = "SELECT professor FROM TFGDB.Relacio WHERE titol='" + rs.getString("titol") + "'";
                rs2 = stmt.executeQuery(query2);
                while (rs2.next()) {
                    prof += rs2.getString("professor") + ", ";
                }
                p = new Projecte(rs.getString("titol"), rs.getString("estat"), prof);
                llista.add(p);
>>>>>>> 8139df0b950e58e63d3e11b0fdc23fbbe0530170
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        request.setAttribute("llistat", llista);
        request.setAttribute("prova", prova);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/actius.jsp").forward(request, response);
    }
}
