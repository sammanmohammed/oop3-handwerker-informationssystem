import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.Test;

import de.handwerkersystem.awk.kundenverwaltung.entity.KundeTO;
import de.handwerkersystem.awk.kundenverwaltung.factory.impl.KundenverwaltungFactory;
import de.handwerkersystem.awk.kundenverwaltung.usecase.IKundenverwaltung;

public class KundenVerwalterTest {

    @Test
    void testKundeMitAuftragKannNichtGeloeschtWerden() throws Exception {

        IKundenverwaltung kundenVw = new KundenverwaltungFactory().getKundenverwaltung();

        // Kunde anlegen
        KundeTO kunde = new KundeTO(
                null, "Mustermann", "Max",
                "Musterstr.", 1, "4321", "Musterstadt");
        kundenVw.kundeAnlegen(kunde);

        KundeTO geladenerKunde = kundenVw.ladeAlleKunde().stream()
                .filter(k -> "Mustermann".equals(k.getName())
                        && "Max".equals(k.getVorname()))
                .findFirst()
                .orElseThrow();

        Long kundenNr = geladenerKunde.getKunde_nr();

        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@oracle.zemit.wi.hs-osnabrueck.de:1521:orclcdb",
                "STUD19",
                "Weltmeister2014");

        Long adresseId;
        String adrSql = "INSERT INTO HA2_ADRESSE (ADRESSE_ID, STRASSE, HAUSNR, PLZ, ORT) " +
                "VALUES (HA2_ADRESSE_ID_SEQ.NEXTVAL, ?, ?, ?, ?)";

        try (PreparedStatement psAdr = con.prepareStatement(adrSql, new String[] { "ADRESSE_ID" })) {

            psAdr.setString(1, "Baustellenstr.");
            psAdr.setString(2, "10");
            psAdr.setString(3, "12345");
            psAdr.setString(4, "Musterstadt");
            psAdr.executeUpdate();

            try (var rs = psAdr.getGeneratedKeys()) {
                rs.next();
                adresseId = rs.getLong(1);
            }
        }

        String auftragSql = "INSERT INTO HA2_KUNDENAUFTRAG " +
                "(AUFTRAG_NR, KUNDE_NR, DATUM, TEXTFELD, " +
                " BAUSTELLE_ADRESSE_ID, BEARBEITUNG_ABGESCHLOSSEN, ABGERECHNET) " +
                "VALUES (HA2_AUFTRAGSNR_SEQ.NEXTVAL, ?, SYSDATE, ?, ?, 0, 0)";

        try (PreparedStatement ps = con.prepareStatement(auftragSql)) {
            ps.setLong(1, kundenNr);
            ps.setString(2, "Rohr verlegen");
            ps.setLong(3, adresseId);
            ps.executeUpdate();
        } finally {
            con.close();
        }

        boolean geloescht = kundenVw.kundenLoeschen(geladenerKunde);

        assertFalse(geloescht,
                "Kunde darf nicht gelöscht werden, da ein Auftrag existiert.");
    }

    @Test
    void kundeOhneAuftraegeKannGeloeschtWerden() {

        IKundenverwaltung kundenVw = new KundenverwaltungFactory().getKundenverwaltung();

   
        KundeTO kunde = new KundeTO(
                null,
                "Mustermann",
                "Janna",
                "Musterstr.",
                4,
                "1234",
                "Musterstadt");
        kundenVw.kundeAnlegen(kunde);

        KundeTO geladenerKunde = kundenVw.ladeAlleKunde().stream()
                .filter(k -> "Mustermann".equals(k.getName())
                        && "Janna".equals(k.getVorname()))
                .findFirst()
                .orElseThrow();

        boolean geloescht = kundenVw.kundenLoeschen(geladenerKunde);

        assertTrue(
                geloescht,
                "Kunde ohne Aufträge sollte gelöscht werden können");
    }

}