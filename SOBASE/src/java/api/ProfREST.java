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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
        
        for(String p : llista){
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
        
        for (User u : info){
            jo.add("Nom d'usuari", u.getNomUsuari());
            jo.add("Nom complet", u.getNomComplet());
            LinkedList<Projecte> projs = dao.findByProfessor(u.getNomUsuari(), true); //TRUE because of API
            for (Projecte p : projs){
                array.add(p.getTitol());
            }
            jo.add("Projecte/s", array.build());
        }
        
        return Response.ok(jo.build()).build();    
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
