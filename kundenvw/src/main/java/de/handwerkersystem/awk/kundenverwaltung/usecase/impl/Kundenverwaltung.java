package de.handwerkersystem.awk.kundenverwaltung.usecase.impl;

import java.util.Collection;

import de.handwerkersystem.awk.kundenverwaltung.entity.KundeTO;
import de.handwerkersystem.awk.kundenverwaltung.entity.internal.Kunde;
import de.handwerkersystem.awk.kundenverwaltung.persistence.IKundenDAO;
import de.handwerkersystem.awk.kundenverwaltung.persistence.impl.KundenDAO;
import de.handwerkersystem.awk.kundenverwaltung.usecase.IKundenverwaltung;

public class Kundenverwaltung implements IKundenverwaltung {
    

    private IKundenDAO dao = new KundenDAO();

    @Override
    public void kundeAnlegen(KundeTO kunde) {
        dao.save(kunde.toEntity());
    }

    public Kundenverwaltung() {
    }



    @Override
    public void kundenaender(KundeTO kunde) {
     dao.update(kunde.toEntity());
    }

    @Override
    public boolean kundeExistiert(String vorname, String nachname, String plz) {
       return dao.existsByNameAndPlz(vorname,nachname,plz);
    }

    @Override
    public Collection<KundeTO> ladeAlleKunde() {
       return dao.findAll()
              .stream()
              .map(Kunde::toTO)
              .toList();
    }

    @Override
    public boolean kundenLoeschen(KundeTO aKunde) {
        if (aKunde == null || aKunde.getKunde_nr() == null) {
        return false;
    }

    Long kundenNr = aKunde.getKunde_nr();

    if (!dao.existsById(kundenNr)) {
        return false;
    }
try{  dao.deleteById(kundenNr);
    return true;

}catch(Exception e){
    e.getMessage();
    
}
return false;


    }


    
}
