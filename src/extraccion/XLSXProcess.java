package extraccion;

import enty.Procesar;
import enty.StrucData;
import enty.VistaExtraccion;
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
        
    }

}
