package dao;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDao {

	public LinkedList<?> findByActiveProjects() throws ServletException, IOException;
	public LinkedList<?> findByAnteriorProjects() throws ServletException, IOException;
	public LinkedList<?> findByInfoProject(String projecte) throws ServletException, IOException;
        public LinkedList<?> login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void modifyProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public LinkedList<?> findByProfessor(String professor, boolean api) throws ServletException, IOException; //TRUE if API is calling the method
        public String createProject(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException;
        public String createProjectAPI(String titol, String prof, String est) throws ServletException, IOException;
        public void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public LinkedList<?> findAll(boolean api) throws ServletException, IOException; //TRUE if API is calling the method
        public LinkedList<?> userZone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public LinkedList<?> findProfWithProjects();
        public LinkedList<?> findInfoProf(String professor);
        public LinkedList<?> findByState(String state);
        public boolean checkUser(String user, String pass);
        public boolean checkOwner(String user, String titol);
        public String assignProjectAPI(String estudiants, String professor, String estudi, String titol);


}
