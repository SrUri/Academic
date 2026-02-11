package service;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entities.Usuari;
import authn.Secured;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ws.rs.core.Response;



@Stateless
@Path("/rest/api/v1/usuari")
public class UsuariFacadeREST extends AbstractFacade<Usuari> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public UsuariFacadeREST() {
        super(Usuari.class);
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response trobaUsuaris() {
        List<Usuari> usuaris = super.findAll();
        if (usuaris != null && !usuaris.isEmpty()) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(usuaris);
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No users found").build();
        }
    }
    
    @GET
    @Path("/{nom}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("nom") String nom) {
        if(super.find(nom) == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
        } else {
            Usuari temp = super.find(nom);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(temp);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        }
    }
    
    @PUT
    @Secured
    @Path("/{nom}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("nom") String nom, Usuari user) {
        Usuari existeix = super.find(nom);
        if(existeix == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
        } else {
            existeix.setUsuari(user.getUsuari());
            existeix.setContrassenya(user.getContrassenya());
            return Response.ok(existeix, MediaType.APPLICATION_JSON).build();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}