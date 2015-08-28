package boe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Variables;
import util.Dates;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Boe {

    int id;
    Date fecha;
    String link;
    boolean isClas;
    List pb;

    public Boe() {

    }

    public Boe(Date fecha) {
        this.fecha = fecha;
        pb = new ArrayList();
    }

    public Boe(Date fecha, String link) {
        this.fecha = fecha;
        this.link = link;
        this.isClas = false;
        pb = new ArrayList();
    }

    public Boe(int id, Date fecha, String link, boolean isProc) {
        this.id = id;
        this.fecha = fecha;
        this.link = link;
        this.isClas = isProc;
        pb = new ArrayList();
    }

    public void getBoletines() {
        DataCnt aux;
        Publicacion pl;
        Iterator it;

        it = separaObjetos(getDatosBoe(link)).iterator();

        while (it.hasNext()) {
            aux=(DataCnt) it.next();
            pl = new Publicacion(aux.getEntidad(),aux.getDatos(), fecha);
            pb.add(pl);
        }
    }

    private String getDatosBoe(String url) {
        boolean print = false;
        String inputLine;
        StringBuilder buffer = new StringBuilder();

        try {
            URL enl = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(enl.openStream()));

            while ((inputLine = in.readLine()) != null) {

                if (inputLine.contains("<div class=\"SumarioNotif\">")) {
                    print = true;
                }

                if (print) {
                    buffer.append(inputLine);
                    buffer.append(System.getProperty("line.separator"));
                }

                if (inputLine.contains("<div class=\"espacio\">")) {
                    print = false;
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Boe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Boe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buffer.toString();
    }

    private List separaObjetos(String datos) {
        List aux = new ArrayList();
        DataCnt dc = new DataCnt();
        String entidad = "";
        boolean print = false;
        boolean printP = false;
        StringBuilder buffer = new StringBuilder();
        StringBuilder bufferP = new StringBuilder();
        String[] a;

        a = datos.split(System.getProperty("line.separator"));

        for (String a1 : a) {

            if (a1.contains("<h5>")) {
                printP = true;
            }

            if (printP) {
                bufferP.append(a1);
                bufferP.append(System.getProperty("line.separator"));
            }

            if (a1.contains("</h5>")) {
                printP = false;
                entidad=bufferP.toString();
                bufferP= new StringBuilder();
            }

            if (a1.contains("<h6>")) {
                print = true;
            }

            if (print) {
                buffer.append(a1);
                buffer.append(System.getProperty("line.separator"));
            }

            if (a1.contains("</ul>")) {
                print = false;
                dc.setDatos(buffer.toString());
                dc.setEntidad(entidad);
                aux.add(dc);
                dc=new DataCnt();
                buffer = new StringBuilder();
            }
        }
        return aux;
    }

    public List getPb() {
        return pb;
    }

    public void setPb(List pb) {
        this.pb = pb;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getLink() {
        return link;
    }

    public boolean isIsClas() {
        return isClas;
    }

    @Override
    public String toString() {
        return Dates.imprimeFecha(fecha);
    }

    public String SQLCrear() {
        return "INSERT into " + Variables.nombreBD + ".boe (fecha,link,isClas) values("
                + Varios.entrecomillar(Dates.imprimeFecha(this.fecha)) + ","
                + Varios.entrecomillar(this.link) + ","
                + this.isClas
                + ");";
    }

    public String SQLBuscar() {
        return "SELECT * FROM " + Variables.nombreBD + ".boe WHERE fecha=" + util.Varios.entrecomillar(Dates.imprimeFecha(this.fecha));
    }
    
    public String SQLSetClas(){
        return "UPDATE "+Variables.nombreBD + ".boe SET isClas=1 where fecha="+Varios.entrecomillar(Dates.imprimeFecha(fecha));
    }

}
