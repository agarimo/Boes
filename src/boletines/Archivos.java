package boletines;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
        Files.borraDirectorio(archivoFecha);
        archivoFecha.mkdir();
        this.boletines = new ArrayList();
        cargaBoletines();
    }

    private void cargaBoletines() {
        String query = "SELECT * FROM boes.vista_boletines where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(this.fecha));
        boletines = SqlBoe.listaVistaBoletin(query);
    }

    public List getBoletines() {
        return this.boletines;
    }

    public void creaArchivo(ModeloBoletines aux) {
        File file;
        File file1;
        StringBuilder buffer;

        try {
            buffer = new StringBuilder();
            file = new File(creaDirectorio(aux.getEntidad(), aux.getOrigen()), getNombreArchivo(aux.getCodigo(), fecha, aux.getEntidad()) + ".txt");
            //temp archivos sueltos.
            file1 = new File(archivoFecha, getNombreArchivoN(aux.getCodigo(), fecha, aux.getEntidad()) + ".txt");
            file.createNewFile();
            //temp archivos sueltos.
            file1.createNewFile();
            buffer.append("BCN2 ");
            buffer.append(aux.getLink());
            buffer.append(System.getProperty("line.separator"));
            buffer.append(getFaseBoletin(aux.codigo.get()));
            buffer.append(System.getProperty("line.separator"));
            buffer.append("BCN5 ");
            buffer.append(aux.getOrigen());
            buffer.append(System.getProperty("line.separator"));
            buffer.append(getCodigoAyutamiento(aux.getOrigen()));
            buffer.append(System.getProperty("line.separator"));
            buffer.append(getDatosBoletin(aux.getIdDescarga()));

            Files.escribeArchivo(file, buffer.toString());
            Files.escribeArchivo(file1, buffer.toString());

        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void creaArchivos() {
        File file;
        StringBuilder buffer;
        ModeloBoletines aux;
        Iterator it = boletines.iterator();

        while (it.hasNext()) {
            try {
                buffer = new StringBuilder();
                aux = (ModeloBoletines) it.next();
                file = new File(creaDirectorio(aux.getEntidad(), aux.getOrigen()), getNombreArchivo(aux.getCodigo(), fecha, aux.getEntidad()) + ".txt");
                file.createNewFile();
                buffer.append("BCN2 ");
                buffer.append(aux.getLink());
                buffer.append(System.getProperty("line.separator"));
                buffer.append(getFaseBoletin(aux.codigo.get()));
                buffer.append(System.getProperty("line.separator"));
                buffer.append("BCN5 ");
                buffer.append(aux.getOrigen());
                buffer.append(System.getProperty("line.separator"));
                buffer.append(getCodigoAyutamiento(aux.getOrigen()));
                buffer.append(System.getProperty("line.separator"));
                buffer.append(getDatosBoletin(aux.getIdDescarga()));

                Files.escribeArchivo(file, buffer.toString());

            } catch (IOException ex) {
                Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private File creaDirectorio(String entidad, String origen) {
        File aux = new File(archivoFecha, entidad);
        File aux1 = new File(aux, origen);
        aux1.mkdirs();

        return aux1;
    }

    private String getFaseBoletin(String codigo) {
        Sql bd;
        String aux = "";

        try {
            bd = new Sql(Variables.con);
            aux = bd.getString("SELECT fase FROM boes.boletin where codigo=" + Varios.entrecomillar(codigo));
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aux;
    }

    private String getCodigoAyutamiento(String nombre) {
        Sql bd;
        String aux = "";

        try {
            bd = new Sql(Variables.con);
            aux = bd.getString("SELECT codigoAy FROM boes.origen where nombre=" + Varios.entrecomillar(nombre));
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aux;
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

    private String getNombreArchivo(String codigo, Date fecha, String entidad) {
        String str = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        String ori = codigo;

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        if (dia < 10) {
            str = str + "0" + dia;
        } else {
            str = str + dia;
        }

        str = str + "0";

        String anno = Integer.toString(cal.get(Calendar.YEAR));
        str = str + anno.charAt(3);

        str = ori + "--" + str + getEntidad(entidad);

        int mes = cal.get(Calendar.MONTH);
        mes++;
        if (mes < 10) {
            str = str + mes + "Z-";
        } else {
            if (mes == 10) {
                str = str + "XZ-";
            }
            if (mes == 11) {
                str = str + "YZ-";
            }
            if (mes == 12) {
                str = str + "ZZ-";
            }
        }

        if (dia < 10) {
            str = str + "0" + dia + ".";
        } else {
            str = str + dia + ".";
        }

        if (mes < 10) {
            str = str + "0" + mes + ".";
        } else {
            str = str + mes + ".";
        }

        return str;
    }

    private String getNombreArchivoN(String codigo, Date fecha, String entidad) {
        String str = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        String ori = codigo;

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        if (dia < 10) {
            str = str + "0" + dia;
        } else {
            str = str + dia;
        }

        str = str + "0";

        String anno = Integer.toString(cal.get(Calendar.YEAR));
        str = str + anno.charAt(3);

        str = str + getEntidad(entidad);

        int mes = cal.get(Calendar.MONTH);
        mes++;
        if (mes < 10) {
            str = str + mes + "Z-";
        } else {
            if (mes == 10) {
                str = str + "XZ-";
            }
            if (mes == 11) {
                str = str + "YZ-";
            }
            if (mes == 12) {
                str = str + "ZZ-";
            }
        }

        if (dia < 10) {
            str = str + "0" + dia + ".";
        } else {
            str = str + dia + ".";
        }

        if (mes < 10) {
            str = str + "0" + mes + ".";
        } else {
            str = str + mes + ".";
        }

        str = str + "--" + ori;

        return str;
    }

    private String getEntidad(String entidad) {
        Sql bd;
        String aux = "";

        try {
            bd = new Sql(Variables.con);
            aux = bd.getString("SELECT codigo FROM boes.entidad where nombre=" + Varios.entrecomillar(entidad));
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aux;
    }

}
