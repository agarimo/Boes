package main;

import boes.Boe;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import util.Sql;

/**
 *
 * @author Agarimo
 */
public class SqlBoe {
    
    public static List<Boe> listaMultas(String query) {
        List<Boe> list = new ArrayList();
        Sql bd;
        ResultSet rs;
        Boe aux;

        try {
            bd = new Sql(Variables.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Boe(rs.getInt("id"), rs.getDate("fecha"), rs.getString("link"),rs.getBoolean("isClas"));
                list.add(aux);
            }

            rs.close();
            bd.close();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR DE CONEXIÓN");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
            Logger.getLogger(SqlBoe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
