package de.handwerkersystem.awk.auftragverwaltung.usecase;

import de.handwerkersystem.awk.auftragverwaltung.entity.AuftragsRessourceZuordungTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;

public interface IAuftragsbearbeitungVw {

    void ressourceZuordnen(KundenauftragTO auftrag, AuftragsRessourceZuordungTO ressourceId, 
                           double stunden, Double sonderStundensatz);

    void zuordnungAendern(AuftragsRessourceZuordungTO zuordnung);

    boolean zuordnungLoeschen(AuftragsRessourceZuordungTO zuordnung);

    boolean auftragAbschliessen(KundenauftragTO auftrag);
}

