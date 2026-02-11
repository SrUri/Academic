package deim.urv.cat.homework2.model;

import jakarta.persistence.CascadeType;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlRootElement
@Table(name="LLOGUER")
public class Lloguer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="LLOGUER_GEN", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LLOGUER_GEN")
    private int lloguer_id;
    
    @ManyToMany(mappedBy="lloguers")
    private List<Videojoc> videojocs = new ArrayList<>();
    
    @ManyToOne
    private Usuari usuari;
    
    private int usuari_id;

    private String data;
    
    public int getId(){
        return lloguer_id;
    }
    
    public String getDateLloguer(){
        return data;
    }
    
    public int getUsuari_id(){
        return usuari_id;
    }
    
    public void setUsuari(Usuari user){
        this.usuari = user;
    }
    
    public void setDateLLoguer(String date){
        String dataNova;
        LocalDate datalocal = LocalDate.parse(date);
        LocalDate novaData = datalocal.plusDays(7);
        dataNova = novaData.toString();
        data = dataNova;
    }
    
    public void setVideojocsLloguer(Videojoc joc){
        videojocs.add(joc);
    }
    
    public List<Videojoc> getVideojocsLloguer() {
        return videojocs;
    }
            
    public float getPreuLloguer(){
        float preu = 0;
        if(videojocs != null){
            for(int i = 0; i < videojocs.size(); i++){
                preu += videojocs.get(i).getPreu();
            }
        }
        return preu;
    }
}