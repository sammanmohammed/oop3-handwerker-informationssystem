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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KundenListeAnzeigenScreenController implements Initializable, ControlledScreen {

        private ScreensController myController;

        @FXML
        private TableView<KundeTO> tv_kunden;
        @FXML
        private TableColumn<KundeTO, Integer> tc_kundenNr;
        @FXML
        private TableColumn<KundeTO, String> tc_vorname;
        @FXML
        private TableColumn<KundeTO, String> tc_nachname;
        @FXML
        private TableColumn<KundeTO, String> tc_strasse;
        @FXML
        private TableColumn<KundeTO, Integer> tc_hausNr;
        @FXML
        private TableColumn<KundeTO, String> tc_plz;
        @FXML
        private TableColumn<KundeTO, String> tc_ort;

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
                tc_strasse.setCellValueFactory(
                                new PropertyValueFactory<KundeTO, String>("strasse"));
                tc_hausNr.setCellValueFactory(
                                new PropertyValueFactory<KundeTO, Integer>("hausNr"));
                tc_plz.setCellValueFactory(
                                new PropertyValueFactory<KundeTO, String>("plz"));
                tc_ort.setCellValueFactory(
                                new PropertyValueFactory<KundeTO, String>("ort"));

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
        private void bt_kundenlisteClicked() {
                HauptmenueService.gKundenverwaltung().ladeAlleKunde();

        }

}
