package de.handwerkersystem.dlg.auftragverwalter;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import de.handwerkersystem.awk.auftragverwaltung.entity.AuftragsRessourceZuordungTO;
import de.handwerkersystem.awk.auftragverwaltung.entity.KundenauftragTO;
import de.handwerkersystem.awk.entity.RessourceTO;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class AbrechnungVerwalterScreenController implements Initializable, ControlledScreen {

    private ScreensController myController;

    @FXML
    private TableView<KundenauftragTO> tv_auftraege;
    @FXML
    private TableColumn<KundenauftragTO, Integer> tc_nr;
    @FXML
    private TableColumn<KundenauftragTO, String> tc_kunde; 
    @FXML
    private TableColumn<KundenauftragTO, Date> tc_datum;

    @FXML
    private TextArea ta_rechnung;

    @FXML
    private Button bt_rechnungErstellen;
    @FXML
    private Button bt_alsAbgerechnet;
    @FXML
    private Button bt_zurueck;

    private final ObservableList<KundenauftragTO> auftragsListe = FXCollections.observableArrayList();

    private KundenauftragTO ausgewaehlterAuftrag;

    private final Map<Long, RessourceTO> ressourcenById = new HashMap<Long, RessourceTO>();

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tc_nr.setCellValueFactory(new PropertyValueFactory<>("auftragNr"));
        tc_datum.setCellValueFactory(new PropertyValueFactory<>("datum"));

        tc_kunde.setCellValueFactory(cell -> {
            KundenauftragTO a = cell.getValue();
            long kundenNr = a.getKunde_nr();   
            return new SimpleStringProperty(String.valueOf(kundenNr));
        });

        tv_auftraege.setItems(auftragsListe);

        bt_rechnungErstellen.setDisable(true);
        bt_alsAbgerechnet.setDisable(true);

        tv_auftraege.getSelectionModel().selectedItemProperty().addListener(
                (obs, alt, neu) -> {
                    ausgewaehlterAuftrag = neu;
                    ta_rechnung.clear();
                    boolean disabled = (neu == null);
                    bt_rechnungErstellen.setDisable(disabled);
                    bt_alsAbgerechnet.setDisable(disabled);
                });
    }

    @Override
    public void initData() {
        auftragsListe.clear();
        ta_rechnung.clear();
        ausgewaehlterAuftrag = null;
        bt_rechnungErstellen.setDisable(true);
        bt_alsAbgerechnet.setDisable(true);

        ressourcenById.clear();

        Collection<RessourceTO> alleRessourcen = HauptmenueService.getRessourcenVw().ladeAlleRessources();
        if (alleRessourcen != null) {
            for (RessourceTO r : alleRessourcen) {
                ressourcenById.put(r.getId(), r); 
            }
        }

        if (myController != null) {
            Collection<KundenauftragTO> data = HauptmenueService.getAbrechnungVw().ladeAbzurechnendeAuftraege();

            if (data != null) {
                auftragsListe.setAll(
                        data.stream()
                                .sorted((a1, a2) -> a1.getDatum().compareTo(a2.getDatum()))
                                .toList());
            }
        }
    }

    @FXML
    private void bt_rechnungErstellenClicked() {
        if (ausgewaehlterAuftrag == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Auftrag auswählen!").show();
            return;
        }

        String text = erzeugeRechnungstext(ausgewaehlterAuftrag);
        ta_rechnung.setText(text);
    }

    @FXML
    private void bt_alsAbgerechnetClicked() {
        if (ausgewaehlterAuftrag == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Auftrag auswählen!").show();
            return;
        }

        HauptmenueService.getAbrechnungVw().auftragAbgerechnetSetzen(ausgewaehlterAuftrag);

        new Alert(Alert.AlertType.INFORMATION,
                "Der Auftrag wurde als abgerechnet markiert.").show();

        initData();
    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.KUNDENAUFTRAG_SCREEN);
    }

    private String erzeugeRechnungstext(KundenauftragTO auftrag) {
        StringBuilder sb = new StringBuilder();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        DecimalFormat df = new DecimalFormat("0.00");

        long kundenNr = auftrag.getKunde_nr(); 
    
        

  

        sb.append("Rechnung für Auftrag Nr. ").append(auftrag.getAuftragNr()).append("\n\n");
        sb.append("Kundennummer:").append(kundenNr).append("\n");

        sb.append("Adresse der Baustelle: ").append(auftrag.printAdresse()).append("\n");

        sb.append("Auftragsdatum: ");
        if (auftrag.getDatum() != null) {
            sb.append(sdf.format(auftrag.getDatum()));
        } else {
            sb.append("-");
        }
        sb.append("\n");

        sb.append("Beschreibung: ");
        if (auftrag.getTextfeld() != null) {
            sb.append(auftrag.getTextfeld());
        } else {
            sb.append("-");
        }
        sb.append("\n\n");

        sb.append("Positionen:\n");
        sb.append("---------------------------------------------------------------\n");
        sb.append("Pos | Ressource     | Art       | Stunden | Satz   | Betrag\n");
        sb.append("---------------------------------------------------------------\n");

        Collection<AuftragsRessourceZuordungTO> eintraege =
                HauptmenueService.getAbrechnungVw().ladeRechnungspositionen(auftrag);

        double gesamt = 0.0;
        int pos = 1;

        if (eintraege != null) {
            for (AuftragsRessourceZuordungTO z : eintraege) {

                long resId = z.getRessource_id(); 
                RessourceTO res = ressourcenById.get(resId);

                String resName = (res != null) ? res.getName() : ("ID " + resId);
                String art = (res != null) ? res.getArt() : "-";

                double stunden = z.getStunden();

                double satz = z.getIndividuellerStundensatz();
                if (satz == 0.0 && res != null) {
                    satz = res.getStandrdKostensatz();
                }

                double betrag = stunden * satz;
                gesamt = gesamt + betrag;

                sb.append(pos).append("   ")
                        .append(resName).append("   ")
                        .append(art).append("   ")
                        .append(df.format(stunden)).append("   ")
                        .append(df.format(satz)).append("   ")
                        .append(df.format(betrag))
                        .append("\n");

                pos++;
            }
        }

        sb.append("---------------------------------------------------------------\n");
        sb.append("Gesamtbetrag: ").append(df.format(gesamt)).append(" €\n");

        return sb.toString();
    }
}
