package entitats.tfg;

import cat.urv.deim.sob.Projecte;
import cat.urv.deim.sob.User;
import java.sql.*;
import java.util.*;
import dao.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TfgDao implements IDao {
	
	public LinkedList<Projecte> findByActiveProjects() throws ServletException, IOException {
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<Projecte>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            String query = "SELECT titol, estat FROM TFGDB.Projecte WHERE estat !='Defensat' ORDER BY titol";
            String query2;
            ResultSet rs2;
            String prof;
            String estudi;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                prof = "";
                query2 = "SELECT DISTINCT professor FROM TFGDB.Relacio WHERE titol='" + rs.getString("titol") + "'";
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    prof += "<a href='proj-professor.do?professor=" + rs2.getString("professor") + "'>" + rs2.getString("professor") + "</a> ";
                }
                estudi = "";
                query2 = "SELECT DISTINCT estudi FROM TFGDB.Relacio WHERE titol='" + rs.getString("titol") + "'";
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    estudi += rs2.getString("estudi") + " ";
                }
                p = new Projecte("<a href='projecte.do?projecte=" + rs.getString("titol") + "'>" + rs.getString("titol") + "</a>", rs.getString("estat"), prof);
                p.setEstudi(estudi);
                llista.add(p);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        return llista;
 }
        
        @Override
        public LinkedList<Projecte> findByAnteriorProjects() throws ServletException, IOException {
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<Projecte>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            String query = "SELECT titol, estat, qualificacio FROM TFGDB.Projecte WHERE estat='Defensat' ORDER BY qualificacio DESC";
            String query2;
            ResultSet rs2;
            String estudi;
            String prof;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                prof = "";
                query2 = "SELECT professor FROM TFGDB.Relacio WHERE titol='" + rs.getString("titol") + "'";
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    prof += "<a href='proj-professor.do?professor=" + rs2.getString("professor") + "'>" + rs2.getString("professor") + "</a> ";
                }
                estudi = "";
                query2 = "SELECT DISTINCT estudi FROM TFGDB.Relacio WHERE titol='" + rs.getString("titol") + "'";
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    estudi += rs2.getString("estudi") + " ";
                }
                p = new Projecte("<a href='projecte.do?projecte=" + rs.getString("titol") + "'>" + rs.getString("titol") + "</a>", rs.getString("estat"), prof);
                p.setEstudi(estudi);
                llista.add(p);
                p.setQualificacio(rs.getString("qualificacio"));
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        return llista;
        }
        
        public LinkedList<Projecte> findByInfoProject(String projecte) throws ServletException, IOException {
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<Projecte>();
        /* -- REQUERIMENTS -- */

        /* ------------------ */
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            String query = "SELECT titol, descripcio, estat, recursos, data_def, qualificacio, data_crea, data_mod FROM TFGDB.Projecte WHERE titol = '" + projecte + "'";
            String query2;
            String titol;
            String prof = "";
            String estudiant = "";
            String estudi = "";
            Statement stmt2 = con.createStatement();
            Statement stmt3 = con.createStatement();
            Statement stmt4 = con.createStatement();
            ResultSet rs2;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                p = new Projecte(rs.getString("titol"), rs.getString("estat"), "");
                p.setDescripcio(rs.getString("descripcio"));
                p.setRecursos(rs.getString("recursos"));
                p.setData_def(rs.getString("data_def"));
                p.setQualificacio(rs.getString("qualificacio"));
                p.setData_crea(rs.getString("data_crea"));
                p.setData_mod(rs.getString("data_mod"));
                titol = rs.getString("titol");
                query2 = "SELECT DISTINCT professor FROM TFGDB.Relacio WHERE titol = '" + titol + "'";
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    prof += rs2.getString("professor") + " ";
                }
                query2 = "SELECT DISTINCT estudiant FROM TFGDB.Relacio WHERE titol = '" + titol + "'";
                ResultSet rs3 = stmt3.executeQuery(query2);
                while (rs3.next()) {
                    estudiant += rs3.getString("estudiant") + " ";
                }
                query2 = "SELECT DISTINCT estudi FROM TFGDB.Relacio WHERE titol = '" + titol + "'";
                ResultSet rs4 = stmt4.executeQuery(query2);
                while (rs4.next()) {
                    estudi += rs4.getString("estudi") + " ";
                }
                p.setProfessor(prof);
                p.setEstudiant(estudiant);
                p.setEstudi(estudi);
                llista.add(p);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        return llista;
        }
        
        public LinkedList<Projecte> login(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        User u = new User("", "", "");
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<>();
        try {
            if ((request.getParameter("user").equals("")) || (request.getParameter("pass").equals(""))) {

            } else {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root")) {
                    Statement stmt = con.createStatement();
                    String query = "SELECT contrasenya, nom_complert FROM TFGDB." + request.getParameter("tipus") + " WHERE nom_usuari = '" + request.getParameter("user") + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        if (rs.getString("contrasenya").equals(request.getParameter("pass"))) {
                            u = new User(request.getParameter("user"), request.getParameter("pass"), rs.getString("nom_complert"));
                            u.setTipus(request.getParameter("tipus"));
                        }
                    }
                    String query2 = "SELECT titol, estat FROM TFGDB.Projecte ORDER BY titol";
                    ResultSet rs2 = stmt.executeQuery(query2);
                    while (rs2.next()) {
                        p = new Projecte(rs.getString("titol"), rs.getString("estat"), "");
                        llista.add(p);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", u);
        return llista;
        }
        
        public void modifyProject(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        HttpSession session = request.getSession();
        try {
            String[] estudiants = null;

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            String query;
            ResultSet rs;
            int i;
            Boolean seguir = true;
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
            query = "SELECT titol FROM TFGDB.Projecte WHERE titol = '" + request.getParameter("titol") + "'";
            ResultSet rs3 = stmt.executeQuery(query);
            String existeix = "";

            while (rs3.next()) {
                existeix = rs3.getString("titol");
            }
            if(session.getAttribute("user") == null){
                  msg = "Accés denegat";
            }else if (request.getParameter("titol").equals("")) {
                msg = "Has d'introduïr un títol de projecte";
            } else if ((request.getParameter("estat").equals("NoEstat"))) {
                msg = "Has de seleccionar un estat";
            } else if (existeix.equals("")) {
                msg = "El projecte " + request.getParameter("titol") + " no existeix a la base de dades.";
            }else{
                String estatActual = "";
                String q = "SELECT estat FROM TFGDB.Projecte WHERE titol='" + request.getParameter("titol") + "'";
                ResultSet rs2 = stmt.executeQuery(q);
                while (rs2.next()) {
                    estatActual = rs2.getString("estat");
                }

                switch (request.getParameter("estat")) {
                    case "Assignat":
                        if ((request.getParameter("descripcio").equals("")) || (request.getParameter("estudiants").equals(""))) {
                            msg = "Has d'omplir tots els camps necessaris";
                        } else if (!estatActual.equals("Proposat")) {
                            msg = "No es pot passar de " + estatActual + " a Assignat";
                        } else {
                            estudiants = request.getParameter("estudiants").split(",");
                            for (i = 0; (i < estudiants.length) && (seguir); i++) {
                                query = "SELECT nom_usuari FROM TFGDB.Estudiant WHERE nom_usuari = '" + estudiants[i] + "'";
                                rs = stmt.executeQuery(query);
                                if (rs.next()) {
                                    // Existeix l'usuari especificat
                                } else {
                                    msg = "Hi ha hagut un error (estudiant/s no existeixen)";
                                    seguir = false;
                                }
                            }
                            if (seguir) {
                                query = "UPDATE TFGDB.Projecte SET estat='Assignat', descripcio='" + request.getParameter("descripcio") + "', data_mod='" + timeStamp + "' WHERE titol = '" + request.getParameter("titol") + "'";
                                stmt.executeUpdate(query);
                                for (String est : estudiants) {
                                    query = "UPDATE TFGDB.Relacio SET estudiant='" + est + "' WHERE titol = '" + request.getParameter("titol") + "'";
                                    stmt.executeUpdate(query);
                                }
                                msg = "S'ha modificat el projecte '" + request.getParameter("titol") + "' correctament";
                            }
                        }
                        break;

                    case "Acabat":
                        if (request.getParameter("recursos").equals("")) {
                            msg = "Has d'omplir tots els camps necessaris";
                        } else if (!estatActual.equals("Assignat")) {
                            msg = "No es pot passar de " + estatActual + " a Acabat";
                        } else {
                            query = "UPDATE TFGDB.Projecte SET estat='Acabat', recursos='" + request.getParameter("recursos") + "', data_mod='" + timeStamp + "' WHERE titol = '" + request.getParameter("titol") + "'";
                            stmt.executeUpdate(query);
                            msg = "S'ha modificat el projecte '" + request.getParameter("titol") + "' correctament";
                        }
                        break;

                    case "Pendent de defensa":
                        if (request.getParameter("dataDef").equals("")) {
                            msg = "Has d'omplir tots els camps necessaris";
                        } else if (!estatActual.equals("Acabat")) {
                            msg = "No es pot passar de " + estatActual + " a Pendent de defensa";
                        } else {
                            query = "UPDATE TFGDB.Projecte SET estat='Pendent de defensa', data_def='" + request.getParameter("dataDef") + "', data_mod='" + timeStamp + "' WHERE titol = '" + request.getParameter("titol") + "'";
                            stmt.executeUpdate(query);
                            msg = "S'ha modificat el projecte '" + request.getParameter("titol") + "' correctament";
                        }
                        break;
                    case "Defensat":
                        if (request.getParameter("qualificacio").equals("")) {
                            msg = "Has d'omplir tots els camps necessaris";
                        } else if (!estatActual.equals("Pendent de defensa")) {
                            msg = "No es pot passar de " + estatActual + " a Defensat";
                        } else {
                            query = "UPDATE TFGDB.Projecte SET estat='Defensat', qualificacio='" + request.getParameter("qualificacio") + "', data_mod='" + timeStamp + "' WHERE titol = '" + request.getParameter("titol") + "'";
                            stmt.executeUpdate(query);
                            msg = "S'ha modificat el projecte '" + request.getParameter("titol") + "' correctament";
                        }
                        break;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            msg += "" + e;
        }
        request.setAttribute("msg", msg);
        }
        
        public LinkedList<Projecte> findByProfessor(String professor, boolean api) throws ServletException, IOException {
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<Projecte>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            String query = "SELECT DISTINCT titol FROM TFGDB.Relacio WHERE professor = '" + professor + "'";
            String query2, titol;
            Statement stmt2 = con.createStatement();
            ResultSet rs2;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                titol = rs.getString("titol");
                query2 = "SELECT titol, estat FROM TFGDB.Projecte WHERE titol = '" + rs.getString("titol") + "'";
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    if(!api) p = new Projecte("<a href='projecte.do?projecte=" + rs.getString("titol") + "'>" + rs.getString("titol") + "</a>", rs2.getString("estat"), professor);
                    else p = new Projecte(rs.getString("titol"), rs2.getString("estat"), professor);
                    llista.add(p);
                }
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        return llista;
        }
        
        public String createProject(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        HttpSession session = request.getSession();
        try {
            if ((request.getParameter("titol").equals("")) || (request.getParameter("professors").equals(""))
                    || (request.getParameter("estudis").equals(""))) {
                msg = "Has d'omplir tots els camps";
            } else if (session.getAttribute("user") == null) {
                msg = "Accés denegat";
            } else {
                String[] professors = request.getParameter("professors").split(",");
                String[] estudis = request.getParameter("estudis").split(",");

                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
                Statement stmt = con.createStatement();
                String query;
                ResultSet rs;
                /* ------- REQUISITS (professors i/o estudis existeixen?) --------- */
                int i;
                Boolean seguir = true;
                for (i = 0; (i < professors.length) && (seguir); i++) {
                    query = "SELECT nom_usuari FROM TFGDB.Professor WHERE nom_usuari = '" + professors[i] + "'";
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        // Existeix l'usuari especificat
                    } else {
                        msg = "Hi ha hagut un error (professor/s no existeixen)";
                        seguir = false;
                    }
                }
                for (i = 0; (i < estudis.length) && (seguir); i++) {
                    query = "SELECT id FROM TFGDB.Estudi WHERE id = '" + estudis[i] + "'";
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        // Existeix l'estudi especificat
                    } else {
                        msg = "Hi ha hagut un error (estudi/s no existeixen)";
                        seguir = false;
                    }
                }
                /* -------------------------------------------------------------- */
                if (seguir) {
                    String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                    query = "INSERT INTO TFGDB.Projecte (titol, estat, data_crea) VALUES ('" + request.getParameter("titol") + "', 'Proposat', '" + timeStamp + "')";
                    stmt.executeUpdate(query);
                    for (String prof : professors) {
                        for (String est : estudis) {
                            query = "INSERT INTO TFGDB.Relacio (titol, professor, estudi) VALUES ('" + request.getParameter("titol") + "', '" + prof + "', '" + est + "')";
                            stmt.executeUpdate(query);
                        }
                    }
                    msg = "S'ha afegit el projecte '" + request.getParameter("titol") + "' correctament";
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            msg += "" + e;
        }
        return msg;
        }
        
        public void signOut(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        }
        
        public void signUp(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String msg;
        try {
            if ((request.getParameter("user").equals("")) || (request.getParameter("pass").equals(""))
                    || (request.getParameter("pass2").equals("")) || (request.getParameter("nom_complet").equals(""))) {
                msg = "Has d'omplir tots els camps";
            } else if (!request.getParameter("user").equals(request.getParameter("pass2"))) {
                msg = "Les contrasenyes no coincideixen";
            } else {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
                Statement stmt = con.createStatement();
                String query = "INSERT INTO TFGDB." + request.getParameter("tipus") + " (nom_usuari, contrasenya, nom_complert) VALUES ('" + request.getParameter("user") + "', '" + request.getParameter("pass") + "', '" + request.getParameter("nom_complet") + "')";
                stmt.executeUpdate(query);
                msg = "S'ha afegit l'usuari " + request.getParameter("user") + " correctament";
            }
        } catch (SQLException | ClassNotFoundException e) {
            msg = "" + e;
        }
        request.setAttribute("msg", msg);
        }
        
        public LinkedList<Projecte> findAll(boolean api) throws ServletException, IOException {
        Projecte p;
        LinkedList<Projecte> llista = new LinkedList<Projecte>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            String query = "SELECT titol, estat FROM TFGDB.Projecte ORDER BY titol";
            String query2;
            ResultSet rs2;
            String estudi, prof;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                prof = "";
                query2 = "SELECT professor FROM TFGDB.Relacio WHERE titol='" + rs.getString("titol") + "'";
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    prof += "<a href='proj-professor.do?professor=" + rs2.getString("professor") + "'>" + rs2.getString("professor") + "</a> ";
                }
                estudi = "";
                query2 = "SELECT DISTINCT estudi FROM TFGDB.Relacio WHERE titol='" + rs.getString("titol") + "'";
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    estudi += rs2.getString("estudi") + " ";
                }
                if (!api) p = new Projecte("<a href='projecte.do?projecte=" + rs.getString("titol") + "'>" + rs.getString("titol") + "</a>", rs.getString("estat"), prof);
                else p = new Projecte(rs.getString("titol"), rs.getString("estat"), prof);
                p.setEstudi(estudi);
                llista.add(p);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        return llista;
        }
        
        public LinkedList<Projecte> userZone(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        User u;
        Projecte p;
        HttpSession session = request.getSession();
        LinkedList<Projecte> llista = new LinkedList<>();
        try {
            if (session.getAttribute("user") == null) {

            } else {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                u = (User) session.getAttribute("user");
                try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root")) {
                    Statement stmt = con.createStatement();
                    String query = "SELECT DISTINCT titol FROM TFGDB.Relacio WHERE " + u.getTipus() + " = '" + u.getNomUsuari() + "'";
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2;
                    String query2, titol;
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        titol = rs.getString("titol");
                        query2 = "SELECT titol, estat FROM TFGDB.PROJECTE WHERE titol='" + titol + "' ORDER BY estat";
                        rs2 = stmt2.executeQuery(query2);
                        while (rs2.next()) {
                            p = new Projecte(rs.getString("titol"), rs2.getString("estat"), "");
                            llista.add(p);
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
        }
        return llista;
        }
        
        public LinkedList<String> findProfWithProjects(){
        Projecte p;
        LinkedList<String> llista = new LinkedList<String>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            String query = "SELECT DISTINCT professor FROM TFGDB.Relacio";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                llista.add(rs.getString("professor"));
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        return llista;
        }
        
        public LinkedList<User> findInfoProf(String professor){
        User u;
        LinkedList<User> llista = new LinkedList<User>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            String query = "SELECT DISTINCT nom_usuari, nom_complert FROM TFGDB.Professor WHERE nom_usuari = '" + professor + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                u = new User(rs.getString("nom_usuari"), null, rs.getString("nom_complert"));
                llista.add(u);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        return llista;
        }
        
        public LinkedList<String> findByState(String state){
            
        LinkedList<String> llista = new LinkedList<String>();
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TFGDB", "root", "root");
            Statement stmt = con.createStatement();
            String query = "SELECT DISTINCT titol FROM TFGDB.Projecte WHERE estat = '" + state + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                llista.add(rs.getString("titol"));
                }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
        }
        return llista;
        }
}