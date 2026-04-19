package de.handwerkersystem.awk.factory.impl;

import de.handwerkersystem.awk.factory.IRessourcenVerwaltungFactory;
import de.handwerkersystem.awk.usecase.IRessourcenVw;
import de.handwerkersystem.awk.usecase.impl.RessorcenVerwalter;

public class RessourceverwaltungFactory implements IRessourcenVerwaltungFactory{

    @Override
    public IRessourcenVw getRessourcenVw() {
       return new RessorcenVerwalter();
    }

}
