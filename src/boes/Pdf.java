package boes;

import main.*;
import java.util.Date;

/**
 *
 * @author Agarimo
 */
public class Pdf {

    String preLink="http://www.boe.es";
    String codigo;
    Date fecha;
    String origen;
    String descripcion;
    String link;

    public Pdf(String datos, String origen, Date fecha) {
        this.fecha=fecha;
        this.origen=origen;

        String[] split = datos.split(System.getProperty("line.separator"));

        for (String a : split) {

            if (a.contains("<p>")) {
                splitDescripcion(a);
            }

            if (a.contains("<a href=\"")) {
                splitLink(a);
            }
        }
        splitCodigo(this.link);
    }

    private void splitDescripcion(String descripcion) {
        descripcion = descripcion.replace("<p>", "");
        descripcion = descripcion.replace("</p>", "");
        this.descripcion = descripcion.trim();
    }
    
    private void splitLink(String link){
        String[] split=link.split("title=");
        
        link=split[0];
        link=link.replace("<a href=\"", "");
        link=link.replace("\"", "");
        this.link=link.trim();
    }
    
    private void splitCodigo(String codigo){
        String[] split=codigo.split("id=");
        this.codigo=split[1].trim();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLink() {
        return preLink+link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    
    
    
}
