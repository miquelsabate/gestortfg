package cat.urv.deim.sob.command;

import cat.urv.deim.sob.Projecte;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

public class InfoProjCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<Projecte>();
        /* -- REQUERIMENTS -- */

 /* ------------------ */
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            String query = "SELECT titol, descripcio, estat, recursos, data_def, qualificacio, data_crea, data_mod FROM TFGDB.Projecte WHERE titol = '" + request.getParameter("projecte") + "'";
            String query2;
            String titol;
            String prof = "";
            String estudiant = "";
            String estudi = "";
            Statement stmt2 = con.createStatement();
            ResultSet rs2;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                p = new Projecte(rs.getString("titol"), rs.getString("estat"), "");
                p.setDescripcio(rs.getString("descripcio"));
                p.setRecursos(rs.getString("recursos"));
                p.setData_def(rs.getString("data_def"));
                p.setQualificacio(rs.getString("qualificacio"));
                p.setData_crea(rs.getString("data_crea"));
                p.setData_mod(rs.getString("data_mod"));
                titol = rs.getString("titol");
                query2 = "SELECT professor, estudiant, estudi FROM TFGDB.Relacio WHERE titol = '" + titol + "'";
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    prof += rs2.getString("professor") + " ";
                    estudiant += rs2.getString("estudiant");
                    estudi += rs2.getString("estudi");
                }
                p.setProfessor(prof);
                p.setEstudiant(estudiant);
                p.setEstudi(estudi);
                llista.add(p);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        request.setAttribute("llistat", llista);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/info-proj.jsp").forward(request, response);
    }
}
