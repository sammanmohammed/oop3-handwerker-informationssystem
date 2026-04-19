package de.handwerkersystem.dlg.hauptmenue;

import de.handwerkersystem.awk.auftragverwaltung.factory.IKundenauftragverwaltungFactory;
import de.handwerkersystem.awk.auftragverwaltung.factory.impl.KundenauftragverwaltungFactory;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IAbrechnungVw;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IAuftragsbearbeitungVw;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IKundenauftragVw;
import de.handwerkersystem.awk.auftragverwaltung.usecase.impl.AbrechnungsVerwalter;
import de.handwerkersystem.awk.auftragverwaltung.usecase.impl.AuftragsverbeitungsVerwalter;
import de.handwerkersystem.awk.auftragverwaltung.usecase.impl.KundenauftragVerwalter;
import de.handwerkersystem.awk.factory.IRessourcenVerwaltungFactory;
import de.handwerkersystem.awk.factory.impl.RessourceverwaltungFactory;
import de.handwerkersystem.awk.kundenverwaltung.factory.IKundenverwaltungFactory;
import de.handwerkersystem.awk.kundenverwaltung.factory.impl.KundenverwaltungFactory;
import de.handwerkersystem.awk.kundenverwaltung.usecase.IKundenverwaltung;
import de.handwerkersystem.awk.kundenverwaltung.usecase.impl.Kundenverwaltung;
import de.handwerkersystem.awk.usecase.IRessourcenVw;
import de.handwerkersystem.awk.usecase.impl.RessorcenVerwalter;


public class HauptmenueService {

	private static IKundenverwaltungFactory kundenvf;
	private static IKundenverwaltung kundevw = new Kundenverwaltung();

	private static IRessourcenVerwaltungFactory ressourcenvf;
	private static IRessourcenVw ressourcenVw = new RessorcenVerwalter();



	private static IKundenauftragverwaltungFactory auftragvf;
	private static IKundenauftragVw auftragVw = new KundenauftragVerwalter();


	
	private static IAbrechnungVw abrechnungVw = new AbrechnungsVerwalter();


	private static IAuftragsbearbeitungVw auftragsbearbeitungVw = new AuftragsverbeitungsVerwalter();

	public HauptmenueService() {
		kundenvf = new KundenverwaltungFactory();
		kundevw = kundenvf.getKundenverwaltung();

		ressourcenvf = new RessourceverwaltungFactory();
		ressourcenVw = ressourcenvf.getRessourcenVw();

		auftragvf = new KundenauftragverwaltungFactory();

		auftragVw = auftragvf.getKundenauftragVw();
		
		abrechnungVw = auftragvf.getAbrechnungVw();

		auftragsbearbeitungVw = auftragvf.getAuftragsbearbeitungVw();
		


	}

	public static IKundenverwaltung getKundevw() {
		return kundevw;
	}

	public static IKundenauftragVw getAuftragVw() {
		return auftragVw;
	}

	public static IAbrechnungVw getAbrechnungVw() {
		return abrechnungVw;
	}

	public static IAuftragsbearbeitungVw getAuftragsbearbeitungVw() {
		return auftragsbearbeitungVw;
	}

	public static IKundenverwaltung gKundenverwaltung() {
		return kundevw;
	}

	public static IRessourcenVw getRessourcenVw() {
		return ressourcenVw;
	}

}
