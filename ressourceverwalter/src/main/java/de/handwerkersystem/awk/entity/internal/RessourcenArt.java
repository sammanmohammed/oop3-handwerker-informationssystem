package de.handwerkersystem.awk.entity.internal;

import java.util.List;

public final class RessourcenArt {
    public static final String MASCHINE = "MASCHINE";
    public static final String GESELLE  = "GESELLE";
    public static final String MEISTER  = "MEISTER";
    
  
    //hier können weiterer Ressourcen eingefügt werden, die müssen auch in der Liste eingefügt werden!
    // Die Methode(bt_aenderungspeichernClicked) im RessourcenAendernScreenController muss anpasst werden.


    public static List<String> alle() {
        return List.of(MASCHINE, GESELLE, MEISTER);
} 
   private RessourcenArt() {}
}
    
    

