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

public class ZonaUserCommand implements Command {
    
    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        User u;
        Projecte p;
        HttpSession session = request.getSession();
        LinkedList<Projecte> llista = new LinkedList<>();
        try{
            if(session.getAttribute("user") == null){
                  
                } else{
                   Class.forName("org.apache.derby.jdbc.ClientDriver");
                   u = (User) session.getAttribute("user");
                try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root")) {
                    Statement stmt = con.createStatement();
                    String query = "SELECT titol FROM TFGDB.Relacio WHERE "+u.getTipus()+" = '"+u.getNomUsuari()+"'";
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2;
                    String query2;
                    String titol; 
                    ResultSet rs = stmt.executeQuery(query);
                    while(rs.next()){
                        titol = rs.getString("titol");
                        query2 = "SELECT titol, estat FROM TFGDB.PROJECTE WHERE titol='"+titol+"'";
                        rs2 = stmt2.executeQuery(query2);
                        while (rs2.next()) {
                            p = new Projecte(rs.getString("titol"),rs2.getString("estat"),"");
                            llista.add(p);
                        }
                }
                }}
        }catch(SQLException | ClassNotFoundException e){ }
        request.setAttribute("llistat", llista);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/zonauser.jsp").forward(request, response);
        //response.sendRedirect("zonauser.jsp");
    }
}
