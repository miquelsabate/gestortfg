package cat.urv.deim.sob.command;

import cat.urv.deim.sob.Projecte;
import entitats.tfg.TfgDao;
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
            HttpServletResponse response) throws ServletException, IOException {
        String professor = request.getParameter("professor");
        TfgDao dao = new TfgDao();
        LinkedList<Projecte> llista = dao.findByProfessor(professor, false); //FALSE because of not API
        request.setAttribute("llistat", llista);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/proj-professor.jsp").forward(request, response);
    }
}
