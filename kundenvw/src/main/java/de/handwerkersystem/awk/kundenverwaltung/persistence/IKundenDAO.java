package de.handwerkersystem.awk.kundenverwaltung.persistence;

import java.util.List;

import de.handwerkersystem.awk.kundenverwaltung.entity.internal.Kunde;

public interface IKundenDAO {

    Kunde save(Kunde kunde);

    Kunde update(Kunde kunde);

    void deleteById(Long id);

    Kunde findById(Long id);

    List<Kunde> findAll();

    boolean existsById(Long kundenNr);

  

    boolean existsByNameAndPlz(String vorname, String nachname, String plz);



}
