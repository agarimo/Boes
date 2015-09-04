package enty;

/**
 *
 * @author Agarimo
 */
public class StrucData {
    int id;
    int idEstructura;
    
    public int expediente;
    public int sancionado;
    public int nif;
    public int localidad;
    public int fechaMulta;
    public int matricula;
    public int cuantia;
    public int precepto;
    public int articulo;
    public int puntos;
    public int reqObs;
    
    public StrucData(){
        
    }
    
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

    public void setIdEstructura(int idEstructura) {
        this.idEstructura = idEstructura;
    }

    public void setExpediente(int expediente) {
        this.expediente = expediente;
    }

    public void setSancionado(int sancionado) {
        this.sancionado = sancionado;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public void setFechaMulta(int fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public void setCuantia(int cuantia) {
        this.cuantia = cuantia;
    }

    public void setPrecepto(int precepto) {
        this.precepto = precepto;
    }

    public void setArticulo(int articulo) {
        this.articulo = articulo;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setReqObs(int reqObs) {
        this.reqObs = reqObs;
    }
    
    public static String SQLBuscar(int idEstructura){
        return "SELECT * FROM boes.strucdata WHERE idEstructura="+idEstructura;
    }
    
    
}
