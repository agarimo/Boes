package main;

import boe.Boe;
import enty.Boletin;
import enty.Cabecera;
import enty.Descarga;
import enty.Estructura;
import enty.Fase;
import enty.Multa;
import enty.Origen;
import enty.OrigenExpediente;
import enty.OrigenFase;
import enty.Pattern;
import enty.Procesar;
import enty.StrucData;
import enty.VistaExtraccion;
import extraccion.ReqObs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR DE CONEXIÓN");
            alert.setContentText(aux);

            alert.showAndWait();
        });
    }

    public static String getString(String query) {
        Sql bd;
        String aux = "";

        try {
            bd = new Sql(Var.con);
            aux = bd.getString(query);
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static int getInt(String query) {
        Sql bd;
        int aux = -1;

        try {
            bd = new Sql(Var.con);
            aux = bd.getInt(query);
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static void eliminaBoletin(String codigo) {
        Sql bd;
        try {
            bd = new Sql(Var.con);
            bd.ejecutar("DELETE FROM boes.boletin where codigo=" + Varios.entrecomillar(codigo));
            bd.ejecutarQueryRs("UPDATE stats.boletines_publicados SET tipo=0 where codigo=" + Varios.entrecomillar(codigo));
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Boe getBoe(Date fecha) {
        Sql bd;
        ResultSet rs;
        Boe aux = null;
        String query = "SELECT * from " + Var.nombreBD + ".boe where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));

        try {
            bd = new Sql(Var.con);
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
        String query = "SELECT * from " + Var.nombreBD + ".origen where id=" + id;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            if (rs.next()) {
                aux = new Origen(rs.getInt("id"), rs.getInt("idEntidad"), rs.getString("nombre"), rs.getString("codigo"),
                        rs.getString("codigoAy"), rs.getString("codigoUn"), rs.getString("codigoTes"), rs.getString("nombreMostrar"));
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
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
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

    public static String getEstructura(int id) {
        Sql bd;
        ResultSet rs;
        String aux = null;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs("SELECT estructura FROM boes.estructura where id=" + id);

            while (rs.next()) {
                aux = rs.getString("estructura");
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static Multa getMulta(String query) {
        Sql bd;
        ResultSet rs;
        Multa aux = null;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            if (rs.next()) {
                aux = new Multa();
                aux.setId(rs.getInt("id"));
                aux.setIdBoletin(rs.getInt("idBoletin"));
                aux.setCodigoSancion(rs.getString("codigoSancion"));
                aux.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                aux.setIdOrganismo(rs.getInt("idOrganismo"));
                aux.setOrganismo(rs.getString("organismo"));
                aux.setBoe(rs.getString("boe"));
                aux.setFase(rs.getString("fase"));
                aux.setTipoJuridico(rs.getString("tipoJuridico"));
                aux.setPlazo(rs.getInt("plazo"));
                aux.setExpediente(rs.getString("expediente"));
                aux.setFechaMulta(rs.getDate("fechaMulta"));
                aux.setArticulo(rs.getString("articulo"));
                aux.setNif(rs.getString("nif"));
                aux.setSancionado(rs.getString("sancionado"));
                aux.setLocalidad(rs.getString("localidad"));
                aux.setMatricula(rs.getString("matricula"));
                aux.setCuantia(rs.getString("cuantia"));
                aux.setPuntos(rs.getString("puntos"));
                aux.setReqObs(rs.getString("reqObs"));
                aux.setLinea(rs.getString("linea"));
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
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloBoletines();
                aux.codigo.set(rs.getString("codigo"));
                aux.idBoletin.set(rs.getInt("idBoletin"));
                aux.entidad.set(rs.getString("entidad"));
                aux.origen.set(rs.getString("origen"));
                aux.fecha.set(Dates.imprimeFecha(rs.getDate("fecha")));
                aux.tipo.set(rs.getString("tipo"));
                aux.fase.set(rs.getString("fase"));
                aux.isFase.set(rs.getInt("isFase"));
                aux.isEstructura.set(rs.getString("isEstructura"));
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
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloUnion();
                aux.codigo.set(rs.getString("codigo"));
                aux.idDescarga.set(rs.getInt("idDescarga"));
                aux.fecha.set(rs.getString("fecha"));
                aux.codigoUn.set(rs.getString("codigoUn"));
                aux.estructura.set(rs.getString("isEstructura"));
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

    public static VistaExtraccion getVistaExtraccion(String query) {
        Sql bd;
        ResultSet rs;
        VistaExtraccion aux = null;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new VistaExtraccion(rs.getString("codigo"), rs.getString("entidad"), rs.getInt("idOrigen"),
                        rs.getString("origen"), rs.getDate("fecha"), rs.getString("tipo"), rs.getString("fase"));
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static StrucData getStrucData(String query) {
        Sql bd;
        ResultSet rs;
        StrucData aux = null;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new StrucData();

                aux.setId(rs.getInt("id"));
                aux.setIdEstructura(rs.getInt("idEstructura"));
                aux.setExpediente(rs.getInt("expediente"));
                aux.setSancionado(rs.getInt("sancionado"));
                aux.setNif(rs.getInt("nif"));
                aux.setLocalidad(rs.getInt("localidad"));
                aux.setFechaMulta(rs.getInt("fechaMulta"));
                aux.setMatricula(rs.getInt("matricula"));
                aux.setCuantia(rs.getInt("cuantia"));
                aux.setPreceptoA(rs.getInt("preceptoA"));
                aux.setPreceptoB(rs.getInt("preceptoB"));
                aux.setPreceptoC(rs.getInt("preceptoC"));
                aux.setArticuloA(rs.getInt("articuloA"));
                aux.setArticuloB(rs.getInt("articuloB"));
                aux.setArticuloC(rs.getInt("articuloC"));
                aux.setArticuloD(rs.getInt("articuloD"));
                aux.setPuntos(rs.getInt("puntos"));
                aux.setReqObs(rs.getInt("reqObs"));
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static Procesar getProcesar(String codigo) {
        Sql bd;
        ResultSet rs;
        String query = "SELECT * FROM boes.procesar where codigo=" + Varios.entrecomillar(codigo);
        Procesar aux = null;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Procesar();
                aux.setId(rs.getInt("id"));
                aux.setCodigo(rs.getString("codigo"));
                aux.setEstructura(rs.getInt("estructura"));
                aux.setFecha(rs.getDate("fecha"));
                aux.setLink(rs.getString("link"));
                aux.setEstado(rs.getInt("estado"));
            }

            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static void eliminarMultasBoletin(String codigo) {
        String query = "DELETE FROM boes.multa WHERE idBoletin=(SELECT id FROM boes.procesar WHERE codigo=" + Varios.entrecomillar(codigo) + ");";
        Sql bd;

        try {
            bd = new Sql(Var.con);
            bd.ejecutar(query);
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getLink(String codigo) {
        Sql bd;
        ResultSet rs;
        String query = "SELECT link FROM boes.descarga where codigo=" + Varios.entrecomillar(codigo);
        String aux = null;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = rs.getString("link");
            }

            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public static String getDesData(String codigo) {
        Sql bd;
        String query = "SELECT datos FROM boes.descarga where codigo=" + Varios.entrecomillar(codigo);
        String aux = "";

        try {
            bd = new Sql(Var.con);
            aux = bd.getString(query);
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
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloBoletines();
                aux.codigo.set(rs.getString("codigo"));
                aux.idBoletin.set(rs.getInt("idBoletin"));
                aux.entidad.set(rs.getString("entidad"));
                aux.origen.set(rs.getString("origen"));
                aux.fecha.set(Dates.imprimeFecha(rs.getDate("fecha")));
                aux.tipo.set(rs.getString("tipo"));
                aux.fase.set(rs.getString("fase"));
                aux.isFase.set(rs.getInt("isFase"));
                aux.isEstructura.set(rs.getString("isEstructura"));
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
        String query = "SELECT * FROM " + Var.nombreBD + ".origen_descartado";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        String aux;

        try {
            bd = new Sql(Var.con);
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
        String query = "SELECT * FROM " + Var.nombreBD + ".texto_descartado";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        String aux;

        try {
            bd = new Sql(Var.con);
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
        String query = "SELECT * FROM " + Var.nombreBD + ".entidad order by nombre";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloComboBox aux;

        try {
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Origen(rs.getInt("id"), rs.getInt("idEntidad"), rs.getString("nombre"), rs.getString("codigo"),
                        rs.getString("codigoAy"), rs.getString("codigoUn"), rs.getString("codigoTes"), rs.getString("nombreMostrar"));
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
        String query = "SELECT * FROM " + Var.nombreBD + ".origen where idEntidad=" + id + " order by nombre";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloComboBox aux;

        try {
            bd = new Sql(Var.con);
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
        String query = "SELECT * FROM " + Var.nombreBD + ".tipo where tipo=0";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloComboBox aux;

        try {
            bd = new Sql(Var.con);
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
        String query = "SELECT * FROM " + Var.nombreBD + ".cabeceras where idOrigen=" + id;
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloCabeceras aux;

        try {
            bd = new Sql(Var.con);
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
        String query = "SELECT * FROM " + Var.nombreBD + ".fase where idOrigen=" + id;
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ModeloFases aux;

        try {
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
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
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ModeloUnion();
                aux.codigo.set(rs.getString("codigo"));
                aux.idDescarga.set(rs.getInt("idDescarga"));
                aux.fecha.set(rs.getString("fecha"));
                aux.codigoUn.set(rs.getString("codigoUn"));
                aux.estructura.set(rs.getString("isEstructura"));
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

    public static List listaEstructurasDia(Date fecha) {
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        String query = "select isEstructura from boes.vista_union "
                + "where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha)) + " "
                + "group by isEstructura;";
        String aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = rs.getString("isEstructura");
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

    public static List listaProcesarPendiente(Date fecha) {
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        String query = "select a.id,a.codigo,b.link,a.isEstructura from boes.boletin a "
                + "left join boes.descarga b on a.idDescarga=b.id "
                + "where a.idBoe=(select id from boes.boe where fecha=" + Varios.entrecomillar(Dates.imprimeFecha(fecha)) + ") "
                + "and a.id not in (select id from boes.procesar)";
        Procesar aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Procesar();
                aux.setId(rs.getInt("id"));
                aux.setFecha(fecha);
                aux.setCodigo(rs.getString("codigo"));
                aux.setLink(rs.getString("link"));
                aux.setEstructura(rs.getInt("isEstructura"));
                aux.setEstado(0);
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

    public static List listaProcesar(String query) {
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Procesar aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Procesar();
                aux.setId(rs.getInt("id"));
                aux.setFecha(rs.getDate("fecha"));
                aux.setCodigo(rs.getString("codigo"));
                aux.setLink(rs.getString("link"));
                aux.setEstructura(rs.getInt("estructura"));
                aux.setEstado(rs.getInt("estado"));
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

    public static List listaEstructurasFechas() {
        String query = "SELECT * FROM " + Var.nombreBD + ".strucfecha";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        String aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = rs.getString("estructura");
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

    public static List listaEstructurasHeader() {
        String query = "SELECT * FROM " + Var.nombreBD + ".strucheader";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        String aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = rs.getString("estructura");
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

    public static List listaMultas(String query) {
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Multa aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Multa();
                aux.setId(rs.getInt("id"));
                aux.setIdBoletin(rs.getInt("idBoletin"));
                aux.setCodigoSancion(rs.getString("codigoSancion"));
                aux.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                aux.setIdOrganismo(rs.getInt("idOrganismo"));
                aux.setOrganismo(rs.getString("organismo"));
                aux.setBoe(rs.getString("boe"));
                aux.setFase(rs.getString("fase"));
                aux.setTipoJuridico(rs.getString("tipoJuridico"));
                aux.setPlazo(rs.getInt("plazo"));
                aux.setExpediente(rs.getString("expediente"));
                aux.setFechaMulta(rs.getDate("fechaMulta"));
                aux.setArticulo(rs.getString("articulo"));
                aux.setNif(rs.getString("nif"));
                aux.setSancionado(rs.getString("sancionado"));
                aux.setLocalidad(rs.getString("localidad"));
                aux.setMatricula(rs.getString("matricula"));
                aux.setCuantia(rs.getString("cuantia"));
                aux.setPuntos(rs.getString("puntos"));
                aux.setReqObs(rs.getString("reqObs"));
                aux.setLinea(rs.getString("linea"));
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

    public static List listaMultasReducidas(String query) {
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Multa aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Multa();
                aux.setId(rs.getInt("id"));
                aux.setCodigoSancion(rs.getString("codigoSancion"));
                aux.setNif(rs.getString("nif"));
                aux.setMatricula(rs.getString("matricula"));
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

    public static List<Integer> listaEstructurasCreadas() {
        String query = "SELECT * FROM " + Var.nombreBD + ".strucdata";
        List<Integer> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        int aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = rs.getInt("idEstructura");
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

    public static List<Integer> listaEstructurasManual() {
        String query = "SELECT id FROM " + Var.nombreBD + ".estructura where procesarManual=1";
        List<Integer> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        int aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = rs.getInt("id");
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

    public static List<ReqObs> listaReqObs(String query) {
        List<ReqObs> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        ReqObs aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new ReqObs(rs.getInt("id"), rs.getInt("idOrigen"), rs.getString("fase"),
                        rs.getString("reqObs"), rs.getString("nuevaFase"));
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

    public static List listaString(String query) {
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        String aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = rs.getString(1);
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

    public static List<Pattern> listaPattern(Date fecha) {
        String query = "SELECT * FROM " + Var.nombreBD + ".vista_pattern where fechaPublicacion=" + Varios.entrecomillar(Dates.imprimeFecha(fecha));
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Pattern aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Pattern();
                aux.setCodigo(rs.getString("codigo"));
                aux.setNif(rs.getString("nif"));
                aux.setMatricula(rs.getString("matricula"));
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

    public static List<OrigenExpediente> listaOrigenExp() {
        String query = "SELECT * FROM " + Var.nombreBD + ".origen_expediente";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        OrigenExpediente aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new OrigenExpediente();
                aux.setIdOrigen(rs.getInt("idOrigen"));
                aux.setCabecera(rs.getString("cabecera"));
                aux.setOrigen(rs.getString("origen"));
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

    public static List<OrigenFase> listaOrigenFase() {
        String query = "SELECT * FROM " + Var.nombreBD + ".origen_fase";
        List list = new ArrayList();
        Sql bd;
        ResultSet rs;
        OrigenFase aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new OrigenFase();
                aux.setIdOrigen(rs.getInt("idOrigen"));
                aux.setCabecera(rs.getString("cabecera"));
                aux.setFase(rs.getString("fase"));
                aux.setNuevaFase(rs.getString("nuevaFase"));
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
