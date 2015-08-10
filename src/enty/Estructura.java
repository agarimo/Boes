package enty;

import main.Variables;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Estructura {

    int id;
    String nombre;
    String estructura;

    public Estructura() {
        this.nombre="null";
        this.estructura="null";
    }

    public Estructura(int id, String nombre, String estructura) {
        this.id = id;
        this.nombre = nombre;
        this.estructura = estructura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public String SQLCrear() {
        return "INSERT into " + Variables.nombreBD + ".estructura (nombre,estructura) values("
                + Varios.entrecomillar(this.nombre) + ","
                + Varios.entrecomillar(this.estructura)
                + ");";
    }

    public String SQLEditar() {
        return "UPDATE " + Variables.nombreBD + ".estructura SET "
                + "nombre=" + Varios.entrecomillar(this.nombre) + ","
                + "estructura=" + Varios.entrecomillar(this.estructura) + " "
                + "WHERE id=" + this.id;
    }

    public String SQLBorrar() {
        return "DELETE FROM " + Variables.nombreBD + ".estructura where id=" + this.id;
    }

    public String SQLBuscarEstructura() {
        return "SELECT * from " + Variables.nombreBD + ".estructura where estructura=" + Varios.entrecomillar(this.estructura);
    }

    public String SQLBuscarNombre() {
        return "SELECT * from " + Variables.nombreBD + ".estructura where nombre=" + Varios.entrecomillar(this.nombre);
    }

}
