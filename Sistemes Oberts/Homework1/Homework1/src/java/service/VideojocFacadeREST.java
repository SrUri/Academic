package service;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entities.Videojoc;
import authn.Secured;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Comparator;

@Stateless
@Path("/rest/api/v1/videojoc")
public class VideojocFacadeREST extends AbstractFacade<Videojoc> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public VideojocFacadeREST() {
        super(Videojoc.class);
    }

    @POST
    @Secured
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response postVideojoc(Videojoc entity) {
        Videojoc temp = super.find(entity.getNom());
        if(temp != null)
            return Response.status(409, "Conflict").build();
        else{
            super.create(entity);
            return Response.status(201, "Created").entity(entity).build();
        }
        
    }
    
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllVideojocs(@QueryParam("tipus") String tipus,
        @QueryParam("videoconsola") String videoconsola) {
        String query = "SELECT v FROM Videojoc v WHERE 1=1";
        if(tipus == null && videoconsola == null) {
            List<Videojoc> jocs = super.findAll();
            jocs.sort(Comparator.comparing(Videojoc::getNom)); //ordenem jocs per ordre alfabètic)
            return Response.ok(jocs).build();
        }
        else if(tipus != null && videoconsola == null){
            if(tipus instanceof String == false){
                return Response.status(Response.Status.BAD_REQUEST).entity("tipus de videojoc invàlids").build();
            }
            else if(tipus.isEmpty()){
                List<Videojoc> jocs = super.findAll();
                jocs.sort(Comparator.comparing(Videojoc::getNom)); //ordenem jocs per ordre alfabètic)
                return Response.ok(jocs).build();
            }
            else{
                query += " AND v.tipus = :tipus";
                TypedQuery<Videojoc> typedQuery = em.createQuery(query, Videojoc.class);
                typedQuery.setParameter("tipus", tipus);
                List<Videojoc> jocs = typedQuery.getResultList();
                return Response.ok(jocs, MediaType.APPLICATION_JSON).build();
            } 
        }
        else if(videoconsola != null && tipus == null)
            if(videoconsola instanceof String == false) {
                return Response.status(Response.Status.BAD_REQUEST).entity("videoconsola invàlida").build();
            }
            else if(videoconsola.isEmpty()){
                List<Videojoc> jocs = super.findAll();
                jocs.sort(Comparator.comparing(Videojoc::getNom)); //ordenem jocs per ordre alfabètic)
                return Response.ok(jocs).build();
            }
            else {
                query += " AND v.videoconsola = :videoconsola";
                TypedQuery<Videojoc> typedQuery = em.createQuery(query, Videojoc.class);
                typedQuery.setParameter("videoconsola", videoconsola);
                List<Videojoc> jocs = typedQuery.getResultList();
                return Response.ok(jocs, MediaType.APPLICATION_JSON).build();
            }
        else if (videoconsola != null && tipus != null) {
            if (!videoconsola.isEmpty() && !tipus.isEmpty()) {
                if(videoconsola instanceof String == false || tipus instanceof String == false) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("tipus de videojoc invàlids i videoconsola invàlida").build();
                }
                else {
                    query += " AND v.videoconsola = :videoconsola AND v.tipus = :tipus";
                    TypedQuery<Videojoc> typedQuery = em.createQuery(query, Videojoc.class);
                    typedQuery.setParameter("videoconsola", videoconsola);
                    typedQuery.setParameter("tipus", tipus);
                    List<Videojoc> jocs = typedQuery.getResultList();
                    return Response.ok(jocs, MediaType.APPLICATION_JSON).build();
                }
            }
            else if(!tipus.isEmpty() && videoconsola.isEmpty()){
                if(tipus instanceof String == false){
                    return Response.status(Response.Status.BAD_REQUEST).entity("tipus de videojoc invàlids").build();
                }
                else {
                    query += " AND v.tipus = :tipus";
                    TypedQuery<Videojoc> typedQuery = em.createQuery(query, Videojoc.class);
                    typedQuery.setParameter("tipus", tipus);
                    List<Videojoc> jocs = typedQuery.getResultList();
                    return Response.ok(jocs, MediaType.APPLICATION_JSON).build();
                } 
            }
            else if(!videoconsola.isEmpty() && tipus.isEmpty()) {
                if(videoconsola instanceof String == false) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("videoconsola invàlida").build();
                }
                else {
                    query += " AND v.videoconsola = :videoconsola";
                    TypedQuery<Videojoc> typedQuery = em.createQuery(query, Videojoc.class);
                    typedQuery.setParameter("videoconsola", videoconsola);
                    List<Videojoc> jocs = typedQuery.getResultList();
                    return Response.ok(jocs, MediaType.APPLICATION_JSON).build();
                }
            }
            else if(videoconsola.isEmpty() && tipus.isEmpty()) {
                List<Videojoc> jocs = super.findAll();
                jocs.sort(Comparator.comparing(Videojoc::getNom)); //ordenem jocs per ordre alfabètic)
                return Response.ok(jocs).build();
            }
            else {
                return Response.status(Response.Status.BAD_REQUEST).entity("tipus de videojoc invàlids i videoconsola invàlida").build();
            }
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).entity("tipus de videojoc invàlids i videoconsola invàlida").build();
        }
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
