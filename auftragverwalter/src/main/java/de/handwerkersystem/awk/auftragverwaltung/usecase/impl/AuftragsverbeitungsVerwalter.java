package de.handwerkersystem.awk.auftragverwaltung.usecase.impl;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.auftragverwaltung.entity.AuftragsRessourceZuordungTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.AuftragsRessourceZuordung;
import de.handwerkersystem.awk.auftragverwaltung.persitence.IAuftragsbearbeitungDAO;
import de.handwerkersystem.awk.auftragverwaltung.persitence.impl.AuftragsbearbeitungDAO;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IAuftragsbearbeitungVw;
import de.handwerkersystem.awk.persistence.IRessourceDAO;
import de.handwerkersystem.awk.persistence.impl.RessourceDAO;

public class AuftragsverbeitungsVerwalter implements IAuftragsbearbeitungVw {
    IAuftragsbearbeitungDAO dao = new AuftragsbearbeitungDAO();

    private IAuftragsbearbeitungDAO bearbeitungDao = new AuftragsbearbeitungDAO();
    private IRessourceDAO resDao = new RessourceDAO(); 
    @Override
    public void ressourceZuordnen(KundenauftragTO auftrag,
            AuftragsRessourceZuordungTO ressourceId,
            double stunden,
            Double sonderStundensatz) {

   
        if (auftrag == null) {
            return;
        }
        if (ressourceId == null) {
            return;
        }
        if (auftrag.getAuftragNr() <= 0) {
            return;
        }
        if (ressourceId.getRessource_id() <= 0) {
            return;
        }
        if (stunden <= 0) {
            return;
        }

        double stundensatz = 0.0;

        if (sonderStundensatz != null) {
            stundensatz = sonderStundensatz.doubleValue();
        } else {
            try {

                stundensatz = resDao.findKostensatzById(ressourceId.getRessource_id());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            } catch (DatenhaltungsException e) {

                e.printStackTrace();
            }
        }

        AuftragsRessourceZuordung z = new AuftragsRessourceZuordung();
        z.setAuftrag_nr(auftrag.getAuftragNr());
        z.setRessource_id(ressourceId.getRessource_id());
        z.setStunden(stunden);
        z.setIndividuellerStundensatz(stundensatz);

        try {
            bearbeitungDao.ressourceZuordnen(z);
        } catch (DatenhaltungsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void zuordnungAendern(AuftragsRessourceZuordungTO zuordnung) {
        if (zuordnung == null) {
            return;
        }
        if (zuordnung.getId() <= 0) {
            return;
        }

        try {
            dao.zuordnungAendern(zuordnung.toEntity());
        } catch (DatenhaltungsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean zuordnungLoeschen(AuftragsRessourceZuordungTO zuordnung) {
        if (zuordnung == null) {
            return false;
        }
        if (zuordnung.getId() <= 0) {
            return false;
        }

        try {
            return dao.zuordnungLoeschen(zuordnung.toEntity());
        } catch (DatenhaltungsException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean auftragAbschliessen(KundenauftragTO auftrag) {
        if (auftrag == null) {
            return false;
        }
        if (auftrag.getAuftragNr() <= 0) {
            return false;
        }

        auftrag.setBearbeitungAbgeschlossen(true);

        try {
         
            return dao.auftragAbschliessen(auftrag.toEntity());
        } catch (DatenhaltungsException e) {
            e.printStackTrace();
            return false;
        }
    }

}