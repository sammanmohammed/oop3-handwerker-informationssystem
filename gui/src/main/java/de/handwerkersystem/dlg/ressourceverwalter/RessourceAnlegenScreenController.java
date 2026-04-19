package de.handwerkersystem.dlg.ressourceverwalter;

import java.net.URL;
import java.util.ResourceBundle;

import de.handwerkersystem.awk.entity.RessourceTO;
import de.handwerkersystem.awk.entity.internal.RessourcenArt;
import de.handwerkersystem.dlg.hauptmenue.ControlledScreen;
import de.handwerkersystem.dlg.hauptmenue.Hauptmenue;
import de.handwerkersystem.dlg.hauptmenue.HauptmenueService;
import de.handwerkersystem.dlg.hauptmenue.ScreensController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class RessourceAnlegenScreenController implements ControlledScreen, Initializable {

    private ScreensController myController;

    @FXML
    private ComboBox<String> cb_art;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_kostensatz;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cb_art.getItems().setAll(RessourcenArt.alle());
    }

    @FXML
    private void bt_speichernClicked() {
        if (cb_art.getValue() == null || tf_name.getText().isEmpty() || tf_kostensatz.getText().isEmpty()) {

            Alert a = new Alert(Alert.AlertType.WARNING,
                    "Bitte alle Felder ausfüllen!");
            a.show();
            return;
        }

        RessourceTO r = new RessourceTO();
        r.setArt(cb_art.getValue());
        r.setName(tf_name.getText());
        r.setStandrdKostensatz(Double.parseDouble(tf_kostensatz.getText()));

       HauptmenueService.getRessourcenVw().ressourceAnlegen(r);

        Alert a = new Alert(Alert.AlertType.INFORMATION, "Ressource gespeichert!");
        a.show();
        initData();

    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.RESSOURCEN_VERWALTER_SCREEN);
    }

    @Override
    public void initData() {
        cb_art.getSelectionModel().clearSelection();
        tf_name.clear();
        tf_kostensatz.clear();
    }

}
