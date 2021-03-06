package view;

import boe.Download;
import enty.Estado;
import enty.Multa;
import enty.Procesar;
import extraccion.BB0;
import extraccion.BB1;
import extraccion.Extraccion;
import extraccion.ReqObs;
import extraccion.ScriptArticulo;
import extraccion.ScriptExp;
import extraccion.ScriptFase;
import extraccion.ScriptOrigen;
import extraccion.ScriptReq;
import extraccion.XLSXProcess;
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
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import main.Var;
import model.ModeloPreview;
import model.ModeloProcesar;
import util.Dates;
import util.Sql;
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
    List<Integer> listaEstructurasManual;
    private final int procesar_to_preview = 1;
    private final int preview_to_procesar = 2;
    private final int procesar_to_wait = 3;
    private final int wait_to_preview = 4;
    private final int wait_to_procesar = 5;
    private final int preview_to_wait = 6;
    //<editor-fold defaultstate="collapsed" desc="FXML VAR">
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane panelProcesar;
    @FXML
    private AnchorPane panelPreview;
    @FXML
    private AnchorPane panelEspera;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private Button btPreview;
    @FXML
    private Button btGenerarPdf;
    @FXML
    private Button btReqObs;
    @FXML
    private Button btAbrirCarpeta;
    @FXML
    private Button btAbrirCarpetaAr;
    @FXML
    private Button btProcesar;
    @FXML
    private Button btForzarProcesar;
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
    private Button btGenerarArchivos;
    @FXML
    private Label lbMultasPreview;
//</editor-fold>
    ObservableList<ModeloProcesar> procesarList;
    ObservableList<ModeloPreview> previewList;

    @FXML
    void abrirCarpeta(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());

        if (fecha != null) {
            File fichero = new File(Var.ficheroEx, Dates.imprimeFecha(fecha));
            try {
                Desktop.getDesktop().browse(fichero.toURI());
            } catch (IOException ex) {
//                Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void abrirCarpetaAr(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());

        if (fecha != null) {
            File fichero = new File(Var.ficheroTxt, Dates.imprimeFecha(fecha));
            try {
                Desktop.getDesktop().browse(fichero.toURI());
            } catch (IOException ex) {
//                Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            //
        }
    }

    @FXML
    public void cargaMain(ActionEvent event) {
        clearWindow();
        myController.setScreen(Boes.screen1ID);
    }

    void cargarDatosPreview(List<Multa> list) {
        previewList.clear();
        ModeloPreview modelo;
        Multa multa;
        Iterator<Multa> it = list.iterator();

        while (it.hasNext()) {
            multa = it.next();

            if (!multa.equals(new Multa())) {
                modelo = new ModeloPreview();
                modelo.setMulta(multa);
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
        lbMultasPreview.setText(Integer.toString(previewList.size()));
    }

    void cargarDatosProcesar(List<Procesar> list) {
        listaEstructurasCreadas = SqlBoe.listaEstructurasCreadas();
        listaEstructurasManual = SqlBoe.listaEstructurasManual();
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
            if (listaEstructurasManual.contains(procesar.getEstructura()) && procesar.getEstado() != Estado.PROCESADO_XLSX) {
                modelo.estado.set(Estado.PROCESAR_MANUAL.getValue());
            } else {
                modelo.estado.set(procesar.getEstado().getValue());
            }
            modelo.link.set(procesar.getLink());
            modelo.fecha.set(Dates.imprimeFecha(procesar.getFecha()));

            procesarList.add(modelo);
        }
    }

    private void clearWindow() {
        dpFecha.setValue(null);
        procesarList.clear();
        previewList.clear();
    }

    @FXML
    void forzarProcesado(ActionEvent event) {
        List<Multa> list = new ArrayList();
        ModeloPreview mp;
        Iterator<ModeloPreview> it = previewList.iterator();

        while (it.hasNext()) {
            mp = it.next();

            if (!mp.getMulta().equals(new Multa())) {
                list.add(mp.getMulta());
            }
        }

        Thread a = new Thread(() -> {

            Platform.runLater(() -> {
                rootPane.getScene().setCursor(Cursor.WAIT);
                btForzarProcesar.setDisable(true);
                btForzarProcesar.setText("PROCESANDO");
                btPreview.setDisable(true);
            });

            Procesar pr;
            ModeloProcesar aux = (ModeloProcesar) tvProcesar.getSelectionModel().getSelectedItem();
            pr = SqlBoe.getProcesar(aux.getCodigo());

            try {
                XLSXProcess.insertMultas(list);
                pr.SQLSetEstado(Estado.PROCESADO_XLSX.getValue());
            } catch (Exception e) {
                pr.SQLSetEstado(Estado.ERROR_PROCESAR.getValue());
            }

            Platform.runLater(() -> {
                rootPane.getScene().setCursor(Cursor.DEFAULT);
                btForzarProcesar.setDisable(false);
                btForzarProcesar.setText("Procesar");
                btPreview.setDisable(false);
                btPreview.setText("Previsualizar Extracción");
                mostrarPanel(this.preview_to_procesar);
                isPreview = !isPreview;
                switchControles(false);
                cambioEnDatePicker(new ActionEvent());
            });
        });
        a.start();
    }

    @FXML
    void generarArchivos(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());

        if (fecha != null) {
            Thread a = new Thread(() -> {

                Platform.runLater(() -> {
                    mostrarPanel(this.procesar_to_wait);
                    btGenerarArchivos.setDisable(true);
                    piProgreso.setProgress(-1);
                    lbProgreso.setText("");
                    lbProceso.setText("GENERANDO ARCHIVOS");
                });

                BB0 bb = new BB0(fecha);
                bb.run();
                BB1 bb1 = new BB1(fecha);
                bb1.run();

                Platform.runLater(() -> {
                    piProgreso.setProgress(1);
                    lbProgreso.setText("");
                    lbProceso.setText("");
                    btGenerarArchivos.setDisable(false);
                    mostrarPanel(this.wait_to_procesar);
                });
            });
            a.start();
        }

    }

    @FXML
    void generarPdf(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());

        if (fecha != null) {
            String query = "SELECT * FROM " + Var.nombreBD + ".procesar where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));
            File fichero = new File(Var.ficheroEx, Dates.imprimeFecha(fecha));
            fichero.mkdirs();

            Thread a = new Thread(() -> {

                Platform.runLater(() -> {
                    mostrarPanel(this.procesar_to_wait);
                    btGenerarPdf.setDisable(true);
                    piProgreso.setProgress(0);
                    lbProgreso.setText("");
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
                        double counter = contador;
                        double toutal = total;
                        lbProgreso.setText("DESCARGANDO " + contadour + " de " + total);
                        piProgreso.setProgress(counter / toutal);
                    });
                    aux = (Procesar) list.get(i);
                    destino = new File(fichero, aux.getCodigo() + ".pdf");
                    Download.descargaPDF(aux.getLink(), destino);
                    aux.SQLSetEstado(Estado.LISTO_PROCESAR.getValue());
                }

                Platform.runLater(() -> {
                    piProgreso.setProgress(1);
                    lbProgreso.setText("");
                    lbProceso.setText("");
                    btGenerarPdf.setDisable(false);
                    mostrarPanel(this.wait_to_procesar);

                    cambioEnDatePicker(new ActionEvent());
                });
            });
            a.start();
        }
    }

    /**
     * Generar PDF individual.
     *
     * @param event
     */
    @FXML
    void generarPdfI(ActionEvent event) {
        ModeloProcesar pr = (ModeloProcesar) tvProcesar.getSelectionModel().getSelectedItem();
        Date fecha = Dates.asDate(dpFecha.getValue());

        if (fecha != null && pr != null) {
            File fichero = new File(Var.ficheroEx, Dates.imprimeFecha(fecha));
            fichero.mkdirs();

            Thread a = new Thread(() -> {

                Platform.runLater(() -> {
                    btGenerarPdf.setDisable(true);
                    piProgreso.setProgress(0);
                    lbProgreso.setText("");
                    lbProceso.setText("GENERANDO PDFs");
                });

                File destino;
                ModeloProcesar aux;
                List list = new ArrayList();
                list.add(pr);

                for (int i = 0; i < list.size(); i++) {
                    final int contador = i;
                    final int total = list.size();
                    Platform.runLater(() -> {
                        int contadour = contador + 1;
                        double counter = contador;
                        double toutal = total;
                        lbProgreso.setText("DESCARGANDO " + contadour + " de " + total);
                        piProgreso.setProgress(counter / toutal);
                    });
                    aux = (ModeloProcesar) list.get(i);
                    destino = new File(fichero, aux.getCodigo() + ".pdf");
                    Download.descargaPDF(aux.getLink(), destino);
                }

                Platform.runLater(() -> {
                    piProgreso.setProgress(1);
                    lbProgreso.setText("");
                    lbProceso.setText("");
                    btGenerarPdf.setDisable(false);

                    cambioEnDatePicker(new ActionEvent());
                });
            });
            a.start();
        }
    }

    private List<ModeloProcesar> getBoletinesProcesar() {
        ModeloProcesar modelo;
        List<ModeloProcesar> list = new ArrayList();
        Iterator<ModeloProcesar> it = procesarList.iterator();

        while (it.hasNext()) {
            modelo = it.next();

            if (modelo.getEstado() == 1 && listaEstructurasCreadas.contains(modelo.getEstructura())) {
                list.add(modelo);
            }
        }
        return list;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isPreview = false;
        panelPreview.setOpacity(0.0);
        panelPreview.setVisible(false);
        panelProcesar.setOpacity(1.0);
        panelProcesar.setVisible(true);
        panelEspera.setOpacity(0.0);
        panelEspera.setVisible(false);
        iniciarTablaProcesar();
        iniciarTablaPreview();

        final ObservableList<ModeloProcesar> ls1 = tvProcesar.getSelectionModel().getSelectedItems();
        ls1.addListener(selectorTablaProcesar);

        final ObservableList<ModeloPreview> ls2 = tvPreview.getSelectionModel().getSelectedItems();
        ls2.addListener(selectorTablaPreview);
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
                                setText(Estado.SIN_PROCESAR.toString());
                                setTextFill(Color.BLACK);
                                break;

                            case 1:
                                setText(Estado.LISTO_PROCESAR.toString());
                                setTextFill(Color.ORCHID);
                                break;

                            case 2:
                                setText(Estado.PROCESADO_XLSX.toString());
                                setTextFill(Color.GREEN);
                                break;

                            case 3:
                                setText(Estado.ERROR_PROCESAR.toString());
                                setTextFill(Color.RED);
                                break;

                            case 4:
                                setText(Estado.PDF_NO_GENERADO.toString());
                                setTextFill(Color.ORANGERED);
                                break;

                            case 5:
                                setText(Estado.XLSX_NO_GENERADO.toString());
                                setTextFill(Color.ORANGE);
                                break;
                            case 6:
                                setText(Estado.PROCESAR_MANUAL.toString());
                                setTextFill(Color.RED);
                        }
                    }
                }
            };
        });

        procesarList = FXCollections.observableArrayList();
        tvProcesar.setItems(procesarList);
    }

    @FXML
    void previsualizar(ActionEvent event) {
        if (isPreview) {
            btPreview.setText("Previsualizar Extracción");
            mostrarPanel(this.preview_to_procesar);
            isPreview = !isPreview;
            switchControles(false);
        } else {
            Date fecha = Dates.asDate(dpFecha.getValue());
            ModeloProcesar aux = (ModeloProcesar) tvProcesar.getSelectionModel().getSelectedItem();
            Extraccion ex;

            if (fecha != null || aux != null) {
                ex = new Extraccion(fecha);
                if (ex.fileExist(aux.getCodigo())) {
                    if (listaEstructurasCreadas.contains(aux.getEstructura())) {

                        Thread a = new Thread(() -> {
                            List<Multa> procesados;

                            Platform.runLater(() -> {
                                switchControles(true);
                                btGenerarPdf.setDisable(true);
                                piProgreso.setProgress(-1);
                                lbProgreso.setText("");
                                lbProceso.setText("PROCESANDO PREVISUALIZACIÓN");
                                btPreview.setText("Volver");
                                mostrarPanel(this.procesar_to_wait);
                                isPreview = !isPreview;
                            });

                            try {
                                procesados = ex.previewXLSX(SqlBoe.getProcesar(aux.getCodigo()));

                                Platform.runLater(() -> {
                                    cargarDatosPreview(procesados);
                                    piProgreso.setProgress(1);
                                    lbProgreso.setText("");
                                    lbProceso.setText("");
                                    mostrarPanel(this.wait_to_preview);
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                Logger.getLogger(ExtC.class.getName()).log(Level.SEVERE, null, ex);
                                Platform.runLater(() -> {
                                    piProgreso.setProgress(1);
                                    lbProgreso.setText("");
                                    lbProceso.setText("");

                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("ERROR");
                                    alert.setHeaderText("XLSX CON ERRORES");
                                    alert.setContentText("El XLSX seleccionado contiene errores de estructura \n"
                                            + e.getMessage());
                                    alert.showAndWait();

                                    btPreview.setText("Previsualizar Extracción");
                                    mostrarPanel(this.wait_to_procesar);
                                    isPreview = !isPreview;
                                    switchControles(false);
                                });
                            }
                        });
                        a.start();

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
    void procesar(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());
        List<ModeloProcesar> list = getBoletinesProcesar();

        if (!list.isEmpty() && fecha != null) {
            Thread a = new Thread(() -> {

                Platform.runLater(() -> {
                    mostrarPanel(this.procesar_to_wait);
                    btGenerarPdf.setDisable(true);
                    piProgreso.setProgress(0);
                    lbProgreso.setText("");
                    lbProceso.setText("PREPARANDO BBDD");
                });
                
                try {
                    Sql bd = new Sql(Var.con);
                    bd.ejecutar("DELETE from boes.boe where DATEDIFF(curdate(),fecha)> 15");
                    bd.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExtC.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Platform.runLater(() -> {
                    lbProgreso.setText("");
                    lbProceso.setText("PROCESANDO BOLETINES");
                });
                

                Extraccion ex = new Extraccion(fecha);
                List<Multa> procesado;
                ModeloProcesar aux;
                Procesar pr;

                for (int i = 0; i < list.size(); i++) {
                    final int contador = i;
                    final int total = list.size();

                    Platform.runLater(() -> {
                        int contadour = contador + 1;
                        double counter = contador;
                        double toutal = total;
                        lbProgreso.setText("PROCESANDO " + contadour + " de " + total);
                        piProgreso.setProgress(counter / toutal);
                    });

                    aux = (ModeloProcesar) list.get(i);
                    pr = SqlBoe.getProcesar(aux.getCodigo());

                    try {
                        procesado = ex.previewXLSX(pr);

                        if (procesado.contains(new Multa())) {
                            pr.SQLSetEstado(Estado.ERROR_PROCESAR.getValue());
                        } else {
                            Platform.runLater(() -> {
                                lbProgreso.setText("INSERTANDO MULTAS");
                            });
                            XLSXProcess.insertMultas(procesado);
                            pr.SQLSetEstado(Estado.PROCESADO_XLSX.getValue());
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println(aux.getCodigo());
                        pr.SQLSetEstado(Estado.ERROR_PROCESAR.getValue());
                    }
                }

                Platform.runLater(() -> {
                    piProgreso.setProgress(1);
                    lbProgreso.setText("");
                    lbProceso.setText("");
                    btGenerarPdf.setDisable(false);
                    mostrarPanel(this.wait_to_procesar);

                    cambioEnDatePicker(new ActionEvent());
                });
            });
            a.start();
        }
    }

    private void mostrarPanel(int panel) {
        FadeTransition fade;

        switch (panel) {
            case procesar_to_preview:
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

            case preview_to_procesar:
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
            case procesar_to_wait:
                fade = new FadeTransition(Duration.millis(1000), panelProcesar);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setAutoReverse(false);
                fade.play();
                panelProcesar.setVisible(false);

                panelEspera.setVisible(true);
                fade = new FadeTransition(Duration.millis(1000), panelEspera);
                fade.setFromValue(0.0);
                fade.setToValue(1.0);
                fade.setAutoReverse(false);
                fade.play();

                break;

            case wait_to_procesar:
                fade = new FadeTransition(Duration.millis(1000), panelEspera);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setAutoReverse(false);
                fade.play();
                panelEspera.setVisible(false);

                panelProcesar.setVisible(true);
                fade = new FadeTransition(Duration.millis(1000), panelProcesar);
                fade.setFromValue(0.0);
                fade.setToValue(1.0);
                fade.setAutoReverse(false);
                fade.play();

                break;

            case wait_to_preview:
                fade = new FadeTransition(Duration.millis(1000), panelEspera);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setAutoReverse(false);
                fade.play();
                panelEspera.setVisible(false);

                panelPreview.setVisible(true);
                fade = new FadeTransition(Duration.millis(1000), panelPreview);
                fade.setFromValue(0.0);
                fade.setToValue(1.0);
                fade.setAutoReverse(false);
                fade.play();

                break;

            case preview_to_wait:
                fade = new FadeTransition(Duration.millis(1000), panelPreview);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setAutoReverse(false);
                fade.play();
                panelEspera.setVisible(false);

                panelPreview.setVisible(true);
                fade = new FadeTransition(Duration.millis(1000), panelEspera);
                fade.setFromValue(0.0);
                fade.setToValue(1.0);
                fade.setAutoReverse(false);
                fade.play();

                break;
        }
    }

    @FXML
    void reqObs(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());

        if (fecha != null) {
            Thread a = new Thread(() -> {

                Platform.runLater(() -> {
                    mostrarPanel(this.procesar_to_wait);
                    btReqObs.setDisable(true);
                    piProgreso.setProgress(-1);
                    lbProgreso.setText("");
                    lbProceso.setText("EJECUTANDO SCRIPT REQ/OBS");
                });

                ScriptReq sr = new ScriptReq(fecha);
                sr.run();

                Platform.runLater(() -> {
                    lbProgreso.setText("");
                    lbProceso.setText("EJECUTANDO SCRIPT EXPEDIENTE");
                });
                ScriptExp se = new ScriptExp(fecha);
                se.run();

                Platform.runLater(() -> {
                    lbProgreso.setText("");
                    lbProceso.setText("EJECUTANDO SCRIPT FASE");
                });
                ScriptFase sf = new ScriptFase();
                sf.run();

                Platform.runLater(() -> {
                    lbProgreso.setText("");
                    lbProceso.setText("EJECUTANDO SCRIPT ORIGEN");
                });
                ScriptOrigen so = new ScriptOrigen(fecha);
                so.run();

                Platform.runLater(() -> {
                    lbProgreso.setText("");
                    lbProceso.setText("EJECUTANDO SCRIPT ARTICULO");
                });
                ScriptArticulo sa = new ScriptArticulo();
                sa.run();

                Platform.runLater(() -> {
                    piProgreso.setProgress(1);
                    lbProgreso.setText("");
                    lbProceso.setText("");
                    btReqObs.setDisable(false);
                    mostrarPanel(this.wait_to_procesar);

                    cambioEnDatePicker(new ActionEvent());
                });
            });
            a.start();
        }
    }

    @FXML
    void resetearEstado(ActionEvent event) {
        ModeloProcesar aux = (ModeloProcesar) tvProcesar.getSelectionModel().getSelectedItem();
        Procesar pr = SqlBoe.getProcesar(aux.getCodigo());
        SqlBoe.eliminarMultasBoletin(pr.getCodigo());
        pr.SQLSetEstado(Estado.LISTO_PROCESAR.getValue());
        cambioEnDatePicker(new ActionEvent());
    }

    @FXML
    void eliminarEstructura(ActionEvent event) {
        ModeloProcesar aux = (ModeloProcesar) tvProcesar.getSelectionModel().getSelectedItem();
        Procesar pr = SqlBoe.getProcesar(aux.getCodigo());
        SqlBoe.eliminarMultasBoletin(pr.getCodigo());
        pr.SQLEliminarEstructura();
        cambioEnDatePicker(new ActionEvent());
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    void switchControles(boolean aux) {
        dpFecha.setDisable(aux);
        btGenerarPdf.setDisable(aux);
        btReqObs.setDisable(aux);
        btAbrirCarpeta.setDisable(aux);
        btProcesar.setDisable(aux);
        btAbrirCarpetaAr.setDisable(aux);
        btGenerarArchivos.setDisable(aux);
    }

    @FXML
    void verPdf(ActionEvent event) {
        ModeloProcesar pr = (ModeloProcesar) tvProcesar.getSelectionModel().getSelectedItem();

        if (pr != null) {
            try {
                Desktop.getDesktop().browse(new URI(pr.link.get()));

            } catch (IOException ex) {
                Logger.getLogger(WinC.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(ExtC.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void verXLSX(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());
        ModeloProcesar pr = (ModeloProcesar) tvProcesar.getSelectionModel().getSelectedItem();

        if (pr != null && fecha != null) {
            File fichero = new File(Var.ficheroEx, Dates.imprimeFecha(fecha));
            File archivo = new File(fichero, pr.getCodigo() + ".xlsx");

            try {
                Desktop.getDesktop().browse(archivo.toURI());

            } catch (IOException ex) {
                Logger.getLogger(WinC.class
                        .getName()).log(Level.SEVERE, null, ex);
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
