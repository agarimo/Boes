package boletines;

import java.util.Date;

/**
 *
 * @author Agarimo
 */
public class Union {
    
    Date fecha;
    int idEntidad;
    
    public Union(Date fecha){
        this.fecha=fecha;
    }
    
    public Union(Date fecha, int idEntidad){
        this.fecha=fecha;
        this.idEntidad=idEntidad;
    }
}
