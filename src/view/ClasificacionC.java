/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import boe.Boe;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import main.Boes;
import main.ControlledScreen;
import main.ScreensController;
import model.ModeloBoes;

/**
 * FXML Controller class
 *
 * @author Agarimo
 */
public class ClasificacionC implements Initializable, ControlledScreen {

    ScreensController myController;
    boolean autoScroll;
    
    ObservableList<ModeloBoes> publicacion;
    ObservableList<ModeloBoes> selectedList;
    ObservableList<ModeloBoes> discartedList;
    
    
    //<editor-fold defaultstate="collapsed" desc="FXML VAR">
    @FXML
    private TableView<ModeloBoes> tvBoes;
    @FXML
    private TableColumn<ModeloBoes, String> codigoCL;
    @FXML
    private TableColumn<ModeloBoes, String> origenCL;
    @FXML
    private TableColumn<ModeloBoes, String> descripcionCL;
    @FXML
    private ListView<ModeloBoes> lvSelect;
    @FXML
    private ListView<ModeloBoes> lvDiscard;
    @FXML
    private Label lbClasificacion;
    @FXML
    private Label lbContadorT;
    @FXML
    private CheckBox cbAutoScroll;
    @FXML
    private ProgressBar pbClasificacion;
    @FXML
    private DatePicker dpFechaC;
    @FXML
    private Button btSelect;
    @FXML
    private Button btRecargarClasificacion;
    @FXML
    private Button btRecoverD;
    @FXML
    private Button btSelectAll;
    @FXML
    private Button btRecoverS;
    @FXML
    private Button btVerWebC;
    @FXML
    private Button btDiscard;
    @FXML
    private Button btDescartaOrigen;
    @FXML
    private Button btFinClas;
    @FXML
    private TitledPane tpDescartados;
    @FXML
    private TitledPane tpSeleccionados;
    //</editor-fold>

    @FXML
    void abrirWeb(ActionEvent event) {

    }

    @FXML
    void cambioEnCheckBox(ActionEvent event) {

    }

    @FXML
    void cambioEnDatePicker(ActionEvent event) {

    }

    @FXML
    public void cargaMain(ActionEvent event) {
        clearWindow();
        myController.setScreen(Boes.screen1ID);
    }

    private void clearWindow() {
        //
    }

    @FXML
    void descartaOrigen(ActionEvent event) {

    }

    @FXML
    void discardPdf(ActionEvent event) {

    }

    @FXML
    void finalizaClas(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        publicacion.clear();
        discartedList.clear();
        selectedList.clear();
        dpFechaC.setValue(null);
        autoScroll = true;
        cbAutoScroll.setSelected(autoScroll);
    }
    
    private void iniciaTablaBoes() {
        origenCL.setCellValueFactory(new PropertyValueFactory<>("origen"));
        origenCL.setCellFactory(new Callback<TableColumn<ModeloBoes, String>, TableCell<ModeloBoes, String>>() {
            @Override
            public TableCell<ModeloBoes, String> call(TableColumn<ModeloBoes, String> arg0) {
                return new TableCell<ModeloBoes, String>() {
                    private Text text;

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item);
                            text.setWrappingWidth(origenCL.getWidth() - 10);
                            this.setWrapText(true);
                            setGraphic(text);
                        } else {
                            text = new Text("");
                            setGraphic(text);
                        }
                    }
                };
            }
        });
        codigoCL.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        descripcionCL.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        descripcionCL.setCellFactory(new Callback<TableColumn<ModeloBoes, String>, TableCell<ModeloBoes, String>>() {
            @Override
            public TableCell<ModeloBoes, String> call(TableColumn<ModeloBoes, String> arg0) {
                return new TableCell<ModeloBoes, String>() {
                    private Text text;

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item);
                            text.setWrappingWidth(descripcionCL.getWidth() - 20);
                            this.setWrapText(true);
                            setGraphic(text);
                        } else {
                            text = new Text("");
                            setGraphic(text);
                        }
                    }
                };
            }
        });

        publicacion = FXCollections.observableArrayList();
        tvBoes.setItems(publicacion);
        selectedList = FXCollections.observableArrayList();
        lvSelect.setItems(selectedList);
        discartedList = FXCollections.observableArrayList();
        lvDiscard.setItems(discartedList);
    }
    
    

    @FXML
    void recoverD(ActionEvent event) {

    }

    @FXML
    void recoverS(ActionEvent event) {

    }

    @FXML
    void selectAll(ActionEvent event) {

    }

    @FXML
    void selectPdf(ActionEvent event) {

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}
