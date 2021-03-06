package enty;

import util.Varios;

/**
 *
 * @author Agarimo
 */
public class OrigenFase {

    int idOrigen;
    String cabecera;
    String fase;
    String nuevaFase;

    public OrigenFase() {

    }

    public OrigenFase(int idOrigen, String cabecera, String fase, String nuevaFase) {
        this.idOrigen = idOrigen;
        this.cabecera = cabecera;
        this.fase = fase;
        this.nuevaFase = nuevaFase;
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

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getNuevaFase() {
        return nuevaFase;
    }

    public void setNuevaFase(String nuevaFase) {
        this.nuevaFase = nuevaFase;
    }
    
    public String SQLScript(){
        return "UPDATE boes.multa SET fase="+Varios.entrecomillar(this.nuevaFase)+" "
                + "WHERE "
                + "idOrganismo="+this.idOrigen+" "
                + "AND "
                + "fase="+Varios.entrecomillar(this.fase)+" "
                + "AND "
                + "expediente like '"+this.cabecera+"%'";
               
    }

}
