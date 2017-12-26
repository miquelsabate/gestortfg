package cat.urv.deim.sob.command;

import entitats.tfg.TfgDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpSession;

public class ProposarProjCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        TfgDao dao = new TfgDao();
        dao.createProject(request, response);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/proposar.jsp").forward(request, response);
    }
}
