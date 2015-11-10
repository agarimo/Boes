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
import main.Regex;
import main.SqlBoe;
import main.Var;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import util.CalculaNif;
import util.Dates;
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
    List<String> header;
    private int contador;

    public XLSXProcess(List<Row> rows, Procesar pr, VistaExtraccion ve, StrucData sd) {
        this.rows = rows;
        this.pr = pr;
        this.ve = ve;
        this.sd = sd;
        contador = 1;
        strucFecha = SqlBoe.listaEstructurasFechas();
        header = SqlBoe.listaEstructurasHeader();
    }

    public List<Multa> splitXLSX() {
        Regex rx = new Regex();
        List<Multa> multas = new ArrayList();
        String estructura = SqlBoe.getEstructura(pr.getEstructura());
        Multa multa;
        Row linea;
        Iterator<Row> it = rows.iterator();

        while (it.hasNext()) {
            linea = it.next();

            if (!getLinea(linea).equals(estructura)) {
                try {
                    multa = splitLinea(linea);

                    if (!header.contains(multa.getExpediente())) {
                        multas.add(multa);

                        if (sd.fechaMulta != 0) {
                            if (multa.getFechaMulta() == null) {
                                if (!isFecha(getCelda(linea, sd.fechaMulta))) {
                                    multa=new Multa();
                                    multas.add(multa);
                                }
                            }
                        }
                    }

                } catch (IndexOutOfBoundsException ex) {
                    multa = new Multa();
                    multas.add(multa);
                    ex.printStackTrace();
                }
            }
        }

        multas = clearMultas(multas);

        return multas;
    }

    public static void insertMultas(List<Multa> multas) {
        Sql bd;
        Multa multa;
        Iterator<Multa> it = multas.iterator();

        try {
            bd = new Sql(Var.con);

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
        multa.setIdOrganismo(ve.getIdOrigen());
        multa.setOrganismo(ve.getOrigen());
        multa.setBoe(ve.getBoe());
        multa.setFase(ve.getFase());
        if (sd.nif != 0) {
            multa.setTipoJuridico(setTipoJuridico(getCelda(linea, sd.nif)));
        } else {
            multa.setTipoJuridico("P");
        }
        multa.setPlazo(ve.getPlazo());

        if (sd.expediente != 0) {
            multa.setExpediente(getCelda(linea, sd.expediente).trim().toUpperCase());
        } else {
            multa.setExpediente("");
        }

        if (sd.fechaMulta != 0) {
            multa.setFechaMulta(setFecha(getCelda(linea, sd.fechaMulta)));
        } else {
            multa.setFechaMulta(null);
        }

        if (sd.preceptoA != 0) {
            prec += "" + getCelda(linea, sd.preceptoA).trim();
        }

        if (sd.preceptoB != 0) {
            prec += "" + getCelda(linea, sd.preceptoB).trim();
        }

        if (sd.preceptoC != 0) {
            prec += "" + getCelda(linea, sd.preceptoC).trim();
        }

        if (sd.articuloA != 0) {
            art += "" + getCelda(linea, sd.articuloA).trim();
        }

        if (sd.articuloB != 0) {
            art += "." + getCelda(linea, sd.articuloB).trim();
        }

        if (sd.articuloC != 0) {
            art += "." + getCelda(linea, sd.articuloC).trim();
        }

        if (sd.articuloD != 0) {
            art += "" + getCelda(linea, sd.articuloD).trim();
        }

        multa.setArticulo((art.trim() + " " + prec.trim()).toUpperCase());

        if (sd.nif != 0) {
            multa.setNif(getCelda(linea, sd.nif).trim().toUpperCase());
        } else {
            multa.setNif("");
        }

        if (sd.sancionado != 0) {
            multa.setSancionado(getCelda(linea, sd.sancionado).trim().toUpperCase());
        } else {
            multa.setSancionado("");
        }

        if (sd.localidad != 0) {
            multa.setLocalidad(getCelda(linea, sd.localidad).trim().toUpperCase());
        } else {
            multa.setLocalidad("");
        }

        if (sd.matricula != 0) {
            multa.setMatricula(getCelda(linea, sd.matricula).trim().toUpperCase());
        } else {
            multa.setMatricula("");
        }

        if (sd.cuantia != 0) {
            multa.setCuantia(getCelda(linea, sd.cuantia).trim().toUpperCase());
        } else {
            multa.setCuantia("");
        }

        if (sd.puntos != 0) {
            multa.setPuntos(getCelda(linea, sd.puntos).trim().toUpperCase());
        } else {
            multa.setPuntos("");
        }

        if (sd.reqObs != 0) {
            multa.setReqObs(getCelda(linea, sd.reqObs).trim().toUpperCase());
        } else {
            multa.setReqObs("");
        }

        multa.setLinea(getLinea(linea));

        return multa;
    }

    private String getCelda(Row linea, int celda) {
        StringBuilder sb = new StringBuilder();
        Cell cell = linea.getCell(celda - 1);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        sb.append(cell.getStringCellValue());

        return sb.toString().trim().replace("'", "\\'");
    }

    private String getCodigoMulta() {
        String aux = this.ve.getCodigo().replace("BOE-N-20", "");
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
            celda.setCellType(Cell.CELL_TYPE_STRING);
            sb.append(celda.getStringCellValue());
            sb.append(" ");
        }
        return sb.toString().trim().replace("'", "\\'");
    }

    private boolean isFecha(String fecha) {
        Iterator<String> it = strucFecha.iterator();
        SimpleDateFormat formato;
        String aux;
        Date dat;
        boolean found = false;

        while (it.hasNext()) {
            aux = it.next();
            formato = new SimpleDateFormat(aux);
            formato.setLenient(false);

            try {
                dat = formato.parse(fecha);
                found = true;
            } catch (ParseException ex) {
                //
            }
        }
        return found;
    }

    private Date setFecha(String fecha) {
        Iterator<String> it = strucFecha.iterator();
        SimpleDateFormat formato;
        String aux;
        Date date = null;

        while (it.hasNext()) {
            aux = it.next();
            formato = new SimpleDateFormat(aux);
            formato.setLenient(false);

            try {
                date = formato.parse(fecha);
            } catch (ParseException ex) {
                //
            }
        }
        return date;
    }

    private String setTipoJuridico(String nif) {
        CalculaNif cn = new CalculaNif();
        return cn.getTipoJuridico(nif);
    }
}
