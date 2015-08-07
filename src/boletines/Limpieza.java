package boletines;

import enty.Boletin;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    String datos;

    public Limpieza(String codigo) {
        boletin = SqlBoe.getBoletin("SELECT * FROM " + Variables.nombreBD + ".boletin where codigo=" + Varios.entrecomillar(codigo));
        if (boletin != null) {
            cargarDatos();
        }
    }
    
    public Limpieza (Boletin boletin){
        this.boletin=boletin;
        cargarDatos();
    }

    private void cargarDatos() {
        Sql bd;
        try {
            bd = new Sql(Variables.con);
            datos = bd.getString("SELECT datos FROM " + Variables.nombreBD + ".descarga where id=" + boletin.getIdDescarga());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Limpieza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        List lista=new ArrayList();
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
    
    private void guardaDatos(String datos){
        Sql bd;
        datos=datos.replace("'", "\\'");
        
        try {
            bd = new Sql(Variables.con);
            bd.ejecutar("UPDATE " + Variables.nombreBD + ".descarga SET datos="+Varios.entrecomillar(datos)+" where id=" + boletin.getIdDescarga());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Limpieza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void runSingle() {
        List lista=new ArrayList();
        StringBuilder buffer = new StringBuilder();
        String[] split = datos.split(System.getProperty("line.separator"));

        for (String split1 : split) {

            if (!lista.contains(split1)) {
                lista.add(split1);
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
