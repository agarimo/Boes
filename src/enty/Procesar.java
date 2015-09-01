package enty;

import java.util.Date;
import main.Variables;
import util.Dates;
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
     * Estado= 0 (Sin procesar) Estado=1 (Generado PDF) Estado=2 (Procesado
     * Excel)
     */
    int estado;

    public Procesar() {

    }

    public Procesar(Date fecha,String codigo, String link, int estructura, int estado) {
        this.fecha=fecha;
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
        this.estado = estado;
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
    
    public String SQLCrear(){
        return "INSERT into " + Variables.nombreBD + ".procesar (id,fecha,codigo,link,estructura,estado) values("
                + this.id + ","
                + Varios.entrecomillar(Dates.imprimeFecha(this.fecha)) + ","
                + Varios.entrecomillar(this.codigo) + ","
                + Varios.entrecomillar(this.link) + ","
                + this.estructura + ","
                + this.estado
                + ");";
    }
}
