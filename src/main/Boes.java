package main;

import extraccion.BB0;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
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
        Var.inicializar();
//        launch(args);
        test();
        System.exit(0);
    }

    public static void test() {
//        checkMatriculas();
        checkNif();
//        printMultaBB0();
//        pruebas();
    }

    public static void pruebas() {
        String matricula = "53483776J";
        
        System.out.println(add0(matricula,9));

    }

    public static void printMultaBB0() {
        BB0 aux = new BB0(285730);
    }

//    public static void checkMatriculas() {
//        Regex rx = new Regex();
//        String aux;
//        List<String> listado = SqlBoe.listaString("SELECT matricula FROM boes.multa limit 5000000");
//        int contador = 0;
//
//        Iterator<String> it = listado.iterator();
//
//        while (it.hasNext()) {
//            aux = it.next();
//
//            if (rx.isBuscar("[A-Z]{1,2}[0-9]{2,3}[A-Z]{1,2}", aux)) {
//                contador++;
//                System.out.println(aux);
//            }
//        }
//
//        double porcentaje = ((double) contador * 100) / (double) listado.size();
//        DecimalFormat f = new DecimalFormat("#.##");
//        System.out.println(System.lineSeparator());
//        System.out.println("Se han cargado " + listado.size() + " multas. " + contador + " en el patrón");
//        System.out.println("Cumple el patrón un " + f.format(porcentaje) + "%");
//    }

    public static void checkNif() {
        Regex rx = new Regex();
        String aux;
        int contador = 0;
        List<String> listado = SqlBoe.listaString("SELECT nif FROM boes.multa limit 5000000");

        System.out.println("Se han cargado " + listado.size() + " multas");

        Iterator<String> it = listado.iterator();

        while (it.hasNext()) {
            aux = it.next();

            if (rx.isBuscar("[0-9]{5,7}[TRWAGMYFPDXBNJZSQVHLCKE]{1}", aux)) {
                contador++;
                System.out.println(aux+" -- "+add0(aux,9));
            }
        }

        double porcentaje = ((double) contador * 100) / (double) listado.size();
        DecimalFormat f = new DecimalFormat("#.##");
        System.out.println(System.lineSeparator());
        System.out.println("Se han cargado " + listado.size() + " multas. " + contador + " en el patrón");
        System.out.println("Cumple el patrón un " + f.format(porcentaje) + "%");
    }
    
    private static String add0(String aux, int size){
        
        while(aux.length()<size){
            aux="0"+aux;
        }
        
        return aux;
    }

    public static Date getFecha() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        return cal.getTime();
    }
}
