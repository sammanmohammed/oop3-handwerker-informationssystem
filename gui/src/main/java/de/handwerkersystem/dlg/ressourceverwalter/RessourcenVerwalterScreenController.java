package de.handwerkersystem.dlg.ressourceverwalter;


import de.handwerkersystem.dlg.hauptmenue.ControlledScreen;
import de.handwerkersystem.dlg.hauptmenue.Hauptmenue;
import de.handwerkersystem.dlg.hauptmenue.ScreensController;
import javafx.fxml.FXML;

public class RessourcenVerwalterScreenController implements ControlledScreen {

    private ScreensController myController;

    public RessourcenVerwalterScreenController() {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;

    }

    @Override
    public void initData() {

    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.MAIN_SCREEN);
    }

    @FXML
    private void bt_ressourceanlegen() {
        myController.setScreen(Hauptmenue.RESSOURCEANLEGEN_SCREEN);
    }

    @FXML
    private void bt_ressourceloeschen() {
        myController.setScreen(Hauptmenue.RESSOURCELOESCHEN_SCREEN);
    }
  @FXML
    private void bt_ressourceaendern() {
        myController.setScreen(Hauptmenue.RESSOURCEAENDERN_SCREEN);
    }
}
