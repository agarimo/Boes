package main;

import boes.Boe;
import boes.Pdf;
import boes.Publicacion;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
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
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.ModeloBoes;
import util.Dates;
import util.Sql;

/**
 *
 * @author Agarimo
 */
public class WinC implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML Var">
    @FXML
    private TableColumn<ModeloBoes, String> origenCL;

    @FXML
    private AnchorPane panelEnlaces;

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
    private Button btClasificar;

    @FXML
    private Button btDescargaTodos;

    @FXML
    private Button btAddBoe;

    @FXML
    private ListView<Pdf> lvSeleccionados;

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
//</editor-fold>

    ObservableList<ModeloBoes> publicacion;
    ObservableList<Boe> boesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iniciaTablaBoes();

        final ObservableList<Boe> aux1 = lvBoe.getSelectionModel().getSelectedItems();
        aux1.addListener(selectorListaBoe);
    }

    //<editor-fold defaultstate="collapsed" desc="GENERAL">
    private void mostrarPanel(int panel) {

        switch (panel) {
            case 1:
                panelEnlaces.setVisible(true);
                panelClasificacion.setVisible(false);
                break;
            case 2:
                panelEnlaces.setVisible(false);
                panelClasificacion.setVisible(true);
                break;
        }
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
        boesList.addAll(SqlBoe.listaMultas("SELECT * FROM boes.boe"));
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
        mostrarPanel(2);
        cargarBoes((Boe) lvBoe.getSelectionModel().getSelectedItem());
        
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
            model.codigo.set(aux.getCodigo());
            model.descripcion.set(aux.getDescripcion());
            model.link.set(aux.getLink());

            publicacion.add(model);
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
