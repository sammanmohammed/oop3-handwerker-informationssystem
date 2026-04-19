package de.handwerkersystem.dlg.kundenverwalter;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import de.handwerkersystem.awk.kundenverwaltung.entity.KundeTO;
import de.handwerkersystem.dlg.hauptmenue.ControlledScreen;
import de.handwerkersystem.dlg.hauptmenue.Hauptmenue;
import de.handwerkersystem.dlg.hauptmenue.HauptmenueService;
import de.handwerkersystem.dlg.hauptmenue.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KundenLoeschenScreenController implements Initializable, ControlledScreen {

    private ScreensController myController;



    @FXML
    private TableView<KundeTO> tv_kunden;
    @FXML
    private TableColumn<KundeTO, Integer> tc_kundenNr;
    @FXML
    private TableColumn<KundeTO, String> tc_vorname;
    @FXML
    private TableColumn<KundeTO, String> tc_nachname;

    private final ObservableList<KundeTO> kundenListe = FXCollections.observableArrayList();

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tc_kundenNr.setCellValueFactory(
                new PropertyValueFactory<KundeTO, Integer>("kunde_nr"));
        tc_vorname.setCellValueFactory(
                new PropertyValueFactory<KundeTO, String>("vorname"));
        tc_nachname.setCellValueFactory(
                new PropertyValueFactory<KundeTO, String>("name"));

        tv_kunden.setItems(kundenListe);
    }

    @Override
    public void initData() {
        kundenListe.clear();
        Collection<KundeTO> data = HauptmenueService.gKundenverwaltung().ladeAlleKunde();
        kundenListe.setAll(data.stream().toList());
    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.KUNDEN_VERWALTER_SCREEN);
    }

    @FXML
    private void bt_kundenloeschenClicked() {
        KundeTO ausgewaehlterKunde = tv_kunden.getSelectionModel().getSelectedItem();

        if (ausgewaehlterKunde == null) {
             Alert a = new Alert(Alert.AlertType.WARNING,
                    "Kunden ist nicht ausgewählt!");
            a.show();
            return;
        }

        boolean geloescht = HauptmenueService.gKundenverwaltung().kundenLoeschen(ausgewaehlterKunde);

        if (geloescht) {
            kundenListe.remove(ausgewaehlterKunde);
              Alert a = new Alert(Alert.AlertType.WARNING,
                    "Kunde gelöscht: " + ausgewaehlterKunde.getName());
          a.show();
          
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING,
                    "Dieser Kunde befindet sich in einem Auftrag!!.");
            a.show();
         
        }
    }
}
