package boletines;

import enty.Boletin;
import enty.Fase;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.SqlBoe;
import main.Variables;
import util.Dates;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Fases {

    Date fecha;
    List boletines;

    public Fases(Date fecha) {
        this.fecha = fecha;
        this.boletines = getBoletines();
    }

    private List getBoletines() {
        return SqlBoe.listaBoletin("SELECT * FROM " + Variables.nombreBD + ".boletin where idBoe in "
                + "(SELECT id FROM " + Variables.nombreBD + ".boe where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(this.fecha)) + ")");
    }

    public void run() {
        Sql bd;
        Fase fase;
        Boletin aux;
        Iterator it = boletines.iterator();

        while (it.hasNext()) {

            aux = (Boletin) it.next();
            System.out.println(aux.getCodigo());
            System.out.println(aux.getIdOrigen());

            fase = compruebaFase(aux.getIdDescarga(), getFases(aux.getIdOrigen()));

            if (fase != null) {
                aux.setTipo(fase.getCodigo());
                aux.setFase(getCodigoOrigen(aux.getIdOrigen()) + "-" + fase.toString());
                aux.setEstado(2);
            } else {
                aux.setFase(getCodigoOrigen(aux.getIdOrigen()));
                aux.setEstado(3);
            }

            try {
                bd = new Sql(Variables.con);
                bd.ejecutar(aux.SQLEditar());
                bd.close();
            } catch (SQLException ex) {
                Logger.getLogger(Fases.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Fase compruebaFase(int id, List fases) {
        Fase fase = null;
        Fase aux;
        Iterator it = fases.iterator();

        while (it.hasNext()) {
            aux = (Fase) it.next();

            if (aux.contiene(getDatos(id))) {
                fase = aux;
            }
        }
        return fase;
    }

    private String getCodigoOrigen(int id) {
        Sql bd;
        String str = "";

        try {
            bd = new Sql(Variables.con);
            str = bd.getString("SELECT codigo FROM " + Variables.nombreBD + ".origen where id=" + id);
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Fases.class.getName()).log(Level.SEVERE, null, ex);
        }

        return str;
    }

    private List getFases(int id) {
        return SqlBoe.listaFase("SELECT * FROM " + Variables.nombreBD + ".fase where idOrigen=" + id);
    }

    private String getDatos(int id) {
        Sql bd;
        String str = null;

        try {
            bd = new Sql(Variables.con);
            str = bd.getString("SELECT datos FROM " + Variables.nombreBD + ".descarga where id=" + id);
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(Fases.class.getName()).log(Level.SEVERE, null, ex);
        }

        return str;
    }

}
