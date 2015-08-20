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
import model.ModeloBoletines;
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
        launch(args);
//        run();
//        System.exit(0);
    }

    public static void run() {
        Variables.inicializar();
        System.out.println("run");

        Union un = new Union(getFecha());

        String aux;
        int aux1;
        ModeloBoletines bol;

        Iterator it = un.getProvincias().iterator();
        Iterator its;
        Iterator itt;

        while (it.hasNext()) {
            aux = (String) it.next();
            un.setMap(un.cargaMap(aux));
            System.out.println("Provincia: " + aux);
            System.out.println("----------------------");
            its = un.getKeySet().iterator();

            while (its.hasNext()) {
                aux1 = (int) its.next();
                System.out.println("Estructura: " + aux1);
                itt = un.getBoletines(aux1).iterator();

                while (itt.hasNext()) {
                    bol=(ModeloBoletines) itt.next();
                    System.out.println(bol.getCodigo());
                }
                System.out.println("......");
            }
            System.out.println("----------------------");
        }
    }

    public static Date getFecha() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 17);
        return cal.getTime();
    }
}
