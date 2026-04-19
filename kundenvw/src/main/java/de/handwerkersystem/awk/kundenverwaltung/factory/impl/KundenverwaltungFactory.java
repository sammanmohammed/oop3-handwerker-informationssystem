package de.handwerkersystem.awk.kundenverwaltung.factory.impl;

import de.handwerkersystem.awk.kundenverwaltung.factory.IKundenverwaltungFactory;
import de.handwerkersystem.awk.kundenverwaltung.usecase.IKundenverwaltung;
import de.handwerkersystem.awk.kundenverwaltung.usecase.impl.Kundenverwaltung;

public class KundenverwaltungFactory implements IKundenverwaltungFactory  {

    @Override
    public IKundenverwaltung getKundenverwaltung(){
        return new Kundenverwaltung();
    }


  
}
