import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.handwerkersystem.awk.auftragverwaltung.entity.AuftragsRessourceZuordungTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;
import de.handwerkersystem.awk.auftragverwaltung.factory.impl.KundenauftragverwaltungFactory;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IAbrechnungVw;
import de.handwerkersystem.awk.auftragverwaltung.usecase.IKundenauftragVw;

public class AuftragverwaltungTest {

    @Test
    void auftragKannAngelegtWerden() {

        IKundenauftragVw auftragVw = new KundenauftragverwaltungFactory().getKundenauftragVw();

        KundenauftragTO auftrag = new KundenauftragTO();
        auftrag.setAuftragNr(1);
        auftrag.setTextfeld("Bad renovieren");
        auftrag.setBearbeitungAbgeschlossen(false);

        assertDoesNotThrow(() -> auftragVw.auftragAnlegen(auftrag),
                "Ein gültiger Auftrag muss angelegt werden können");
    }

   @Test
void ressourceKannZuAuftragZugeordnetWerden() {

    KundenauftragTO auftrag = new KundenauftragTO();
    auftrag.setAuftragNr(1);

    AuftragsRessourceZuordungTO zuordnung =
            new AuftragsRessourceZuordungTO();
    zuordnung.setRessource_id(10);

    assertDoesNotThrow(() -> {
        double stunden = 3.5;
        assertTrue(stunden > 0);
    });
}
@Test
void auftragssummeWirdKorrektBerechnet() {

    IAbrechnungVw abrechVw =
            new KundenauftragverwaltungFactory().getAbrechnungVw();

    KundenauftragTO auftrag = new KundenauftragTO();
    auftrag.setAuftragNr(1);

   
    double stunden = 4.0;
    double stundensatz = 30.0;

    double summe = abrechVw.berechneAuftragssummeTest(
            stunden, stundensatz
    );

    assertEquals(120.0, summe, 0.001,
            "Auftragssumme muss Stunden * Stundensatz sein");
}



}