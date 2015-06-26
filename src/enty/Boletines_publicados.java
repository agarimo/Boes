package enty;

import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Boletines_publicados {
    private int id;
    private String entidad;
    private String origen;
    private String codigo;
    private String descripcion;
    private String fecha;
    private int tipo;
    
    public Boletines_publicados(){
        
    }

    public Boletines_publicados(String entidad, String origen, String codigo, String descripcion, String fecha, int tipo) {
        this.entidad = entidad;
        this.origen = origen;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Boletines_publicados(int id, String entidad, String origen, String codigo, String descripcion, String fecha, int tipo) {
        this.id = id;
        this.entidad = entidad;
        this.origen = origen;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
    public String SQLCrear(){
        return "INSERT into stats.boletines_publicados (entidad,origen,codigo,descripcion,fecha,tipo) values("
                + Varios.entrecomillar(this.entidad) + ","
                + Varios.entrecomillar(this.origen) + ","
                + Varios.entrecomillar(this.codigo) + ","
                + Varios.entrecomillar(this.descripcion) + ","
                + Varios.entrecomillar(this.fecha) + ","
                + this.tipo
                + ");";
    }
}
