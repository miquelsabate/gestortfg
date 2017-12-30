package cat.urv.deim.sob.command;

import cat.urv.deim.sob.Projecte;
import entitats.tfg.*;
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
        TfgDao dao = new TfgDao();
        LinkedList<Projecte> llista = dao.findByActiveProjects();
        request.setAttribute("llistat", llista);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/actius.jsp").forward(request, response);
    }
}
