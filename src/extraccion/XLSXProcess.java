package extraccion;

import enty.Multa;
import enty.Procesar;
import enty.StrucData;
import enty.VistaExtraccion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import util.Dates;

/**
 *
 * @author Agarimo
 */
public class XLSXProcess {

    List<Row> rows;
    Procesar pr;
    VistaExtraccion ve;
    StrucData sd;
    private int contador;

    public XLSXProcess(List<Row> rows, Procesar pr, VistaExtraccion ve, StrucData sd) {
        this.rows = rows;
        this.pr = pr;
        this.ve = ve;
        this.sd = sd;
        contador = 1;
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

        System.out.println(multas.size());

    }

    private List<Multa> clearMultas(List<Multa> multas) {
        LinkedHashSet hs = new LinkedHashSet();
        hs.addAll(multas);
        multas.clear();
        multas.addAll(hs);

        return multas;
    }

    private Multa splitLinea(Row linea) {
        Multa multa = new Multa();

        multa.setIdBoletin(pr.getId());
        multa.setCodigoSancion(getCodigoMulta());
        multa.setFechaPublicacion(ve.getFecha());
        multa.setOrganismo(ve.getOrigen());
        multa.setBoe(ve.getBoe());
        multa.setFase(ve.getFase());
        multa.setTipoJuridico("-"); //Por determinar.
        multa.setPlazo(ve.getPlazo());

        multa.setExpediente(getCelda(linea, sd.expediente));
        multa.setFechaMulta(setFecha(getCelda(linea, sd.fechaMulta))); //hacer método.
        multa.setArticulo(getCelda(linea, sd.articulo));
        multa.setNif(getCelda(linea, sd.nif)); //¿Comprobar nif?
        multa.setSancionado(getCelda(linea, sd.sancionado));
        multa.setLocalidad(getCelda(linea, sd.localidad));
        multa.setMatricula(getCelda(linea, sd.matricula));
        multa.setCuantia(getCelda(linea, sd.cuantia));
        multa.setPuntos(getCelda(linea, sd.puntos));
        multa.setReqObs(getCelda(linea, sd.reqObs));
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

        return sb.toString();
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
        return sb.toString();
    }

    private Date setFecha(String fecha) {
        //Crear listado de formatos e ir rellenando;
        Date date;

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date = formato.parse(fecha);
        } catch (ParseException ex) {
            date = null;
        }

        return date;
    }

}
