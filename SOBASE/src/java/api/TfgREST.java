/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import cat.urv.deim.sob.Projecte;
import entitats.tfg.TfgDao;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.json.*;

// ---> http://localhost:8080/SOBASE/webresources/rest/api/v1/tfg/METHOD

//@Stateless
@Path("/rest/api/v1/tfg")
public class TfgREST {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@QueryParam("state") List<String> state) throws ServletException, IOException {
        TfgDao dao = new TfgDao();
        JsonArrayBuilder array = Json.createArrayBuilder();
        
        if(state.isEmpty()){
            LinkedList<Projecte> llista = dao.findAll(true); //TRUE because of API
        
            for(Projecte p : llista){
               array.add(p.getTitol());
            }
        }
        else{
            LinkedList<String> projs;
            for (String s : state){
                projs = dao.findByState(s);
                for (String x : projs){
                    array.add(x);
                }
            }
        }

        return Response.ok(array.build()).build();    
    }
    
    @GET
    @Path("{id}")
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
    
    @POST
    @Path("{id}/assign")
    @Consumes("application/json")
    public Response createProjectInJSON(@PathParam("id") String id, Projecte product) {

	System.out.println(id);
        String result = "Product created : " + product;
	return Response.status(201).entity(result).build();

	}

    /*@PUT
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
