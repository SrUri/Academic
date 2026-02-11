package deim.urv.cat.homework2.model;

import jakarta.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlRootElement
@Table(name="VIDEOJOC")
public class Videojoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String nom;
    
    //*
    @ManyToMany
    @JoinTable(
        name="VIDEOJOC_LLOGUER",
        joinColumns = @JoinColumn(
                name="NOM_VIDEOJOC", referencedColumnName="nom"),
        inverseJoinColumns = @JoinColumn(
                name="ID_LLOGUER", referencedColumnName="lloguer_id")
    )//*/
    @JsonbTransient List<Lloguer> lloguers = new ArrayList<>();
    
    private String videoconsola;
    private int disponibilitat;
    private float preu;
    private String descripcio;
    private String tipus;
    private String botiguesFisiques;
    
    
    public String getNom(){
        return nom;
    }
    
    public void setNom(String nom){
        this.nom = nom;
    }
    
    public String getVideoconsola(){
        return videoconsola;
    }
    
    public void setVideoconsola(String videoconsola){
        this.videoconsola = videoconsola;
    }
    
    public int getDisponibilitat(){
        return disponibilitat;
    }
    
    public void setDisponibilitat(int stock){
        this.disponibilitat = stock;
    }
    
    public float getPreu(){
        return preu;
    }
    
    public void setPreu(float preu){
        this.preu = preu;
    }
    
    public String getDescripcio(){
        return descripcio;
    }
    
    public void setDescripcio(String descripcio){
        this.descripcio = descripcio;
    }
    
    public String getTipus(){
        return tipus;
    }
    
    public void setTipus(String tipus){
        this.tipus = tipus;
    }
    
    public String getBotiguesFisiques(){
        return botiguesFisiques;
    }
    
    public void setBotiguesFisiques(String botiguesFisiques){
        this.botiguesFisiques = botiguesFisiques;
    }
    
    public List<Lloguer> getLloguers() {
        return lloguers;
    }
    
    public void setLloguer(Lloguer ll) {
        lloguers.add(ll);
    }
}