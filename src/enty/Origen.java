package enty;

import main.Variables;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Origen {

    int id;
    String nombre;
    String codigo;

    public Origen() {
    }

    public Origen(String nombre) {
        this.nombre = nombre;
    }

    public Origen(int id, String nombre, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
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
    
    public String SQLCrear(){
        return "INSERT into "+Variables.nombreBD+".origen (nombre) values("
                + Varios.entrecomillar(this.nombre)
                + ");";
    }
    
    public String SQLBuscarNombre(){
        return "SELECT * FROM "+Variables.nombreBD+".origen WHERE nombre=" + util.Varios.entrecomillar(this.nombre);
    }
    
    public String SQLBuscarCodigo(){
        return "SELECT * FROM "+Variables.nombreBD+".origen WHERE codigo=" + util.Varios.entrecomillar(this.codigo);
    }
    
}
