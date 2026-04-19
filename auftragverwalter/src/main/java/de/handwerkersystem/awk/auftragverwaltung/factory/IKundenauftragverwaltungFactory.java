package de.handwerkersystem.awk.auftragverwaltung.factory;

import de.handwerkersystem.awk.auftragverwaltung.usecase.IAbrechnungVw;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IAuftragsbearbeitungVw;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IKundenauftragVw;

public interface IKundenauftragverwaltungFactory {

IKundenauftragVw getKundenauftragVw();
IAuftragsbearbeitungVw  getAuftragsbearbeitungVw();
IAbrechnungVw getAbrechnungVw();
}
