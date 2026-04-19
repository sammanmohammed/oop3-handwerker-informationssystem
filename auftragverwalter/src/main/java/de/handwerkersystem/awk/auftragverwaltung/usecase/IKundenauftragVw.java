package de.handwerkersystem.awk.auftragverwaltung.usecase;

import java.util.Collection;

import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;

public interface IKundenauftragVw {
    public boolean auftragAnlegen(KundenauftragTO aAuftrag);

    public void auftragAendern(KundenauftragTO aAuftrag);

    public boolean auftragLoeschen(KundenauftragTO auftragId);
     Collection<KundenauftragTO> ladeAlleAuftraege();

}
