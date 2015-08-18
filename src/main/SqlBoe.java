package main;

import boes.Boe;
import enty.Boletin;
import enty.Cabecera;
import enty.Descarga;
import enty.Estructura;
import enty.Fase;
import enty.Origen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import model.ModeloBoletines;
import model.ModeloCabeceras;
import model.ModeloComboBox;
import model.ModeloFases;
import model.ModeloUnion;
import util.Dates;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class SqlBoe {

    private static void error(String aux) {
        System.out.println(aux);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR DE CONEXIÓN");
        alert.setContentText(aux);

        alert.showAndWait();
    }

    public static void eliminaBoletin(String codigo) {
        Sql bd;
        try {
            bd = new Sql(Variables.con);
            bd.ejecutar("DELETE FROM boes.boletin where codigo=" + Varios.entrecomillar(codigo));
            bd.ejecutarQueryRs("UPDATE stats.boletines_publicados SET tipo=0 where codigo=" + Varios.entrecomillar(codigo));
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Boe getBoe(Date fecha) {
        Sql bd;
        ResultSet rs;
        Boe aux = null;
        String query = "SELECT * from " + Variables.nombreBD + ".boe where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            if (rs.next()) {
                aux = new Boe(rs.getInt("id"), rs.getDate("fecha"), rs.getString("link"), rs.getBoolean("isClas"));
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static Origen getOrigen(int id) {
        Sql bd;
        ResultSet rs;
        Origen aux = null;
        String query = "SELECT * from " + Variables.nombreBD + ".origen where id=" + id;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            if (rs.next()) {
                aux = new Origen(rs.getInt("id"), rs.getInt("idEntidad"), rs.getString("nombre"), rs.getString("codigo"),
                        rs.getString("codigoAy"), rs.getString("codigoUn"), rs.getString("codigoTes"));
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static Boletin getBoletin(String query) {
        Sql bd;
        ResultSet rs;
        Boletin aux = null;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Boletin(rs.getInt("id"), rs.getInt("idOrigen"), rs.getInt("idBoe"), rs.getInt("idDescarga"),
                        rs.getString("codigo"), rs.getString("tipo"), rs.getString("fase"), rs.getInt("isFase"),
                        rs.getInt("isEstructura"), rs.getInt("idioma"));
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static Estructura getEstructura(String query) {
        Sql bd;
        ResultSet rs;
        Estructura aux = null;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Estructura(rs.getInt("id"), rs.getString("nombre"), rs.getString("estructura"));
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static ModeloBoletines getModeloBoletines(String codigo) {
        Sql bd;
        ResultSet rs;
        ModeloBoletines aux = null;
        String query = "SELECT * FROM boes.vista_boletines where codigo=" + Varios.entrecomillar(codigo);

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloBoletines();
                aux.codigo.set(rs.getString("codigo"));
                aux.entidad.set(rs.getString("entidad"));
                aux.origen.set(rs.getString("origen"));
                aux.fecha.set(Dates.imprimeFecha(rs.getDate("fecha")));
                aux.tipo.set(rs.getString("tipo"));
                aux.fase.set(rs.getString("fase"));
                aux.isFase.set(rs.getInt("isFase"));
                aux.isEstructura.set(rs.getInt("isEstructura"));
                aux.idDescarga.set(rs.getInt("idDescarga"));
                aux.link.set(rs.getString("link"));
                aux.idioma.set(rs.getInt("idioma"));
            }

            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static ModeloUnion getUnion(String query) {
        Sql bd;
        ResultSet rs;
        ModeloUnion aux = null;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloUnion();
                aux.codigo.set(rs.getString("codigo"));
                aux.idDescarga.set(rs.getInt("idDescarga"));
                aux.fecha.set(rs.getString("fecha"));
                aux.codigoUn.set(rs.getString("codigoUn"));
                aux.estructura.set(rs.getInt("isEstructura"));
                aux.codigoProv.set(rs.getString("codigoProv"));
            }

            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    //<editor-fold defaultstate="collapsed" desc="LISTADOS">
    public static List<Boe> listaBoe(String query) {
        List<Boe> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Boe aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Boe(rs.getInt("id"), rs.getDate("fecha"), rs.getString("link"), rs.getBoolean("isClas"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Boletin> listaBoletin(String query) {

        List<Boletin> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Boletin aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Boletin(rs.getInt("id"), rs.getInt("idOrigen"), rs.getInt("idBoe"), rs.getInt("idDescarga"),
                        rs.getString("codigo"), rs.getString("tipo"), rs.getString("fase"), rs.getInt("isFase"),
                        rs.getInt("isEstructura"), rs.getInt("idioma"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Descarga> listaDescargaPendiente() {
        String query = "SELECT * FROM boes.descarga where datos='null'";
        List<Descarga> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Descarga aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Descarga(rs.getInt("id"), rs.getString("codigo"), rs.getString("link"), rs.getString("datos"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Descarga> listaDescarga(String query) {
        List<Descarga> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Descarga aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Descarga(rs.getInt("id"), rs.getString("codigo"), rs.getString("link"), rs.getString("datos"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<ModeloBoletines> listaModeloBoletines(String query) {
        List<ModeloBoletines> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloBoletines aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloBoletines();
                aux.codigo.set(rs.getString("codigo"));
                aux.entidad.set(rs.getString("entidad"));
                aux.origen.set(rs.getString("origen"));
                aux.fecha.set(Dates.imprimeFecha(rs.getDate("fecha")));
                aux.tipo.set(rs.getString("tipo"));
                aux.fase.set(rs.getString("fase"));
                aux.isFase.set(rs.getInt("isFase"));
                aux.isEstructura.set(rs.getInt("isEstructura"));
                aux.idDescarga.set(rs.getInt("idDescarga"));
                aux.link.set(rs.getString("link"));
                aux.idioma.set(rs.getInt("idioma"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List listaOrigenDescartado() {
        String query = "SELECT * FROM " + Variables.nombreBD + ".origen_descartado";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        String aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = rs.getString("nombre");
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List listaTextoDescartado() {
        String query = "SELECT * FROM " + Variables.nombreBD + ".texto_descartado";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        String aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = rs.getString("texto");
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<ModeloComboBox> listaModeloComboBoxEntidades() {
        String query = "SELECT * FROM " + Variables.nombreBD + ".entidad order by nombre";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloComboBox aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloComboBox();
                aux.id.set(rs.getInt("id"));
                aux.nombre.set(rs.getString("nombre"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Origen> listaOrigen(String query) {
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Origen aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Origen(rs.getInt("id"), rs.getInt("idEntidad"), rs.getString("nombre"), rs.getString("codigo"),
                        rs.getString("codigoAy"), rs.getString("codigoUn"), rs.getString("codigoTes"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<ModeloComboBox> listaModeloComboBoxOrigenes(int id) {
        String query = "SELECT * FROM " + Variables.nombreBD + ".origen where idEntidad=" + id + " order by nombre";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloComboBox aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloComboBox();
                aux.id.set(rs.getInt("id"));
                aux.nombre.set(rs.getString("nombre"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<ModeloComboBox> listaModeloComboBoxTipo() {
        String query = "SELECT * FROM " + Variables.nombreBD + ".tipo where tipo=0";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloComboBox aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloComboBox();
                aux.id.set(1);
                aux.nombre.set(rs.getString("id"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<ModeloCabeceras> listaModeloCabeceras(int id) {
        String query = "SELECT * FROM " + Variables.nombreBD + ".cabeceras where idOrigen=" + id;
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloCabeceras aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloCabeceras();
                aux.id.set(rs.getInt("id"));
                aux.idOrigen.set(rs.getInt("idOrigen"));
                aux.cabecera.set(rs.getString("cabecera"));
                aux.tipo.set(rs.getInt("tipo"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<ModeloFases> listaModeloFases(int id) {
        String query = "SELECT * FROM " + Variables.nombreBD + ".fase where idOrigen=" + id;
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloFases aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloFases();
                aux.id.set(rs.getInt("id"));
                aux.idOrigen.set(rs.getInt("idOrigen"));
                aux.codigo.set(rs.getString("codigo"));
                aux.tipo.set(rs.getInt("tipo"));
                aux.texto1.set(rs.getString("texto1"));
                aux.texto2.set(rs.getString("texto2"));
                aux.texto3.set(rs.getString("texto3"));
                aux.dias.set(rs.getInt("dias"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Fase> listaFase(String query) {
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Fase aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Fase();
                aux.setId(rs.getInt("id"));
                aux.setIdOrigen(rs.getInt("idOrigen"));
                aux.setCodigo(rs.getString("codigo"));
                aux.setTipo(rs.getInt("tipo"));
                aux.setTexto1(rs.getString("texto1"));
                aux.setTexto2(rs.getString("texto2"));
                aux.setTexto3(rs.getString("texto3"));
                aux.setDias(rs.getInt("dias"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Fase> listaFaseTestra(String query) {
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Fase aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Fase();
                aux.setId(rs.getInt("idfase"));
                aux.setIdOrigen(rs.getInt("origen"));
                aux.setCodigo(rs.getString("codigo"));
                aux.setTipo(rs.getInt("tipo"));
                aux.setTexto1(rs.getString("texto1"));
                aux.setTexto2(rs.getString("texto2"));
                aux.setTexto3(rs.getString("texto3"));
                aux.setDias(rs.getInt("dias"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Cabecera> listaCabeceras(int idOrigen, int tipo) {
        String query = "SELECT * FROM boes.cabeceras where idOrigen=" + idOrigen + " and tipo=" + tipo;
        List<Cabecera> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Cabecera aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Cabecera(rs.getInt("id"), rs.getInt("idOrigen"), rs.getString("cabecera"), rs.getInt("tipo"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Estructura> listaEstructuras(String query) {
        List<Estructura> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Estructura aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Estructura(rs.getInt("id"), rs.getString("nombre"), rs.getString("estructura"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<ModeloUnion> listaUnion(String query) {
        List<ModeloUnion> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloUnion aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloUnion();
                aux.codigo.set(rs.getString("codigo"));
                aux.idDescarga.set(rs.getInt("idDescarga"));
                aux.fecha.set(rs.getString("fecha"));
                aux.codigoUn.set(rs.getString("codigoUn"));
                aux.estructura.set(rs.getInt("isEstructura"));
                aux.codigoProv.set(rs.getString("codigoProv"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
//</editor-fold>
}
