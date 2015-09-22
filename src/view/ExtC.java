package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.Boes;
import main.ControlledScreen;
import main.ScreensController;

/**
 *
 * @author Agarimo
 */
public class ExtC implements Initializable, ControlledScreen {

    ScreensController myController;
    int PanelProcesar = 1;
    int PanelPreview = 2;
    boolean isPreview;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane panelProcesar;

    @FXML
    private AnchorPane panelPreview;
    
    @FXML
    private DatePicker dpFecha;

    @FXML
    private Button btPreview;
    
    @FXML
    private Button btGenerarPdf;
    
    @FXML
    private Button btVerPdf;
    
    @FXML
    private Button btAbrirCarpeta;
    
    @FXML
    private Button btProcesar;
    
    @FXML
    private TableView tvProcesar;
    
    @FXML
    private TableColumn clCodigo;
    
    @FXML 
    private TableColumn clEstructura;
    
    @FXML
    private TableColumn clEstado;
    
    @FXML
    private TableView tvPreview;
    
    @FXML
    private TableColumn clExpediente;
    
    @FXML
    private TableColumn clSancionado;
    
    @FXML
    private TableColumn clNif;
    
    @FXML
    private TableColumn clLocalidad;
    
    @FXML
    private TableColumn clFecha;
    
    @FXML
    private TableColumn clMatricula;
    
    @FXML
    private TableColumn clCuantia;
    
    @FXML
    private TableColumn clArticulo;
    
    @FXML
    private TableColumn clPuntos;
    
    @FXML
    private TableColumn clReqObs;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panelPreview.setOpacity(0.0);
        panelProcesar.setOpacity(1.0);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    public void cargaMain(ActionEvent event) {
        myController.setScreen(Boes.screen1ID);
    }

    private void mostrarPanel(int panel) {
        FadeTransition fade;

        switch (panel) {
            case 1:
                fade = new FadeTransition(Duration.millis(500), panelProcesar);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setAutoReverse(false);
                fade.play();

                fade = new FadeTransition(Duration.millis(500), panelPreview);
                fade.setFromValue(0.0);
                fade.setToValue(1.0);
                fade.setAutoReverse(false);
                fade.play();

                break;

            case 2:
                fade = new FadeTransition(Duration.millis(500), panelPreview);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setAutoReverse(false);
                fade.play();

                fade = new FadeTransition(Duration.millis(500), panelProcesar);
                fade.setFromValue(0.0);
                fade.setToValue(1.0);
                fade.setAutoReverse(false);
                fade.play();

                break;
        }
    }

    @FXML
    void showPreview(ActionEvent event) {
        isPreview = !isPreview;

        if (isPreview) {
            btPreview.setText("Volver");
            mostrarPanel(this.PanelProcesar);
        } else {
            btPreview.setText("Previsualizar Extracción");
            mostrarPanel(this.PanelPreview);
        }
    }
}
