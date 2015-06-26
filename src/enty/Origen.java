package enty;

import main.Variables;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Origen {

    private int id;
    private int idEntidad;
    private String nombre;
    private String codigo;

    public Origen() {
    }

    public Origen(String nombre) {
        this.nombre = nombre;
    }

    public Origen(int id,int idEntidad, String nombre, String codigo) {
        this.id = id;
        this.idEntidad=idEntidad;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return codigo;
    }

    public String SQLCrear() {
        return "INSERT into " + Variables.nombreBD + ".origen (idEntidad ,nombre) values("
                + this.idEntidad + ","
                + Varios.entrecomillar(this.nombre)
                + ");";
    }
    
    public String SQLBuscar(){
        return "SELECT * FROM "+ Variables.nombreBD + ".origen WHERE nombre="+ Varios.entrecomillar(this.nombre)+" "
                + "and idEntidad="+this.idEntidad;
    }

    public String SQLBuscarNombre() {
        return "SELECT * FROM " + Variables.nombreBD + ".origen WHERE nombre=" + util.Varios.entrecomillar(this.nombre);
    }

    public String SQLBuscarCodigo() {
        return "SELECT * FROM " + Variables.nombreBD + ".origen WHERE codigo=" + util.Varios.entrecomillar(this.codigo);
    }
}
