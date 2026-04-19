package de.handwerkersystem.awk.entity.internal;

import java.io.Serializable;

import de.handwerkersystem.awk.entity.RessourceTO;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA2_Ressourcen")
public class Ressource implements Serializable {
    
    private static final long serialVersionUID = -3130735335614620677L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HA2_RES_SEQ")
    @SequenceGenerator(name = "HA2_RES_SEQ", sequenceName = "HA2_RES_SEQ", allocationSize = 1)
    private Long id;
    private String art;
    private double standrdKostensatz;
    private String name;


    public Ressource() {
    }

    public Ressource(String art, double standrdKostensatz, String name) {
        this.art = art;
        this.standrdKostensatz = standrdKostensatz;
        this.name = name;
 
    }

    public RessourceTO toTO() {
        return new RessourceTO(id, art, standrdKostensatz, name);
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public double getStandrdKostensatz() {
        return standrdKostensatz;
    }

    public void setStandrdKostensatz(double standrdKostensatz) {
        this.standrdKostensatz = standrdKostensatz;
    }

   

    @Override
    public String toString() {

        return name + " (" + art + ")";

    }

    public void setId(Long id2) {
        this.id = id2;
    }

}
