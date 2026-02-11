package deim.urv.cat.homework2.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
//import com.google.gson.annotations.Expose;

@Entity
@XmlRootElement
@Table(name="USUARI")
public class Usuari implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="USUARI_GEN", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARI_GEN")
    //@Expose
    private int usuari_id;

    @OneToMany(mappedBy="usuari")
    //@Expose
    List<Lloguer> lloguers;
    
    //@Expose
    private String nom;
    private String contrassenya;
    
    public int getId(){
        return usuari_id;
    }
    
    public void setId(int id){
        usuari_id = id;
    }
    
    public String getUsuari(){
        return nom;
    }
    
    public void setUsuari(String usuari){
        this.nom = usuari;
    }
    
    public String getContrassenya(){
        return contrassenya;
    }
    
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