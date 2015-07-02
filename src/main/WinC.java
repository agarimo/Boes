package main;

import boes.Boe;
import boes.Download;
import boes.Insercion;
import boes.Pdf;
import boes.Publicacion;
import boletines.Archivos;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.ModeloBoes;
import model.ModeloBoletines;
import util.Dates;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class WinC implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML Var">
    @FXML
    private VBox rootPane;

    @FXML
    private TableColumn<ModeloBoes, String> origenCL;

    @FXML
    private AnchorPane panelEnlaces;

    @FXML
    private AnchorPane panelInicio;

    @FXML
    private TableColumn<ModeloBoes, String> descripcionCL;

    @FXML
    private Button btAbrirCarpeta;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TableColumn<ModeloBoes, String> codigoCL;

    @FXML
    private AnchorPane panelClasificacion;

    @FXML
    private AnchorPane panelBoletines;

    @FXML
    private AnchorPane panelFases;

    @FXML
    private Button btClasificar;

    @FXML
    private Button btDescargaTodos;

    @FXML
    private Button btAddBoe;

    @FXML
    private TableView<ModeloBoes> tvBoes;

    @FXML
    private TextField tfLink;

    @FXML
    private TextField tfFechaV;

    @FXML
    private TextField tfLinkV;

    @FXML
    private TextField tfIsClas;

    @FXML
    private ListView<Boe> lvBoe;

    @FXML
    private DatePicker dpFechaC;

    @FXML
    private Button btSelect;

    @FXML
    private Button btDiscard;

    @FXML
    private ListView<ModeloBoes> lvDiscard;

    @FXML
    private ListView<ModeloBoes> lvSelect;

    @FXML
    private Button btRecoverS;

    @FXML
    private Button btRecoverD;

    @FXML
    private Button btFinClas;

    @FXML
    private Button btDescargar;

    @FXML
    private TableView<ModeloBoletines> tvBoletines;

    @FXML
    private TableColumn<ModeloBoletines, String> codigoCLB;

    @FXML
    private TableColumn<ModeloBoletines, String> origenCLB;

    @FXML
    private TableColumn<ModeloBoletines, String> fechaCLB;

    @FXML
    private TableColumn<ModeloBoletines, String> tipoCLB;

    @FXML
    private TableColumn<ModeloBoletines, String> estadoCLB;

    @FXML
    private DatePicker dpFechaB;

    @FXML
    private Button btDescargaBoletines;

    @FXML
    private Button btGenerarArchivos;

    @FXML
    private Button btComprobarFases;

    @FXML
    private Button btAbrirCarpetaBoletines;

    @FXML
    private Button btDescartaOrigen;

//</editor-fold>
    ObservableList<ModeloBoes> publicacion;
    ObservableList<Boe> boesList;
    ObservableList<ModeloBoes> selectedList;
    ObservableList<ModeloBoes> discartedList;
    ObservableList<ModeloBoletines> boletinesList;
    List origen_descartado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        origen_descartado = SqlBoe.listaOrigenDescartado();
        iniciaTablaBoes();

        final ObservableList<Boe> aux1 = lvBoe.getSelectionModel().getSelectedItems();
        aux1.addListener(selectorListaBoe);
    }

    //<editor-fold defaultstate="collapsed" desc="GENERAL">
    @FXML
    void iniciaInicio() {
        mostrarPanel(0);
    }

    private void mostrarPanel(int panel) {

        switch (panel) {

            case 0:
                panelInicio.setVisible(true);
                panelEnlaces.setVisible(false);
                panelClasificacion.setVisible(false);
                panelBoletines.setVisible(false);
                panelFases.setVisible(false);
                break;
            case 1:
                panelInicio.setVisible(false);
                panelEnlaces.setVisible(true);
                panelClasificacion.setVisible(false);
                panelBoletines.setVisible(false);
                panelFases.setVisible(false);
                break;
            case 2:
                panelInicio.setVisible(false);
                panelEnlaces.setVisible(false);
                panelClasificacion.setVisible(true);
                panelBoletines.setVisible(false);
                panelFases.setVisible(false);
                break;
            case 3:
                panelInicio.setVisible(false);
                panelEnlaces.setVisible(false);
                panelClasificacion.setVisible(false);
                panelBoletines.setVisible(true);
                panelFases.setVisible(false);
                break;

            case 4:
                panelInicio.setVisible(false);
                panelEnlaces.setVisible(false);
                panelClasificacion.setVisible(false);
                panelBoletines.setVisible(false);
                panelFases.setVisible(true);
                break;
        }
    }

    @FXML
    void descargaPendientes(ActionEvent event) {
        Download dw = new Download();
        dw.start();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="DESCARGA">
    //<editor-fold defaultstate="collapsed" desc="PANEL ENLACES">
    @FXML
    void iniciaEnlaces(ActionEvent event) {
        mostrarPanel(1);
        cleanEnlaces();
        iniciaListaBoe();
    }

    private void iniciaListaBoe() {
        boesList = FXCollections.observableArrayList();
        cargaListaBoe();
        lvBoe.setItems(boesList);
    }

    private void cargaListaBoe() {
        boesList.addAll(SqlBoe.listaBoe("SELECT * FROM boes.boe"));
    }

    @FXML
    void addBoe(ActionEvent event) {
        Boe aux;
        Sql bd;

        aux = new Boe(Dates.asDate(dpFecha.getValue()), tfLink.getText());
        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(aux.SQLCrear());
            bd.close();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error SQL");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

        cleanEnlaces();
        iniciaListaBoe();
    }

    private void mostrarBoe() {
        Boe aux = (Boe) lvBoe.getSelectionModel().getSelectedItem();
        tfFechaV.setText(aux.toString());
        tfLinkV.setText(aux.getLink());

        if (aux.isIsClas()) {
            tfIsClas.setText("SI");
        } else {
            tfIsClas.setText("NO");
        }
    }

    private void cleanEnlaces() {
        dpFecha.setValue(null);
        tfLink.setText("");
        tfLinkV.setText("");
        tfFechaV.setText("");
        tfIsClas.setText("");
    }

    @FXML
    void clasificarBoe(ActionEvent event) {
        try {
            dpFechaC.setValue(Dates.asLocalDate(lvBoe.getSelectionModel().getSelectedItem().getFecha()));
            mostrarPanel(2);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar una fecha");
            alert.showAndWait();
        }
    }

    @FXML
    void abrirCarpetaData(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new File("data").toURI());
        } catch (IOException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void descargaTodosBoe(ActionEvent event) {
        //TODO ejecutar este método en hilo secundario y buscar forma de mostrar un progreso en el hilo principal.
        tfLinkV.setText("DESCARGANDO TODOS LOS BOES");

        Boe boe = lvBoe.getSelectionModel().getSelectedItem();
        boe.getBoletines();

        if (DWBoe(boe)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("COMPLETADO");
            alert.setHeaderText(null);
            alert.setContentText("DESCARGA Y CONVERSIÓN FINALIZADA");
            alert.showAndWait();

            tfLink.setText("");
            dpFecha.setValue(null);
        }
    }

    private boolean DWBoe(Boe boe) {
        Iterator it;
        Iterator it2;
        Publicacion pb;
        Pdf pd;

        it = boe.getPb().iterator();

        while (it.hasNext()) {
            pb = (Publicacion) it.next();
            it2 = pb.getPdfs().iterator();

            while (it2.hasNext()) {
                pd = (Pdf) it2.next();
                try {
                    pd.descargaPDF();
                    pd.convertirPDF();
                } catch (IOException | SQLException ex) {
                    return false;
                }
            }
        }
        return true;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PANEL CLASIFICACION">

    @FXML
    void iniciaClasificacion(ActionEvent event) {
        mostrarPanel(2);
        iniciaTablaBoes();
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

    private void cargarBoes(Boe boe) {
        List bol = new ArrayList();
        boe.getBoletines();

        Iterator it;
        Iterator it2;
        Publicacion pb;
        Pdf pd;

        it = boe.getPb().iterator();

        while (it.hasNext()) {
            pb = (Publicacion) it.next();
            it2 = pb.getPdfs().iterator();

            while (it2.hasNext()) {
                pd = (Pdf) it2.next();
                bol.add(pd);
            }
        }
        cargarTablaBoes(bol);
    }

    private void cargarTablaBoes(List lista) {
        publicacion.clear();
        Pdf aux;
        ModeloBoes model;
        Iterator it = lista.iterator();

        while (it.hasNext()) {
            aux = (Pdf) it.next();
            model = new ModeloBoes();
            model.origen.set(aux.getOrigen());
            model.entidad.set(aux.getEntidad());
            model.fecha.set(Dates.imprimeFecha(aux.getFecha()));
            model.codigo.set(aux.getCodigo());
            model.descripcion.set(aux.getDescripcion());
            model.link.set(aux.getLink());

            publicacion.add(model);
        }
        updateClasificacion();
        getFocusTablaBoes();
    }

    private void getFocusTablaBoes() {
        tvBoes.getSelectionModel().select(0);
        tvBoes.scrollTo(0);
//        tvBoes.focusModelProperty().get().focus(new TablePosition(tvBoes, 0, null));
        tvBoes.requestFocus();
    }

    @FXML
    void cambioEnDatePicker() {
        publicacion.clear();
        selectedList.clear();
        discartedList.clear();
        Date aux = Dates.asDate(dpFechaC.getValue());
        cargarBoes(SqlBoe.cargaBoe(aux));
        Variables.isClasificando = true;
    }

    void updateClasificacion() {
        ModeloBoes aux;
        Iterator it = publicacion.iterator();

        while (it.hasNext()) {
            aux = (ModeloBoes) it.next();

            if (origen_descartado.contains(aux.getOrigen())) {
                discartedList.add(aux);
            }
        }
        
        it=discartedList.iterator();
        
        while(it.hasNext()){
            aux=(ModeloBoes) it.next();
            
            if(publicacion.contains(aux)){
                publicacion.remove(aux);
            }
        }
        getFocusTablaBoes();
    }

    @FXML
    void selectPdf() {
        ModeloBoes aux = tvBoes.getSelectionModel().getSelectedItem();

        if (aux != null) {
            selectedList.add(0, aux);
            publicacion.remove(aux);
            getFocusTablaBoes();
        }
    }

    @FXML
    void discardPdf() {
        ModeloBoes aux = tvBoes.getSelectionModel().getSelectedItem();

        if (aux != null) {
            discartedList.add(0, aux);
            publicacion.remove(aux);
            getFocusTablaBoes();
        }
    }

    @FXML
    void recoverS() {
        ModeloBoes aux = lvSelect.getSelectionModel().getSelectedItem();

        if (aux != null) {
            publicacion.add(0, aux);
            selectedList.remove(aux);
            getFocusTablaBoes();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar un elemento.");
            alert.showAndWait();
        }
    }

    @FXML
    void recoverD() {
        ModeloBoes aux = lvDiscard.getSelectionModel().getSelectedItem();

        if (aux != null) {
            publicacion.add(0, aux);
            discartedList.remove(aux);
            getFocusTablaBoes();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar un elemento.");
            alert.showAndWait();
        }
    }

    @FXML
    void descartaOrigen(ActionEvent event) {
        Sql bd;
        ModeloBoes aux = tvBoes.getSelectionModel().getSelectedItem();

        if (aux != null) {
            try {
                bd = new Sql(Variables.con);
                bd.ejecutar("INSERT into " + Variables.nombreBD + ".origen_descartado (nombre) values("
                        + Varios.entrecomillar(aux.getOrigen())
                        + ");");
                bd.close();
                origen_descartado.add(aux.getOrigen());
                updateClasificacion();
            } catch (SQLException ex) {
                Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar un elemento.");
            alert.showAndWait();
        }
    }

    void limpiarClasificacion() {
        publicacion.clear();
        discartedList.clear();
        selectedList.clear();
        dpFechaC.setValue(null);
    }

    @FXML
    void finalizaClas() {
        if (publicacion.isEmpty()) {
            Variables.isClasificando = false;
            Insercion in = new Insercion(this.selectedList, this.discartedList, Dates.asDate(dpFechaC.getValue()));
            in.run();
            limpiarClasificacion();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Todavía quedan Boletines sin clasificar");
            alert.showAndWait();
        }
    }

    @FXML
    void abrirWeb(ActionEvent event) {
        ModeloBoes aux = tvBoes.getSelectionModel().getSelectedItem();
        try {
            Desktop.getDesktop().browse(new URI(aux.getLink()));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="BOLETINES">
    @FXML
    void iniciaBoletines(ActionEvent event) {
        mostrarPanel(3);
        iniciaTablaBoletines();
    }

    void iniciaTablaBoletines() {
        codigoCLB.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        origenCLB.setCellValueFactory(new PropertyValueFactory<>("origen"));
        fechaCLB.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tipoCLB.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        estadoCLB.setCellValueFactory(new PropertyValueFactory<>("estado"));

        boletinesList = FXCollections.observableArrayList();
        tvBoletines.setItems(boletinesList);
    }

    void cargaDatosTablaBoletines(Date fecha) {
        ModeloBoletines aux;
        String query = "SELECT * FROM " + Variables.nombreBD + ".vista_boletines where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));
        Iterator it = SqlBoe.listaVistaBoletin(query).iterator();

        while (it.hasNext()) {
            aux = (ModeloBoletines) it.next();
            boletinesList.add(aux);
        }
    }

    @FXML
    void cambioEnDatePickerBoletines(ActionEvent event) {
        boletinesList.clear();
        Date aux = Dates.asDate(dpFechaB.getValue());
        cargaDatosTablaBoletines(aux);
    }

    @FXML
    void descargarBoletines(ActionEvent event) {

    }

    @FXML
    void generarArchivos(ActionEvent event) {
        Archivos ar = new Archivos(Dates.asDate(dpFechaB.getValue()));
        ar.creaArchivos();
    }

    @FXML
    void comprobarFases(ActionEvent event) {

    }

    @FXML
    void abrirCarpetaBoletines(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new File("data").toURI());
        } catch (IOException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FASES">
    @FXML
    void iniciaFases(ActionEvent event) {
        mostrarPanel(4);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="LISTENERS">
    /**
     * Listener de la lista multasAvg
     */
    private final ListChangeListener<Boe> selectorListaBoe
            = (ListChangeListener.Change<? extends Boe> c) -> {
                mostrarBoe();
            };
//</editor-fold>
}
