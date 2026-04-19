package de.handwerkersystem.awk.auftragverwaltung.entity.internal;

import java.io.Serializable;

import de.handwerkersystem.awk.auftragverwaltung.entity.AdresseTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;

public class Kundenauftrag implements Serializable {

    private long auftragNr;
    private java.util.Date datum;
    private String textfeld;
    private long kunde_nr;
    private boolean bearbeitung_Abgeschlossen;
    private boolean abgerechnet;
    private Adresse baustellenAdresse;

    public Kundenauftrag(long auftragNr, java.util.Date datum, String textfeld, long kunde_nr,
            boolean bearbeitung_Abgeschlossen, boolean abgerechnet, Adresse baustellenAdresse) {
        this.auftragNr = auftragNr;
        this.datum = datum;
        this.textfeld = textfeld;
        this.kunde_nr = kunde_nr;
        this.bearbeitung_Abgeschlossen = bearbeitung_Abgeschlossen;
        this.abgerechnet = abgerechnet;
        this.baustellenAdresse = baustellenAdresse;
    }

    public Adresse getBaustellenAdresse() {
        return baustellenAdresse;
    }

    public void setBaustellenAdresse(Adresse baustellenAdresse) {
        this.baustellenAdresse = baustellenAdresse;
    }

    public KundenauftragTO toTo() {

        AdresseTO adrTO = null;

        if (this.baustellenAdresse != null) {
            adrTO = new AdresseTO();
            adrTO.setAdresseId(this.baustellenAdresse.getAdresseId());
            adrTO.setStrasse(this.baustellenAdresse.getStrasse());
            adrTO.setHausnr(this.baustellenAdresse.getHausnr());
            adrTO.setPlz(this.baustellenAdresse.getPlz());
            adrTO.setOrt(this.baustellenAdresse.getOrt());
        }

        return new KundenauftragTO(
                this.auftragNr,
                this.datum,
                this.textfeld,
                this.kunde_nr,
                this.bearbeitung_Abgeschlossen,
                this.abgerechnet,
                adrTO);
    }

    public java.util.Date getDatum() {
        return datum;
    }

    public long getAuftragNr() {
        return auftragNr;
    }

    public boolean isBearbeitungAbgeschlossen() {
        return bearbeitung_Abgeschlossen;
    }

    public void setBearbeitungAbgeschlossen(boolean bearbeitungAbgeschlossen) {
        this.bearbeitung_Abgeschlossen = bearbeitungAbgeschlossen;
    }

    public long getKunde_nr() {
        return kunde_nr;
    }

    public void setKunde_nr(long kunde_nr) {
        this.kunde_nr = kunde_nr;
    }

    public boolean isBearbeitung_Abgeschlossen() {
        return bearbeitung_Abgeschlossen;
    }

    public void setBearbeitung_Abgeschlossen(boolean bearbeitung_Abgeschlossen) {
        this.bearbeitung_Abgeschlossen = bearbeitung_Abgeschlossen;
    }

    public boolean isAbgerechnet() {
        return abgerechnet;
    }

    public void setAbgerechnet(boolean abgerechnet) {
        this.abgerechnet = abgerechnet;
    }

    public Kundenauftrag() {
    }

    public void setAuftragNr(long auftragNr) {
        this.auftragNr = auftragNr;
    }

    public void setDatum(java.util.Date datum) {
        this.datum = datum;
    }

    public String getTextfeld() {
        return textfeld;
    }

    public void setTextfeld(String textfeld) {
        this.textfeld = textfeld;
    }
}
