package enty;

/**
 *
 * @author Agarimo
 */
public class Boletin {
    int id;
    int idOrigen;
    int idBoe;
    String codigo;
    String fase;
    
    public Boletin(){
    }
    
    public Boletin(int id, int idOrigen, int idBoe, String codigo, String fase){
        this.id=id;
        this.idOrigen=idOrigen;
        this.idBoe=idBoe;
        this.codigo=codigo;
        this.fase=fase;
    }
}
