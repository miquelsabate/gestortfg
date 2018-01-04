/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import cat.urv.deim.sob.Projecte;
import cat.urv.deim.sob.User;
import entitats.tfg.TfgDao;
import java.io.IOException;
import java.util.LinkedList;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.json.*;
import javax.ws.rs.core.MediaType;

// ---> http://localhost:8080/SOBASE/webresources/rest/api/v1/professors/METHOD
//@Stateless
@Path("/rest/api/v1/professors")
public class ProfREST {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findProfWithProjects() throws ServletException, IOException {
        TfgDao dao = new TfgDao();
        LinkedList<String> llista = dao.findProfWithProjects();

        JsonArrayBuilder array = Json.createArrayBuilder();

        for (String p : llista) {
            array.add(p);
        }

        return Response.ok(array.build()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByProject(@PathParam("id") String id) throws ServletException, IOException {
        TfgDao dao = new TfgDao();
        LinkedList<User> info = dao.findInfoProf(id);

        JsonObjectBuilder jo = Json.createObjectBuilder();
        JsonArrayBuilder array = Json.createArrayBuilder();

        for (User u : info) {
            jo.add("Nom d'usuari", u.getNomUsuari());
            jo.add("Nom complet", u.getNomComplet());
            LinkedList<Projecte> projs = dao.findByProfessor(u.getNomUsuari(), true); //TRUE because of API
            for (Projecte p : projs) {
                array.add(p.getTitol());
            }
            jo.add("Projecte/s", array.build());
        }

        return Response.ok(jo.build()).build();
    }

    // json a utilitzar: {"user":"msabate","pass":"miquel","nomUsuari":"UsuariProva1","passNew":"passProva1","nomComplet":"usuari de prova"}
    @POST
    @Consumes("application/json")
    public Response createProfessor(JsonObject professor) throws ServletException, IOException {

        String result = null;
        boolean check = false;
        if (professor.get("nomUsuari") == null || professor.get("passNew") == null || professor.get("user") == null || professor.get("pass") == null || professor.get("nomComplet") == null) {
            result = "Format del Json incorrecte";
        } else {
            TfgDao dao = new TfgDao();
            check = dao.checkUser(professor.get("user").toString().substring(1, professor.get("user").toString().length() - 1), professor.get("pass").toString().substring(1, professor.get("pass").toString().length() - 1));
            if (check) {
                result = dao.createProfessorAPI(professor.get("nomUsuari").toString().substring(1, professor.get("nomUsuari").toString().length() - 1), professor.get("passNew").toString().substring(1, professor.get("passNew").toString().length() - 1),
                        professor.get("nomComplet").toString().substring(1, professor.get("nomComplet").toString().length() - 1));
            } else {
                result = "Accès denegat, requereix d'autenticació com a professor al JSON.";
            }
        }
        return Response.status(201).entity(result).build();

    }

    //json a utilitzar de prova: {"user":"UsuariProva1","pass":"pass1mod","passNew":"pass1mod2","nomComplet":"usuari de prova modificat"}
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response editProfessor(@PathParam("id") String id, JsonObject professor) throws ServletException, IOException {
        String result = null;
        boolean check = false;
        boolean checkOwn = false;
        if (professor.get("user") == null || professor.get("pass") == null || professor.get("passNew") == null || professor.get("nomComplet") == null) {
            result = "Format del Json incorrecte";
        } else {
            TfgDao dao = new TfgDao();
            check = dao.checkUser(professor.get("user").toString().substring(1, professor.get("user").toString().length() - 1), professor.get("pass").toString().substring(1, professor.get("pass").toString().length() - 1));
            checkOwn = dao.checkOwnerID(professor.get("user").toString().substring(1, professor.get("user").toString().length() - 1), id);
            if (check && checkOwn) {
                result = dao.editProfessorAPI(id, professor.get("passNew").toString().substring(1, professor.get("passNew").toString().length() - 1), professor.get("nomComplet").toString().substring(1, professor.get("nomComplet").toString().length() - 1));
            } else {
                result = "Accès denegat, requereix d'autenticació com a professor al JSON o ser professor coordinador.";
            }
        }
        return Response.status(201).entity(result).build();

    }
    /*@POST
    @Consumes({"application/xml", "application/json"})
    public void create() {
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit() {
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public void find(@PathParam("id") Integer id) {
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public void findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
    }

    /*@GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }*/

}
