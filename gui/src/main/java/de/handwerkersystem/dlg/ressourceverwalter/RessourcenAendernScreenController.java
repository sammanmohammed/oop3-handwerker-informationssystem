package de.handwerkersystem.dlg.ressourceverwalter;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import de.handwerkersystem.awk.entity.RessourceTO;
import de.handwerkersystem.awk.entity.internal.RessourcenArt;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RessourcenAendernScreenController implements Initializable, ControlledScreen {
        private ScreensController myController;

        @FXML
        private TableView<RessourceTO> tv_ressource;
        @FXML
        private TableColumn<RessourceTO, Integer> tc_id;
        @FXML
        private TableColumn<RessourceTO, String> tc_art;
        @FXML
        private TableColumn<RessourceTO, String> tc_name;
        @FXML
        private TableColumn<RessourceTO, Double> tc_standrdkostensatz;
        @FXML
        private TextField tf_art;
        @FXML
        private TextField tf_name;
        @FXML
        private TextField tf_standrdkostensatz;

        private final ObservableList<RessourceTO> ressourcenListe = FXCollections.observableArrayList();
        private RessourceTO ausgewalteRessource;

        @Override
        public void initialize(URL location, ResourceBundle resources) {

                tc_id.setCellValueFactory(
                                new PropertyValueFactory<RessourceTO, Integer>("id"));
                tc_art.setCellValueFactory(
                                new PropertyValueFactory<RessourceTO, String>("art"));
                tc_name.setCellValueFactory(
                                new PropertyValueFactory<RessourceTO, String>("name"));
                tc_standrdkostensatz.setCellValueFactory(
                                new PropertyValueFactory<RessourceTO, Double>("StandrdKostensatz"));

                tv_ressource.setItems(ressourcenListe);
                tv_ressource.getSelectionModel().selectedItemProperty().addListener(
                                (obs, oldRessource, newRessource) -> {
                                        if (newRessource != null) {
                                                ausgewalteRessource = newRessource;
                                                tf_art.setText(newRessource.getArt());
                                                tf_name.setText(newRessource.getName());
                                                tf_standrdkostensatz.setText(
                                                                String.valueOf(newRessource.getStandrdKostensatz()));

                                        }

                                }

                );
        }

        @Override
        public void setScreenParent(ScreensController screenPage) {
                this.myController = screenPage;
        }

        @Override
        public void initData() {
                ressourcenListe.clear();
                Collection<RessourceTO> data = HauptmenueService.getRessourcenVw().ladeAlleRessources();
                ressourcenListe.setAll(data.stream().toList());
                tf_art.clear();
                tf_name.clear();
                tf_standrdkostensatz.clear();

        }

        @FXML
        private void bt_aenderungspeichernClicked() {
                if (ausgewalteRessource == null) {
                        new Alert(Alert.AlertType.WARNING, "Bitte zuerst eine Ressource in der Tabelle auswählen!")
                                        .show();
                        return;
                }

                if (tf_art.getText().isEmpty() ||
                                tf_name.getText().isEmpty() || tf_standrdkostensatz.getText().isEmpty()) {
                        new Alert(Alert.AlertType.WARNING, "Bitte alle Felder ausfüllen!").show();
                        return;
                }
                String art = tf_art.getText();

                if (!art.equalsIgnoreCase(RessourcenArt.GESELLE) &&
                                !art.equalsIgnoreCase(RessourcenArt.MEISTER) &&
                                !art.equalsIgnoreCase(RessourcenArt.MASCHINE)) {

                        new Alert(Alert.AlertType.WARNING, "Bitte eine gültige Ressourcenkategorie eingeben!").show();
                        return;
                }

                Double standrdKostensatz;
                try {
                        standrdKostensatz = Double.parseDouble(tf_standrdkostensatz.getText());
                } catch (NumberFormatException e) {
                        new Alert(Alert.AlertType.WARNING,
                                        "Die Hausnummer muss eine gültige Zahl sein!").show();
                        return;
                }
                ausgewalteRessource.setArt(tf_art.getText());
                ausgewalteRessource.setName(tf_name.getText());
                ausgewalteRessource.setStandrdKostensatz(standrdKostensatz);
                HauptmenueService.getRessourcenVw().ressourceAendern(ausgewalteRessource);
                tv_ressource.refresh();
                new Alert(Alert.AlertType.INFORMATION,
                                "Ressourcendaten wurden erfolgreich geändert!").show();
                initData();

        }

        @FXML
        private void bt_zurueckClicked() {
                myController.setScreen(Hauptmenue.RESSOURCEN_VERWALTER_SCREEN);
        }
}
