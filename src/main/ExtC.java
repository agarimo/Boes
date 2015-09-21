package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Agarimo
 */
public class ExtC implements Initializable, ControlledScreen {
    
    ScreensController myController;
    
    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   @Override
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }
    
    @FXML
    public void cargaMain(ActionEvent event){
        myController.setScreen(Boes.screen1ID);
    }
    
}
