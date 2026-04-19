package de.handwerkersystem.awk.entity;
import de.handwerkersystem.awk.entity.internal.Ressource;

public class RessourceTO {

    private Long id;
    private String art;
    private double standrdKostensatz;

    public RessourceTO(Long id, String art, double standrdKostensatz, String name) {
        this.id = id;
        this.art = art;
        this.standrdKostensatz = standrdKostensatz;
        this.name = name;
    }

    public Ressource toEntity() {
        Ressource r = new Ressource(art, standrdKostensatz, name);
        if (id != null) {
            r.setId(id);
        }
        return r;

    }

    public RessourceTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public double getStandrdKostensatz() {
        return standrdKostensatz;
    }

    public void setStandrdKostensatz(double standrdKostensatz) {
        this.standrdKostensatz = standrdKostensatz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
        @Override
    public String toString() {

        return name + " (" + art + ")";

    }
}
