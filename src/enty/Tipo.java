package enty;

import main.Var;
import util.Varios;

/**
 *
 * @author Agárimo
 */
public class Tipo {

    private String id;
    private String nombre;

    public Tipo(String idTipo) {
        this.id = idTipo;
    }

    public Tipo(String idTipo, String nombre) {
        this.id = idTipo;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public String SQLCrear() {
        return "INSERT into " + Var.nombreBD + ".tipo (id, nombre) values("
                + Varios.entrecomillar(this.id) + ","
                + Varios.entrecomillar(this.nombre)
                + ")";
    }

    public String SQLEditar() {
        return "UPDATE " + Var.nombreBD + ".tipo SET "
                + "nombre=" + Varios.entrecomillar(this.nombre)
                + "WHERE id=" + Varios.entrecomillar(this.id);
    }

    public String SQLBorrar() {
        return "DELETE FROM " + Var.nombreBD + ".tipo WHERE id=" + Varios.entrecomillar(this.id);
    }

    public String SQLBuscar() {
        return "SELECT * FROM " + Var.nombreBD + ".tipo WHERE id=" + Varios.entrecomillar(this.id);
    }
}
