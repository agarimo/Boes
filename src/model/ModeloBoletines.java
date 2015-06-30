package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agarimo
 */
public class ModeloBoletines {

    public SimpleStringProperty codigo = new SimpleStringProperty();
    public SimpleStringProperty origen = new SimpleStringProperty();
    public SimpleStringProperty fecha = new SimpleStringProperty();
    public SimpleStringProperty tipo = new SimpleStringProperty();
    public SimpleIntegerProperty estado = new SimpleIntegerProperty();

    public String getEstado() {
        String aux = "ERROR";

        switch (estado.get()) {
            case 0:
                aux = "SIN DESCARGAR";
                break;
            case 1:
                aux = "DESCARGADO";
                break;
            case 2:
                aux = "CLASIFICADO";
                break;
            case 3:
                aux = "COMPROBAR";
                break;
        }
        return aux;
    }

    @Override
    public String toString() {
        return codigo.get();
    }
}
