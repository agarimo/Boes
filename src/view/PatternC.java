/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Boes;
import main.ControlledScreen;
import main.ScreensController;
import main.SqlBoe;
import util.Dates;
import util.Varios;

/**
 * FXML Controller class
 *
 * @author Agarimo
 */
public class PatternC implements Initializable, ControlledScreen {
    
    ScreensController myController;
    
    @FXML
    private Label lbConPatronNif;
    @FXML
    private Label lbSinPatronNif;
    @FXML
    private Label lbPorcentajeNif;
    @FXML
    private Label lbConPatronMat;
    @FXML
    private Label lbSinPatronMat;
    @FXML
    private Label lbPorcentajeMat;
    @FXML
    private Label lbCount;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TableView tvNif;
    @FXML
    private TableColumn idNifCL;
    @FXML
    private TableColumn  nifCL;
    @FXML
    private TableView tvMat;
    @FXML
    private TableColumn idMatCL;
    @FXML
    private TableColumn  matCL;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void cargaMain(ActionEvent event) {
        clearWindow();
        myController.setScreen(Boes.screen1ID);
    }
    
    private void clearWindow(){
        
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    
    @FXML
    private void cambioEnDatePicker(ActionEvent event){
        try {
            Date fecha = Dates.asDate(dpFecha.getValue());

            if (fecha != null) {
                String query = "SELECT * FROM boes.procesar where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));
//                cargarDatosProcesar(SqlBoe.listaProcesar(query));
            }
        } catch (NullPointerException ex) {
            //
        }
    }
    
}
