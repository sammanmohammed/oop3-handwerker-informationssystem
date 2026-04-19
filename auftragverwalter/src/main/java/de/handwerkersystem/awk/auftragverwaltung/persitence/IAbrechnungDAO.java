package de.handwerkersystem.awk.auftragverwaltung.persitence;

import java.util.Collection;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.auftragverwaltung.entity.AuftragsRessourceZuordungTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;

public interface IAbrechnungDAO {

   
    Collection<KundenauftragTO> ladeAbzurechnendeAuftraege() throws DatenhaltungsException;

    Collection<AuftragsRessourceZuordungTO> ladeRechnungspositionen(long auftragNr) throws DatenhaltungsException;

    double berechneAuftragssumme(long auftragNr) throws DatenhaltungsException;

    void auftragAbgerechnetSetzen(long auftragNr) throws DatenhaltungsException;
}
