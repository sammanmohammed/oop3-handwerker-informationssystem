package de.handwerkersystem.awk.auftragverwaltung.persitence;

import java.util.List;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.Kundenauftrag;

public interface IKundenauftragDAO {
    Kundenauftrag save(Kundenauftrag auftrag) throws DatenhaltungsException;

    Kundenauftrag update(Kundenauftrag auftrag) throws DatenhaltungsException;

    void deleteById(long id)throws DatenhaltungsException;

    List<Kundenauftrag> findAll()throws DatenhaltungsException;

    boolean existsById(long auftrag_nr)throws DatenhaltungsException;

}
