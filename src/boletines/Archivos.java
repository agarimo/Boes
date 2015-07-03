package boletines;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.SqlBoe;
import main.Variables;
import model.ModeloBoletines;
import util.Dates;
import util.Files;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Archivos {

    File archivoFecha;
    Date fecha;
    List boletines;

    public Archivos(Date fecha) {
        this.fecha = fecha;
        this.archivoFecha = new File(Variables.ficheroBoe, Dates.imprimeFecha(this.fecha));
        this.boletines = new ArrayList();
        cargaBoletines();
    }

    private void cargaBoletines() {
        String query = "SELECT * FROM boes.vista_boletines where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(this.fecha));
        boletines = SqlBoe.listaVistaBoletin(query);
    }

    public void creaArchivos() {
        File file;
        StringBuilder buffer = new StringBuilder();
        ModeloBoletines aux;
        Iterator it = boletines.iterator();

        while (it.hasNext()) {
            try {
                aux = (ModeloBoletines) it.next();
                file = new File(creaDirectorio(aux.getEntidad(), aux.getOrigen()), aux.getCodigo() + ".txt");
                file.createNewFile();
                buffer.append("BCN2 ");
                buffer.append(aux.getLink());
                buffer.append(System.getProperty("line.separator"));
                buffer.append(aux.getFase());
                buffer.append(System.getProperty("line.separator"));
                buffer.append(aux.getOrigen());
                buffer.append(System.getProperty("line.separator"));
                buffer.append(getDatosBoletin(aux.getIdDescarga()));

                Files.escribeArchivo(file, buffer.toString());

            } catch (IOException ex) {
                Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private File creaDirectorio(String entidad, String origen) {
        File aux = new File(Variables.ficheroBoe, entidad);
        File aux1 = new File(aux, origen);
        aux1.mkdirs();

        return aux1;
    }

    private String getDatosBoletin(int id) {
        Sql bd;
        String aux;

        try {
            bd = new Sql(Variables.con);
            aux = bd.getString("SELECT datos from " + Variables.nombreBD + ".descarga where id=" + id);
            bd.close();
        } catch (SQLException ex) {
            aux = "ERROR AL GENERAR EL ARCHIVO ----- " + ex.getMessage();
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aux;
    }

}
