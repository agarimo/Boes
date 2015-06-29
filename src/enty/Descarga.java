package enty;

import main.Variables;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Descarga {

    int id;
    String link;
    String datos;

    public Descarga() {
    }

    public Descarga(String link) {
        this.link = link;
        this.datos = "PD";
    }

    public Descarga(int id, String link, String datos) {
        this.id = id;
        this.link = link;
        this.datos = datos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String SQLCrear() {
        return "INSERT into " + Variables.nombreBD + ".descarga (link,datos) values("
                + Varios.entrecomillar(this.link) + ","
                + Varios.entrecomillar(this.datos)
                + ");";
    }
    
    public String SQLSetDatos(){
        return "UPDATE boes.descarga SET "
                + "datos=" + Varios.entrecomillar(this.datos) + " "
                + "WHERE id=" + this.id;
    }

    public String SQLBuscar() {
        return "SELECT * from " + Variables.nombreBD + ".descarga where link=" + Varios.entrecomillar(this.link);
    }
}
