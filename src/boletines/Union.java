package boletines;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import main.SqlBoe;
import main.Variables;
import model.ModeloBoletines;
import model.ModeloUnion;
import org.apache.commons.collections4.map.MultiValueMap;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Union {

    Date fecha;
    List estructuras;
    MultiValueMap map;

    public Union(Date fecha) {
        this.fecha = fecha;
        this.estructuras = SqlBoe.listaEstructurasDia(fecha);
    }

    public MultiValueMap cargaMap(String estructura) {
        MultiValueMap mp = new MultiValueMap();
        ModeloUnion aux;
        Iterator it;

        if (estructura == null) {
            it = SqlBoe.listaUnion("SELECT * FROM " + Variables.nombreBD + ".vista_union "
                    + "where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha)) + " "
                    + "and isEstructura is null").iterator();
        } else {
            it = SqlBoe.listaUnion("SELECT * FROM " + Variables.nombreBD + ".vista_union "
                    + "where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha)) + " "
                    + "and isEstructura=" + Varios.entrecomillar(estructura)).iterator();
        }

        while (it.hasNext()) {
            aux = (ModeloUnion) it.next();
            mp.put(aux.getCodigoUn(), aux);
        }

        return mp;
    }

    public void setMap(MultiValueMap aux) {
        this.map = aux;
    }

    public List getEstructuras() {
        return this.estructuras;
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
}
