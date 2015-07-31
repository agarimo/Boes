package boletines;

import enty.Boletin;
import enty.Cabecera;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.SqlBoe;
import main.Variables;
import main.WinC;
import util.Files;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Limpieza {

    Boletin boletin;
    List cabeceras;
    String datos;

    public Limpieza(String codigo) {
        boletin = SqlBoe.getBoletin("SELECT * FROM " + Variables.nombreBD + ".boletin where codigo=" + Varios.entrecomillar(codigo));
        if (boletin != null) {
            cargarDatos();
        }
    }

    private void cargarDatos() {
        Sql bd;
        cabeceras = SqlBoe.listaCabeceras(boletin.getIdOrigen(), 1);

        try {
            bd = new Sql(Variables.con);
            datos = bd.getString("SELECT datos FROM " + Variables.nombreBD + ".descarga where id=" + boletin.getIdDescarga());
        } catch (SQLException ex) {
            Logger.getLogger(Limpieza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        Cabecera aux;
        Iterator it = cabeceras.iterator();

        while (it.hasNext()) {
            aux = (Cabecera) it.next();

            if (datos.contains(aux.getCabecera())) {
                lector(aux.getCabecera());
                break;
            }
        }
    }

    private void lector(String cabecera) {
        boolean leer = true;
        StringBuilder buffer = new StringBuilder();
        String[] split = datos.split(System.getProperty("line.separator"));

        for (String split1 : split) {

            if (split1.contains(cabecera)) {
                leer = false;
            }

            if (leer) {
                buffer.append(split1);
                buffer.append(System.getProperty("line.separator"));
            }
        }
        escribeArchivo(buffer.toString());
    }

    private void escribeArchivo(String datos) {
        File aux = new File("tempLp.txt");

        try {
            aux.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Limpieza.class.getName()).log(Level.SEVERE, null, ex);
        }

        Files.escribeArchivo(aux, datos);

        try {
            Desktop.getDesktop().browse(aux.toURI());
        } catch (IOException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
