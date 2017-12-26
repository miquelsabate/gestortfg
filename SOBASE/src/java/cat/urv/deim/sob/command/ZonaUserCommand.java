package cat.urv.deim.sob.command;

import cat.urv.deim.sob.Projecte;
import cat.urv.deim.sob.User;
import entitats.tfg.TfgDao;
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
            HttpServletResponse response) throws ServletException, IOException {
        TfgDao dao = new TfgDao();
        dao.userZone(request, response);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/zonauser.jsp").forward(request, response);
    }
}
