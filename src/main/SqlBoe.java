package main;

import boes.Boe;
import enty.Descarga;
import enty.Fase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import model.ModeloBoletines;
import model.ModeloComboBox;
import model.ModeloFases;
import util.Dates;
import util.Sql;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class SqlBoe {

    private static void error(String aux) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR DE CONEXIÓN");
        alert.setContentText(aux);

        alert.showAndWait();
    }

    public static Boe cargaBoe(Date fecha) {
        Sql bd;
        ResultSet rs;
        Boe aux = null;
        String query = "SELECT * from boes.boe where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));

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
                aux = new Descarga(rs.getInt("id"), rs.getString("link"), rs.getString("datos"));
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

    public static List<ModeloBoletines> listaVistaBoletin(String query) {
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
                aux.estado.set(rs.getInt("estado"));
                aux.idDescarga.set(rs.getInt("idDescarga"));
                aux.link.set(rs.getString("link"));
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
    
    public static List<ModeloComboBox> listaEntidades() {
        String query = "SELECT * FROM " + Variables.nombreBD + ".entidad";
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
    
    public static List<ModeloComboBox> listaOrigenes(int id) {
        String query = "SELECT * FROM " + Variables.nombreBD + ".origen where idEntidad="+id;
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
    
    public static List<ModeloComboBox> listaTipo() {
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
    
    public static List<ModeloFases> listaFases(int id) {
        String query = "SELECT * FROM " + Variables.nombreBD + ".fase where idOrigen="+id;
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
//</editor-fold>
}
