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
    MultiValueMap map;

    public Union(Date fecha) {
        this.fecha = fecha;
        reparteBolProv(cargaMap());
    }

    private MultiValueMap cargaMap() {
        MultiValueMap mp = new MultiValueMap();
        ModeloUnion aux;
        Iterator it = SqlBoe.listaUnion("SELECT * FROM " + Variables.nombreBD + ".vista_union where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha))).iterator();

        while (it.hasNext()) {
            aux = (ModeloUnion) it.next();
            mp.put(aux.getCodigoProv(), aux);
        }

        return mp;
    }
    
    private void reparteBolProv(MultiValueMap mapProv){
        MultiValueMap tmp= new MultiValueMap();
        String aux;
        Iterator it=new ArrayList(mapProv.keySet()).iterator();
        
        while(it.hasNext()){
            aux=(String) it.next();
            tmp.put(aux, buildMap(mapProv,aux));
        }
        
        map=tmp;
    }
    
    private MultiValueMap buildMap(MultiValueMap mapProv,String prov){
        ModeloUnion un;
        MultiValueMap aux = new MultiValueMap();
        
        Iterator it = mapProv.iterator(prov);

        while (it.hasNext()) {
            un = (ModeloUnion) it.next();
            aux.put(un.getEstructura(), un);
        }
        
        return aux;
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
    
    public MultiValueMap getMap(){
        return this.map;
    }
    
    
}
