package boletines;

import enty.Boletin;
import enty.Cabecera;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.SqlBoe;
import main.Var;
import view.WinC;
import util.Files;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Limpieza {

    Sql bd;
    Boletin boletin;
    List cabeceras;
    String datos;

    public Limpieza(String codigo) {
        boletin = SqlBoe.getBoletin("SELECT * FROM " + Var.nombreBD + ".boletin where codigo=" + Varios.entrecomillar(codigo));
        if (boletin != null) {
            cargarDatos();
        }
    }

    public Limpieza(Boletin boletin) {
        this.boletin = boletin;
        cargarDatos();
    }

    private void cargarDatos() {
        try {
            bd = new Sql(Var.con);
            datos = bd.getString("SELECT datos FROM " + Var.nombreBD + ".descarga where id=" + boletin.getIdDescarga());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Limpieza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        List lista = new ArrayList();
        StringBuilder buffer = new StringBuilder();
        String[] split = datos.split(System.getProperty("line.separator"));

        for (String split1 : split) {

            if (!lista.contains(split1)) {
                lista.add(split1);
                buffer.append(split1);
                buffer.append(System.getProperty("line.separator"));
            }
        }
        guardaDatos(buffer.toString());
    }

    private void guardaDatos(String datos) {
        datos = datos.replace("'", "\\'");

        try {
            bd = new Sql(Var.con);
            bd.ejecutar("UPDATE " + Var.nombreBD + ".descarga SET datos=" + Varios.entrecomillar(datos) + " where id=" + boletin.getIdDescarga());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Limpieza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void runSingle() {
        cabeceras = SqlBoe.listaCabeceras(boletin.getIdOrigen(), 1);
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
