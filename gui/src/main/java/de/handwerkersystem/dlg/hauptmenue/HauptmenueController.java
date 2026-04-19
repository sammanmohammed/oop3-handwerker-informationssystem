package de.handwerkersystem.dlg.hauptmenue;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class HauptmenueController implements Initializable, ControlledScreen {
    private ScreensController myController;

    public HauptmenueController() {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    public void bt_kundenVerwaltenClicked() {
        myController.setScreen(Hauptmenue.KUNDEN_VERWALTER_SCREEN);

    }

    @FXML
    public void bt_ressourcenVerwaltenClicked() {
        myController.setScreen(Hauptmenue.RESSOURCEN_VERWALTER_SCREEN);

    }

    @FXML
    public void bt_kundenauftraegeClicked() {
        myController.setScreen(Hauptmenue.KUNDENAUFTRAG_SCREEN);

    }

    @FXML
    private void bt_beendenClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Anwendung beenden");
        alert.setHeaderText(null);
        alert.setContentText("Möchten Sie die Anwendung wirklich beenden?");

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                Platform.exit();
            }
        });
    }

}
