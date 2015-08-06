package boes;

import enty.Boletin;
import enty.Boletines_publicados;
import enty.Descarga;
import enty.Entidad;
import enty.Origen;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private Sql bd;

    public Insercion() {

    }

    //<editor-fold defaultstate="collapsed" desc="Comprobar Selección">
    public List limpiarDuplicadosLista(List list) {
        List lista = new ArrayList();
        ModeloBoes aux;
        Iterator it = list.iterator();

        try {
            bd = new Sql(Variables.con);

            while (it.hasNext()) {
                aux = (ModeloBoes) it.next();
                if (!checkSelected(aux)) {
                    lista.add(aux);
                }
            }

            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public List limpiarDuplicadosListaD(List list) {
        List lista = new ArrayList();
        ModeloBoes aux;
        Iterator it = list.iterator();

        try {
            bd = new Sql(Variables.con);

            while (it.hasNext()) {
                aux = (ModeloBoes) it.next();
                if (!checkSelectedD(aux)) {
                    lista.add(aux);
                }
            }

            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    private boolean checkSelected(ModeloBoes aux) throws SQLException {
        boolean is = false;

        int a = bd.buscar("SELECT * FROM " + Variables.nombreBD + ".boletin where codigo=" + Varios.entrecomillar(aux.getCodigo()));

        if (a > 0) {
            is = true;
        }

        return is;
    }
    
    private boolean checkSelectedD(ModeloBoes aux) throws SQLException {
        boolean is = false;

        int a = bd.buscar("SELECT * FROM stats.boletines_publicados where codigo=" + Varios.entrecomillar(aux.getCodigo()));

        if (a > 0) {
            is = true;
        }

        return is;
    }
//</editor-fold>

    public void insertaBoletin(ModeloBoes aux) {
        Boletin bl = new Boletin();

        try {
            bd = new Sql(Variables.con);

            bl.setIdOrigen(getIdOrigen(aux.getEntidad(), aux.getOrigen()));
            bl.setIdBoe(getIdBoe(aux.getFecha()));
            bl.setIdDescarga(getIdDescarga(aux.getCodigo(),aux.getLink()));
            bl.setCodigo(aux.getCodigo());
            bl.setTipo("*711*");
            bl.setFase("BCN1");
            bl.setEstado(0);
            bl.setIdioma(getIdioma(bl.getIdOrigen()));

            bd.ejecutar(bl.SQLCrear());
            bd.close();

        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Inserción">
    private int getIdOrigen(String entidad, String origen) throws SQLException {
        int aux;
        Origen or = new Origen();
        or.setIdEntidad(getIdEntidad(entidad));
        or.setNombre(origen.replace("'", "\\'"));

        aux = bd.buscar(or.SQLBuscar());

        if (aux == -1) {
            bd.ejecutar(or.SQLCrear());
            aux = bd.ultimoRegistro();
        }

        return aux;
    }

    private int getIdEntidad(String nombre) throws SQLException {
        int aux;
        Entidad en = new Entidad();
        en.setNombre(nombre.replace("'", "\\'"));

        aux = bd.buscar(en.SQLBuscar());

        if (aux == -1) {
            bd.ejecutar(en.SQLCrear());
            aux = bd.ultimoRegistro();
        }

        return aux;
    }

    private int getIdBoe(String fecha) throws SQLException {
        return bd.getInt("SELECT * FROM boes.boe where fecha=" + Varios.entrecomillar(fecha));
    }

    private int getIdioma(int idOrigen) throws SQLException {
        return bd.getInt("SELECT idioma FROM " + Variables.nombreBD + ".origen where id=" + idOrigen);
    }

    private int getIdDescarga(String codigo,String link) throws SQLException {
        int aux;
        Descarga ds = new Descarga();
        ds.setCodigo(codigo);
        ds.setLink(link);
        ds.setDatos("null");

        aux = bd.buscar(ds.SQLBuscar());

        if (aux == -1) {
            bd.ejecutar(ds.SQLCrear());
            aux = bd.ultimoRegistro();
        }
        return aux;
    }
//</editor-fold>

    public void marcarClasificado(Date fecha) throws SQLException {
        Boe boe = new Boe(fecha);
        bd.ejecutar(boe.SQLSetClas());
    }

    public void guardaStatsD(List lista) {
        Boletines_publicados bp;
        ModeloBoes aux;
        Iterator it = lista.iterator();

        try {
            Sql bdd = new Sql(Variables.con);

            while (it.hasNext()) {
                aux = (ModeloBoes) it.next();
                bp = new Boletines_publicados();
                bp.setEntidad(aux.getEntidad().replace("'", "\\'"));
                bp.setOrigen(aux.getOrigen().replace("'", "\\'"));
                bp.setCodigo(aux.getCodigo());
                bp.setDescripcion(aux.getDescripcion().replace("'", "\\'"));
                bp.setFecha(aux.getFecha());
                bp.setTipo(0);

                bdd.ejecutar(bp.SQLCrear());
            }

            bdd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardaStatsS(List lista) {
        Boletines_publicados bp;
        ModeloBoes aux;
        Iterator it = lista.iterator();

        try {
            Sql bdd = new Sql(Variables.con);

            while (it.hasNext()) {
                aux = (ModeloBoes) it.next();
                bp = new Boletines_publicados();
                bp.setEntidad(aux.getEntidad().replace("'", "\\'"));
                bp.setOrigen(aux.getOrigen().replace("'", "\\'"));
                bp.setCodigo(aux.getCodigo());
                bp.setDescripcion(aux.getDescripcion().replace("'", "\\'"));
                bp.setFecha(aux.getFecha());
                bp.setTipo(1);

                bdd.ejecutar(bp.SQLCrear());
            }

            bdd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Insercion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
