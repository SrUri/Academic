package service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entities.Lloguer;
import authn.Secured;
import com.google.gson.Gson;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import java.util.List;
import model.entities.Usuari;
import model.entities.Videojoc;

@Stateless
@Path("/rest/api/v1/lloguer")
public class LloguerFacadeREST extends AbstractFacade<Lloguer> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public LloguerFacadeREST() {
        super(Lloguer.class);
    }
    
    @POST
    @Secured
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response postLloguer(String entity) {
        Gson gson = new Gson();
        Lloguer result = new Lloguer();
        Lloguer temp = gson.fromJson(entity, Lloguer.class);
        result.setDateLLoguer(temp.getDateLloguer());
        List<Videojoc> tempGames = temp.getVideojocsLloguer();
        for(int i = 0; i < tempGames.size(); i++){
            result.setVideojocsLloguer(tempGames.get(i));
            tempGames.get(i).setLloguer(temp);
        }
        TypedQuery<Usuari> tq = em.createQuery("SELECT u FROM Usuari u WHERE u.nom IS NOT NULL AND u.nom = "+temp.getUsuari_id(), Usuari.class);
        Usuari usuari = tq.getSingleResult();
        result.setUsuari(usuari);
        super.create(result);
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Secured
    @Path("/${id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") int id) {
        if(super.find(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
        } else {
            Lloguer temp = super.find(id);
            return Response.ok(temp, MediaType.APPLICATION_JSON).build();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}