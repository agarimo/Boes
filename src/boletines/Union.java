package boletines;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.SqlBoe;
import main.Variables;
import model.ModeloBoletines;
import model.ModeloUnion;
import org.apache.commons.collections4.map.MultiValueMap;
import util.Dates;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Union {

    Date fecha;
    List boletines;
    MultiValueMap map;

    public Union(Date fecha) {
        this.fecha = fecha;
        this.boletines = cargaBoletines();
        this.map = cargaMap();
    }

    private List cargaBoletines() {
        return SqlBoe.listaUnion("SELECT * FROM " + Variables.nombreBD + ".vista_union where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha)));
    }

    private MultiValueMap cargaMap() {
        MultiValueMap mp = new MultiValueMap();
        ModeloUnion aux;
        Iterator it = boletines.iterator();

        while (it.hasNext()) {
            aux = (ModeloUnion) it.next();
            mp.put(aux.getCodigoUn(), aux);
        }

        return mp;
    }

    public MultiValueMap getMap() {
        return this.map;
    }

    public List getKeySet() {
        return new ArrayList(map.keySet());
    }

    public List getBoletines(String aux) {
        List list = new ArrayList();
        ModeloBoletines bol;
        ModeloUnion un;
        Iterator it = map.iterator(aux);

        while (it.hasNext()) {
            un = (ModeloUnion) it.next();
            bol = SqlBoe.getModeloBoletines(un.getCodigo());
            list.add(bol);
        }

        return list;
    }

    public void run() {
        ModeloUnion un;
        Iterator in;
        Iterator it = map.keySet().iterator();

        while (it.hasNext()) {
            String aux = (String) it.next();
            in = map.iterator(aux);
            System.out.println(aux);
            System.out.println("--------------------------");

            while (in.hasNext()) {
                un = (ModeloUnion) in.next();
                System.out.println(un.getCodigo());
            }

            System.out.println("--------------------------");
            System.out.println("--------------------------");
        }
    }
}
