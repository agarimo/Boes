package boletines;

import enty.Origen;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import main.SqlBoe;
import main.Variables;

/**
 *
 * @author Agarimo
 */
public class Union {
    
    Date fecha;
    int idEntidad;
    List union;
    
    public Union(Date fecha){
        this.fecha=fecha;
    }
    
    public Union(Date fecha, int idEntidad){
        this.fecha=fecha;
        this.idEntidad=idEntidad;
        this.union= cargaUnion();
    }
    
    private List cargaUnion() {
        Origen or;
        List aux = new ArrayList();
        Iterator it;

        if (this.idEntidad == 0) {
            it = SqlBoe.listaOrigen("SELECT * FROM " + Variables.nombreBD + ".origen group by codigoUn;").iterator();
        } else {
            it = SqlBoe.listaOrigen("SELECT * FROM " + Variables.nombreBD + ".origen where idEntidad=" + this.idEntidad + " group by codigoUn;").iterator();
        }

        while (it.hasNext()) {
            or = (Origen) it.next();

            if (or.getCodigoUn() != null) {
                aux.add(or.getCodigoUn());
            }
        }
        return aux;
    }
    
    public void run(){
        
    }
    
}
