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
        public LinkedList<?> findByProfessor(String professor) throws ServletException, IOException;
        public String createProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public LinkedList<?> findAll(boolean api) throws ServletException, IOException; //TRUE if API is calling the method
        public LinkedList<?> userZone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
