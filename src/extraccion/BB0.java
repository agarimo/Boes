package extraccion;

import enty.Multa;
import enty.Procesar;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import main.SqlBoe;
import main.Variables;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public final class BB0 {

    Date fecha;
    List<Procesar> boletines;
    List<String[]> data;

    public BB0(Date fecha) {
        this.fecha = fecha;
        data= new ArrayList();
        this.boletines = SqlBoe
                .listaProcesar("SELECT * FROM " + Variables.nombreBD + ".procesar "
                        + "WHERE fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha))
                        + " AND estado!=1");
        procesar();
    }
    
    public List<String[]> getData(){
        return this.data;
    }
    
    public String getLineaBB0(String[] linea){
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < linea.length-2; i++) {
            sb.append(linea[i]);
            
            if(i!=23){
                sb.append("|");
            }
            
        }
        return sb.toString();
    }
    
    public String getLineaBB1(String[] linea){
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < linea.length; i++) {
            sb.append(linea[i]);
            
            if(i!=25){
                sb.append("|");
            }
            
        }
        return sb.toString();
    }

    private void procesar() {
        data.clear();
        Procesar aux;
        Iterator<Procesar> it = boletines.iterator();

        while (it.hasNext()) {
            aux = it.next();
            getDatos(aux);
        }
    }

    private void getDatos(Procesar pr) {
        String[] linea;
        Multa multa;
        List<Multa> multas = SqlBoe.listaMultas("SELECT * FROM " + Variables.nombreBD + ".multa WHERE idBoletin="+pr.getId());

        Iterator<Multa> it=multas.iterator();
        
        while(it.hasNext()){
            linea = new String[26];
            multa=it.next();
            
            linea[0]="00000";
            linea[1]=Dates.imprimeFecha(pr.getFecha());
            linea[2]=multa.getBoe();
            linea[3]=multa.getFase();
            linea[4]=multa.getTipoJuridico();
            linea[5]=Integer.toString(multa.getPlazo());
            linea[6]="  ";
            linea[7]="  ";
            linea[8]=splitCodigoSancion(multa.getCodigoSancion());
            linea[9]=multa.getExpediente();
            linea[10]=multa.getExpediente();
            linea[11]=Dates.imprimeFecha(multa.getFechaMulta(), "ddMMyy");
            linea[12]=multa.getArticulo();
            linea[13]=multa.getSancionado();
            linea[14]=multa.getMatricula();
            linea[15]=multa.getNif();
            linea[16]=multa.getOrganismo();
            linea[17]=multa.getPuntos();
            linea[18]="  ";
            linea[19]="  ";
            linea[20]="  ";
            linea[21]="  ";
            linea[22]="  ";
            linea[23]=multa.getLinea();
            linea[24]=pr.getLink();
            linea[25]=multa.getLocalidad();
            
            data.add(linea);
        }
    }
    
    private String splitCodigoSancion(String codigo){
        String[] split = codigo.split("-");
        return split[2];
    }
}
