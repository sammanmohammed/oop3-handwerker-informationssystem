package de.handwerkersystem.awk.auftragverwaltung.usecase;

import java.util.Collection;

import de.handwerkersystem.awk.auftragverwaltung.entity.AuftragsRessourceZuordungTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;


public interface IAbrechnungVw {

public Collection<KundenauftragTO> ladeAbzurechnendeAuftraege();
public Collection<AuftragsRessourceZuordungTO> ladeRechnungspositionen(KundenauftragTO auftrag);
public double berechneAuftragssumme(KundenauftragTO auftrag);
public void auftragAbgerechnetSetzen(KundenauftragTO auftrag);
//Es gehört zum Testen
public double berechneAuftragssummeTest(double stunden, double stundensatz) ;

}