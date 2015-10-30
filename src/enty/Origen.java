package enty;

import main.Var;
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
    private String codigoAy;
    private String codigoUn;
    private String codigoTes;

    public Origen() {
    }

    public Origen(String nombre) {
        this.nombre = nombre;
    }

    public Origen(int id, int idEntidad, String nombre, String codigo, String codigoAy, String codigoUn, String codigoTes) {
        this.id = id;
        this.idEntidad = idEntidad;
        this.nombre = nombre;
        this.codigo = codigo;
        this.codigoAy = codigoAy;
        this.codigoUn = codigoUn;
        this.codigoTes = codigoTes;
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

    public String getCodigoAy() {
        return codigoAy;
    }

    public void setCodigoAy(String codigoAy) {
        this.codigoAy = codigoAy;
    }

    public String getCodigoUn() {
        return codigoUn;
    }

    public void setCodigoUn(String codigoUn) {
        this.codigoUn = codigoUn;
    }

    public String getCodigoTes() {
        return codigoTes;
    }

    public void setCodigoTes(String codigoTes) {
        this.codigoTes = codigoTes;
    }

    @Override
    public String toString() {
        return codigo;
    }

    public String SQLCrear() {
        return "INSERT into " + Var.nombreBD + ".origen (idEntidad ,nombre) values("
                + this.idEntidad + ","
                + Varios.entrecomillar(this.nombre)
                + ");";
    }
    
    public String SQLBuscar(){
        return "SELECT * FROM "+ Var.nombreBD + ".origen WHERE nombre="+ Varios.entrecomillar(this.nombre)+" "
                + "and idEntidad="+this.idEntidad;
    }

    public String SQLBuscarNombre() {
        return "SELECT * FROM " + Var.nombreBD + ".origen WHERE nombre=" + util.Varios.entrecomillar(this.nombre);
    }

    public String SQLBuscarCodigo() {
        return "SELECT * FROM " + Var.nombreBD + ".origen WHERE codigo=" + util.Varios.entrecomillar(this.codigo);
    }
}
