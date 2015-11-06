package enty;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.Var;
import util.CalculaNif;
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
    int idOrganismo;
    String organismo;
    String boe;
    String fase;
    String tipoJuridico;
    int plazo;
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

    public Multa(int idBoletin, String codigoSancion, Date fechaPublicacion,int idOrganismo, String organismo, String boe, String fase, String tipoJuridico, int plazo, String expediente, Date fechaMulta, String articulo, String nif, String sancionado, String localidad, String matricula, String cuantia, String puntos, String reqObs, String linea) {
        this.idBoletin = idBoletin;
        this.codigoSancion = codigoSancion;
        this.fechaPublicacion = fechaPublicacion;
        this.idOrganismo=idOrganismo;
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

    public Multa(int id, int idBoletin, String codigoSancion, Date fechaPublicacion,int idOrganismo, String organismo, String boe, String fase, String tipoJuridico, int plazo, String expediente, Date fechaMulta, String articulo, String nif, String sancionado, String localidad, String matricula, String cuantia, String puntos, String reqObs, String linea) {
        this.id = id;
        this.idBoletin = idBoletin;
        this.codigoSancion = codigoSancion;
        this.fechaPublicacion = fechaPublicacion;
        this.idOrganismo=idOrganismo;
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

    public int getIdOrganismo() {
        return idOrganismo;
    }

    public void setIdOrganismo(int idOrganismo) {
        this.idOrganismo = idOrganismo;
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

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
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
        this.nif = checkNif(limpia(nif));
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
        this.matricula = limpia(matricula);
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

    @Override
    public String toString() {
        return "Multa{" + "id=" + id + ", idBoletin=" + idBoletin + ", codigoSancion=" + codigoSancion + ", fechaPublicacion=" + fechaPublicacion + ", organismo=" + organismo + ", boe=" + boe + ", fase=" + fase + ", tipoJuridico=" + tipoJuridico + ", plazo=" + plazo + ", expediente=" + expediente + ", fechaMulta=" + fechaMulta + ", articulo=" + articulo + ", nif=" + nif + ", sancionado=" + sancionado + ", localidad=" + localidad + ", matricula=" + matricula + ", cuantia=" + cuantia + ", puntos=" + puntos + ", reqObs=" + reqObs + ", linea=" + linea + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.idBoletin;
        hash = 67 * hash + Objects.hashCode(this.expediente);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Multa other = (Multa) obj;
        if (this.idBoletin != other.idBoletin) {
            return false;
        }
        if (!Objects.equals(this.expediente, other.expediente)) {
            return false;
        }
        return true;
    }
    
    private String limpia(String str) {
        Pattern p = Pattern.compile("[^0-9A-Z]");
        Matcher m = p.matcher(str);

        if (m.find()) {
            str = m.replaceAll("");
        }
        return str.trim();
    }
    
    private String checkNif(String nif) {
        CalculaNif cn = new CalculaNif();
        String aux;

        try {
            if (cn.isvalido(nif)) {
                aux = nif;
            } else {
                aux = cn.calcular(nif);
            }
            return aux;
        } catch (Exception ex) {
            return nif;
        }
    }

    public String SQLCrear() {
        if (this.fechaMulta != null) {
            return "INSERT into " + Var.nombreBD + ".multa (idBoletin,codigoSancion,fechaPublicacion,idOrganismo,organismo,boe,fase,tipoJuridico,plazo,expediente,fechaMulta,articulo,nif,sancionado,localidad,matricula,cuantia,puntos,reqObs,linea) values("
                    + this.idBoletin + ","
                    + Varios.entrecomillar(this.codigoSancion) + ","
                    + Varios.entrecomillar(Dates.imprimeFecha(fechaPublicacion)) + ","
                    + this.idOrganismo + ","
                    + Varios.entrecomillar(this.organismo.replace("'", "\\'")) + ","
                    + Varios.entrecomillar(this.boe) + ","
                    + Varios.entrecomillar(this.fase) + ","
                    + Varios.entrecomillar(this.tipoJuridico) + ","
                    + this.plazo + ","
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
        } else {
            return "INSERT into " + Var.nombreBD + ".multa (idBoletin,codigoSancion,fechaPublicacion,idOrganismo,organismo,boe,fase,tipoJuridico,plazo,expediente,fechaMulta,articulo,nif,sancionado,localidad,matricula,cuantia,puntos,reqObs,linea) values("
                    + this.idBoletin + ","
                    + Varios.entrecomillar(this.codigoSancion) + ","
                    + Varios.entrecomillar(Dates.imprimeFecha(fechaPublicacion)) + ","
                    + this.idOrganismo + ","
                    + Varios.entrecomillar(this.organismo) + ","
                    + Varios.entrecomillar(this.boe) + ","
                    + Varios.entrecomillar(this.fase) + ","
                    + Varios.entrecomillar(this.tipoJuridico) + ","
                    + this.plazo + ","
                    + Varios.entrecomillar(this.expediente) + ","
                    + null + ","
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
}
