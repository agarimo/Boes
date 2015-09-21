package main;

import enty.Procesar;
import extraccion.Extraccion;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Agarimo
 */
public class Boes extends Application {
    
    public static String screen1ID = "main";
    public static String screen1File = "Win.fxml";
    public static String screen2ID = "extraccion";
    public static String screen2File = "Ext.fxml";
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
        stage.show();

//        Parent root = FXMLLoader.load(getClass().getResource("Win.fxml"));
//        Scene scene = new Scene(root);
//
//        stage.setScene(scene);
//        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

//        Variables.inicializar();
//
//        Procesar pr;
//        Extraccion ex = new Extraccion(getFecha());
//        List<Procesar> list = ex.getBoletines();
//
//        Iterator<Procesar> it = list.iterator();
//
//        while (it.hasNext()) {
//            pr = it.next();
//
//            try {
//                if (!ex.procesaXLSX(pr)) {
//                    System.out.println("No se encuentra el archivo.");
//                } else {
//                    System.out.println(pr.getCodigo());
//                }
//            } catch (Exception exc) {
//                System.err.println("Error en archivo : " + pr.getCodigo());
//            }
//        }
//
//        System.exit(0);
    }

    public static Date getFecha() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        return cal.getTime();
    }
}
