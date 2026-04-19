package de.handwerkersystem.dlg.kundenverwalter;

import de.handwerkersystem.awk.kundenverwaltung.entity.KundeTO;
import de.handwerkersystem.dlg.hauptmenue.ControlledScreen;
import de.handwerkersystem.dlg.hauptmenue.Hauptmenue;
import de.handwerkersystem.dlg.hauptmenue.HauptmenueService;
import de.handwerkersystem.dlg.hauptmenue.ScreensController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class KundenAnlegenScreenController implements ControlledScreen {

    private ScreensController myController;

    @FXML
    private TextField tf_vorname;
    @FXML
    private TextField tf_nachname;
    @FXML
    private TextField tf_strasse;
    @FXML
    private TextField tf_hausNr;
    @FXML
    private TextField tf_plz;
    @FXML
    private TextField tf_ort;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initData() {
        tf_vorname.clear();
        tf_nachname.clear();
        tf_strasse.clear();
        tf_plz.clear();
        tf_ort.clear();
        tf_hausNr.clear();
    }

    @FXML
    private void bt_speichernClicked() {

        if (tf_vorname.getText().isEmpty() ||
                tf_nachname.getText().isEmpty() ||
                tf_strasse.getText().isEmpty() ||
                tf_hausNr.getText().isEmpty() ||
                tf_plz.getText().isEmpty() ||
                tf_ort.getText().isEmpty()) {

            Alert a = new Alert(Alert.AlertType.WARNING,
                    "Bitte alle Felder ausfüllen!");
            a.show();
            return;
        }

        int hausNr;
        try {
            hausNr = Integer.parseInt(tf_hausNr.getText());
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.WARNING,
                    "Die Hausnummer muss eine gültige Zahl sein!");
            a.show();
            return;
        }

        boolean existiert = HauptmenueService.gKundenverwaltung()
                .kundeExistiert(
                        tf_vorname.getText(),
                        tf_nachname.getText(),
                        tf_plz.getText());

        if (existiert) {
            Alert a = new Alert(Alert.AlertType.WARNING,
                    "Dieser Kunde existiert bereits in der Datenbank!");
            a.show();
            return;
        }

        KundeTO neuerKunde = new KundeTO(
                null,
                tf_nachname.getText(),
                tf_vorname.getText(),
                tf_strasse.getText(),
                hausNr,
                tf_plz.getText(),
                tf_ort.getText());
        HauptmenueService.gKundenverwaltung().kundeAnlegen(neuerKunde);

        initData();

        Alert a = new Alert(Alert.AlertType.INFORMATION,
                "Kunde erfolgreich angelegt!");
        a.show();
    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.KUNDEN_VERWALTER_SCREEN);
    }
}
