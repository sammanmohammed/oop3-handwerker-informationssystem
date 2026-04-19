package de.handwerkersystem.awk.usecase;

import java.util.Collection;

import de.handwerkersystem.awk.entity.RessourceTO;




public interface IRessourcenVw {
public void ressourceAnlegen(RessourceTO ressource);
public void ressourceAendern(RessourceTO ressource);
public boolean ressourceLoeschen(RessourceTO ressourceID);
 Collection<RessourceTO> ladeAlleRessources();
}
