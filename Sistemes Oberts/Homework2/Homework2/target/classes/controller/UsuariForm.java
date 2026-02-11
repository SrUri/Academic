package deim.urv.cat.homework2.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.mvc.binding.MvcBinding;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import java.io.Serializable;

@Named("userForm")
@RequestScoped
public class UsuariForm implements Serializable {
    private static final long serialVersionUID = 1L;
        
    // JSR 303 validation
    @NotBlank
    @FormParam("nom")
    @MvcBinding
    @Size(min=2, max=30, message = "First name must be between 2 and 30 characters")
    private String nom;
    
    // JSR 303 validation
    @NotBlank
    @FormParam("contrasenya")
    @MvcBinding
    @Size(min=8, max=20, message = "Password should be between 8 and 20 characters")
    private String contrasenya;
    
    public String getName() {
        return fixNull(this.nom);
    }

    public void setName(String firstName) {
        this.nom = firstName;
    }

    public String getPwd() {
        return this.contrasenya;
    }

    public void setPwd(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    private String fixNull(String in) {
        return (in == null) ? "" : in;
    }
}
