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

        if (state.isEmpty()) {
            LinkedList<Projecte> llista = dao.findAll(true); //TRUE because of API

            for (Projecte p : llista) {
                array.add(p.getTitol());
            }
        } else {
            LinkedList<String> projs;
            String st;
            for (String s : state) {
                s = s.toLowerCase();
                st = Character.toUpperCase(s.charAt(0)) + s.substring(1, s.length());
                projs = dao.findByState(st);
                for (String x : projs) {
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

        for (Projecte p : llista) {
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
    @Consumes("application/json")
    public Response createProject(JsonObject projecte) throws ServletException, IOException {

        String result = null;
        boolean check = false;
        if (projecte.get("titol") == null || projecte.get("professors") == null || projecte.get("estudis") == null || projecte.get("user") == null || projecte.get("pass") == null) {
            result = "Format del Json incorrecte";
        } else {
            TfgDao dao = new TfgDao();
            check = dao.checkUser(projecte.get("user").toString().substring(1, projecte.get("user").toString().length() - 1), projecte.get("pass").toString().substring(1, projecte.get("pass").toString().length() - 1));
            if (check) {
                result = dao.createProjectAPI(projecte.get("titol").toString().substring(1, projecte.get("titol").toString().length() - 1), projecte.get("professors").toString().substring(1, projecte.get("professors").toString().length() - 1), projecte.get("estudis").toString().substring(1, projecte.get("estudis").toString().length() - 1));
            } else {
                result = "Accès denegat, requereix d'autenticació com a professor al JSON.";
            }
        }
        return Response.status(201).entity(result).build();

    }

    @POST
    @Path("{id}/assign")
    @Consumes("application/json")
    public Response assignProject(@PathParam("id") String id, JsonObject projecte) throws ServletException, IOException {
        String result = null;
        boolean check = false;
        boolean checkOwn = false;
        if (projecte.get("estudiants") == null || projecte.get("user") == null || projecte.get("pass") == null || projecte.get("estudi") == null) {
            result = "Format del Json incorrecte";
        } else {
            TfgDao dao = new TfgDao();
            check = dao.checkUser(projecte.get("user").toString().substring(1, projecte.get("user").toString().length() - 1), projecte.get("pass").toString().substring(1, projecte.get("pass").toString().length() - 1));
            checkOwn = dao.checkOwner(projecte.get("user").toString().substring(1, projecte.get("user").toString().length() - 1), id);
            if (check && checkOwn) {
                result = dao.assignProjectAPI(projecte.get("estudiants").toString().substring(1, projecte.get("estudiants").toString().length() - 1), projecte.get("user").toString().substring(1, projecte.get("user").toString().length() - 1), projecte.get("estudi").toString().substring(1, projecte.get("estudi").toString().length() - 1), id);
            } else {
                result = "Accès denegat, requereix d'autenticació com a professor al JSON o ser professor coordinador.";
            }
        }
        return Response.status(201).entity(result).build();

    }
    
    //json a utilitzar de prova:
    //{"user":"msabate","pass":"miquel","estudiants":"dflores","estudi":"GEI","descripcio":"projecte de prova","qualificacio":"9","recursos":"www.wikipedia.com","data_crea":"03-01-2018","estat":"","professors":"msabate"}
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response editProject(@PathParam("id") String id, JsonObject projecte) throws ServletException, IOException {
        String result = null;
        boolean check = false;
        boolean checkOwn = false;
        if (projecte.get("estudiants") == null || projecte.get("user") == null || projecte.get("pass") == null || projecte.get("estudi") == null || projecte.get("professors") == null
                || projecte.get("descripcio") == null || projecte.get("qualificacio") == null || projecte.get("recursos") == null || projecte.get("data_crea") == null) {
            result = "Format del Json incorrecte";
        } else {
            TfgDao dao = new TfgDao();
            check = dao.checkUser(projecte.get("user").toString().substring(1, projecte.get("user").toString().length() - 1), projecte.get("pass").toString().substring(1, projecte.get("pass").toString().length() - 1));
            checkOwn = dao.checkOwner(projecte.get("user").toString().substring(1, projecte.get("user").toString().length() - 1), id);
            if (check && checkOwn) {
                result = dao.editProjectAPI(projecte.get("estudiants").toString().substring(1, projecte.get("estudiants").toString().length() - 1), projecte.get("professors").toString().substring(1, projecte.get("professors").toString().length() - 1),
                        id, projecte.get("estudi").toString().substring(1, projecte.get("estudi").toString().length() - 1), projecte.get("descripcio").toString().substring(1, projecte.get("descripcio").toString().length() - 1),
                        projecte.get("qualificacio").toString().substring(1, projecte.get("qualificacio").toString().length() - 1), projecte.get("recursos").toString().substring(1, projecte.get("recursos").toString().length() - 1),
                        projecte.get("data_crea").toString().substring(1, projecte.get("data_crea").toString().length() - 1), projecte.get("estat").toString().substring(1, projecte.get("estat").toString().length() - 1));
            } else {
                result = "Accès denegat, requereix d'autenticació com a professor al JSON o ser professor coordinador.";
            }
        }
        return Response.status(201).entity(result).build();

    }
    
    //Atualment no operatiu
    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    public Response deleteProject(@PathParam("id") String id, JsonObject projecte) throws ServletException, IOException {
        String result = null;
        boolean check = false;
        boolean checkOwn = false;
        if (projecte.get("user") == null || projecte.get("pass") == null) {
            result = "Format del Json incorrecte";
        } else {
            TfgDao dao = new TfgDao();
            check = dao.checkUser(projecte.get("user").toString().substring(1, projecte.get("user").toString().length() - 1), projecte.get("pass").toString().substring(1, projecte.get("pass").toString().length() - 1));
            checkOwn = dao.checkOwner(projecte.get("user").toString().substring(1, projecte.get("user").toString().length() - 1), id);
            System.out.println(check + " " + checkOwn);
            if (check && checkOwn) {
                result = dao.deleteProjectAPI(id);
            } else {
                result = "Accès denegat, requereix d'autenticació com a professor al JSON o ser professor coordinador.";
            }
        }
        return Response.status(201).entity(result).build();
    }
}
