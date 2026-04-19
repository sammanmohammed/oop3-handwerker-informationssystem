package de.handwerkersystem.dlg.ressourceverwalter;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import de.handwerkersystem.awk.entity.RessourceTO;
import de.handwerkersystem.dlg.hauptmenue.ControlledScreen;
import de.handwerkersystem.dlg.hauptmenue.Hauptmenue;
import de.handwerkersystem.dlg.hauptmenue.HauptmenueService;
import de.handwerkersystem.dlg.hauptmenue.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RessourceLoeschenScreenController implements ControlledScreen, Initializable {
    private ScreensController myController;

   
    @FXML
    private Button bt_zurueck;
    @FXML
    private TableView<RessourceTO> tv_ressource;
    @FXML
    private TableColumn<RessourceTO, Integer> tc_id;
    @FXML
    private TableColumn<RessourceTO, String> tc_art;
    @FXML
    private TableColumn<RessourceTO, String> tc_name;

    private final ObservableList<RessourceTO> ressourcenListe = FXCollections.observableArrayList();

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }
  @Override
    public void initialize(URL location, ResourceBundle resources) {

        tc_id.setCellValueFactory(
                new PropertyValueFactory<RessourceTO, Integer>("id"));
        tc_art.setCellValueFactory(
                new PropertyValueFactory<RessourceTO, String>("art"));
        tc_name.setCellValueFactory(
                new PropertyValueFactory<RessourceTO, String>("name"));


        tv_ressource.setItems(ressourcenListe);

    }


    @Override
    public void initData() {
        ressourcenListe.clear();
        Collection<RessourceTO> data = HauptmenueService.getRessourcenVw().ladeAlleRessources();
        ressourcenListe.setAll(data.stream().toList());
    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.RESSOURCEN_VERWALTER_SCREEN);
    }
       @FXML
    private void bt_ressourceloeschenClicked() {
        RessourceTO ausgewaehlteRessource = tv_ressource.getSelectionModel().getSelectedItem();

        if (ausgewaehlteRessource == null) {
             Alert a = new Alert(Alert.AlertType.WARNING,
                    "Ressource ist nicht ausgewählt!");
            a.show();
            return;
        }

  boolean geloescht = HauptmenueService.getRessourcenVw().ressourceLoeschen(ausgewaehlteRessource);

        if (geloescht) {
            ressourcenListe.remove(ausgewaehlteRessource);
              Alert a = new Alert(Alert.AlertType.WARNING,
                    "Ressource gelöscht: " + ausgewaehlteRessource.getName());
          a.show();
          
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING,
                    "Diese Ressource befindet sich in einem Auftrag!");
            a.show();
         
        }
    }
}
