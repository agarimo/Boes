package enty;

import java.util.Date;
import main.Variables;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Multa {

    int id;
    int idBoletin;
    String expediente;
    String sancionado;
    String nif;
    String localidad;
    Date fecha;
    String matricula;
    String cuantia;
    String precepto;
    String articulo;
    String puntos;
    String reqObs;

    public Multa() {

    }

    public Multa(int idBoletin, String expediente, String sancionado, String nif, String localidad, Date fecha, String matricula, String cuantia, String precepto, String articulo, String puntos, String reqObs) {
        this.idBoletin = idBoletin;
        this.expediente = expediente;
        this.sancionado = sancionado;
        this.nif = nif;
        this.localidad = localidad;
        this.fecha = fecha;
        this.matricula = matricula;
        this.cuantia = cuantia;
        this.precepto = precepto;
        this.articulo = articulo;
        this.puntos = puntos;
        this.reqObs = reqObs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBoletin() {
        return idBoletin;
    }

    public void setIdBoletin(int idBoletin) {
        this.idBoletin = idBoletin;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getSancionado() {
        return sancionado;
    }

    public void setSancionado(String sancionado) {
        this.sancionado = sancionado;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCuantia() {
        return cuantia;
    }

    public void setCuantia(String cuantia) {
        this.cuantia = cuantia;
    }

    public String getPrecepto() {
        return precepto;
    }

    public void setPrecepto(String precepto) {
        this.precepto = precepto;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getReqObs() {
        return reqObs;
    }

    public void setReqObs(String reqObs) {
        this.reqObs = reqObs;
    }

    public String SQLCrear() {
        return "INSERT into " + Variables.nombreBD + ".multa (idBoletin,expediente,sancionado,nif,localidad,fecha,matricula,cuantia,precepto,articulo,puntos,reqObs) values("
                + this.idBoletin + ","
                + Varios.entrecomillar(this.expediente) + ","
                + Varios.entrecomillar(this.sancionado) + ","
                + Varios.entrecomillar(this.nif) + ","
                + Varios.entrecomillar(this.localidad) + ","
                + Varios.entrecomillar(Dates.imprimeFecha(this.fecha)) + ","
                + Varios.entrecomillar(this.matricula) + ","
                + Varios.entrecomillar(this.cuantia) + ","
                + Varios.entrecomillar(this.precepto) + ","
                + Varios.entrecomillar(this.articulo) + ","
                + Varios.entrecomillar(this.puntos) + ","
                + Varios.entrecomillar(this.reqObs)
                + ");";
    }
}
