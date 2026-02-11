package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Usuari;
import deim.urv.cat.homework2.model.AlertMessage;
import deim.urv.cat.homework2.model.SignUpAttempts;
import deim.urv.cat.homework2.service.UsuariService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.UriRef;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.ParamError;
import jakarta.mvc.security.CsrfProtected;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Path("Login")
public class LoginFormController {
    // CDI
    @Inject BindingResult bindingResult;
    @Inject Logger log;
    @Inject UsuariService service;
    @Inject Models models;
    @Inject AlertMessage flashMessage;

    @GET
    public String showForm() {
        return "login.jsp"; // Injects CRSF token
    }

    @POST
    @UriRef("login")
    @CsrfProtected
    public String login(@FormParam("nomUsuari") String username, @FormParam("password") String password) {
        if (username == null || password == null) {
            models.put("message", "Invalid username or password");
            return "login.jsp";
        }

        Usuari user = service.authenticate(username, password);

        if (user == null) {
            models.put("message", "Cannot find User");
            return "login.jsp";
        }

        log.log(Level.INFO, "Redirecting to the success page.");
        // You can add additional logic based on successful login
        return "login-success.jsp";
    }
}
