package main;

import java.io.File;

/**
 *
 * @author Agarimo
 */
public class Variables {

    public static File ficheroPdf;
    public static File ficheroTxt;

    public static void inicializar() {
        ficheroPdf = new File(new File("data"), "pdfData");
        ficheroTxt = new File(new File("data"), "txtData");
        initFiles();
    }

    private static void initFiles() {
        if (!ficheroPdf.exists()) {
            ficheroPdf.mkdirs();
        }
        if (!ficheroTxt.exists()) {
            ficheroTxt.mkdirs();
        }
    }
}
