package enty;

import main.Variables;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Boletin {

    private int id;
    private int idOrigen;
    private int idBoe;
    private int idDescarga;
    private String codigo;
    private String fase;

    public Boletin() {
    }

    public Boletin(int id, int idOrigen, int idBoe, int idDescarga, String codigo, String fase) {
        this.id = id;
        this.idOrigen = idOrigen;
        this.idBoe = idBoe;
        this.idDescarga = idDescarga;
        this.codigo = codigo;
        this.fase = fase;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
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

    public int getIdBoe() {
        return idBoe;
    }

    public void setIdBoe(int idBoe) {
        this.idBoe = idBoe;
    }

    public int getIdDescarga() {
        return idDescarga;
    }

    public void setIdDescarga(int idDescarga) {
        this.idDescarga = idDescarga;
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
        return "INSERT into " + Variables.nombreBD + ".boletin (idOrigen,idBoe,idDescarga,codigo,fase) values("
                + this.idOrigen + ","
                + this.idBoe + ","
                + this.idDescarga + ","
                + Varios.entrecomillar(this.codigo) + ","
                + Varios.entrecomillar(this.fase)
                + ");";
    }
    
    public String SQLEditar(){
        String query="";
        return query;
    }
    
    public String SQLBuscar(){
        return "SELECT * from "+Variables.nombreBD+".boletin where codigo="+Varios.entrecomillar(this.codigo);
    }
    
    
    
    

}
