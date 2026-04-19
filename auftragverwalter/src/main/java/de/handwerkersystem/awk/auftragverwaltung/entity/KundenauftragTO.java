package de.handwerkersystem.awk.auftragverwaltung.entity;

import java.io.Serializable;

import de.handwerkersystem.awk.auftragverwaltung.entity.internal.Adresse;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.Kundenauftrag;

public class KundenauftragTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long auftragNr;
    private java.util.Date datum;
    private String textfeld;
    private long kunde_nr;
    private boolean bearbeitung_Abgeschlossen;
    private boolean abgerechnet;

    private AdresseTO baustellenAdresse;

    public KundenauftragTO() {
    }

    public KundenauftragTO(long auftragNr, java.util.Date datum, String textfeld, long kunde_nr,
            boolean bearbeitung_Abgeschlossen, boolean abgerechnet,
            AdresseTO baustellenAdresse) {
        this.auftragNr = auftragNr;
        this.datum = datum;
        this.textfeld = textfeld;
        this.kunde_nr = kunde_nr;
        this.bearbeitung_Abgeschlossen = bearbeitung_Abgeschlossen;
        this.abgerechnet = abgerechnet;
        this.baustellenAdresse = baustellenAdresse;
    }

    public Kundenauftrag toEntity() {

        Adresse adrEntity = null;

        if (baustellenAdresse != null) {
            adrEntity = new Adresse();
            adrEntity.setAdresseId(0);
            adrEntity.setStrasse(baustellenAdresse.getStrasse());
            adrEntity.setHausnr(baustellenAdresse.getHausnr());
            adrEntity.setPlz(baustellenAdresse.getPlz());
            adrEntity.setOrt(baustellenAdresse.getOrt());
        }

        return new Kundenauftrag(
                auftragNr, datum, textfeld, kunde_nr,
                bearbeitung_Abgeschlossen, abgerechnet,
                adrEntity);
    }

    public long getAuftragNr() {
        return auftragNr;
    }

    public void setAuftragNr(long auftragNr) {
        this.auftragNr = auftragNr;
    }

    public java.util.Date getDatum() {
        return datum;
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

    public Long getKunde_nr() {
        return kunde_nr;
    }

    public void setKunde_nr(long kunde_nr) {
        this.kunde_nr = kunde_nr;
    }

    public boolean isBearbeitungAbgeschlossen() {
        return bearbeitung_Abgeschlossen;
    }

    public void setBearbeitungAbgeschlossen(boolean b) {
        this.bearbeitung_Abgeschlossen = b;
    }

    public boolean isAbgerechnet() {
        return abgerechnet;
    }

    public void setAbgerechnet(boolean abgerechnet) {
        this.abgerechnet = abgerechnet;
    }

    public AdresseTO getBaustellenAdresse() {
        return baustellenAdresse;
    }

    public void setBaustellenAdresse(AdresseTO baustellenAdresse) {
        this.baustellenAdresse = baustellenAdresse;
    }

    public String printAdresse() {
        if (baustellenAdresse == null) {
            return "-";
        }
        String adresse = "\nHausNr.: " + baustellenAdresse.getHausnr() + "\nStraße: " + baustellenAdresse.getStrasse()
                + "\nPLZ:"
                + baustellenAdresse.getPlz() + "\nOrt:" + baustellenAdresse.getOrt();
        return adresse;
    }

}
