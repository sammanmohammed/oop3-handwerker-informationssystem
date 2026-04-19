package de.handwerkersystem.awk.usecase.impl;

import java.util.Collection;

import de.handwerkersystem.awk.entity.RessourceTO;
import de.handwerkersystem.awk.entity.internal.Ressource;
import de.handwerkersystem.awk.persistence.IRessourceDAO;
import de.handwerkersystem.awk.persistence.impl.RessourceDAO;
import de.handwerkersystem.awk.usecase.IRessourcenVw;

public class RessorcenVerwalter implements IRessourcenVw {

    public RessorcenVerwalter() {
    }

    private IRessourceDAO dao = new RessourceDAO();

    @Override
    public void ressourceAnlegen(RessourceTO ressource) {
        dao.save(ressource.toEntity());
    }

    @Override
    public void ressourceAendern(RessourceTO ressource) {
        dao.update(ressource.toEntity());
    }

    @Override
    public boolean ressourceLoeschen(RessourceTO ressource) {
        if (ressource == null || ressource.getId() == null) {
            return false;
        }
        Long ressourceId = ressource.getId();
        if (!dao.existsById(ressourceId)) {
            return false;
        }
        try {
            dao.deleteById(ressourceId);
            return true;

        } catch (Exception e) {
            e.getMessage();

        }
        return false;

    }

    @Override
    public Collection<RessourceTO> ladeAlleRessources() {
        return dao.findAll().stream().map(Ressource::toTO).toList();
    }

}
