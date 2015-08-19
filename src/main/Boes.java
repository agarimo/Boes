package main;

import boletines.Union;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author Agarimo
 */
public class Boes extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Variables.inicializar();

        Parent root = FXMLLoader.load(getClass().getResource("Win.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        launch(args);
        run();
        System.exit(0);
    }

    public static void run() {
        Variables.inicializar();
        System.out.println("run");
        
        Union un = new Union(getFecha());
        
        String aux;
        MapIterator it = un.getMap().mapIterator();
        
        while(it.hasNext()){
            it.next();
            String key = (String) it.getKey();
            System.out.println(key);
            MultiValueMap a = (MultiValueMap) it.getValue();
            System.out.println(a.entrySet());
            System.out.println("--------------------------------");
        }
    }

    public static Date getFecha() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 8);
        return cal.getTime();
    }
}
