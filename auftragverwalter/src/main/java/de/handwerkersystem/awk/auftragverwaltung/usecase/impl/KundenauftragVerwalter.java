package de.handwerkersystem.awk.auftragverwaltung.usecase.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.Kundenauftrag;
import de.handwerkersystem.awk.auftragverwaltung.persitence.IKundenauftragDAO;
import de.handwerkersystem.awk.auftragverwaltung.persitence.impl.KundenauftragDAO;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IKundenauftragVw;

public class KundenauftragVerwalter implements IKundenauftragVw {
  IKundenauftragDAO dao = new KundenauftragDAO();

 @Override
public boolean auftragAnlegen(KundenauftragTO aAuftrag) {
  if (aAuftrag == null) return false;

  if (aAuftrag.getTextfeld() != null && aAuftrag.getTextfeld().length() > 2000) {
    return false;
  }

  try {
    dao.save(aAuftrag.toEntity());
    return true;
  } catch (DatenhaltungsException e) {
    e.printStackTrace();
    return false;
  }
}


  @Override
  public void auftragAendern(KundenauftragTO aAuftrag) {
    try {
      dao.update(aAuftrag.toEntity());
    } catch (DatenhaltungsException e) {
     
      e.printStackTrace();
    }
  }

@Override
public boolean auftragLoeschen(KundenauftragTO auftragTO) {
  if (auftragTO == null || auftragTO.getAuftragNr() == 0) {
    return false;
  }

  long auftragNr = auftragTO.getAuftragNr();

  try {
    if (!dao.existsById(auftragNr)) {
      return false;
    }
    dao.deleteById(auftragNr);
    return true;

  } catch (DatenhaltungsException e) {
    e.printStackTrace();
    return false;
  }
}


@Override
public Collection<KundenauftragTO> ladeAlleAuftraege() {
    List<Kundenauftrag> auftraege;
    Collection<KundenauftragTO> tos = new ArrayList<KundenauftragTO>();

    try {
        auftraege = dao.findAll();

        for (Kundenauftrag a : auftraege) {
            tos.add(a.toTo());
        }

        return tos;

    } catch (DatenhaltungsException e) {
        e.printStackTrace();
        return new ArrayList<KundenauftragTO>();  
    }
}



}
