package de.handwerkersystem.awk.auftragverwaltung.usecase.impl;

import java.util.ArrayList;
import java.util.Collection;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.auftragverwaltung.entity.AuftragsRessourceZuordungTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;
import de.handwerkersystem.awk.auftragverwaltung.persitence.IAbrechnungDAO;
import de.handwerkersystem.awk.auftragverwaltung.persitence.impl.AbrechnungDAO;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IAbrechnungVw;

public class AbrechnungsVerwalter implements IAbrechnungVw {

    private IAbrechnungDAO dao = new AbrechnungDAO();

    @Override
    public Collection<KundenauftragTO> ladeAbzurechnendeAuftraege() {
        try {
            Collection<KundenauftragTO> auftraege = dao.ladeAbzurechnendeAuftraege();
            if (auftraege == null) {
                return new ArrayList<KundenauftragTO>();
            }
            return auftraege;
        } catch (DatenhaltungsException e) {
            e.printStackTrace();
            return new ArrayList<KundenauftragTO>();
        }
    }

    @Override
    public Collection<AuftragsRessourceZuordungTO> ladeRechnungspositionen(KundenauftragTO auftrag) {

        if (auftrag == null) {
            return new ArrayList<AuftragsRessourceZuordungTO>();
        }
        if (auftrag.getAuftragNr() <= 0) {
            return new ArrayList<AuftragsRessourceZuordungTO>();
        }

        try {
            Collection<AuftragsRessourceZuordungTO> pos =
                    dao.ladeRechnungspositionen(auftrag.getAuftragNr());

            if (pos == null) {
                return new ArrayList<AuftragsRessourceZuordungTO>();
            }
            return pos;

        } catch (DatenhaltungsException e) {
            e.printStackTrace();
            return new ArrayList<AuftragsRessourceZuordungTO>();
        }
    }

    @Override
    public double berechneAuftragssumme(KundenauftragTO auftrag) {

        if (auftrag == null) {
            return 0.0;
        }
        if (auftrag.getAuftragNr() <= 0) {
            return 0.0;
        }

        double summe = 0.0;

        Collection<AuftragsRessourceZuordungTO> positionen = ladeRechnungspositionen(auftrag);

        if (positionen != null) {
            for (AuftragsRessourceZuordungTO pos : positionen) {
                double stunden = pos.getStunden();
                double stundensatz = pos.getIndividuellerStundensatz();
                summe = summe + (stunden * stundensatz);
            }
        }

        return summe;
    }

    @Override
    public void auftragAbgerechnetSetzen(KundenauftragTO auftrag) {

        if (auftrag == null) {
            return;
        }
        if (auftrag.getAuftragNr() <= 0) {
            return;
        }

        try {
            dao.auftragAbgerechnetSetzen(auftrag.getAuftragNr());
            auftrag.setAbgerechnet(true);
        } catch (DatenhaltungsException e) {
            e.printStackTrace();
        }
    }
// Es gehört zum Testen
public double berechneAuftragssummeTest(double stunden, double stundensatz) {
    return stunden * stundensatz;
}


}
