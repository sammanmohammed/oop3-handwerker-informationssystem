package de.handwerkersystem.dlg.hauptmenue;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

//Matrikel-Nr: 1134978
//Mohammed Samman

public class Hauptmenue extends Application {

    private Stage mainStage;
    // Hauptmenue Szene
    public static final String MAIN_SCREEN = "main";
    public static final String MAIN_SCREEN_FXML = "de/handwerkersystem/dlg/hauptmenue/Hauptmenue.fxml";

    // Kunenverwaltungsszenen
    public static final String KUNDEN_VERWALTER_SCREEN = "kundenverwalter";
    public static final String KUNDEN_VERWALTER_SCREEN_FXML = "de/handwerkersystem/dlg/kundenverwalter/KundenVerwalter_Screen.fxml";

    public static final String KUNDENANLEGEN_SCREEN = "kundenanlegen";
    public static final String KUNDENANLEGEN_SCREEN_FXML = "de/handwerkersystem/dlg/kundenverwalter/KundenAnlegen_Screen.fxml";

    public static final String KUNDENLOESCHEN_SCREEN = "kundenloeschen";
    public static final String KUNDENLOESCHEN_SCREEN_FXML = "de/handwerkersystem/dlg/kundenverwalter/KundenLoeschen_Screen.fxml";

    public static final String KUNDENAENDERN_SCREEN = "kundenaendern";
    public static final String KUNDENAENDERN_SCREEN_FXML = "de/handwerkersystem/dlg/kundenverwalter/KundenAendern_Screen.fxml";

    public static final String KUNDENLISTEANZEIGEN_SCREEN = "kundenliste anzeigen";
    public static final String KUNDENLISTEANZEIGEN_SCREEN_FXML = "de/handwerkersystem/dlg/kundenverwalter/KundenListeAnzeigen_Screen.fxml";

    // Ressourcenverwaltungsszennen
    public static final String RESSOURCEN_VERWALTER_SCREEN = "ressourcenverwalter";
    public static final String RESSOURCEN_VERWALTER_SCREEN_FXML = "de/handwerkersystem/dlg/ressourceverwalter/Ressourcen_Verwalter.fxml";
    public static final String RESSOURCEANLEGEN_SCREEN = "ressourceanlegen";
    public static final String RESSOURCEANLEGEN_SCREEN_FXML = "de/handwerkersystem/dlg/ressourceverwalter/RessourceAnlegen_Sreen.fxml";
    public static final String RESSOURCELOESCHEN_SCREEN = "ressourceloeschen";
    public static final String RESSOURCELOESCHEN_SCREEN_FXML = "de/handwerkersystem/dlg/ressourceverwalter/RessourceLoeschen_Screen.fxml";
    public static final String RESSOURCEAENDERN_SCREEN = "ressourceaendern";
    public static final String RESSOURCEAENDERN_SCREEN_FXML = "de/handwerkersystem/dlg/ressourceverwalter/RessourceAendern_Screen.fxml";

    // Aufträge Verwaltungsszenen

    public static final String KUNDENAUFTRAG_SCREEN = "kundenauftrag";
    public static final String KUNDENAUFTRAG_SCREEN_FXML = "de/handwerkersystem/dlg/auftragverwalter/KundenAuftrag_Screen.fxml";

    public static final String AUFTRAGSBEARBEITUNG_SCREEN = "auftragsbearbeitung";
    public static final String AUFTRAGSBEARBEITUNG_SCREEN_FXML = "de/handwerkersystem/dlg/auftragverwalter/Auftragsbearbeitung_Screen.fxml";

    public static final String ABRECHNUNG_SCREEN = "abrechnung";
    public static final String ABRECHNUNG_SCREEN_FXML = "de/handwerkersystem/dlg/auftragverwalter/Abrechnung_Screen.fxml";

    public static final String AUFTRAGANLEGEN_SCREEN = "auftraganlegen";
    public static final String AUFTRAGANLEGEN_SCREEN_FXML = "de/handwerkersystem/dlg/auftragverwalter/AuftragAnlegen_Screen.fxml";

    @Override
    public void start(Stage primaryStage) {

        this.mainStage = primaryStage;

        ScreensController mainContainer = new ScreensController();
        // alle Screens laden
        // screen von Hauptmenü laden
        mainContainer.loadScreen(Hauptmenue.MAIN_SCREEN, Hauptmenue.MAIN_SCREEN_FXML);
        // screens von Kundenverwalter laden
        mainContainer.loadScreen(KUNDEN_VERWALTER_SCREEN, KUNDEN_VERWALTER_SCREEN_FXML);
        mainContainer.loadScreen(KUNDENANLEGEN_SCREEN, KUNDENANLEGEN_SCREEN_FXML);
        mainContainer.loadScreen(KUNDENLOESCHEN_SCREEN, KUNDENLOESCHEN_SCREEN_FXML);
        mainContainer.loadScreen(KUNDENAENDERN_SCREEN, KUNDENAENDERN_SCREEN_FXML);
        mainContainer.loadScreen(KUNDENLISTEANZEIGEN_SCREEN, KUNDENLISTEANZEIGEN_SCREEN_FXML);
        // screens von Ressourceverwalter laden
        mainContainer.loadScreen(RESSOURCEN_VERWALTER_SCREEN,
                RESSOURCEN_VERWALTER_SCREEN_FXML);
        mainContainer.loadScreen(RESSOURCEANLEGEN_SCREEN, RESSOURCEANLEGEN_SCREEN_FXML);
        mainContainer.loadScreen(RESSOURCELOESCHEN_SCREEN, RESSOURCELOESCHEN_SCREEN_FXML);
        mainContainer.loadScreen(RESSOURCEAENDERN_SCREEN, RESSOURCEAENDERN_SCREEN_FXML);

        // screens von Kundenauftragverwalter laden
        mainContainer.loadScreen(KUNDENAUFTRAG_SCREEN, KUNDENAUFTRAG_SCREEN_FXML);
        mainContainer.loadScreen(AUFTRAGSBEARBEITUNG_SCREEN,
                AUFTRAGSBEARBEITUNG_SCREEN_FXML);
        mainContainer.loadScreen(ABRECHNUNG_SCREEN, ABRECHNUNG_SCREEN_FXML);
        mainContainer.loadScreen(AUFTRAGANLEGEN_SCREEN, AUFTRAGANLEGEN_SCREEN_FXML);

        mainContainer.print();

        mainContainer.setScreen(Hauptmenue.MAIN_SCREEN);
        Group root = new Group();
        // BorderPane root = new BorderPane();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(confirmCloseEventHandler);
        primaryStage.show();

    }

    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        // Quelle:
        // http://stackoverflow.com/questions/29710492/javafx-internal-close-request
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit?");
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK);
        exitButton.setText("Exit");
        closeConfirmation.setHeaderText("Confirm Exit");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        closeConfirmation.initOwner(mainStage);

        // normally, you would just use the default alert positioning,
        // but for this simple sample the main stage is small,
        // so explicitly position the alert so that the main window can still be seen.
        closeConfirmation.setX(mainStage.getX() + 150);
        closeConfirmation.setY(mainStage.getY() - 300 + mainStage.getHeight());

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }
}
