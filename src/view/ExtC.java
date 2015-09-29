package view;

import boe.Download;
import enty.Multa;
import enty.Procesar;
import extraccion.Extraccion;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.Boes;
import main.ControlledScreen;
import main.ScreensController;
import main.SqlBoe;
import main.Variables;
import model.ModeloPreview;
import model.ModeloProcesar;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class ExtC implements Initializable, ControlledScreen {

    ScreensController myController;
    int PanelPreview = 1;
    int PanelProcesar = 2;
    boolean isPreview;
    List<Integer> listaEstructurasCreadas;

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

    @FXML
    private ProgressIndicator piProgreso;

    @FXML
    private Label lbProceso;

    @FXML
    private Label lbProgreso;

    @FXML
    private Button btRefrescar;

    ObservableList<ModeloProcesar> procesarList;
    ObservableList<ModeloPreview> previewList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isPreview = false;
        panelPreview.setOpacity(0.0);
        panelPreview.setVisible(false);
        panelProcesar.setOpacity(1.0);
        panelProcesar.setVisible(true);
        iniciarTablaProcesar();
        iniciarTablaPreview();

        final ObservableList<ModeloProcesar> ls1 = tvProcesar.getSelectionModel().getSelectedItems();
        ls1.addListener(selectorTablaProcesar);

        final ObservableList<ModeloPreview> ls2 = tvPreview.getSelectionModel().getSelectedItems();
        ls2.addListener(selectorTablaPreview);
    }

    private void clearWindow() {
        dpFecha.setValue(null);
        procesarList.clear();
        previewList.clear();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    public void cargaMain(ActionEvent event) {
        clearWindow();
        myController.setScreen(Boes.screen1ID);
    }

    private void mostrarPanel(int panel) {
        FadeTransition fade;

        switch (panel) {
            case 1:
                fade = new FadeTransition(Duration.millis(1000), panelProcesar);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setAutoReverse(false);
                fade.play();
                panelProcesar.setVisible(false);

                panelPreview.setVisible(true);
                fade = new FadeTransition(Duration.millis(1000), panelPreview);
                fade.setFromValue(0.0);
                fade.setToValue(1.0);
                fade.setAutoReverse(false);
                fade.play();

                break;

            case 2:
                fade = new FadeTransition(Duration.millis(1000), panelPreview);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setAutoReverse(false);
                fade.play();
                panelPreview.setVisible(false);

                panelProcesar.setVisible(true);
                fade = new FadeTransition(Duration.millis(1000), panelProcesar);
                fade.setFromValue(0.0);
                fade.setToValue(1.0);
                fade.setAutoReverse(false);
                fade.play();

                break;
        }
    }

    void switchControles(boolean aux) {
        dpFecha.setDisable(aux);
        btGenerarPdf.setDisable(aux);
        btVerPdf.setDisable(aux);
        btAbrirCarpeta.setDisable(aux);
        btProcesar.setDisable(aux);
    }

    void showPreview() {
        isPreview = !isPreview;

        if (isPreview) {
            btPreview.setText("Volver");
            mostrarPanel(this.PanelPreview);
        } else {
            btPreview.setText("Previsualizar Extracción");
            mostrarPanel(this.PanelProcesar);
        }
        switchControles(isPreview);
    }

    private void iniciarTablaProcesar() {
        clCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        clCodigo.setCellFactory(column -> {
            return new TableCell<ModeloProcesar, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setAlignment(Pos.CENTER);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setTextFill(Color.BLACK);
                    }
                }
            };
        });
        clEstructura.setCellValueFactory(new PropertyValueFactory<>("estructura"));
        clEstructura.setCellFactory(column -> {
            return new TableCell<ModeloProcesar, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setAlignment(Pos.CENTER);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("");

                        switch (item) {
                            case -1:
                                setText("Sin estructura");
                                setTextFill(Color.RED);
                                break;
                            default:
                                if (listaEstructurasCreadas.contains(item)) {
                                    setText("OK : " + Integer.toString(item));
                                    setTextFill(Color.GREEN);
                                } else {
                                    setText("Estructura no creada : " + Integer.toString(item));
                                    setTextFill(Color.RED);
                                }

                                break;
                        }
                    }
                }
            };
        });
        clEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        clEstado.setCellFactory(column -> {
            return new TableCell<ModeloProcesar, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setAlignment(Pos.CENTER);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {

                        switch (item) {
                            case 0:
                                setText("Sin procesar");
                                setTextFill(Color.BLACK);
                                break;

                            case 1:
                                setText("Listo para procesar");
                                setTextFill(Color.ORCHID);
                                break;

                            case 2:
                                setText("Procesado XLSX");
                                setTextFill(Color.GREEN);
                                break;

                            case 3:
                                setText("Error al procesar");
                                setTextFill(Color.RED);
                                break;

                            case 4:
                                setText("PDF no generado");
                                setTextFill(Color.ORANGERED);
                                break;

                            case 5:
                                setText("XLSX no generado");
                                setTextFill(Color.ORANGE);
                                break;
                        }
                    }
                }
            };
        });

        procesarList = FXCollections.observableArrayList();
        tvProcesar.setItems(procesarList);
    }

    void cargarDatosProcesar(List<Procesar> list) {
        listaEstructurasCreadas = SqlBoe.listaEstructurasCreadas();
        procesarList.clear();
        ModeloProcesar modelo;
        Procesar procesar;
        Iterator<Procesar> it = list.iterator();

        while (it.hasNext()) {
            procesar = it.next();

            modelo = new ModeloProcesar();
            modelo.id.set(procesar.getId());
            modelo.codigo.set(procesar.getCodigo());
            modelo.estructura.set(procesar.getEstructura());
            modelo.estado.set(procesar.getEstado());
            modelo.link.set(procesar.getLink());
            modelo.fecha.set(Dates.imprimeFecha(procesar.getFecha()));

            procesarList.add(modelo);
        }
    }

    private void iniciarTablaPreview() {
        clExpediente.setCellValueFactory(new PropertyValueFactory<>("expediente"));
        clSancionado.setCellValueFactory(new PropertyValueFactory<>("sancionado"));
        clNif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        clLocalidad.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        clFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        clMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        clCuantia.setCellValueFactory(new PropertyValueFactory<>("cuantia"));
        clArticulo.setCellValueFactory(new PropertyValueFactory<>("articulo"));
        clPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));
        clReqObs.setCellValueFactory(new PropertyValueFactory<>("reqObs"));

        previewList = FXCollections.observableArrayList();
        tvPreview.setItems(previewList);
    }

    void cargarDatosPreview(List<Multa> list) {
        previewList.clear();
        ModeloPreview modelo;
        Multa multa;
        Iterator<Multa> it = list.iterator();

        while (it.hasNext()) {
            multa = it.next();

            modelo = new ModeloPreview();
            modelo.expediente.set(multa.getExpediente());
            modelo.sancionado.set(multa.getSancionado());
            modelo.nif.set(multa.getNif());
            modelo.localidad.set(multa.getLocalidad());
            modelo.fecha.set(Dates.imprimeFecha(multa.getFechaMulta()));
            modelo.matricula.set(multa.getMatricula());
            modelo.cuantia.set(multa.getCuantia());
            modelo.articulo.set(multa.getArticulo());
            modelo.puntos.set(multa.getPuntos());
            modelo.reqObs.set(multa.getReqObs());

            previewList.add(modelo);
        }
    }

    @FXML
    void cambioEnDatePicker(ActionEvent event) {
        try {
            Date fecha = Dates.asDate(dpFecha.getValue());

            if (fecha != null) {
                String query = "SELECT * FROM boes.procesar where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));
                cargarDatosProcesar(SqlBoe.listaProcesar(query));
            }
        } catch (NullPointerException ex) {
        }
    }

    @FXML
    void generarPdf() {
        Date fecha = Dates.asDate(dpFecha.getValue());

        if (fecha != null) {
            String query = "SELECT * FROM " + Variables.nombreBD + ".procesar where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));
            File fichero = new File(Variables.ficheroEx, Dates.imprimeFecha(fecha));
            fichero.mkdirs();

            Thread a = new Thread(() -> {

                Platform.runLater(() -> {
                    btGenerarPdf.setDisable(true);
                    piProgreso.setVisible(true);
                    piProgreso.setProgress(0);
                    lbProgreso.setVisible(true);
                    lbProgreso.setText("");
                    lbProceso.setVisible(true);
                    lbProceso.setText("GENERANDO PDFs");
                });

                File destino;
                Procesar aux;
                List list = SqlBoe.listaProcesar(query);

                for (int i = 0; i < list.size(); i++) {
                    final int contador = i;
                    final int total = list.size();
                    Platform.runLater(() -> {
                        int contadour = contador + 1;
                        double counter = contador + 1;
                        double toutal = total;
                        lbProgreso.setText("DESCARGANDO " + contadour + " de " + total);
                        piProgreso.setProgress(counter / toutal);
                    });
                    aux = (Procesar) list.get(i);
                    destino = new File(fichero, aux.getCodigo() + ".pdf");
                    Download.descargaPDF(aux.getLink(), destino);
                    aux.SQLSetEstado(1);
                }

                Platform.runLater(() -> {
                    piProgreso.setProgress(1);
                    piProgreso.setVisible(false);
                    lbProgreso.setText("");
                    lbProgreso.setVisible(false);
                    lbProceso.setText("");
                    lbProceso.setVisible(false);
                    btGenerarPdf.setDisable(false);

                    cambioEnDatePicker(new ActionEvent());
                });
            });
            a.start();
        }
    }

    @FXML
    void verPdf() {
        ModeloProcesar pr = (ModeloProcesar) tvProcesar.getSelectionModel().getSelectedItem();

        if (pr != null) {
            try {
                Desktop.getDesktop().browse(new URI(pr.link.get()));
            } catch (IOException ex) {
                Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(ExtC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void abrirCarpeta(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());

        if (fecha != null) {
            File fichero = new File(Variables.ficheroEx, Dates.imprimeFecha(fecha));
            try {
                Desktop.getDesktop().browse(fichero.toURI());
            } catch (IOException ex) {
                Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void previsualizar(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());
        ModeloProcesar aux = (ModeloProcesar) tvProcesar.getSelectionModel().getSelectedItem();
        Extraccion ex;

        if (isPreview) {
            showPreview();
        } else {
            if (fecha != null || aux != null) {
                ex = new Extraccion(fecha);
                if (ex.fileExist(aux.getCodigo())) {
                    if (listaEstructurasCreadas.contains(aux.getEstructura())) {
                        cargarDatosPreview(ex.previewXLSX(SqlBoe.getProcesar(aux.getCodigo())));
                        showPreview();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("STRUCDATA NO CREADO");
                        alert.setContentText("Debe crear el STRUCDATA para previsualizar el boletín");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("FICHERO NO ENCONTRADO");
                    alert.setContentText("Debe generar el fichero .xlsx para previsualizar el contenido.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("ERROR EN SELECCIÓN");
                alert.setContentText("Debe seleccionar un boletín para su visualización");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    void procesar(){
        ModeloProcesar modelo;
        Iterator<ModeloProcesar> it = procesarList.iterator();
        
        while(it.hasNext()){
            modelo= it.next();
            
            if(modelo.getEstado()==1 && listaEstructurasCreadas.contains(modelo.getEstructura())){
                System.out.println(modelo.getCodigo());
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="LISTENER">
    /**
     * Listener de la Tabla Procesar
     */
    private final ListChangeListener<ModeloProcesar> selectorTablaProcesar
            = (ListChangeListener.Change<? extends ModeloProcesar> c) -> {
//                cargaDatosFase();
            };

    /**
     * Listener de la Tabla Preview
     */
    private final ListChangeListener<ModeloPreview> selectorTablaPreview
            = (ListChangeListener.Change<? extends ModeloPreview> c) -> {
//                cargaDatosFase();
            };
//</editor-fold>
}
