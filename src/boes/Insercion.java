package boes;

import enty.Boletin;
import enty.Boletines_publicados;
import enty.Descarga;
import enty.Entidad;
import enty.Origen;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Variables;
import model.ModeloBoes;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Insercion {

    List select;
    List discard;
    Date fecha;

    public Insercion(List select, List discard, Date fecha) {
        this.select = select;
        this.discard = discard;
        this.fecha = fecha;
    }

    public void run() {
        runSelect();
        runDiscard();
        runClear();
    }

    private void runClear() {
        Boe boe = new Boe(this.fecha);
        Sql bd;

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(boe.SQLSetClas());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void runSelect() {
        ModeloBoes aux;
        guardaStatsS();
        Iterator it = select.iterator();

        while (it.hasNext()) {
            aux = (ModeloBoes) it.next();
            insertaSelected(aux);
        }
    }

    private void guardaStatsS() {
        Boletines_publicados bp;
        ModeloBoes aux;
        Iterator it = select.iterator();

        try {
            Sql bd = new Sql(Variables.con);

            while (it.hasNext()) {
                aux = (ModeloBoes) it.next();
                bp = new Boletines_publicados();
                bp.setEntidad(aux.getEntidad());
                bp.setOrigen(aux.getOrigen());
                bp.setCodigo(aux.getCodigo());
                bp.setDescripcion(aux.getDescripcion());
                bp.setFecha(aux.getFecha());
                bp.setTipo(1);

                bd.ejecutar(bp.SQLCrear());
            }

            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertaSelected(ModeloBoes aux) {
        Sql bd;
        Boletin bl = new Boletin();

        bl.setIdOrigen(insertaOrigen(aux.getEntidad(), aux.getOrigen()));
        bl.setIdBoe(getBoe(aux.getFecha()));
        bl.setIdDescarga(insertaDescarga(aux.getLink()));
        bl.setCodigo(aux.getCodigo());
        bl.setTipo("*711*");
        bl.setFase("BCN1LA01-(");
        bl.setEstado(0);

        try {
            bd = new Sql(Variables.con);
            bd.ejecutar(bl.SQLCrear());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int insertaOrigen(String entidad, String origen) {
        int aux = 0;
        Sql bd;
        Origen or = new Origen();
        or.setIdEntidad(insertaEntidad(entidad));
        or.setNombre(origen);

        try {
            bd = new Sql(Variables.con);
            aux = bd.buscar(or.SQLBuscar());

            if (aux == -1) {
                bd.ejecutar(or.SQLCrear());
                aux = bd.ultimoRegistro();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    private int insertaEntidad(String nombre) {
        int aux = 0;
        Sql bd;
        Entidad en = new Entidad();
        en.setNombre(nombre);

        try {
            bd = new Sql(Variables.con);
            aux = bd.buscar(en.SQLBuscar());

            if (aux == -1) {
                bd.ejecutar(en.SQLCrear());
                aux = bd.ultimoRegistro();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aux;
    }
    

    private int getBoe(String fecha) {
        int aux = 0;
        Sql bd;

        try {
            bd = new Sql(Variables.con);
            aux = bd.getInt("SELECT * FROM boes.boe where fecha=" + Varios.entrecomillar(fecha));
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aux;
    }

    private int insertaDescarga(String link) {
        int aux = 0;
        Descarga ds = new Descarga();
        ds.setLink(link);
        Sql bd;

        try {
            bd = new Sql(Variables.con);
            aux = bd.buscar(ds.SQLBuscar());

            if (aux == -1) {
                bd.ejecutar(ds.SQLCrear());
                aux = bd.ultimoRegistro();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    private void runDiscard() {
        guardaStatsD();
    }

    private void guardaStatsD() {
        Boletines_publicados bp;
        ModeloBoes aux;
        Iterator it = discard.iterator();

        try {
            Sql bd = new Sql(Variables.con);

            while (it.hasNext()) {
                aux = (ModeloBoes) it.next();
                bp = new Boletines_publicados();
                bp.setEntidad(aux.getEntidad());
                bp.setOrigen(aux.getOrigen());
                bp.setCodigo(aux.getCodigo());
                bp.setDescripcion(aux.getDescripcion());
                bp.setFecha(aux.getFecha());
                bp.setTipo(0);

                bd.ejecutar(bp.SQLCrear());
            }

            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
