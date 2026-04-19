package de.handwerkersystem.awk.auftragverwaltung.entity;

import java.io.Serializable;


import de.handwerkersystem.awk.auftragverwaltung.entity.internal.Adresse;

public class AdresseTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int adresseId;
    private String strasse;
    private String hausnr;
    private String plz;
    private String ort;


    public AdresseTO() {
    }

    public AdresseTO(int adresseId, String strasse, String hausnr, String plz, String ort)  {
        this.adresseId = adresseId;
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.plz = plz;
        this.ort = ort;
      
    }

    public Adresse toEntity()  {

        return new Adresse(adresseId, strasse, hausnr, plz,ort);

    }

    public int getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(int adresseId) {
        this.adresseId = adresseId;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnr() {
        return hausnr;
    }

    public void setHausnr(String hausnr) {
        this.hausnr = hausnr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Override
    public String toString() {
        return strasse + " " + hausnr + ", " + plz + " " + ort;
    }
}
