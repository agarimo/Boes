package extraccion;

import enty.Multa;
import enty.OrigenExpediente;
import java.util.Iterator;
import java.util.List;
import main.SqlBoe;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author Agarimo
 */
public class ScriptExp {
    List<Multa> listado;
    MultiValueMap map;
    
    public ScriptExp(){
        map=cargaMap();
        
    }
    
    public void run(){
        
    }
    
    private MultiValueMap cargaMap(){
        OrigenExpediente aux;
        MultiValueMap mp=new MultiValueMap();
        List<OrigenExpediente> list= SqlBoe.listaOrigenExp();
        Iterator<OrigenExpediente> it = list.iterator();
        
        while(it.hasNext()){
            aux=it.next();
            mp.put(aux.getIdOrigen(), aux);
        }
        
        
        return mp;
    }
    
}
