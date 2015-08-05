package main;

import boes.Boe;
import boes.Download;
import boes.Insercion;
import boes.Pdf;
import boes.Publicacion;
import boletines.Archivos;
import boletines.Fases;
import boletines.Limpieza;
import enty.Boletin;
import enty.Cabecera;
import enty.Descarga;
import enty.Fase;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.ModeloBoes;
import model.ModeloBoletines;
import model.ModeloCabeceras;
import model.ModeloComboBox;
import model.ModeloFases;
import util.Dates;
import util.Files;
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

    @FXML
    private Button btRecargarBoletines;

//</editor-fold>
    Sql bd;
    ObservableList<ModeloBoes> publicacion;
    ObservableList<Boe> boesList;
    ObservableList<ModeloBoes> selectedList;
    ObservableList<ModeloBoes> discartedList;
    ObservableList<ModeloBoletines> boletinesList;
    List origen_descartado;
    List texto_descartado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        origen_descartado = SqlBoe.listaOrigenDescartado();
        texto_descartado = SqlBoe.listaTextoDescartado();
        iniciaTablaBoes();

        final ObservableList<Boe> ls1 = lvBoe.getSelectionModel().getSelectedItems();
        ls1.addListener(selectorListaBoe);

        final ObservableList<ModeloComboBox> ls2 = lvOrigen.getSelectionModel().getSelectedItems();
        ls2.addListener(selectorListaOrigen);

        final ObservableList<ModeloFases> ls3 = tvFases.getSelectionModel().getSelectedItems();
        ls3.addListener(selectorTablaFases);

        final ObservableList<ModeloCabeceras> ls4 = tvCabeceras.getSelectionModel().getSelectedItems();
        ls4.addListener(selectorTablaCabeceras);

        final ObservableList<ModeloComboBox> ls5 = lvOrigenC.getSelectionModel().getSelectedItems();
        ls5.addListener(selectorListaOrigenC);
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
                panelCabeceras.setVisible(false);
                break;
            case 1:
                panelInicio.setVisible(false);
                panelEnlaces.setVisible(true);
                panelClasificacion.setVisible(false);
                panelBoletines.setVisible(false);
                panelFases.setVisible(false);
                panelCabeceras.setVisible(false);
                break;
            case 2:
                panelInicio.setVisible(false);
                panelEnlaces.setVisible(false);
                panelClasificacion.setVisible(true);
                panelBoletines.setVisible(false);
                panelFases.setVisible(false);
                panelCabeceras.setVisible(false);
                break;
            case 3:
                panelInicio.setVisible(false);
                panelEnlaces.setVisible(false);
                panelClasificacion.setVisible(false);
                panelBoletines.setVisible(true);
                panelFases.setVisible(false);
                panelCabeceras.setVisible(false);
                break;

            case 4:
                panelInicio.setVisible(false);
                panelEnlaces.setVisible(false);
                panelClasificacion.setVisible(false);
                panelBoletines.setVisible(false);
                panelFases.setVisible(true);
                panelCabeceras.setVisible(false);
                break;

            case 5:
                panelInicio.setVisible(false);
                panelEnlaces.setVisible(false);
                panelClasificacion.setVisible(false);
                panelBoletines.setVisible(false);
                panelFases.setVisible(false);
                panelCabeceras.setVisible(true);
                break;
        }
    }

    @FXML
    void descargaPendientes(ActionEvent event) {
//        Download dw = new Download();
//        dw.start();
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
    private boolean autoScroll;
    private boolean isInsercion;

    @FXML
    private Button btRecargarClasificacion;

    @FXML
    private CheckBox cbAutoScroll;

    @FXML
    private Label lbClasificacion;

    @FXML
    private ProgressBar pbClasificacion;

    @FXML
    private Button btVerWebC;

    @FXML
    private Button btSelectAll;

    @FXML
    void iniciaClasificacion(ActionEvent event) {
        autoScroll = true;
        cbAutoScroll.setSelected(autoScroll);
        mostrarPanel(2);
        setProcesandoC(false);
        limpiarClasificacion();
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

    private void cargarBoes(Boe boe) {
        origen_descartado = SqlBoe.listaOrigenDescartado();
        texto_descartado = SqlBoe.listaTextoDescartado();
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
    }

    private void getFocusTablaBoes() {
        if (autoScroll) {
            tvBoes.getSelectionModel().select(0);
            tvBoes.scrollTo(0);
            tvBoes.requestFocus();
        }
    }

    @FXML
    void cambioEnDatePicker() {
        publicacion.clear();
        selectedList.clear();
        discartedList.clear();

        Thread a = new Thread(() -> {

            Platform.runLater(() -> {
                rootPane.getScene().setCursor(Cursor.WAIT);
            });

            try {
                Date aux = Dates.asDate(dpFechaC.getValue());
                cargarBoes(SqlBoe.getBoe(aux));
                Variables.isClasificando = true;
            } catch (Exception ex) {
                //
            }

            Platform.runLater(() -> {
                rootPane.getScene().setCursor(Cursor.DEFAULT);
            });
        });
        a.start();
    }

    @FXML
    void cambioEnCheckBox(ActionEvent event) {
        autoScroll = cbAutoScroll.isSelected();
    }

    void updateClasificacion() {
        ModeloBoes aux;
        ObservableList<ModeloBoes> dList = FXCollections.observableArrayList();
        ObservableList<ModeloBoes> sList = FXCollections.observableArrayList();
        Iterator it = publicacion.iterator();

        try {
            bd = new Sql(Variables.con);
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (it.hasNext()) {
            aux = (ModeloBoes) it.next();

            if (origen_descartado.contains(aux.getOrigen()) || textoDescartado(aux.getDescripcion())) {
                dList.add(aux);
            }

            if (boletinProcesado(aux.getCodigo())) {
                sList.add(aux);
            }
        }

        try {
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        it = dList.iterator();

        while (it.hasNext()) {
            aux = (ModeloBoes) it.next();

            if (publicacion.contains(aux)) {
                publicacion.remove(aux);
            }
        }

        it = sList.iterator();

        while (it.hasNext()) {
            aux = (ModeloBoes) it.next();

            if (publicacion.contains(aux)) {
                publicacion.remove(aux);
            }
        }

        Platform.runLater(() -> {
            getFocusTablaBoes();
            discartedList.clear();
            discartedList.addAll(dList);
            selectedList.clear();
            selectedList.addAll(sList);
        });
    }

    boolean textoDescartado(String aux) {
        boolean a = false;
        String str;
        Iterator it = texto_descartado.iterator();

        while (it.hasNext()) {
            str = (String) it.next();

            if (aux.toUpperCase().contains(str.toUpperCase())) {
                a = true;
            }
        }
        return a;
    }

    boolean boletinProcesado(String aux) {
        int a;
        boolean is = false;

        try {
            a = bd.buscar("SELECT * FROM " + Variables.nombreBD + ".boletin where codigo=" + Varios.entrecomillar(aux));
            if (a > 0) {
                is = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return is;
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
    void selectAll(ActionEvent event) {
        ModeloBoes aux;
        Iterator it;

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("SELECCIONAR TODOS");
        alert.setHeaderText(null);
        alert.setContentText("¿Desea SELECCIONAR TODOS?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            it = publicacion.iterator();

            while (it.hasNext()) {
                aux = (ModeloBoes) it.next();
                selectedList.add(0, aux);
            }
            publicacion.clear();
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
        Sql bdd;
        ModeloBoes aux = tvBoes.getSelectionModel().getSelectedItem();

        if (aux != null) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("DESCARTAR ORIGEN");
            alert.setHeaderText(aux.getEntidad());
            alert.setContentText("¿Desea DESCARTAR el ORIGEN " + aux.getOrigen());

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                try {
                    bdd = new Sql(Variables.con);
                    bdd.ejecutar("INSERT into " + Variables.nombreBD + ".origen_descartado (nombre) values("
                            + Varios.entrecomillar(aux.getOrigen())
                            + ");");
                    bdd.close();
                    origen_descartado.add(aux.getOrigen());
                    updateClasificacion();
                } catch (SQLException ex) {
                    Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    private void setProcesandoC(boolean aux) {
        lbClasificacion.setVisible(aux);
        pbClasificacion.setVisible(aux);
        btFinClas.setDisable(aux);
        dpFecha.setDisable(aux);
        btSelect.setDisable(aux);
        btDiscard.setDisable(aux);
        btVerWebC.setDisable(aux);
        btDescartaOrigen.setDisable(aux);
        btRecargarClasificacion.setDisable(aux);
        btRecoverD.setDisable(aux);
        btRecoverS.setDisable(aux);
        btSelectAll.setDisable(aux);
    }

    @FXML
    void finalizaClas() {
        if (publicacion.isEmpty()) {
            Variables.isClasificando = false;
            insercion();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("ACEPTAR BOLETINES");
            alert.setHeaderText("Todavía quedan Boletines sin clasificar");
            alert.setContentText("¿Desea CONTINUAR?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Variables.isClasificando = false;
                insercion();
            }
        }
    }

    void insercion() {
        Thread a = new Thread(() -> {

            Platform.runLater(() -> {
                setProcesandoC(true);
                pbClasificacion.setProgress(-1);
                lbClasificacion.setText("LIMPIANDO DUPLICADOS");
            });

            ModeloBoes aux;
            Insercion in = new Insercion();
            List list = in.limpiarDuplicadosLista(this.selectedList);
            List listD = in.limpiarDuplicadosLista(this.discartedList);

            in.guardaStatsS(list);
            in.guardaStatsD(listD);

            Platform.runLater(() -> {
                pbClasificacion.setProgress(0);
                lbClasificacion.setText("INICIANDO CARGA DE BOLETINES");
            });

            for (int i = 0; i < list.size(); i++) {
                final int contador = i;
                final int total = list.size();

                Platform.runLater(() -> {
                    int contadour = contador + 1;
                    double counter = contador + 1;
                    double toutal = total;
                    lbClasificacion.setText("INSERTANDO BOLETÍN " + contadour + " de " + total);
                    pbClasificacion.setProgress(counter / toutal);
                });

                aux = (ModeloBoes) list.get(i);
                in.insertaBoletin(aux);
            }

            Platform.runLater(() -> {
                setProcesandoC(false);
                lbClasificacion.setText("INSERCIÓN FINALIZADA");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("COMPLETADO");
                alert.setHeaderText("INSERCIÓN FINALIZADA");
                alert.setContentText("SE HA FINALIZADO LA INSERCIÓN");
                alert.showAndWait();

                limpiarClasificacion();
            });
        });
        a.start();
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
    Label lbEstado;

    @FXML
    ProgressBar pbEstado;

    @FXML
    Button btEliminarBoletin;

    @FXML
    Button btVerBoletin;

    @FXML
    Button btVerBoletinWeb;

    @FXML
    Button btLimpiar;

    List boletinesIdioma;

    @FXML
    void iniciaBoletines(ActionEvent event) {
        boletinesIdioma = new ArrayList();
        mostrarPanel(3);
        iniciaTablaBoletines();

        btRecargarBoletines.setTooltip(new Tooltip("recarga boletines"));
    }

    void iniciaTablaBoletines() {
        codigoCLB.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        codigoCLB.setCellFactory(column -> {
            return new TableCell<ModeloBoletines, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);

                        if (boletinesIdioma.contains(item)) {
                            setTextFill(Color.BLACK);
                            setStyle("-fx-background-color: #999999");
                        } else {
                            setTextFill(Color.BLACK);
                            setStyle("");
                        }
                    }
                }
            };
        });
        origenCLB.setCellValueFactory(new PropertyValueFactory<>("origen"));
        fechaCLB.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tipoCLB.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        estadoCLB.setCellValueFactory(new PropertyValueFactory<>("estado"));

        boletinesList = FXCollections.observableArrayList();
        tvBoletines.setItems(boletinesList);
    }

    void cargaDatosTablaBoletines(Date fecha) {
        boletinesIdioma.clear();
        ModeloBoletines aux;
        String query = "SELECT * FROM " + Variables.nombreBD + ".vista_boletines where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));
        Iterator it = SqlBoe.listaVistaBoletin(query).iterator();

        while (it.hasNext()) {
            aux = (ModeloBoletines) it.next();
            boletinesList.add(aux);

            if (aux.idioma.get() == 1) {
                boletinesIdioma.add(aux.codigo.get());
            }
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
        Thread a = new Thread(() -> {

            Platform.runLater(() -> {
                btDescargaBoletines.setDisable(true);
                btComprobarFases.setDisable(true);
                btGenerarArchivos.setDisable(true);
                pbEstado.setVisible(true);
                pbEstado.setProgress(0);
                lbEstado.setText("DESCARGANDO BOLETINES");
            });

            Descarga aux;
            Download dw = new Download();
            List list = dw.getListado();

            for (int i = 0; i < list.size(); i++) {
                final int contador = i;
                final int total = list.size();
                Platform.runLater(() -> {
                    int contadour = contador + 1;
                    double counter = contador + 1;
                    double toutal = total;
                    lbEstado.setText("DESCARGANDO ARCHIVO " + contadour + " de " + total);
                    pbEstado.setProgress(counter / toutal);
                });
                aux = (Descarga) list.get(i);
                dw.descarga(aux);
            }

            Platform.runLater(() -> {
                lbEstado.setText("DESCARGA COMPLETADA");
                btDescargaBoletines.setDisable(false);
                btComprobarFases.setDisable(false);
                btGenerarArchivos.setDisable(false);
                pbEstado.setProgress(1);
                pbEstado.setVisible(false);
                lbEstado.setText("");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("COMPLETADO");
                alert.setHeaderText("DESCARGA FINALIZADA");
                alert.setContentText("SE HAN COMPLETADO LAS DESCARGAS");
                alert.showAndWait();

            });
        });
        a.start();
    }

    @FXML
    void generarArchivos(ActionEvent event) {
        Date fecha = Dates.asDate(dpFechaB.getValue());

        if (fecha != null) {
            Thread a = new Thread(() -> {

                Platform.runLater(() -> {
                    btDescargaBoletines.setDisable(true);
                    btComprobarFases.setDisable(true);
                    btGenerarArchivos.setDisable(true);
                    pbEstado.setVisible(true);
                    pbEstado.setProgress(0);
                    lbEstado.setText("DESCARGANDO BOLETINES");
                });

                ModeloBoletines aux;
                Archivos ar = new Archivos(fecha);
                List list = ar.getBoletines();

                for (int i = 0; i < list.size(); i++) {
                    final int contador = i;
                    final int total = list.size();
                    Platform.runLater(() -> {
                        int contadour = contador + 1;
                        double counter = contador + 1;
                        double toutal = total;
                        lbEstado.setText("GENERANDO ARCHIVO " + contadour + " de " + total);
                        pbEstado.setProgress(counter / toutal);
                    });
                    aux = (ModeloBoletines) list.get(i);
                    ar.creaArchivo(aux);
                }

                Platform.runLater(() -> {
                    lbEstado.setText("ARCHIVOS GENERADOS");
                    btDescargaBoletines.setDisable(false);
                    btComprobarFases.setDisable(false);
                    btGenerarArchivos.setDisable(false);
                    pbEstado.setProgress(1);
                    pbEstado.setVisible(false);
                    lbEstado.setText("");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("COMPLETADO");
                    alert.setHeaderText("ARCHIVOS GENERADOS");
                    alert.setContentText("SE HAN GENERADO TODOS LOS ARCHIVOS");
                    alert.showAndWait();

                });
            });
            a.start();
        }
    }

    @FXML
    void comprobarFases(ActionEvent event) {
        Date fecha = Dates.asDate(dpFechaB.getValue());

        if (fecha != null) {
            Thread a = new Thread(() -> {

                Platform.runLater(() -> {
                    btDescargaBoletines.setDisable(true);
                    btComprobarFases.setDisable(true);
                    btGenerarArchivos.setDisable(true);
                    pbEstado.setVisible(true);
                    pbEstado.setProgress(0);
                    lbEstado.setText("DESCARGANDO BOLETINES");
                });

                Boletin aux;
                Fases fs = new Fases(fecha);
                List list = fs.getBoletines();

                for (int i = 0; i < list.size(); i++) {
                    final int contador = i;
                    final int total = list.size();
                    Platform.runLater(() -> {
                        int contadour = contador + 1;
                        double counter = contador + 1;
                        double toutal = total;
                        lbEstado.setText("COMPROBANDO FASE " + contadour + " de " + total);
                        pbEstado.setProgress(counter / toutal);
                    });
                    aux = (Boletin) list.get(i);
                    fs.runFase(aux);
                }

                Platform.runLater(() -> {
                    lbEstado.setText("COMPROBACIÓN FINALIZADA");
                    btDescargaBoletines.setDisable(false);
                    btComprobarFases.setDisable(false);
                    btGenerarArchivos.setDisable(false);
                    pbEstado.setProgress(1);
                    pbEstado.setVisible(false);
                    lbEstado.setText("");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("COMPLETADO");
                    alert.setHeaderText("COMPROBACIÓN FINALIZADA");
                    alert.setContentText("SE HA FINALIZADO LA COMPROBACIÓN DE FASES");
                    alert.showAndWait();

                });
            });
            a.start();
        }
        recargarBoletines();
    }

    @FXML
    void limpiarBoletin(ActionEvent event) {
        ModeloBoletines aux = tvBoletines.getSelectionModel().getSelectedItem();

        if (aux != null) {
            Limpieza li = new Limpieza(aux.getCodigo());
            li.run();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("GUARDAR BOLETÍN");
            alert.setHeaderText(aux.getCodigo());
            alert.setContentText("¿Desea GUARDAR el boletín limpiado?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                guardarBoletinLimpiado(aux.getIdDescarga(), aux.codigo.get());
            }

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ERROR de selección.");
            alert.setContentText("Debes seleccionar un boletín.");

            alert.showAndWait();
        }
    }

    void guardarBoletinLimpiado(int id, String codigo) {
        Sql bd;
        File aux = new File("tempLp.txt");
        String datos = Files.leeArchivo(aux);
        datos = datos.replace("'", "\\'");

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar("UPDATE " + Variables.nombreBD + ".descarga SET datos=" + Varios.entrecomillar(datos) + " where id=" + id);
            bd.ejecutar("UPDATE " + Variables.nombreBD + ".boletin SET idioma=" + 0 + " where codigo=" + Varios.entrecomillar(codigo));
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void recargarBoletines(ActionEvent event) {
        recargarBoletines();
    }

    void recargarBoletines() {
        boletinesList.clear();
        Date aux = Dates.asDate(dpFechaB.getValue());

        if (aux != null) {
            cargaDatosTablaBoletines(aux);
        }
    }

    @FXML
    void abrirCarpetaBoletines(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new File(new File("data"), "boeData").toURI());
        } catch (IOException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void eliminarBoletin(ActionEvent event) {
        ModeloBoletines aux = tvBoletines.getSelectionModel().getSelectedItem();

        if (aux != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("BORRAR BOLETÍN");
            alert.setHeaderText(aux.getCodigo());
            alert.setContentText("¿Desea BORRAR el boletín seleccionado?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                SqlBoe.eliminaBoletin(aux.getCodigo());
                recargarBoletines();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ERROR de selección.");
            alert.setContentText("Debes seleccionar un boletín.");

            alert.showAndWait();
        }
    }

    @FXML
    void verBoletin(ActionEvent event) {
        Sql bd;
        File file = new File("boletinTemp.txt");
        ModeloBoletines aux = tvBoletines.getSelectionModel().getSelectedItem();
        String datos = "";

        try {
            bd = new Sql(Variables.con);
            datos = bd.getString("SELECT datos FROM boes.descarga WHERE id=" + aux.getIdDescarga());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        Files.escribeArchivo(file, datos);

        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void verBoletinWeb(ActionEvent event) {
        ModeloBoletines aux = tvBoletines.getSelectionModel().getSelectedItem();

        try {
            Desktop.getDesktop().browse(new URI(aux.getLink()));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FASES">
    //<editor-fold defaultstate="collapsed" desc="Variables FXML">
    @FXML
    ComboBox cbEntidad;

    @FXML
    ListView lvOrigen;

    @FXML
    TextField tfOrigen;

    @FXML
    TableView tvFases;

    @FXML
    ComboBox cbCodigo;

    @FXML
    ComboBox cbTipo;

    @FXML
    TextField tfDias;

    @FXML
    TextArea taTexto1;

    @FXML
    TextArea taTexto2;

    @FXML
    TextArea taTexto3;

    @FXML
    Button btNuevaFase;

    @FXML
    Button btEditarFase;

    @FXML
    Button btBorrarFase;

    @FXML
    Button btGuardarFase;

    @FXML
    TableColumn<ModeloFases, String> faseCLF;

    @FXML
    TableColumn<ModeloFases, String> codigoCLF;

    @FXML
    TableColumn<ModeloFases, String> tipoCLF;

    @FXML
    TableColumn<ModeloFases, String> diasCLF;

    //</editor-fold>
    ObservableList<ModeloComboBox> comboEntidades;
    ObservableList<ModeloComboBox> comboCodigo;
    ObservableList<ModeloComboBox> comboTipo;
    ObservableList<ModeloComboBox> listOrigenes;
    ObservableList<ModeloFases> tablaFases;

    //<editor-fold defaultstate="collapsed" desc="Metodos FXML">
    @FXML
    void iniciaFases(ActionEvent event) {
        mostrarPanel(4);
        inicializaDatosFases();
        inicializaTablaFases();
        btGuardarFase.setVisible(false);
        btBorrarFase.setDisable(true);
        btEditarFase.setDisable(true);
    }

    @FXML
    void nuevaFase(ActionEvent event) {
        btNuevaFase.setDisable(true);
        ModeloComboBox origen = (ModeloComboBox) lvOrigen.getSelectionModel().getSelectedItem();
        btGuardarFase.setVisible(true);
        ModeloFases aux = new ModeloFases();
        aux.id.set(0);
        aux.idOrigen.set(origen.getId());
        aux.codigo.set(null);
        aux.dias.set(0);
        aux.tipo.set(1);

        tablaFases.add(0, aux);
        btBorrarFase.setVisible(false);
        btEditarFase.setVisible(false);

        tvFases.getSelectionModel().select(0);
        tvFases.scrollTo(0);
        tvFases.requestFocus();
    }

    @FXML
    void editaFase(ActionEvent event) {
        Sql bd;
        Fase aux = getDatosFase();

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(aux.SQLEditar());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        btGuardarFase.setVisible(false);
        btBorrarFase.setVisible(true);
        btEditarFase.setVisible(true);
        btNuevaFase.setDisable(false);

        limpiarFases();
        cargaFasesOrigen();
    }

    @FXML
    void borraFase(ActionEvent event) {
        Sql bd;
        Fase aux = getDatosFase();

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(aux.SQLBorrar());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        btGuardarFase.setVisible(false);
        btBorrarFase.setVisible(true);
        btEditarFase.setVisible(true);
        btNuevaFase.setDisable(false);

        limpiarFases();
        cargaFasesOrigen();
    }

    @FXML
    void guardaFase(ActionEvent event) {
        Sql bd;
        Fase aux = getDatosFase();

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(aux.SQLCrear());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        btGuardarFase.setVisible(false);
        btBorrarFase.setVisible(true);
        btEditarFase.setVisible(true);
        btNuevaFase.setDisable(false);

        limpiarFases();
        cargaFasesOrigen();
    }

    @FXML
    void cargaOrigenFase(ActionEvent event) {
        limpiarFases();
        listOrigenes.clear();
        ModeloComboBox aux = (ModeloComboBox) cbEntidad.getSelectionModel().getSelectedItem();
        Iterator it = SqlBoe.listaOrigenes(aux.getId()).iterator();

        while (it.hasNext()) {
            aux = (ModeloComboBox) it.next();
            listOrigenes.add(aux);
        }
        lvOrigen.setItems(listOrigenes);
        lvOrigen.setVisible(false);
        lvOrigen.setVisible(true);
    }
//</editor-fold>

    private void inicializaDatosFases() {
        comboEntidades = FXCollections.observableArrayList();
        cbEntidad.setItems(comboEntidades);
        comboCodigo = FXCollections.observableArrayList();
        cbCodigo.setItems(comboCodigo);
        comboTipo = FXCollections.observableArrayList();
        cbTipo.setItems(comboTipo);
        listOrigenes = FXCollections.observableArrayList();
        lvOrigen.setItems(listOrigenes);

        lvOrigen.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                final ListCell cell = new ListCell() {
                    private Text text;

                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item.toString());
                            text.setWrappingWidth(lvOrigen.getPrefWidth() - 30);
                            setGraphic(text);
                        } else {
                            text = new Text("");
                            setGraphic(text);
                        }
                    }
                };

                return cell;
            }
        });

        ModeloComboBox aux;
        Iterator it = SqlBoe.listaEntidades().iterator();

        while (it.hasNext()) {
            aux = (ModeloComboBox) it.next();
            comboEntidades.add(aux);
        }

        it = SqlBoe.listaTipo().iterator();

        while (it.hasNext()) {
            aux = (ModeloComboBox) it.next();
            comboCodigo.add(aux);
        }

        aux = new ModeloComboBox();
        aux.id.set(1);
        aux.nombre.set("ND");
        comboTipo.add(aux);
        aux = new ModeloComboBox();
        aux.id.set(2);
        aux.nombre.set("RS");
        comboTipo.add(aux);
        aux = new ModeloComboBox();
        aux.id.set(3);
        aux.nombre.set("RR");
        comboTipo.add(aux);
    }

    private void inicializaTablaFases() {
        faseCLF.setCellValueFactory(new PropertyValueFactory<>("fase"));
        codigoCLF.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tipoCLF.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        diasCLF.setCellValueFactory(new PropertyValueFactory<>("dias"));

        tablaFases = FXCollections.observableArrayList();
        tvFases.setItems(tablaFases);
    }

    void cargaFasesOrigen() {
        tablaFases.clear();
        ModeloFases mf;
        ModeloComboBox aux = (ModeloComboBox) lvOrigen.getSelectionModel().getSelectedItem();
        tfOrigen.setText(aux.getNombre());
        Iterator it = SqlBoe.listaFases(aux.getId()).iterator();

        while (it.hasNext()) {
            mf = (ModeloFases) it.next();
            tablaFases.add(mf);
        }

        btBorrarFase.setDisable(true);
        btEditarFase.setDisable(true);
    }

    void cargaDatosFase() {
        ModeloFases aux = (ModeloFases) tvFases.getSelectionModel().getSelectedItem();

        if (aux != null) {
            ModeloComboBox cb = new ModeloComboBox();
            cb.id.set(1);
            cb.nombre.set(aux.codigo.get());
            cbCodigo.getSelectionModel().select(cb);
            cbTipo.getSelectionModel().select(aux.getIdTipo() - 1);
            tfDias.setText(Integer.toString(aux.getDias()));
            taTexto1.setText(aux.getTexto1());
            taTexto2.setText(aux.getTexto2());
            taTexto3.setText(aux.getTexto3());
        }

        btBorrarFase.setDisable(false);
        btEditarFase.setDisable(false);
    }

    void limpiarFases() {
        cbCodigo.getSelectionModel().select(null);
        cbTipo.getSelectionModel().select(null);
        tfDias.setText(null);
        taTexto1.setText(null);
        taTexto2.setText(null);
        taTexto3.setText(null);
        tfOrigen.setText(null);
    }

    Fase getDatosFase() {
        ModeloFases aux = new ModeloFases();
        ModeloFases mf;
        ModeloComboBox mc;

        mf = (ModeloFases) tvFases.getSelectionModel().getSelectedItem();
        aux.id.set(mf.getId());
        aux.idOrigen.set(mf.getIdOrigen());
        mc = (ModeloComboBox) cbCodigo.getSelectionModel().getSelectedItem();
        aux.codigo.set(mc.getNombre());
        mc = (ModeloComboBox) cbTipo.getSelectionModel().getSelectedItem();
        aux.tipo.set(mc.getId());
        aux.texto1.set(taTexto1.getText().trim());
        aux.texto2.set(taTexto2.getText().trim());
        aux.texto3.set(taTexto3.getText().trim());
        aux.dias.set(Integer.parseInt(tfDias.getText()));

        Fase fase = new Fase();
        fase.setId(aux.getId());
        fase.setCodigo(aux.getCodigo());
        fase.setIdOrigen(aux.getIdOrigen());
        fase.setTipo(aux.getIdTipo());
        fase.setTexto1(aux.getTexto1());
        fase.setTexto2(aux.getTexto2());
        fase.setTexto3(aux.getTexto3());
        fase.setDias(aux.getDias());

        return fase;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CABECERAS">
    //<editor-fold defaultstate="collapsed" desc="Variables FXML">
    @FXML
    private AnchorPane panelCabeceras;

    @FXML
    private ComboBox cbEntidadC;

    @FXML
    private ListView lvOrigenC;

    @FXML
    private TextField tfOrigenC;

    @FXML
    private Button btNuevaCabecera;

    @FXML
    private TableView<ModeloCabeceras> tvCabeceras;

    @FXML
    private TableColumn<ModeloCabeceras, String> idCLC;

    @FXML
    private TableColumn<ModeloCabeceras, String> cabeceraCLC;

    @FXML
    private TextArea taCabecera;

    @FXML
    private Button btEditaCabecera;

    @FXML
    private Button btBorraCabecera;

    @FXML
    private Button btGuardaCabecera;
    //</editor-fold>

    ObservableList<ModeloComboBox> comboEntidadesC;
    ObservableList<ModeloComboBox> listOrigenesC;
    ObservableList<ModeloCabeceras> tablaCabeceras;

    //<editor-fold defaultstate="collapsed" desc="Métodos FXML">
    @FXML
    void iniciaCabeceras(ActionEvent event) {
        mostrarPanel(5);
        inicializaDatosCabeceras();
        inicializaTablaCabeceras();
        btGuardaCabecera.setVisible(false);
        btBorraCabecera.setDisable(true);
        btEditaCabecera.setDisable(true);
    }

    @FXML
    void cargaOrigenCabecera(ActionEvent event) {
        limpiaCabecera();
        listOrigenesC.clear();
        ModeloComboBox aux = (ModeloComboBox) cbEntidadC.getSelectionModel().getSelectedItem();
        Iterator it = SqlBoe.listaOrigenes(aux.getId()).iterator();

        while (it.hasNext()) {
            aux = (ModeloComboBox) it.next();
            listOrigenesC.add(aux);
        }
        lvOrigenC.setItems(listOrigenesC);
        lvOrigenC.setVisible(false);
        lvOrigenC.setVisible(true);
    }

    @FXML
    void nuevaCabecera(ActionEvent event) {
        btNuevaCabecera.setDisable(true);
        ModeloComboBox origen = (ModeloComboBox) lvOrigenC.getSelectionModel().getSelectedItem();
        btGuardaCabecera.setVisible(true);
        ModeloCabeceras aux = new ModeloCabeceras();
        aux.id.set(0);
        aux.idOrigen.set(origen.getId());
        aux.cabecera.set(null);
        aux.tipo.set(1);

        tablaCabeceras.add(0, aux);
        btBorraCabecera.setVisible(false);
        btEditaCabecera.setVisible(false);
        taCabecera.setText("");

        tvCabeceras.getSelectionModel().select(0);
        tvCabeceras.scrollTo(0);
        tvCabeceras.requestFocus();
    }

    @FXML
    void editaCabecera(ActionEvent event) {
        Sql bd;
        Cabecera aux = getDatosCabecera();

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(aux.SQLEditar());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        btGuardaCabecera.setVisible(false);
        btBorraCabecera.setVisible(true);
        btEditaCabecera.setVisible(true);
        btNuevaCabecera.setDisable(false);

        limpiaCabecera();
        cargaCabecerasOrigen();
    }

    @FXML
    void borraCabecera(ActionEvent event) {
        Sql bd;
        Cabecera aux = getDatosCabecera();

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(aux.SQLBorrar());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        btGuardaCabecera.setVisible(false);
        btBorraCabecera.setVisible(true);
        btEditaCabecera.setVisible(true);
        btNuevaCabecera.setDisable(false);

        limpiaCabecera();
        cargaCabecerasOrigen();
    }

    @FXML
    void guardaCabecera(ActionEvent event) {
        Sql bd;
        Cabecera aux = getDatosCabecera();

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(aux.SQLCrear());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }

        btGuardaCabecera.setVisible(false);
        btBorraCabecera.setVisible(true);
        btEditaCabecera.setVisible(true);
        btNuevaCabecera.setDisable(false);

        limpiaCabecera();
        cargaCabecerasOrigen();
    }
    //</editor-fold>

    private void limpiaCabecera() {
        taCabecera.setText(null);
        tfOrigenC.setText(null);
    }

    private void inicializaDatosCabeceras() {
        comboEntidadesC = FXCollections.observableArrayList();
        cbEntidadC.setItems(comboEntidadesC);
        listOrigenesC = FXCollections.observableArrayList();
        lvOrigenC.setItems(listOrigenesC);

        lvOrigenC.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                final ListCell cell = new ListCell() {
                    private Text text;

                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item.toString());
                            text.setWrappingWidth(lvOrigenC.getPrefWidth() - 30);
                            setGraphic(text);
                        } else {
                            text = new Text("");
                            setGraphic(text);
                        }
                    }
                };

                return cell;
            }
        });

        ModeloComboBox aux;
        Iterator it = SqlBoe.listaEntidades().iterator();

        while (it.hasNext()) {
            aux = (ModeloComboBox) it.next();
            comboEntidadesC.add(aux);
        }
    }

    private void inicializaTablaCabeceras() {
        idCLC.setCellValueFactory(new PropertyValueFactory<>("id"));
        cabeceraCLC.setCellValueFactory(new PropertyValueFactory<>("cabecera"));

        tablaCabeceras = FXCollections.observableArrayList();
        tvCabeceras.setItems(tablaCabeceras);
    }

    private void cargaCabecerasOrigen() {
        limpiaCabecera();
        tablaCabeceras.clear();
        ModeloCabeceras mf;
        ModeloComboBox aux = (ModeloComboBox) lvOrigenC.getSelectionModel().getSelectedItem();
        tfOrigenC.setText(aux.getNombre());
        Iterator it = SqlBoe.listaCabeceras(aux.getId()).iterator();

        while (it.hasNext()) {
            mf = (ModeloCabeceras) it.next();
            tablaCabeceras.add(mf);
        }

        btBorraCabecera.setDisable(true);
        btEditaCabecera.setDisable(true);
    }

    private void cargaDatosCabecera() {
        ModeloCabeceras aux = (ModeloCabeceras) tvCabeceras.getSelectionModel().getSelectedItem();

        if (aux != null) {
            taCabecera.setText(aux.getCabecera());
        }

        btBorraCabecera.setDisable(false);
        btEditaCabecera.setDisable(false);
    }

    private Cabecera getDatosCabecera() {
        ModeloCabeceras mc = tvCabeceras.getSelectionModel().getSelectedItem();
        Cabecera aux = new Cabecera();

        aux.setId(mc.getId());
        aux.setIdOrigen(mc.getIdOrigen());
        aux.setCabecera(taCabecera.getText());
        aux.setTipo(mc.getTipo());

        return aux;
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

    /**
     * Listener de la lista OrigenFase
     */
    private final ListChangeListener<ModeloComboBox> selectorListaOrigen
            = (ListChangeListener.Change<? extends ModeloComboBox> c) -> {
                cargaFasesOrigen();
            };

    /**
     * Listener de la Tabla Fases
     */
    private final ListChangeListener<ModeloFases> selectorTablaFases
            = (ListChangeListener.Change<? extends ModeloFases> c) -> {
                cargaDatosFase();
            };

    /**
     * Listener de la lista OrigenFase
     */
    private final ListChangeListener<ModeloComboBox> selectorListaOrigenC
            = (ListChangeListener.Change<? extends ModeloComboBox> c) -> {
                cargaCabecerasOrigen();
            };

    /**
     * Listener de la Tabla Cabeceras
     */
    private final ListChangeListener<ModeloCabeceras> selectorTablaCabeceras
            = (ListChangeListener.Change<? extends ModeloCabeceras> c) -> {
                cargaDatosCabecera();
            };
//</editor-fold>
}
