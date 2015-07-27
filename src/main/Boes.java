package main;

import enty.Cabecera;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Sql;

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
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
//        launch(args);
        test();
        System.exit(0);
    }

    public static void test() throws SQLException {
        Variables.inicializar();
        Cabecera aux;
        Sql bd;
        Iterator it;
        List cabeceras = SqlBoe.listaCabeceras(372, 1);

        bd = new Sql(Variables.con);
        String datos = bd.getString("SELECT datos FROM boes.descarga where id=3916");

        it = cabeceras.iterator();

        while (it.hasNext()) {
            aux = (Cabecera) it.next();

            if (datos.contains(aux.getCabecera())) {
                lector(datos,aux.getCabecera());
            }
        }
    }

    public static void lector(String datos,String cabecera) {
        boolean leer = true;
        String[] split = datos.split(System.getProperty("line.separator"));

        for (String split1 : split) {

            if(split1.contains(cabecera)){
                leer=false;
            }
            
            if (leer) {
                System.out.println(split1);
            }
            
            if(split1.contains("cve: BOE-")){
                leer=true;
            }
        }
    }

}
