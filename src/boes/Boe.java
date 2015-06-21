package boes;

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

/**
 *
 * @author Agarimo
 */
public class Boe {

    Date fecha;
    String link;
    List pb;

    public Boe(Date fecha, String link) {
        this.fecha = fecha;
        this.link = link;
        pb=new ArrayList();
    }

    public void getBoletines() {
        Publicacion pl;
        Iterator it;

        it = separaObjetos(getDatosBoe(link)).iterator();

        while (it.hasNext()) {
            pl = new Publicacion((String) it.next(), fecha);
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
        boolean print = false;
        StringBuilder buffer = new StringBuilder();
        String linea;
        String[] a;

        a = datos.split(System.getProperty("line.separator"));

        for (String a1 : a) {

            if (a1.contains("<h6>")) {
                print = true;
            }

            if (print) {
                buffer.append(a1);
                buffer.append(System.getProperty("line.separator"));
            }

            if (a1.contains("</ul>")) {
                print = false;
                linea = buffer.toString();
                aux.add(linea);
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
    
    
}
