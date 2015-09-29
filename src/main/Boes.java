package main;

import java.util.Calendar;
import java.util.Date;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.NodeOrientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Agarimo
 */
public class Boes extends Application {

    public static String screen1ID = "main";
    public static String screen1File = "/view/Win.fxml";
    public static String screen2ID = "extraccion";
    public static String screen2File = "/view/Ext.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "Screen3.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        Variables.inicializar();

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Boes.screen1ID, Boes.screen1File);
        mainContainer.loadScreen(Boes.screen2ID, Boes.screen2File);
//        mainContainer.loadScreen(Boes.screen3ID, Boes.screen3File);

        mainContainer.setScreen(Boes.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("BOEAPP");
        stage.setResizable(false);
        stage.setWidth(1005);
        stage.setHeight(625);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static Date getFecha() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        return cal.getTime();
    }
}
