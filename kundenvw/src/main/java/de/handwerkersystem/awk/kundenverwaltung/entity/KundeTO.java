package de.handwerkersystem.awk.kundenverwaltung.entity;

import java.io.Serializable;

import de.handwerkersystem.awk.kundenverwaltung.entity.internal.Kunde;

public class KundeTO implements Serializable {

    private static final long serialVersionUID = 1L;

  private Long kunde_nr;
    private String name;
    private String vorname;
    private String strasse;
    private int hausNr;
    private String plz;
    private String ort;
    

 

    public KundeTO() {}

    public KundeTO(Long kunde_nr, String name, String vorname,
                   String strasse, int hausNr, String plz, String ort) {
        this.kunde_nr = kunde_nr;
        this.name = name;
        this.vorname = vorname;
        this.strasse = strasse;
        this.hausNr = hausNr;
        this.plz = plz;
        this.ort = ort;
    }

    public Kunde toEntity() {
        Kunde k = new Kunde(name, vorname, strasse, hausNr, plz, ort);
        if (kunde_nr != null) {
            k.setKunde_nr(kunde_nr);
        }
        return k;
    }

 
    public Long getKunde_nr() {
        return kunde_nr;
    }

    public void setKunde_nr(Long kunde_nr) {
        this.kunde_nr = kunde_nr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public int getHausNr() {
        return hausNr;
    }

    public void setHausNr(int hausNr) {
        this.hausNr = hausNr;
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
        return vorname + " " + name;
    }
}
