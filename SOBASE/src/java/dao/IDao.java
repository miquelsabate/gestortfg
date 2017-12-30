package dao;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDao {

	public void findByActiveProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	public void findByAnteriorProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	public void findByInfoProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void modifyProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void findByProfessor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void createProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
        public void userZone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
