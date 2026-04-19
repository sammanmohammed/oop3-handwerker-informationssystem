package de.handwerkersystem.awk.auftragverwaltung.entity;

import java.io.Serializable;


import de.handwerkersystem.awk.auftragverwaltung.entity.internal.AuftragsRessourceZuordung;




public class AuftragsRessourceZuordungTO implements Serializable {

   
    private long id;

    private long auftrag_nr;

    private long ressource_id;

    private double stunden;
    private double individuellerStundensatz;

    public long getId() {
        return id;
    }

    public AuftragsRessourceZuordungTO( long id,long auftrag_nr, long ressource_id, double stunden,
            double individuellerStundensatz) {
        this.auftrag_nr = auftrag_nr;
        this.ressource_id = ressource_id;
        this.stunden = stunden;
        this.individuellerStundensatz = individuellerStundensatz;
    }

    public AuftragsRessourceZuordungTO() {
    }



    public AuftragsRessourceZuordung toEntity(){
       return new AuftragsRessourceZuordung(id,auftrag_nr, ressource_id, stunden, individuellerStundensatz);
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getAuftrag_nr() {
        return auftrag_nr;
    }

    public void setAuftrag_nr(long auftrag_nr) {
        this.auftrag_nr = auftrag_nr;
    }

    public long getRessource_id() {
        return ressource_id;
    }

    public void setRessource_id(long ressource_id) {
        this.ressource_id = ressource_id;
    }

    public double getStunden() {
        return stunden;
    }

    public void setStunden(double stunden) {
        this.stunden = stunden;
    }

    public double getIndividuellerStundensatz() {
        return individuellerStundensatz;
    }

    public void setIndividuellerStundensatz(double individuellerStundensatz) {
        this.individuellerStundensatz = individuellerStundensatz;
    }

}