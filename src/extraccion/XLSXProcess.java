package extraccion;

import enty.Multa;
import enty.Procesar;
import enty.StrucData;
import enty.VistaExtraccion;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Agarimo
 */
public class XLSXProcess {

    List<Row> rows;
    Procesar pr;
    VistaExtraccion ve;
    StrucData sd;

    public XLSXProcess(List<Row> rows, Procesar pr, VistaExtraccion ve, StrucData sd) {
        this.rows = rows;
        this.pr = pr;
        this.ve = ve;
        this.sd = sd;
    }
    
    public void run(){
        //contador para el código de sanción, global para la sesión y por día.
        //Contador con, fecha/número boletin/número de linea
        Row linea;
        Iterator<Row> it = rows.iterator();
        
        while(it.hasNext()){
            linea=it.next();
        }
    }
    
    private Multa splitLinea(){
        Multa multa=new Multa();
        
        
        return multa;
    }
    
    

}
