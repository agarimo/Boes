package main;

import enty.Multa;
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
        checkMatriculas();
//        checkNif();

    }

    public static void checkMatriculas() {
        Regex rx = new Regex();
        Multa aux;
        String str;
        List<Multa> listado = SqlBoe.listaMultas("SELECT * FROM boes.multa limit 5000000");

        System.out.println("Se han cargado " + listado.size() + " multas");
        System.out.println(System.lineSeparator());
        System.out.println(System.lineSeparator());

        Iterator<Multa> it = listado.iterator();

        while (it.hasNext()) {
            aux = it.next();

            str = rx.buscar(aux.getMatricula(), Regex.matriculas);

            if (str == null) {

                if (!aux.getMatricula().equals("")) {
                    System.out.println(aux.getId() + " " + aux.getCodigoSancion() + " " + aux.getMatricula());
                }
            }
        }
    }

    public static void checkNif() {
        Regex rx = new Regex();
        Multa aux;
        String str;
        List<Multa> listado = SqlBoe.listaMultas("SELECT * FROM boes.multa limit 5000000");

        System.out.println("Se han cargado " + listado.size() + " multas");

        Iterator<Multa> it = listado.iterator();

        while (it.hasNext()) {
            aux = it.next();

            str = rx.buscar(aux.getNif(), Regex.nif);

            if (str == null) {

                if (!aux.getNif().equals("")) {
                    System.out.println(aux.getId() + " " + aux.getCodigoSancion() + " " + aux.getMatricula());
                }
            }
        }
    }

    public static Date getFecha() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        return cal.getTime();
    }
}
