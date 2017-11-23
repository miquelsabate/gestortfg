<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%!
    private List<String> resetDatabase(boolean force) throws Exception {
        LinkedList<String> messages = new LinkedList();
        /* How to customize:
         * 1. Update the database name on dbname.
         * 2. Create the list of tables, under tablenames[].
         * 3. Create the list of table definition, under tables[].
         * 4. Create the data into the above table, under data[]. 
         * 
         * If there is any problem, it will exit at the very first error.
         */
        String dbname = "TFGDB";
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        /* this will generate database if not exist */
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbname, "root", "root");
        Statement stmt = con.createStatement();

        try {
            stmt.execute("CREATE SCHEMA " + dbname);
            System.out.println("db creada");
            // schema was created; it will appear into: Other schemas -> "dbname"
        } catch (SQLException e) {
            // schema already exists; do nothing.
            if (!force) {
                messages.add("Database exists. Doing nothing. Visit install.jsp on your browser to reset your database content.");
                return messages;
            }
        }

        /* drop tables if they exist */
        String tablenames[] = new String[]{
            "Professor",};
        for (String tablename : tablenames) {
            try {
                stmt.executeUpdate("DROP TABLE " + dbname + "." + tablename);
                messages.add("<pre> -> DROP TABLE " + dbname + "." + tablename + "<pre>");
            } catch (SQLException e) {
                // table didn't exist; it is the first time
            }
        }

        /* creating tables */
        String tables[] = new String[]{
            "CREATE TABLE " + dbname + ".Professor ("
            + "nom_usuari VARCHAR(20) NOT NULL,"
            + " contrasenya VARCHAR(10),"
            + " nom_complert VARCHAR(32),"
            + " PRIMARY KEY(nom_usuari))",
            "CREATE TABLE " + dbname + ".Estudiant ("
            + "nom_usuari VARCHAR(20) NOT NULL,"
            + " contrasenya VARCHAR(10),"
            + " nom_complert VARCHAR(32),"
            + " PRIMARY KEY(nom_usuari))",
            "CREATE TABLE " + dbname + ".Projecte ("
            + "titol VARCHAR(100) NOT NULL,"
            + " descripcio VARCHAR(140),"
            + " estat VARCHAR(20),"
            + " recursos VARCHAR(250),"
            + " data_def VARCHAR (10),"
            + " qualificacio VARCHAR(5),"
            + " data_crea VARCHAR(20),"
            + " data_mod VARCHAR(20),"
            + "PRIMARY KEY(titol))",
            "CREATE TABLE " + dbname + ".Estudi ("
            + "id VARCHAR(10) NOT NULL,"
            + "PRIMARY KEY(id))",
            "CREATE TABLE " + dbname + ".Relacio ("
            + "titol VARCHAR(100),"
            + " professor VARCHAR(20),"
            + " estudiant VARCHAR(20),"
            + " estudi VARCHAR(10),"
            + " FOREIGN KEY (titol) REFERENCES " + dbname + ".Projecte(titol),"
            + " FOREIGN KEY (professor) REFERENCES " + dbname + ".Professor(nom_usuari),"
            + " FOREIGN KEY (estudiant) REFERENCES " + dbname + ".Estudiant(nom_usuari),"
            + " FOREIGN KEY (estudi) REFERENCES " + dbname + ".Estudi(id))"
        };

        for (String table : tables) {
            try {
                stmt.executeUpdate(table);
            } catch (SQLException e) {
                messages.add("<span class='error'>Error creating table: " + e + "</span>");
                return messages;
            }
            messages.add("<pre> -> " + table + "<pre>");
        }

        /* inserting data */
 /* you have to exclude the id autogenerated from the list of columns if you have use it. */
        String data[] = new String[]{
            "INSERT INTO " + dbname + ".Professor (nom_usuari, contrasenya, nom_complert) VALUES ('msabate', 'miquel', 'Miquel Sabaté')",
            "INSERT INTO " + dbname + ".Professor (nom_usuari, contrasenya, nom_complert) VALUES ('sob', 'sob', 'Sistemes Oberts')",
            "INSERT INTO " + dbname + ".Professor (nom_usuari, contrasenya, nom_complert) VALUES ('profestfg', 'notfg', 'No vull fer tfg')",
            "INSERT INTO " + dbname + ".Estudiant (nom_usuari, contrasenya, nom_complert) VALUES ('dflores', 'david', 'David Flores')",
            "INSERT INTO " + dbname + ".Estudiant (nom_usuari, contrasenya, nom_complert) VALUES ('msanchez', 'sobmola', 'Marc Sánchez')",
            "INSERT INTO " + dbname + ".Estudi (id) VALUES ('GEI')",
            "INSERT INTO " + dbname + ".Estudi (id) VALUES ('GEE')",
            "INSERT INTO " + dbname + ".Estudi (id) VALUES ('GET')",
            "INSERT INTO " + dbname + ".Projecte (titol, estat, data_crea) VALUES ('Projecte 1', 'Proposat', '10-10-2010')",
            "INSERT INTO " + dbname + ".Relacio (titol, professor, estudi) VALUES ('Projecte 1', 'msabate', 'GEI')",
            "INSERT INTO " + dbname + ".Projecte (titol, estat, descripcio, data_crea, data_mod) VALUES ('Projecte 2', 'Assignat', 'Descripcio projecte 2', '11-11-2011', '15-11-2011')",
            "INSERT INTO " + dbname + ".Relacio (titol, professor, estudi, estudiant) VALUES ('Projecte 2', 'sob', 'GEE', 'dflores')",
            "INSERT INTO " + dbname + ".Projecte (titol, estat, descripcio, data_crea, data_mod, recursos) VALUES ('Projecte 3', 'Acabat', 'Descripcio projecte 3', '12-12-2012', '18-12-2012', 'http://randomcolour.com/')",
            "INSERT INTO " + dbname + ".Relacio (titol, professor, estudi, estudiant) VALUES ('Projecte 3', 'msabate', 'GEI', 'dflores')",
            "INSERT INTO " + dbname + ".Relacio (titol, professor, estudi, estudiant) VALUES ('Projecte 3', 'sob', 'GET', 'msanchez')",
            "INSERT INTO " + dbname + ".Projecte (titol, estat, descripcio, data_crea, data_mod, recursos, data_def) VALUES ('Projecte 4', 'Pendent de defensa', 'Descripcio projecte 4', '20-10-2016', '21-11-2016', 'http://llibertatpresospolitics.cat/', '01-10-2017')",
            "INSERT INTO " + dbname + ".Relacio (titol, professor, estudi, estudiant) VALUES ('Projecte 4', 'sob', 'GET', 'msanchez')",
            "INSERT INTO " + dbname + ".Projecte (titol, estat, descripcio, data_crea, data_mod, recursos, data_def, qualificacio) VALUES ('Projecte 5', 'Defensat', 'Descripcio projecte 5', '27-10-2017', '21-11-2017', 'http://www.staggeringbeauty.com/', '10-12-2017', 'MH')",
            "INSERT INTO " + dbname + ".Relacio (titol, professor, estudi, estudiant) VALUES ('Projecte 5', 'msabate', 'GEI', 'dflores')"
        };
        for (String datum : data) {
            if (stmt.executeUpdate(datum) <= 0) {
                messages.add("<span class='error'>Error inserting data: " + datum + "</span>");
                return messages;
            }
            messages.add("<pre> -> " + datum + "<pre>");
        }
        return messages;
    }

    public void jspInit() {
        try {
            List<String> messages = resetDatabase(false);
            for (String message : messages) {
                System.out.println("install.jsp: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Force database installation</title>
    </head>
    <body>
        <style>
            .error {
                color: red;
            }
            pre {
                color: green;
            }
        </style>
        <h2>Database initialization in progress</h2>
        <%
            List<String> messages = resetDatabase(true);
            for (String message : messages) {
                out.println(message);
            }
        %>
        <!-- <button onclick="window.location = '<%//=request.getSession().getServletContext().getContextPath()%>'">Go home</button> -->
        <button onclick="window.location = 'actius.do'">Go home</button>

    </body>
</html>
