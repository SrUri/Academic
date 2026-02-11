package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.model.Usuari;
import deim.urv.cat.homework2.controller.UsuariForm;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.client.Entity;
import deim.urv.cat.homework2.model.Usuari;
        
public class UsuariServiceImpl implements UsuariService {
    private final WebTarget webTarget;
    private final jakarta.ws.rs.client.Client client;
    private static final String BASE_URI = "http://localhost:8080/Homework1/webresources/rest/api/v1";
    
    public UsuariServiceImpl() {
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("usuari");
    }
    
    @Override
    public Usuari authenticate (String usuari, String contrassenya) {
        Response response = webTarget.path(usuari)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == 200) {
            return response.readEntity(Usuari.class);
        }
        return null;
    }

    @Override
    public boolean addUser(UsuariForm user) {
       Response response = webTarget.request(MediaType.APPLICATION_JSON)
               .post(Entity.entity(user, MediaType.APPLICATION_JSON), 
                    Response.class);
     return response.getStatus() == 201;
    }
}
