package de.handwerkersystem.awk.kundenverwaltung.entity.internal;

import java.io.Serializable;

import de.handwerkersystem.awk.kundenverwaltung.entity.KundeTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ha2_Kunde")
@NamedQuery(name = Kunde.FIND_BY_NAME_PLZ, query = "SELECT k FROM Kunde k WHERE k.vorname = :vorname AND k.name = :nachname AND k.plz = :plz")

public class Kunde implements Serializable {
    private static final long serialVersionUID = -3130735335614620677L;

    public static final String FIND_BY_NAME_PLZ = "Kunde.findByNameAndPlz";

    @Id
    @SequenceGenerator(name = "H2_KUNDENR_SEQ", sequenceName = "H2_KUNDENR_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "H2_KUNDENR_SEQ")
    private Long kunde_nr;
    private String name;
    private String vorname;
    private String strasse;
    private int hausNr;
    private String plz;
    private String ort;

    public Kunde() {
    }

    public KundeTO toTO() {
        return new KundeTO(
                kunde_nr,
                name,
                vorname,
                strasse,
                hausNr,
                plz,
                ort);
    }

    public Kunde(String name, String vorname, String strasse, int hausNr, String plz, String ort) {
        this.name = name;
        this.vorname = vorname;
        this.strasse = strasse;
        this.hausNr = hausNr;
        this.plz = plz;
        this.ort = ort;
    }

    public void setKunde_nr(Long kunde_nr) {
        this.kunde_nr = kunde_nr;
    }

    public String getStrasse() {
        return strasse;
    }

    public Long getKunde_nr() {
        return kunde_nr;
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

    @Override
    public String toString() {
        return vorname + " " + name;
    }
}
