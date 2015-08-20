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
    List provincias;
    MultiValueMap map;

    public Union(Date fecha) {
        this.fecha = fecha;
        this.provincias=SqlBoe.listaProvinciasDia(fecha);
    }
    
    public MultiValueMap cargaMap(String codigoProv) {
        MultiValueMap mp = new MultiValueMap();
        ModeloUnion aux;
        Iterator it = SqlBoe.listaUnion("SELECT * FROM " + Variables.nombreBD + ".vista_union "
                + "where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha))+" "
                + "and codigoProv="+Varios.entrecomillar(codigoProv)).iterator();

        while (it.hasNext()) {
            aux = (ModeloUnion) it.next();
            mp.put(aux.getEstructura(), aux);
        }

        return mp;
    }
    
    public void setMap(MultiValueMap aux){
        this.map=aux;
    }
    
    public List getProvincias(){
        return this.provincias;
    }

    public List getKeySet() {
        return new ArrayList(map.keySet());
    }

    public List getBoletines(int aux) {
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
