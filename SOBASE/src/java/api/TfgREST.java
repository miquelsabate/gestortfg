/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import cat.urv.deim.sob.Projecte;
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

// ---> http://localhost:8080/SOBASE/webresources/rest/api/v1/tfg/METHOD

//@Stateless
@Path("/rest/api/v1/tfg")
public class TfgREST {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() throws ServletException, IOException {
        TfgDao dao = new TfgDao();
        LinkedList<Projecte> llista = dao.findAll(true); //TRUE because of API
 
        JsonArrayBuilder array = Json.createArrayBuilder();
        
        for(Projecte p : llista){
           array.add(p.getTitol());
        }

        return Response.ok(array.build()).build();    
    }
    
    @GET
    @Path("/state={state}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByState(@PathParam("state") String state) throws ServletException, IOException {
        TfgDao dao = new TfgDao();
        LinkedList<String> llista = dao.findByState(state);
 
        //JsonObjectBuilder jo = Json.createObjectBuilder();
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
        LinkedList<Projecte> llista = dao.findByInfoProject(id);
 
        JsonObjectBuilder jo = Json.createObjectBuilder();
        //JsonArrayBuilder array = Json.createArrayBuilder();
        
        for(Projecte p : llista){
            jo.add("Títol", p.getTitol());
            jo.add("Estat", p.getEstat());
            jo.add("Professors/s", p.getProfessor());
            jo.add("Estudi/s", p.getEstudi());
            if (p.getEstudiant() != null) {
                jo.add("Estudiant/s", p.getEstudiant());
            }
            if (p.getDescripcio() != null) {
                jo.add("Descripció", p.getDescripcio());
            }
            if (p.getRecursos() != null) {
                jo.add("Recursos", p.getRecursos());
            }
            if (p.getData_crea() != null) {
               jo.add("Data creació", p.getData_crea());
            }
            if (p.getData_mod() != null) {
                jo.add("Data modificació", p.getData_mod());
            }
            if (p.getData_def() != null) {
                jo.add("Data defensa", p.getData_def());
            }
            if (p.getQualificacio() != null) {
                jo.add("Qualificació", p.getQualificacio());
            }       
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
