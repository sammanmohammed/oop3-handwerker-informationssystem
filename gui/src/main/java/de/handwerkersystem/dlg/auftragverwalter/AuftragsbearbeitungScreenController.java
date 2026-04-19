package de.handwerkersystem.dlg.auftragverwalter;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import de.handwerkersystem.awk.auftragverwaltung.entity.AuftragsRessourceZuordungTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;
import de.handwerkersystem.awk.entity.RessourceTO;
import de.handwerkersystem.awk.kundenverwaltung.entity.KundeTO;
import de.handwerkersystem.dlg.hauptmenue.ControlledScreen;
import de.handwerkersystem.dlg.hauptmenue.Hauptmenue;
import de.handwerkersystem.dlg.hauptmenue.HauptmenueService;
import de.handwerkersystem.dlg.hauptmenue.ScreensController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuftragsbearbeitungScreenController implements Initializable, ControlledScreen {

    private ScreensController myController;

    @FXML
    private TableView<KundenauftragTO> tv_auftraege;
    @FXML
    private TableColumn<KundenauftragTO, Integer> tc_auftragsNr;
    @FXML
    private TableColumn<KundenauftragTO, String> tc_kunde;
    @FXML
    private TableColumn<KundenauftragTO, java.util.Date> tc_datum;

    @FXML
    private TableView<AuftragsRessourceZuordungTO> tv_zuordnungen;
    @FXML
    private TableColumn<AuftragsRessourceZuordungTO, String> tc_ressource;
    @FXML
    private TableColumn<AuftragsRessourceZuordungTO, Double> tc_stunden;
    @FXML
    private TableColumn<AuftragsRessourceZuordungTO, Double> tc_satz;

    @FXML
    private ComboBox<RessourceTO> cb_ressource;
    @FXML
    private TextField tf_stunden;
    @FXML
    private TextField tf_standardSatz;
    @FXML
    private TextField tf_stundensatz;
    @FXML
    private CheckBox cb_abgeschlossen;
    private final Map<Long, String> kundeNameById = new HashMap<>();

    private final ObservableList<KundenauftragTO> auftragsListe = FXCollections.observableArrayList();
    private final ObservableList<AuftragsRessourceZuordungTO> zuordnungsListe = FXCollections.observableArrayList();
    private final ObservableList<RessourceTO> ressourcenListe = FXCollections.observableArrayList();

    private KundenauftragTO ausgewaehlterAuftrag;
    private AuftragsRessourceZuordungTO ausgewaehlteZuordnung;

    private final Map<Long, RessourceTO> resById = new HashMap<Long, RessourceTO>();

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tc_auftragsNr.setCellValueFactory(new PropertyValueFactory<>("auftragNr"));
        tc_datum.setCellValueFactory(new PropertyValueFactory<>("datum"));

        tc_kunde.setCellValueFactory(cell -> {
            KundenauftragTO a = cell.getValue();
            long kundenNr = a.getKunde_nr();
            

            String name = kundeNameById.getOrDefault(
                    kundenNr,
                    "unbekannt");

            return new SimpleStringProperty(name);
        });

        tv_auftraege.setItems(auftragsListe);

        tc_stunden.setCellValueFactory(new PropertyValueFactory<>("stunden"));
        tc_satz.setCellValueFactory(new PropertyValueFactory<>("individuellerStundensatz"));

        tc_ressource.setCellValueFactory(cell -> {
            AuftragsRessourceZuordungTO z = cell.getValue();
            Long rid = z.getRessource_id();
            RessourceTO r = resById.get(rid);
            if (r != null) {
                return new SimpleStringProperty(r.getName());
            }
            return new SimpleStringProperty(String.valueOf(rid));
        });

        tv_zuordnungen.setItems(zuordnungsListe);

        cb_ressource.setItems(ressourcenListe);
        cb_ressource.getSelectionModel().selectedItemProperty().addListener((obs, oldRes, newRes) -> {
            if (newRes != null) {
                tf_standardSatz.setText(String.valueOf(newRes.getStandrdKostensatz()));
            } else {
                tf_standardSatz.clear();
            }
        });

        tv_auftraege.getSelectionModel().selectedItemProperty().addListener((obs, oldA, newA) -> {
            if (newA != null) {
                ausgewaehlterAuftrag = newA;

                ladeZuordnungenFuerAuftrag(newA);

                cb_abgeschlossen.setSelected(newA.isBearbeitungAbgeschlossen());
                formularLeeren();
            }
        });

        tv_zuordnungen.getSelectionModel().selectedItemProperty().addListener((obs, oldZ, newZ) -> {
            if (newZ != null) {
                ausgewaehlteZuordnung = newZ;

                Long rid = newZ.getRessource_id();
                cb_ressource.setValue(resById.get(rid));

                tf_stunden.setText(String.valueOf(newZ.getStunden()));
                tf_stundensatz.setText(String.valueOf(newZ.getIndividuellerStundensatz()));
            }
        });
    }

    @Override
    public void initData() {

        auftragsListe.clear();
        zuordnungsListe.clear();
        ressourcenListe.clear();
        resById.clear();
        kundeNameById.clear();
        Collection<KundeTO> alleKunden = HauptmenueService.getKundevw().ladeAlleKunde();

        if (alleKunden != null) {
            for (KundeTO k : alleKunden) {
                kundeNameById.put(
                        k.getKunde_nr(),
                        k.getVorname() + " " + k.getName());
            }
        }

        ausgewaehlterAuftrag = null;
        ausgewaehlteZuordnung = null;
        cb_abgeschlossen.setSelected(false);
        formularLeeren();

        // Aufträge laden
        Collection<KundenauftragTO> auftraege = HauptmenueService.getAuftragVw().ladeAlleAuftraege();
        if (auftraege != null) {
            auftragsListe.addAll(auftraege);
        }

        Collection<RessourceTO> alleRessourcen = HauptmenueService.getRessourcenVw().ladeAlleRessources();
        if (alleRessourcen != null) {
            ressourcenListe.addAll(alleRessourcen);

            for (RessourceTO r : alleRessourcen) {

                resById.put(r.getId(), r);

            }
        }
    }

    private void ladeZuordnungenFuerAuftrag(KundenauftragTO auftrag) {
        zuordnungsListe.clear();

        if (auftrag == null) {
            return;
        }

        Collection<AuftragsRessourceZuordungTO> pos = HauptmenueService.getAbrechnungVw()
                .ladeRechnungspositionen(auftrag);

        if (pos != null) {
            zuordnungsListe.addAll(pos);
        }
    }

    private void formularLeeren() {
        cb_ressource.getSelectionModel().clearSelection();
        tf_stunden.clear();
        tf_stundensatz.clear();
        tf_standardSatz.clear();
        ausgewaehlteZuordnung = null;
    }

    @FXML
    private void bt_zuordnungSpeichernClicked() {

        if (ausgewaehlterAuftrag == null) {
            new Alert(Alert.AlertType.WARNING, "Bitte zuerst einen Auftrag auswählen!").show();
            return;
        }

        if (ausgewaehlterAuftrag.isBearbeitungAbgeschlossen()) {
            new Alert(Alert.AlertType.WARNING, "Abgeschlossene Aufträge können nicht mehr bearbeitet werden.").show();
            return;
        }

        RessourceTO res = cb_ressource.getValue();
        if (res == null || tf_stunden.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Bitte Ressource und Stunden angeben!").show();
            return;
        }

        double stunden;
        Double sonderSatz = null;

        try {
            stunden = Double.parseDouble(tf_stunden.getText());
            if (!tf_stundensatz.getText().isEmpty()) {
                sonderSatz = Double.parseDouble(tf_stundensatz.getText());
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING,
                    "Stunden und Sonder-Stundensatz müssen gültige Zahlen sein!\n" +
                            "Hinweis: Dezimalzahlen müssen mit Punkt geschrieben werden (z.B. 1.5 statt 1,5)")
                    .show();
            return;
        }

        if (ausgewaehlteZuordnung == null) {

            AuftragsRessourceZuordungTO dummy = new AuftragsRessourceZuordungTO();
            dummy.setRessource_id(res.getId());

            HauptmenueService.getAuftragsbearbeitungVw().ressourceZuordnen(
                    ausgewaehlterAuftrag,
                    dummy,
                    stunden,
                    sonderSatz);
        } else {

            ausgewaehlteZuordnung.setRessource_id(res.getId());
            ausgewaehlteZuordnung.setStunden(stunden);

            if (sonderSatz != null) {
                ausgewaehlteZuordnung.setIndividuellerStundensatz(sonderSatz);
            }

            HauptmenueService.getAuftragsbearbeitungVw().zuordnungAendern(ausgewaehlteZuordnung);
        }

        ladeZuordnungenFuerAuftrag(ausgewaehlterAuftrag);
        formularLeeren();
    }

    @FXML
    private void bt_zuordnungLoeschenClicked() {

        if (ausgewaehlterAuftrag == null) {
            new Alert(Alert.AlertType.WARNING, "Bitte zuerst einen Auftrag auswählen!").show();
            return;
        }

        if (ausgewaehlterAuftrag.isBearbeitungAbgeschlossen()) {
            new Alert(Alert.AlertType.WARNING, "Abgeschlossene Aufträge können nicht mehr bearbeitet werden.").show();
            return;
        }

        if (ausgewaehlteZuordnung == null) {
            new Alert(Alert.AlertType.WARNING, "Bitte zuerst einen Ressourceneinsatz in der Tabelle auswählen!").show();
            return;
        }

        boolean ok = HauptmenueService.getAuftragsbearbeitungVw().zuordnungLoeschen(ausgewaehlteZuordnung);

        if (ok) {
            zuordnungsListe.remove(ausgewaehlteZuordnung);
            new Alert(Alert.AlertType.INFORMATION, "Ressource wurde erfolgreich gelöscht!").show();
            formularLeeren();
        } else {
            new Alert(Alert.AlertType.ERROR, "Die Zuordnung konnte nicht gelöscht werden.").show();
        }
    }

    @FXML
    private void bt_auftragSpeichernClicked() {

        if (ausgewaehlterAuftrag == null) {
            new Alert(Alert.AlertType.WARNING, "Bitte zuerst einen Auftrag auswählen!").show();
            return;
        }

        // Auftrag abschließen
        ausgewaehlterAuftrag.setBearbeitungAbgeschlossen(true);

        boolean ok = HauptmenueService.getAuftragsbearbeitungVw()
                .auftragAbschliessen(ausgewaehlterAuftrag);

        if (!ok) {
            new Alert(Alert.AlertType.ERROR, "Auftrag konnte nicht abgeschlossen werden.").show();
            return;
        }

        cb_abgeschlossen.setSelected(true);

        new Alert(Alert.AlertType.INFORMATION, "Der Auftrag wurde erfolgreich abgeschlossen!").show();

        initData();
    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.KUNDENAUFTRAG_SCREEN);
    }
}
