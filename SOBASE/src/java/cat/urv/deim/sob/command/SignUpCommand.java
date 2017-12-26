package cat.urv.deim.sob.command;

import entitats.tfg.TfgDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.*;

public class SignUpCommand implements Command {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        TfgDao dao = new TfgDao();
        dao.signUp(request, response);
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/signup.jsp").forward(request, response);
    }
}
