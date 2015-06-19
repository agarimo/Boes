package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author Agarimo
 */
public class WinController implements Initializable {

    @FXML
    private Button btDescargar;
    
    @FXML
    private TextField btAbrirCarpeta;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField tfLink;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
