package boes;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import main.*;
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
public class Publicacion {

    String origen;
    String datos;
    List boletines;
    Date fecha;
    List pdfs;

    public Publicacion(String datos, Date fecha) {
        this.fecha = fecha;
        boletines = new ArrayList();
        pdfs = new ArrayList();
        StringBuilder buffer = new StringBuilder();
        boolean print = false;
        String linea;
        String[] split = datos.split(System.getProperty("line.separator"));

        for (String a : split) {

            if (a.contains("<a name=\"\">")) {
                splitOrigen(a);
            }

            if (a.contains("<li class=\"notif\">")) {
                print = true;
            }

            if (print) {
                buffer.append(a);
                buffer.append(System.getProperty("line.separator"));
            }

            if (a.contains("</li>")) {
                print = false;
                linea = buffer.toString();
                boletines.add(linea);
                buffer = new StringBuilder();
            }
        }

        splitPdf();
    }

    private void splitOrigen(String origen) {
        origen = origen.replace("<a name=\"\">", "");
        origen = origen.replace("</a>", "");
        this.origen = origen.trim();
    }

    private void splitPdf() {
        Pdf pdf;
        Iterator it = boletines.iterator();

        while (it.hasNext()) {
            pdf = new Pdf((String) it.next(), this.origen, this.fecha);
            pdfs.add(pdf);
        }
    }

    public void listarPdf() {
        Pdf pd;
        Iterator it = pdfs.iterator();

        while (it.hasNext()) {
            pd = (Pdf) it.next();
            System.out.println(pd.getCodigo());
            System.out.println(pd.getOrigen());
            System.out.println(pd.getDescripcion());
            System.out.println(pd.getLink());
            System.out.println("------------------------------------------------");
        }
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public List getBoletines() {
        return boletines;
    }

    public void setBoletines(List boletines) {
        this.boletines = boletines;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List getPdfs() {
        return pdfs;
    }

    public void setPdfs(List pdfs) {
        this.pdfs = pdfs;
    }
}
