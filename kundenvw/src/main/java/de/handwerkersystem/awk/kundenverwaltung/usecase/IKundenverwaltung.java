package de.handwerkersystem.awk.kundenverwaltung.usecase;

import java.util.Collection;

import de.handwerkersystem.awk.kundenverwaltung.entity.KundeTO;

public interface IKundenverwaltung {
    public void kundeAnlegen(KundeTO aKunde);

    public boolean kundenLoeschen(KundeTO aKunde);

    public void kundenaender(KundeTO kunde);

    boolean kundeExistiert(String vorname, String nachname, String plz);

    Collection<KundeTO> ladeAlleKunde();
}
