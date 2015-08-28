package enty;

/**
 *
 * @author Agarimo
 */
public class Procesar {

    int id;
    String codigo;
    String link;
    int estructura;
    /**
     * Estado= 0 (Sin procesar) Estado=1 (Generado PDF) Estado=2 (Procesado
     * Excel)
     */
    int estado;

    public Procesar() {

    }

    public Procesar(String codigo, String link, int estructura, int estado) {
        this.codigo = codigo;
        this.link = link;
        this.estructura = estructura;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getEstado() {
        return estado;
    }

    public int getEstructura() {
        return estructura;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setEstructura(int estructura) {
        this.estructura = estructura;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return this.codigo;
    }

}
