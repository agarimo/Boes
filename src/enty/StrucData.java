package enty;

/**
 *
 * @author Agarimo
 */
public class StrucData {
    int id;
    int idEstructura;
    
    int expediente;
    int sancionado;
    int nif;
    int localidad;
    int fechaMulta;
    int matricula;
    int cuantia;
    int precepto;
    int articulo;
    int puntos;
    int reqObs;
    
    public StrucData(int idEstructura){
        this.idEstructura=idEstructura;
    }

    public StrucData(int id, int idEstructura, int expediente, int sancionado, int nif, int localidad, int fechaMulta, int matricula, int cuantia, int precepto, int articulo, int puntos, int reqObs) {
        this.id = id;
        this.idEstructura = idEstructura;
        this.expediente = expediente;
        this.sancionado = sancionado;
        this.nif = nif;
        this.localidad = localidad;
        this.fechaMulta = fechaMulta;
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

    public int getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(int idEstructura) {
        this.idEstructura = idEstructura;
    }

    public int getExpediente() {
        return expediente;
    }

    public void setExpediente(int expediente) {
        this.expediente = expediente;
    }

    public int getSancionado() {
        return sancionado;
    }

    public void setSancionado(int sancionado) {
        this.sancionado = sancionado;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public int getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(int fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getCuantia() {
        return cuantia;
    }

    public void setCuantia(int cuantia) {
        this.cuantia = cuantia;
    }

    public int getPrecepto() {
        return precepto;
    }

    public void setPrecepto(int precepto) {
        this.precepto = precepto;
    }

    public int getArticulo() {
        return articulo;
    }

    public void setArticulo(int articulo) {
        this.articulo = articulo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getReqObs() {
        return reqObs;
    }

    public void setReqObs(int reqObs) {
        this.reqObs = reqObs;
    }
    
    public static String SQLBuscar(int idEstructura){
        return "SELECT * FROM boes.strucdata WHERE idEstructura="+idEstructura;
    }
    
    
}
