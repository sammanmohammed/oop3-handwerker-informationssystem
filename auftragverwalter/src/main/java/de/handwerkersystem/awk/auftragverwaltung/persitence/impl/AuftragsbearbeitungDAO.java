package de.handwerkersystem.awk.auftragverwaltung.persitence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.AuftragsRessourceZuordung;
import de.handwerkersystem.awk.auftragverwaltung.entity.internal.Kundenauftrag;
import de.handwerkersystem.awk.auftragverwaltung.persitence.IAuftragsbearbeitungDAO;
import de.handwerkersystem.awk.auftragverwaltung.persitence.Persistence;

public class AuftragsbearbeitungDAO implements IAuftragsbearbeitungDAO {

    @Override
    public void ressourceZuordnen(AuftragsRessourceZuordung zuordnung) throws DatenhaltungsException {
        Connection con = Persistence.getConnection();

        String sql = "INSERT INTO HA2_AUFTRAGSRESSOURCEZUORDNUNG " +
                "(ID, AUFTRAG_NR, RESSOURCE_ID, STUNDEN, INDIVIDUELLER_STUNDENSATZ) " +
                "VALUES (HA2_ARZ_SEQ.NEXTVAL, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, zuordnung.getAuftrag_nr());
            ps.setLong(2, zuordnung.getRessource_id());
            ps.setDouble(3, zuordnung.getStunden());
            ps.setDouble(4, zuordnung.getIndividuellerStundensatz());
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

    @Override
    public void zuordnungAendern(AuftragsRessourceZuordung zuordnung) throws DatenhaltungsException {
        Connection con = Persistence.getConnection();

        String sql = "UPDATE HA2_AUFTRAGSRESSOURCEZUORDNUNG SET " +
                "STUNDEN = ?, " +
                "INDIVIDUELLER_STUNDENSATZ = ? " +
                "WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, zuordnung.getStunden());
            ps.setDouble(2, zuordnung.getIndividuellerStundensatz());
            ps.setLong(3, zuordnung.getId());
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

    @Override
    public boolean zuordnungLoeschen(AuftragsRessourceZuordung zuordnung) throws DatenhaltungsException {
        Connection con = Persistence.getConnection();

        String sql = "DELETE FROM HA2_AUFTRAGSRESSOURCEZUORDNUNG WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, zuordnung.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
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
    public boolean auftragAbschliessen(Kundenauftrag auftrag) throws DatenhaltungsException {
        Connection con = Persistence.getConnection();

        String sql = "UPDATE HA2_KUNDENAUFTRAG " +
                "SET BEARBEITUNG_ABGESCHLOSSEN = 1 " +
                "WHERE AUFTRAG_NR = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, auftrag.getAuftragNr());
            int rows = ps.executeUpdate();
            return rows > 0;
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
