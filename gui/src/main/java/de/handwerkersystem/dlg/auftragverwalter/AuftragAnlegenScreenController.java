package de.handwerkersystem.dlg.auftragverwalter;

import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import de.handwerkersystem.awk.auftragverwaltung.entity.AdresseTO;   
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;
import de.handwerkersystem.awk.kundenverwaltung.entity.KundeTO;
import de.handwerkersystem.dlg.hauptmenue.ControlledScreen;
import de.handwerkersystem.dlg.hauptmenue.Hauptmenue;
import de.handwerkersystem.dlg.hauptmenue.HauptmenueService;
import de.handwerkersystem.dlg.hauptmenue.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuftragAnlegenScreenController implements ControlledScreen, Initializable {

    private ScreensController myController;

    @FXML
    private TableView<KundeTO> tv_kunden;
    @FXML
    private TableColumn<KundeTO, Long> tc_kundenNr;
    @FXML
    private TableColumn<KundeTO, String> tc_vorname;
    @FXML
    private TableColumn<KundeTO, String> tc_nachname;

    @FXML
    private TextArea ta_beschreibung;

  
    @FXML
    private TextField tf_strasse;
    @FXML
    private TextField tf_hausnr;
    @FXML
    private TextField tf_plz;
    @FXML
    private TextField tf_ort;

    private final ObservableList<KundeTO> kundenListe = FXCollections.observableArrayList();

    private Long ausgewaehlteKundenNr;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tc_kundenNr.setCellValueFactory(new PropertyValueFactory<KundeTO, Long>("kunde_nr"));
        tc_vorname.setCellValueFactory(new PropertyValueFactory<KundeTO, String>("vorname"));
        tc_nachname.setCellValueFactory(new PropertyValueFactory<KundeTO, String>("name"));

        tv_kunden.setItems(kundenListe);

        tv_kunden.getSelectionModel().selectedItemProperty().addListener((obs, oldKunde, newKunde) -> {
            if (newKunde != null) {
                ausgewaehlteKundenNr = newKunde.getKunde_nr();
            }
        });
    }




    @FXML
    private void bt_speichernClicked() {

        if (ausgewaehlteKundenNr == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Kunden in der Tabelle auswählen!").show();
            return;
        }

        String beschreibung = ta_beschreibung.getText();
        if (beschreibung == null || beschreibung.isBlank()) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte eine Auftragsbeschreibung eingeben!").show();
            return;
        }

        if (beschreibung.length() > 2000) {
            new Alert(Alert.AlertType.WARNING,
                    "Die Beschreibung darf maximal 2000 Zeichen enthalten!").show();
            return;
        }

      
        String strasse = (tf_strasse != null) ? tf_strasse.getText() : null;
        String hausnr  = (tf_hausnr != null) ? tf_hausnr.getText() : null;
        String plz     = (tf_plz != null) ? tf_plz.getText() : null;
        String ort     = (tf_ort != null) ? tf_ort.getText() : null;

        if (strasse == null || strasse.isBlank() ||
            hausnr == null  || hausnr.isBlank()  ||
            plz == null     || plz.isBlank()     ||
            ort == null     || ort.isBlank()) {

            new Alert(Alert.AlertType.WARNING,
                    "Bitte die komplette Baustellenadresse eingeben (Straße, Hausnr, PLZ, Ort).").show();
            return;
        }

     
        AdresseTO baustelle = new AdresseTO();
        baustelle.setStrasse(strasse.trim());
        baustelle.setHausnr(hausnr.trim());
        baustelle.setPlz(plz.trim());
        baustelle.setOrt(ort.trim());

      
        
        KundenauftragTO auftrag = new KundenauftragTO();
        auftrag.setKunde_nr(ausgewaehlteKundenNr);
        auftrag.setTextfeld(beschreibung.trim());
        auftrag.setDatum(new Date());
        auftrag.setBearbeitungAbgeschlossen(false);
        auftrag.setAbgerechnet(false);

        // Baustellenadresse im Auftrag speichern
        auftrag.setBaustellenAdresse(baustelle); 

        boolean ok = HauptmenueService.getAuftragVw().auftragAnlegen(auftrag);

        if (!ok) {
            new Alert(Alert.AlertType.ERROR,
                    "Auftrag konnte nicht angelegt werden!").show();
            return;
        }

        new Alert(Alert.AlertType.INFORMATION,
                "Auftrag wurde erfolgreich angelegt!").show();

        // Felder leeren
        ta_beschreibung.clear();
        tv_kunden.getSelectionModel().clearSelection();
        ausgewaehlteKundenNr = null;

        if (tf_strasse != null) tf_strasse.clear();
        if (tf_hausnr != null) tf_hausnr.clear();
        if (tf_plz != null) tf_plz.clear();
        if (tf_ort != null) tf_ort.clear();
    }

    @FXML
    private void bt_zurueckClicked(ActionEvent event) {
        myController.setScreen(Hauptmenue.KUNDENAUFTRAG_SCREEN);
    }

    @Override
    public void initData() {
        kundenListe.clear();
        Collection<KundeTO> data = HauptmenueService.getKundevw().ladeAlleKunde();

        if (data != null) {
            kundenListe.setAll(data.stream().toList());
        }
    }
}
