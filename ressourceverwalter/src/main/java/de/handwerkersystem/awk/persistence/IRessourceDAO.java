package de.handwerkersystem.awk.persistence;

import java.util.List;

import de.handwerkersystem.awk.DatenhaltungsException;
import de.handwerkersystem.awk.entity.internal.Ressource;


public interface IRessourceDAO {

    Ressource save(Ressource ressource);

    Ressource update(Ressource ressource);

    void deleteById(Long id);

    Ressource findById(Long id);

    List<Ressource> findAll();

    boolean existsById(Long id);
     double findKostensatzById(long ressourceId) throws DatenhaltungsException;

  




}
