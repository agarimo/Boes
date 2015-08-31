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
    String codigoSancion;
    Date fechaPublicacion;
    String organismo;
    String boe;
    String fase;
    String tipoJuridico;
    String plazo;
    String expediente;
    Date fechaMulta;
    String articulo;
    String nif;
    String sancionado;
    String localidad;
    String matricula;
    String cuantia;
    String puntos;
    String reqObs;
    String linea;

    public Multa() {

    }

    public Multa(int idBoletin, String codigoSancion, Date fechaPublicacion, String organismo, String boe, String fase, String tipoJuridico, String plazo, String expediente, Date fechaMulta, String articulo, String nif, String sancionado, String localidad, String matricula, String cuantia, String puntos, String reqObs, String linea) {
        this.idBoletin = idBoletin;
        this.codigoSancion = codigoSancion;
        this.fechaPublicacion = fechaPublicacion;
        this.organismo = organismo;
        this.boe = boe;
        this.fase = fase;
        this.tipoJuridico = tipoJuridico;
        this.plazo = plazo;
        this.expediente = expediente;
        this.fechaMulta = fechaMulta;
        this.articulo = articulo;
        this.nif = nif;
        this.sancionado = sancionado;
        this.localidad = localidad;
        this.matricula = matricula;
        this.cuantia = cuantia;
        this.puntos = puntos;
        this.reqObs = reqObs;
        this.linea = linea;
    }

    public Multa(int id, int idBoletin, String codigoSancion, Date fechaPublicacion, String organismo, String boe, String fase, String tipoJuridico, String plazo, String expediente, Date fechaMulta, String articulo, String nif, String sancionado, String localidad, String matricula, String cuantia, String puntos, String reqObs, String linea) {
        this.id = id;
        this.idBoletin = idBoletin;
        this.codigoSancion = codigoSancion;
        this.fechaPublicacion = fechaPublicacion;
        this.organismo = organismo;
        this.boe = boe;
        this.fase = fase;
        this.tipoJuridico = tipoJuridico;
        this.plazo = plazo;
        this.expediente = expediente;
        this.fechaMulta = fechaMulta;
        this.articulo = articulo;
        this.nif = nif;
        this.sancionado = sancionado;
        this.localidad = localidad;
        this.matricula = matricula;
        this.cuantia = cuantia;
        this.puntos = puntos;
        this.reqObs = reqObs;
        this.linea = linea;
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

    public String getCodigoSancion() {
        return codigoSancion;
    }

    public void setCodigoSancion(String codigoSancion) {
        this.codigoSancion = codigoSancion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getOrganismo() {
        return organismo;
    }

    public void setOrganismo(String organismo) {
        this.organismo = organismo;
    }

    public String getBoe() {
        return boe;
    }

    public void setBoe(String boe) {
        this.boe = boe;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getTipoJuridico() {
        return tipoJuridico;
    }

    public void setTipoJuridico(String tipoJuridico) {
        this.tipoJuridico = tipoJuridico;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public Date getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(Date fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getSancionado() {
        return sancionado;
    }

    public void setSancionado(String sancionado) {
        this.sancionado = sancionado;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
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

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String SQLCrear() {
        return "INSERT into " + Variables.nombreBD + ".multa (idBoletin,codigoSancion,fechaPublicacion,organismo,boe,fase,tipoJuridico,plazo,expediente,fechaMulta,articulo,nif,sancionado,localidad,matricula,cuantia,puntos,reqObs,linea) values("
                + this.idBoletin + ","
                + Varios.entrecomillar(this.codigoSancion) + ","
                + Varios.entrecomillar(Dates.imprimeFecha(fechaPublicacion)) + ","
                + Varios.entrecomillar(this.organismo) + ","
                + Varios.entrecomillar(this.boe) + ","
                + Varios.entrecomillar(this.fase) + ","
                + Varios.entrecomillar(this.tipoJuridico) + ","
                + Varios.entrecomillar(this.plazo) + ","
                + Varios.entrecomillar(this.expediente) + ","
                + Varios.entrecomillar(Dates.imprimeFecha(this.fechaMulta)) + ","
                + Varios.entrecomillar(this.articulo) + ","
                + Varios.entrecomillar(this.nif) + ","
                + Varios.entrecomillar(this.sancionado) + ","
                + Varios.entrecomillar(this.localidad) + ","
                + Varios.entrecomillar(this.matricula) + ","
                + Varios.entrecomillar(this.cuantia) + ","
                + Varios.entrecomillar(this.puntos) + ","
                + Varios.entrecomillar(this.reqObs) + ","
                + Varios.entrecomillar(this.linea)
                + ");";
    }
}
