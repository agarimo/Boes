package main;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Conexion;

/**
 *
 * @author Agarimo
 */
public class Var {

    public static Conexion con;
    public static String nombreBD = "boes";
    public static String nombreBDStats = "boes_stats";
    public static File ficheroPdf;
    public static File ficheroTxt;
    public static File ficheroBoe;
    public static File ficheroUnion;
    public static File ficheroEx;
    public static boolean isClasificando;
    public static boolean isDownloading;

    public static void inicializar() {
        driver();
        setConexion();
        ficheroPdf = new File(new File("data"), "pdfData");
        ficheroTxt = new File(new File("data"), "txtData");
        ficheroBoe = new File(new File("data"), "boeData");
        ficheroUnion = new File(new File("data"), "unionData");
        ficheroEx = new File(new File("data"), "exData");
        isClasificando = false;
        isDownloading = false;
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

    private static void driver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Var.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void setConexion() {
        con = new Conexion();
        con.setDireccion("oficina.redcedeco.net");
//        con.setDireccion("192.168.1.40");
//        con.setDireccion("localhost");
        con.setUsuario("admin");
        con.setPass("IkuinenK@@m.s84");
        con.setPuerto("3306");
    }

    public enum Status {

        USER {
                    @Override
                    public String toString() {
                        return "USER";
                    }
                },
        SOURCE {
                    @Override
                    public String toString() {
                        return "SOURCE";
                    }
                },
        APP {
                    @Override
                    public String toString() {
                        return "APP";
                    }
                },
        DELETED {
                    @Override
                    public String toString() {
                        return "DELETED";
                    }
                },
        DUPLICATED {
                    @Override
                    public String toString() {
                        return "DUPLICATED";
                    }
                },
        PENDING {
                    @Override
                    public String toString() {
                        return "PENDING";
                    }
                }
    }
}
