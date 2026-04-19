package de.handwerkersystem.awk.auftragverwaltung.persitence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.auftragverwaltung.entity.AdresseTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.AuftragsRessourceZuordungTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;
import de.handwerkersystem.awk.auftragverwaltung.persitence.IAbrechnungDAO;
import de.handwerkersystem.awk.auftragverwaltung.persitence.Persistence;
import de.handwerkersystem.awk.persistence.IRessourceDAO;
import de.handwerkersystem.awk.persistence.impl.RessourceDAO;

public class AbrechnungDAO implements IAbrechnungDAO {
    @Override
    public Collection<KundenauftragTO> ladeAbzurechnendeAuftraege()
            throws DatenhaltungsException {

        Collection<KundenauftragTO> liste = new ArrayList<>();
        Connection con = Persistence.getConnection();

        String sql = "SELECT a.AUFTRAG_NR, a.DATUM, a.TEXTFELD, a.KUNDE_NR, " +
                "       a.BEARBEITUNG_ABGESCHLOSSEN, a.ABGERECHNET, " +
                "       adr.ADRESSE_ID, adr.STRASSE, adr.HAUSNR, adr.PLZ, adr.ORT " +
                "FROM HA2_KUNDENAUFTRAG a, HA2_ADRESSE adr " +
                "WHERE a.BAUSTELLE_ADRESSE_ID = adr.ADRESSE_ID(+) " +
                "AND a.ABGERECHNET = 0";

        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                long nr = rs.getLong("AUFTRAG_NR");
                java.util.Date datum = rs.getDate("DATUM");
                String text = rs.getString("TEXTFELD");
                long kunde = rs.getLong("KUNDE_NR");
                boolean bearb = rs.getInt("BEARBEITUNG_ABGESCHLOSSEN") == 1;
                boolean abgr = rs.getInt("ABGERECHNET") == 1;

                AdresseTO adrTO = null;
                try {
                    if (rs.getObject("ADRESSE_ID") != null) {
                        adrTO = new AdresseTO(
                                rs.getInt("ADRESSE_ID"),
                                rs.getString("STRASSE"),
                                rs.getString("HAUSNR"),
                                rs.getString("PLZ"),
                                rs.getString("ORT"));
                    }
                } catch (SQLException e) {
                 
                    adrTO = null;
                }

                liste.add(new KundenauftragTO(
                        nr,
                        datum,
                        text,
                        kunde,
                        bearb,
                        abgr,
                        adrTO));
            }

            return liste;

        } catch (SQLException e) {
            e.printStackTrace();
            DatenhaltungsException ex = new DatenhaltungsException();
            ex.initCause(e);
            throw ex;

        } finally {
            Persistence.closeConnection(con);
        }
    }

    @Override
    public Collection<AuftragsRessourceZuordungTO> ladeRechnungspositionen(long auftragNr)
            throws DatenhaltungsException {

        Collection<AuftragsRessourceZuordungTO> positionen = new ArrayList<>();
        Connection con = Persistence.getConnection();

        String sql = "SELECT ID, AUFTRAG_NR, RESSOURCE_ID, STUNDEN, INDIVIDUELLER_STUNDENSATZ " +
                "FROM HA2_AUFTRAGSRESSOURCEZUORDNUNG " +
                "WHERE AUFTRAG_NR = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, auftragNr);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AuftragsRessourceZuordungTO pos = new AuftragsRessourceZuordungTO();
                    pos.setId(rs.getLong("ID"));
                    pos.setAuftrag_nr(rs.getLong("AUFTRAG_NR"));
                    pos.setRessource_id(rs.getLong("RESSOURCE_ID"));
                    pos.setStunden(rs.getDouble("STUNDEN"));
                    pos.setIndividuellerStundensatz(rs.getDouble("INDIVIDUELLER_STUNDENSATZ"));
                    positionen.add(pos);
                }
            }

            return positionen;

        } catch (SQLException e) {
            e.printStackTrace();
            DatenhaltungsException ex = new DatenhaltungsException();
            ex.initCause(e);
            throw ex;

        } finally {
            Persistence.closeConnection(con);
        }
    }

    @Override
    public double berechneAuftragssumme(long auftragNr) throws DatenhaltungsException {
        Collection<AuftragsRessourceZuordungTO> positionen = ladeRechnungspositionen(auftragNr);
        double summe = 0.0;

        for (AuftragsRessourceZuordungTO pos : positionen) {
            double stunden = pos.getStunden();
            double sonder = pos.getIndividuellerStundensatz();

            double stundensatz;
            if (sonder > 0) {
                stundensatz = sonder;
            } else {
                IRessourceDAO resDao = new RessourceDAO();
                double standard = resDao.findKostensatzById(pos.getRessource_id());
                stundensatz = standard;
            }

            summe += stunden * stundensatz;
        }

        return summe;
    }

    @Override
    public void auftragAbgerechnetSetzen(long auftragNr) throws DatenhaltungsException {
        Connection con = Persistence.getConnection();

        String sql = "UPDATE HA2_KUNDENAUFTRAG SET ABGERECHNET = 1 WHERE AUFTRAG_NR = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, auftragNr);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            DatenhaltungsException ex = new DatenhaltungsException();
            ex.initCause(e);
            throw ex;
        } finally {
            Persistence.closeConnection(con);
        }
    }
}
