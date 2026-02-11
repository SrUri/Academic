package model.entities;

import authn.Secured;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import com.google.gson.annotations.Expose;

@Entity
@XmlRootElement
@Table(name="USUARI")
public class Usuari implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy="usuari")
    @Expose
    List<Lloguer> lloguers;
    
    @Id
    @Expose
    private String nom;
    
    private String contrassenya;
    
    public String getUsuari(){
        return nom;
    }
    
    public void setUsuari(String usuari){
        this.nom = usuari;
    }
    
    @Secured
    public String getContrassenya(){
        return contrassenya;
    }
    
    @Secured
    public void setContrassenya(String contrassenya){
        this.contrassenya = contrassenya;
    }
    
    public List<Lloguer> getLloguers(){
        return lloguers;
    }
     
    public void setLloguers(List<Lloguer> lloguer){
        lloguers = lloguer;
    }
}