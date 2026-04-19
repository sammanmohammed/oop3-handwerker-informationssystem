package de.handwerkersystem.awk.auftragverwaltung.factory.impl;

import de.handwerkersystem.awk.auftragverwaltung.factory.IKundenauftragverwaltungFactory;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IAbrechnungVw;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IAuftragsbearbeitungVw;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IKundenauftragVw;
import de.handwerkersystem.awk.auftragverwaltung.usecase.impl.AbrechnungsVerwalter;
import de.handwerkersystem.awk.auftragverwaltung.usecase.impl.AuftragsverbeitungsVerwalter;
import de.handwerkersystem.awk.auftragverwaltung.usecase.impl.KundenauftragVerwalter;

public class KundenauftragverwaltungFactory implements IKundenauftragverwaltungFactory {

    @Override
    public IKundenauftragVw getKundenauftragVw() {
        return new KundenauftragVerwalter();
    }

    @Override
    public IAuftragsbearbeitungVw getAuftragsbearbeitungVw() {
        return new AuftragsverbeitungsVerwalter();
    }

    @Override
    public IAbrechnungVw getAbrechnungVw() {
        return new AbrechnungsVerwalter();
    }

}
