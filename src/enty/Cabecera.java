package enty;

import main.Var;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Cabecera {

    private int id;
    private int idOrigen;
    private String cabecera;
    private int tipo;
    
    public Cabecera(){
        
    }

    public Cabecera(int id, int idOrigen, String cabecera, int tipo) {
        this.id = id;
        this.idOrigen = idOrigen;
        this.cabecera = cabecera;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(int idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public String SQLBuscarOrigen(int id){
        return "SELECT * FROM boes.cabeceras where idOrigen="+id;
    }
    
    public String SQLCrear() {
        return "INSERT into " + Var.nombreBD + ".cabeceras (idOrigen, cabecera, tipo) values("
                + this.idOrigen + ","
                + Varios.entrecomillar(this.cabecera) + ","
                + this.tipo
                + ")";
    }

    public String SQLEditar() {
        return "UPDATE " + Var.nombreBD + ".cabeceras SET "
                + "cabecera=" + Varios.entrecomillar(this.cabecera) + ","
                + "tipo=" + this.tipo +" "
                + "WHERE id=" + this.id;
    }

    public String SQLBorrar() {
        return "DELETE FROM " + Var.nombreBD + ".cabeceras WHERE id=" + this.id + ";";
    }
}
