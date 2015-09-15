package extraccion;

import enty.Multa;
import enty.Procesar;
import enty.StrucData;
import enty.VistaExtraccion;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.SqlBoe;
import main.Variables;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import util.CalculaNif;
import util.Sql;

/**
 *
 * @author Agarimo
 */
public class XLSXProcess {

    List<Row> rows;
    Procesar pr;
    VistaExtraccion ve;
    StrucData sd;
    List<String> strucFecha;
    private int contador;

    public XLSXProcess(List<Row> rows, Procesar pr, VistaExtraccion ve, StrucData sd) {
        this.rows = rows;
        this.pr = pr;
        this.ve = ve;
        this.sd = sd;
        contador = 1;
        strucFecha = SqlBoe.listaEstructurasFechas();
    }

    public void run() {
        List<Multa> multas = new ArrayList();
        Multa multa;
        Row linea;
        Iterator<Row> it = rows.iterator();

        while (it.hasNext()) {
            linea = it.next();
            multa = splitLinea(linea);
            multas.add(multa);
        }

        multas = clearMultas(multas);
        insertMultas(multas);
    }

    private void insertMultas(List<Multa> multas) {
        Sql bd;
        Multa multa;
        Iterator<Multa> it = multas.iterator();

        try {
            bd = new Sql(Variables.con);

            while (it.hasNext()) {
                multa = it.next();
                bd.ejecutar(multa.SQLCrear());
            }

            bd.close();

        } catch (SQLException ex) {
            Logger.getLogger(XLSXProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Multa> clearMultas(List<Multa> multas) {
        LinkedHashSet hs = new LinkedHashSet();
        hs.addAll(multas);
        multas.clear();
        multas.addAll(hs);

        return multas;
    }

    private Multa splitLinea(Row linea) {
        String prec = "";
        String art = "";
        Multa multa = new Multa();

        multa.setIdBoletin(pr.getId());
        multa.setCodigoSancion(getCodigoMulta());
        multa.setFechaPublicacion(ve.getFecha());
        multa.setOrganismo(ve.getOrigen());
        multa.setBoe(ve.getBoe());
        multa.setFase(ve.getFase());
        multa.setTipoJuridico(setTipoJuridico(getCelda(linea, sd.nif)));
        multa.setPlazo(ve.getPlazo());

        if (sd.expediente != 0) {
            multa.setExpediente(getCelda(linea, sd.expediente).trim());
        }
        if (sd.fechaMulta != 0) {
            System.out.println(getCelda(linea,sd.fechaMulta));
            multa.setFechaMulta(setFecha(getCelda(linea, sd.fechaMulta)));
        }
        
        if (sd.precepto != 0) {
            prec = getCelda(linea, sd.precepto).trim();
        }
        if (sd.articulo != 0) {
            art = getCelda(linea, sd.articulo).trim();
        }
        
        multa.setArticulo((art + " " + prec).trim());

        if (sd.nif != 0) {
            multa.setNif(setNif(getCelda(linea, sd.nif)).trim());
        }
        if (sd.sancionado != 0) {
            multa.setSancionado(getCelda(linea, sd.sancionado).trim());
        }
        if (sd.localidad != 0) {
            multa.setLocalidad(getCelda(linea, sd.localidad).trim());
        }
        if (sd.matricula != 0) {
            multa.setMatricula(getCelda(linea, sd.matricula).trim());
        }
        if (sd.cuantia != 0) {
            multa.setCuantia(getCelda(linea, sd.cuantia).trim());
        }
        if (sd.puntos != 0) {
            multa.setPuntos(getCelda(linea, sd.puntos).trim());
        }
        if (sd.reqObs != 0) {
            multa.setReqObs(getCelda(linea, sd.reqObs).trim());
        }

        multa.setLinea(getLinea(linea));

        return multa;
    }

    private String getCelda(Row linea, int celda) {
        StringBuilder sb = new StringBuilder();
        Cell cell = linea.getCell(celda - 1);

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    sb.append(cell.getDateCellValue());
                } else {
                    sb.append(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                sb.append(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                sb.append(cell.getBooleanCellValue());
                break;
        }

        return sb.toString().trim();
    }

    private String getCodigoMulta() {
        String aux = this.ve.getCodigo().replace("BOE-N-", "");

        aux = aux + "-" + Integer.toString(contador);
        contador++;

        return aux;
    }

    private String getLinea(Row linea) {
        Iterator<Cell> cellIterator = linea.cellIterator();
        Cell celda;

        StringBuilder sb = new StringBuilder();
        while (cellIterator.hasNext()) {
            celda = cellIterator.next();

            switch (celda.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(celda)) {
                        sb.append(celda.getDateCellValue());
                        sb.append(" ");
                    } else {
                        sb.append(celda.getNumericCellValue());
                        sb.append(" ");
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    sb.append(celda.getStringCellValue());
                    sb.append(" ");
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    sb.append(celda.getBooleanCellValue());
                    sb.append(" ");
                    break;
            }
        }
        return sb.toString().trim();
    }

    private Date setFecha(String fecha) {
        Iterator<String> it = strucFecha.iterator();
        String aux;
        Date date = null;

        while (it.hasNext()) {
            aux = it.next();

            SimpleDateFormat formato = new SimpleDateFormat(aux);

            try {
                date = formato.parse(fecha);
                break;
            } catch (ParseException ex) {
                date = null;
            }
        }

        return date;
    }

    private String setTipoJuridico(String nif) {
        CalculaNif cn = new CalculaNif();
        return cn.getTipoJuridico(nif);
    }

    private String setNif(String nif) {
        CalculaNif cn = new CalculaNif();
        String aux;

        if (nif.length() < 9) {
            nif = cn.completaCeros(nif, 9);
        }

        if (cn.isvalido(nif)) {
            aux = nif;
        } else {
            aux = cn.calcular(nif);
        }

        return aux;
    }

}
