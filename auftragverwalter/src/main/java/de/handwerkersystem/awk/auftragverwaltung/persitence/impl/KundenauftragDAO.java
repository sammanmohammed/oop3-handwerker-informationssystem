package de.handwerkersystem.awk.auftragverwaltung.persitence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.Adresse;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.Kundenauftrag;
import de.handwerkersystem.awk.auftragverwaltung.persitence.IKundenauftragDAO;
import de.handwerkersystem.awk.auftragverwaltung.persitence.Persistence;

public class KundenauftragDAO implements IKundenauftragDAO {

    @Override
    public Kundenauftrag save(Kundenauftrag auftrag) throws DatenhaltungsException {
        Connection con = Persistence.getConnection();

        try {
            con.setAutoCommit(false);

            long adresseId;
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT HA2_ADRESSE_ID_SEQ.NEXTVAL AS ID FROM DUAL");
                    ResultSet rs = ps.executeQuery()) {
                rs.next();
                adresseId = rs.getLong("ID");
            }

            Adresse adr = auftrag.getBaustellenAdresse();
            if (adr == null)
                throw new SQLException("Baustellenadresse ist null");

            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO HA2_ADRESSE (ADRESSE_ID, STRASSE, HAUSNR, PLZ, ORT) VALUES (?, ?, ?, ?, ?)")) {
                ps.setLong(1, adresseId);
                ps.setString(2, adr.getStrasse());
                ps.setString(3, adr.getHausnr());
                ps.setString(4, adr.getPlz());
                ps.setString(5, adr.getOrt());
                ps.executeUpdate();
            }
            adr.setAdresseId((int) adresseId);

            long auftragNr = auftrag.getAuftragNr();
            if (auftragNr <= 0) {
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT HA2_AUFTRAGSNR_SEQ.NEXTVAL AS NR FROM DUAL");
                        ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    auftragNr = rs.getLong("NR");
                }
                auftrag.setAuftragNr(auftragNr);
            }

            if (auftrag.getDatum() == null)
                throw new SQLException("Datum ist null");

            int bearb = auftrag.isBearbeitung_Abgeschlossen() ? 1 : 0;
            int abrech = auftrag.isAbgerechnet() ? 1 : 0;

            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO HA2_KUNDENAUFTRAG " +
                            "(AUFTRAG_NR, DATUM, TEXTFELD, KUNDE_NR, BAUSTELLE_ADRESSE_ID, BEARBEITUNG_ABGESCHLOSSEN, ABGERECHNET) "
                            +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                ps.setLong(1, auftragNr);
                ps.setDate(2, new java.sql.Date(auftrag.getDatum().getTime()));
                if (auftrag.getTextfeld() == null) {
                    ps.setNull(3, java.sql.Types.VARCHAR);
                } else {
                    ps.setString(3, auftrag.getTextfeld());
                }
                ps.setLong(4, auftrag.getKunde_nr());
                ps.setLong(5, adresseId);
                ps.setInt(6, bearb);
                ps.setInt(7, abrech);

                ps.executeUpdate();
            }

            con.commit();
            return auftrag;

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ignore) {
            }
            e.printStackTrace();
            DatenhaltungsException ex = new DatenhaltungsException();
            ex.initCause(e);
            throw ex;

        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
            Persistence.closeConnection(con);
        }
    }

    @Override
    public Kundenauftrag update(Kundenauftrag auftrag) throws DatenhaltungsException {
        Connection con = Persistence.getConnection();

        try {
            con.setAutoCommit(false);

            if (auftrag == null)
                throw new SQLException("auftrag ist null");
            if (auftrag.getAuftragNr() <= 0)
                throw new SQLException("auftragNr ist nicht gesetzt");

            long adresseId;

       
            Adresse adr = auftrag.getBaustellenAdresse();
            if (adr != null && adr.getAdresseId() > 0) {
                adresseId = adr.getAdresseId();
            } else {
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT BAUSTELLE_ADRESSE_ID FROM HA2_KUNDENAUFTRAG WHERE AUFTRAG_NR = ?")) {
                    ps.setLong(1, auftrag.getAuftragNr());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next())
                            throw new SQLException("Auftrag nicht gefunden: " + auftrag.getAuftragNr());
                        adresseId = rs.getLong("BAUSTELLE_ADRESSE_ID");
                    }
                }
                if (adr != null)
                    adr.setAdresseId((int) adresseId);
            }

            if (adr != null) {
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE HA2_ADRESSE SET STRASSE = ?, HAUSNR = ?, PLZ = ?, ORT = ? WHERE ADRESSE_ID = ?")) {
                    ps.setString(1, adr.getStrasse());
                    ps.setString(2, adr.getHausnr());
                    ps.setString(3, adr.getPlz());
                    ps.setString(4, adr.getOrt());
                    ps.setLong(5, adresseId);
                    ps.executeUpdate();
                }
            }

            if (auftrag.getDatum() == null)
                throw new SQLException("Datum ist null");

            int bearb = auftrag.isBearbeitung_Abgeschlossen() ? 1 : 0;
            int abrech = auftrag.isAbgerechnet() ? 1 : 0;

            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE HA2_KUNDENAUFTRAG SET " +
                            "DATUM = ?, TEXTFELD = ?, KUNDE_NR = ?, BAUSTELLE_ADRESSE_ID = ?, " +
                            "BEARBEITUNG_ABGESCHLOSSEN = ?, ABGERECHNET = ? " +
                            "WHERE AUFTRAG_NR = ?")) {

                ps.setDate(1, new java.sql.Date(auftrag.getDatum().getTime()));
                if (auftrag.getTextfeld() == null) {
                    ps.setNull(2, java.sql.Types.VARCHAR);
                } else {
                    ps.setString(2, auftrag.getTextfeld());
                }
                ps.setLong(3, auftrag.getKunde_nr());
                ps.setLong(4, adresseId);
                ps.setInt(5, bearb);
                ps.setInt(6, abrech);
                ps.setLong(7, auftrag.getAuftragNr());

                ps.executeUpdate();
            }

            con.commit();
            return auftrag;

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ignore) {
            }
            e.printStackTrace();
            DatenhaltungsException ex = new DatenhaltungsException();
            ex.initCause(e);
            throw ex;

        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
            Persistence.closeConnection(con);
        }
    }

    @Override
    public List<Kundenauftrag> findAll() throws DatenhaltungsException {
        List<Kundenauftrag> liste = new ArrayList<>();
        Connection con = Persistence.getConnection();

        String sql = "SELECT ka.AUFTRAG_NR, ka.DATUM, ka.TEXTFELD, ka.KUNDE_NR, " +
                "       ka.BEARBEITUNG_ABGESCHLOSSEN, ka.ABGERECHNET, " +
                "       a.ADRESSE_ID, a.STRASSE, a.HAUSNR, a.PLZ, a.ORT " +
                "FROM HA2_KUNDENAUFTRAG ka " +
                "JOIN HA2_ADRESSE a ON ka.BAUSTELLE_ADRESSE_ID = a.ADRESSE_ID " +
                "ORDER BY ka.AUFTRAG_NR";

        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long auftragNr = rs.getLong("AUFTRAG_NR");
                java.util.Date datum = rs.getDate("DATUM");
                String textfeld = rs.getString("TEXTFELD");
                long kundeNr = rs.getLong("KUNDE_NR");

                boolean bearb = rs.getInt("BEARBEITUNG_ABGESCHLOSSEN") == 1;
                boolean abgerechnet = rs.getInt("ABGERECHNET") == 1;

                Adresse adr = new Adresse();
                adr.setAdresseId(rs.getInt("ADRESSE_ID"));
                adr.setStrasse(rs.getString("STRASSE"));
                adr.setHausnr(rs.getString("HAUSNR"));
                adr.setPlz(rs.getString("PLZ"));
                adr.setOrt(rs.getString("ORT"));

                liste.add(new Kundenauftrag(auftragNr, datum, textfeld, kundeNr, bearb, abgerechnet, adr));
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
    public boolean existsById(long auftrag_nr) throws DatenhaltungsException {
        Connection con = Persistence.getConnection();

        String sql = "SELECT COUNT(*) AS ANZ FROM HA2_KUNDENAUFTRAG WHERE AUFTRAG_NR = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, auftrag_nr);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("ANZ") > 0;
            }
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
    public void deleteById(long id) throws DatenhaltungsException {
        Connection con = Persistence.getConnection();

        try {
            con.setAutoCommit(false);

            long adresseId;
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT BAUSTELLE_ADRESSE_ID FROM HA2_KUNDENAUFTRAG WHERE AUFTRAG_NR = ?")) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        con.rollback();
                        return;
                    }
                    adresseId = rs.getLong("BAUSTELLE_ADRESSE_ID");
                }
            }

         
            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM HA2_AUFTRAGSRESSOURCEZUORDNUNG WHERE AUFTRAG_NR = ?")) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM HA2_KUNDENAUFTRAG WHERE AUFTRAG_NR = ?")) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }

            int anz;
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT COUNT(*) AS ANZ FROM HA2_KUNDENAUFTRAG WHERE BAUSTELLE_ADRESSE_ID = ?")) {
                ps.setLong(1, adresseId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    anz = rs.getInt("ANZ");
                }
            }

            if (anz == 0) {
                try (PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM HA2_ADRESSE WHERE ADRESSE_ID = ?")) {
                    ps.setLong(1, adresseId);
                    ps.executeUpdate();
                }
            }

            con.commit();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ignore) {
            }
            e.printStackTrace();
            DatenhaltungsException ex = new DatenhaltungsException();
            ex.initCause(e);
            throw ex;

        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
            Persistence.closeConnection(con);
        }
    }
}
