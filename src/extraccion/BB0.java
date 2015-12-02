package extraccion;

import boletines.Archivos;
import boletines.Union;
import enty.Multa;
import enty.Procesar;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import main.SqlBoe;
import main.Var;
import model.ModeloBoletines;
import util.Dates;
import util.Files;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public final class BB0 {

    private final File fichero;
    private final Date fecha;
    private final List<Procesar> boletines;
    private final List<ModeloBoletines> boletinesD;
    private final List<String[]> data;

    private final int BB0 = 1;
    private final int BB1 = 2;

    public BB0(int id) {
        fecha = null;
        fichero = null;
        boletines = null;
        boletinesD = null;
        data = new ArrayList();
        printLinea(id);
    }

    public void printLinea(int id) {
        String[] linea;
        Multa multa = SqlBoe.getMulta("SELECT * FROM " + Var.nombreBD + ".multa WHERE id=" + id);
        linea = new String[26];

        linea[0] = "00000";
        linea[1] = "ESPACIO PARA LA FECHA";
        linea[2] = multa.getBoe();
        linea[3] = multa.getFase();
        linea[4] = multa.getTipoJuridico();
        linea[5] = Integer.toString(multa.getPlazo());
        linea[6] = Integer.toString(multa.getId());
        linea[7] = "ND";
        linea[8] = splitCodigoSancion(multa.getCodigoSancion());
        linea[9] = multa.getExpediente();
        linea[10] = multa.getExpediente();
        linea[11] = Dates.imprimeFecha(multa.getFechaMulta(), "ddMMyy");
        linea[12] = multa.getArticulo();
        linea[13] = multa.getSancionado();
        linea[14] = multa.getMatricula();
        linea[15] = multa.getNif();
        linea[16] = multa.getOrganismo();
        linea[17] = multa.getCuantia();
        linea[18] = multa.getPuntos();
        linea[19] = Integer.toString(multa.getIdOrganismo());
        linea[20] = multa.getReqObs();
        linea[21] = "  ";
        linea[22] = "  ";
        linea[23] = multa.getLinea();
        linea[24] = "ESPACIO PARA EL LINK";
        linea[25] = multa.getLocalidad();

        for (int i = 0; i < linea.length; i++) {
            String linea1 = linea[i];

            if (linea1 != null) {
                String aux = linea1.replaceAll("\n", " ");
                aux = aux.replaceAll(System.lineSeparator(), " ");
                linea[i] = aux;
            }
        }

        System.out.println(getLinea(linea, this.BB0));
    }

    public BB0(Date fecha) {
        this.fecha = fecha;
        data = new ArrayList();
        this.boletines = SqlBoe
                .listaProcesar("SELECT * FROM " + Var.nombreBD + ".procesar "
                        + "WHERE fecha=" + Varios.entrecomillar(Dates.imprimeFecha(this.fecha))
                        + " AND estado!=1");
        this.boletinesD = SqlBoe
                .listaModeloBoletines("SELECT * FROM boes.vista_boletines "
                        + "where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(this.fecha)) + " "
                        + "and codigo in (select codigo from boes.procesar where estructura=-1 and estado=1)");
        fichero = new File(Var.ficheroTxt, Dates.imprimeFecha(fecha));
        fichero.mkdirs();
    }

    public void run() {
        data.clear();
        Procesar aux;
        Iterator<Procesar> it = boletines.iterator();

        while (it.hasNext()) {
            aux = it.next();
            getDatos(aux);
        }
        crearArchivos();
        runUnion();
    }

    private String getLinea(String[] linea, int tipo) {
        StringBuilder sb = new StringBuilder();

        switch (tipo) {
            case BB0:
                for (int i = 0; i < linea.length - 2; i++) {
                    sb.append(linea[i]);

                    if (i != 23) {
                        sb.append("|");
                    }
                }
                break;

            case BB1:
                for (int i = 0; i < linea.length; i++) {
                    sb.append(linea[i]);

                    if (i != 25) {
                        sb.append("|");
                    }
                }
                break;
        }
        return sb.toString();
    }

    private void getDatos(Procesar pr) {
        String[] linea;
        Multa multa;
        List<Multa> multas = SqlBoe.listaMultas("SELECT * FROM " + Var.nombreBD + ".multa WHERE idBoletin=" + pr.getId());

        Iterator<Multa> it = multas.iterator();

        while (it.hasNext()) {
            linea = new String[26];
            multa = it.next();

            linea[0] = "00000";
            linea[1] = Dates.imprimeFecha(pr.getFecha(), "dd/MM/yyyy");
            linea[2] = multa.getBoe();
            linea[3] = multa.getFase();
            linea[4] = multa.getTipoJuridico();
            linea[5] = Integer.toString(multa.getPlazo());
            linea[6] = Integer.toString(multa.getId());
            linea[7] = "ND";
            linea[8] = splitCodigoSancion(multa.getCodigoSancion());
            linea[9] = multa.getExpediente();
            linea[10] = multa.getExpediente();
            linea[11] = Dates.imprimeFecha(multa.getFechaMulta(), "ddMMyy");
            linea[12] = multa.getArticulo();
            linea[13] = multa.getSancionado();
            linea[14] = multa.getMatricula();
            linea[15] = multa.getNif();
            linea[16] = multa.getOrganismo();
            linea[17] = multa.getCuantia();
            linea[18] = multa.getPuntos();
            linea[19] = Integer.toString(multa.getIdOrganismo());
            linea[20] = multa.getReqObs();
            linea[21] = " ";
            linea[22] = Integer.toString(1);
            linea[23] = multa.getLinea();
            linea[24] = pr.getLink();
            linea[25] = multa.getLocalidad();

            for (int i = 0; i < linea.length; i++) {
                String linea1 = linea[i];

                if (linea1 != null) {
                    String aux = linea1.replaceAll("\n", " ");
                    aux = aux.replaceAll(System.lineSeparator(), " ");
                    linea[i] = aux;
                }
            }

            data.add(linea);
        }
    }

    private String splitCodigoSancion(String codigo) {
        String[] split = codigo.split("-");
        return split[2];
    }

    private String getDataArchivos(int tipo) {
        StringBuilder sb;

        switch (tipo) {
            case BB0:
                sb = new StringBuilder();

                for (int i = 0; i < data.size(); i++) {
                    String[] arr = data.get(i);
                    sb.append(getLinea(arr, BB0));

                    if (i != data.size() - 1) {
                        sb.append(System.lineSeparator());
                    }
                }
                break;

            case BB1:
                sb = new StringBuilder();

                for (int i = 0; i < data.size(); i++) {
                    String[] arr = data.get(i);
                    sb.append(getLinea(arr, BB1));

                    if (i != data.size() - 1) {
                        sb.append(System.lineSeparator());
                    }
                }
                break;

            default:
                throw new IllegalArgumentException();
        }
        return sb.toString();
    }

    private void crearArchivos() {
        File archivoBB0 = new File(fichero, Dates.imprimeFechaSinFormato(fecha) + ".bb0");
        File archivoBB1 = new File(fichero, Dates.imprimeFechaSinFormato(fecha) + ".bb1");

        Files.escribeArchivo(archivoBB1, getDataArchivos(BB1));
        Files.escribeArchivo(archivoBB0, getDataArchivos(BB0));

        crearArchivosD(fichero);
    }

    private void crearArchivosD(File fichero) {
        Archivos ar = new Archivos(this.fecha, fichero, this.boletinesD);
        ar.creaArchivos();
    }

    private void runUnion() {
        String codigoUn, struc;
        Iterator it;
        Union un = new Union(fecha);
        List list = un.getEstructuras();

        for (Object list1 : list) {
            struc = (String) list1;
            un.setMap(un.cargaMap(struc));
            it = un.getKeySet().iterator();

            while (it.hasNext()) {
                codigoUn = (String) it.next();
                crearArchivoUnion(struc, codigoUn, un.getProcesar(codigoUn));
            }
        }
    }

    private void crearArchivoUnion(String struc, String codigoUn, List<Procesar> list) {
        data.clear();
        Procesar aux;
        Iterator<Procesar> it = list.iterator();

        while (it.hasNext()) {
            aux = it.next();
            getDatos(aux);
        }

        File archivo = new File(fichero, struc + codigoUn + ".bb0");
        Files.escribeArchivo(archivo, getDataArchivos(BB0));
    }
}
