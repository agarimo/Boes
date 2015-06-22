package main;

import boes.Boe;
import boes.Pdf;
import boes.Publicacion;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.Dates;
import util.Sql;

/**
 *
 * @author Agarimo
 */
public class WinC implements Initializable {

    @FXML
    private Button btDescargar;

    @FXML
    private Button btAbrirCarpeta;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField tfLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void descargaBoes(ActionEvent event) {
        Date fecha = Dates.asDate(dpFecha.getValue());
        String link = tfLink.getText();

        Boe boe = new Boe(fecha, link);
        boe.getBoletines();

        if (BDCreaBoe(boe)) {
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

    private boolean BDCreaBoe(Boe aux) {
        try {
            Sql bd = new Sql(Variables.con);
            bd.ejecutar(aux.SQLCrear());
            return true;
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL ERROR");
            alert.setHeaderText(ex.getSQLState());
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            return false;
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

}
