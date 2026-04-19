package de.handwerkersystem.awk.auftragverwaltung.persitence;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.Kundenauftrag;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.AuftragsRessourceZuordung;

public interface IAuftragsbearbeitungDAO {

    void ressourceZuordnen(AuftragsRessourceZuordung zuordnung) throws DatenhaltungsException;

    void zuordnungAendern(AuftragsRessourceZuordung zuordnung) throws DatenhaltungsException;

    boolean zuordnungLoeschen(AuftragsRessourceZuordung zuordnung) throws DatenhaltungsException;

    boolean auftragAbschliessen(Kundenauftrag auftrag) throws DatenhaltungsException;
}
