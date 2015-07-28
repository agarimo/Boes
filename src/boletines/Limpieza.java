package boletines;

import enty.Boletin;
import java.util.List;
import main.SqlBoe;

/**
 *
 * @author Agarimo
 */
public class Limpieza {

    Boletin boletin;
    List cabeceras;
    String datos;

    public Limpieza(int idBoletin) {
        boletin = SqlBoe.getBoletin("SELECT * FROM boes.boletin where id=" + idBoletin);
        if (boletin != null) {
            cargarDatos();
        }
    }

    private void cargarDatos() {
        cabeceras = SqlBoe.listaCabeceras(boletin.getIdDescarga(), 1);
    }

}
