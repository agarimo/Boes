package enty;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Variables;
import util.Dates;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Procesar {

    int id;
    Date fecha;
    String codigo;
    String link;
    int estructura;
    /**
     * Estado= 0 (Sin procesar) Estado=1 (Ready to process) Estado=2 (Procesado
     * Excel) Estado= 3 (Error al procesar) Estado=4 (Falta PDF) Estado=5 (Falta
     * XLSX)
     */
    int estado;

    public Procesar() {

    }

    public Procesar(Date fecha, String codigo, String link, int estructura, int estado) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.link = link;
        this.estructura = estructura;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getEstado() {
        return estado;
    }

    public int getEstructura() {
        return estructura;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEstado(int estado) {
        if (estado != 1) {
            this.estado = estado;
        } else {
            this.estado = checkEstado();
        }
    }

    public void setEstructura(int estructura) {
        this.estructura = estructura;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return this.codigo;
    }

    private int checkEstado() {
        int a = 1;
        File fichero = new File(Variables.ficheroEx, Dates.imprimeFecha(fecha));
        File fileXLSX = new File(fichero, codigo + ".xlsx");
        File filePDF = new File(fichero, codigo + ".pdf");

        if (!fileXLSX.exists()) {
            a = 5;
        }

        if (!filePDF.exists()) {
            a = 4;
        }

        return a;
    }

    public String SQLCrear() {
        return "INSERT into " + Variables.nombreBD + ".procesar (id,fecha,codigo,link,estructura,estado) values("
                + this.id + ","
                + Varios.entrecomillar(Dates.imprimeFecha(this.fecha)) + ","
                + Varios.entrecomillar(this.codigo) + ","
                + Varios.entrecomillar(this.link) + ","
                + this.estructura + ","
                + 1
                + ");";
    }

    public void SQLSetEstado(int estado) {
        String query = "UPDATE " + Variables.nombreBD + ".procesar SET "
                + "estado=" + estado + " "
                + "WHERE id=" + this.id;

        try {
            Sql bd = new Sql(Variables.con);
            bd.ejecutar(query);
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Procesar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
