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

public class LoginCommand implements Command {
    
    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        User u = new User ("","","");
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<>();
        try{
            if((request.getParameter("user").equals("")) || (request.getParameter("pass").equals(""))){
                  
                } else{
                   Class.forName("org.apache.derby.jdbc.ClientDriver");
                try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root")) {
                    Statement stmt = con.createStatement();
                    String query = "SELECT contrasenya, nom_complert FROM TFGDB."+request.getParameter("tipus")+" WHERE nom_usuari = '"+request.getParameter("user")+"'";
                    ResultSet rs = stmt.executeQuery(query);
                    while(rs.next()){
                        if (rs.getString("contrasenya").equals(request.getParameter("pass"))){
                            u = new User(request.getParameter("user"), request.getParameter("pass"), rs.getString("nom_complert"));
                            u.setTipus(request.getParameter("tipus"));
                        }
                    }

                    //String query2 = "SELECT P.titol, P.estat, Pr.nom_complert FROM TFGDB.Projecte P, TFGDB.Relacio R, TFGDB.Professor Pr WHERE Pr.nom_usuari = '"+request.getParameter("user")+"' ORDER BY P.estat";
                    String query2 = "SELECT titol, estat FROM TFGDB.Projecte";
                    ResultSet rs2 = stmt.executeQuery(query2);
                    while (rs2.next()) {
                        p = new Projecte(rs.getString("titol"),rs.getString("estat"),"Prova");
                        llista.add(p);
                    }
                }
                }
        }catch(SQLException | ClassNotFoundException e){ }
        request.setAttribute("llistat", llista);
        request.setAttribute("user", u);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/zonauser.jsp").forward(request, response);
        //response.sendRedirect("zonauser.jsp");
    }
}
