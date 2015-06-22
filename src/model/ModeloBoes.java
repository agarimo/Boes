package model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agarimo
 */
public class ModeloBoes {
    public SimpleStringProperty origen = new SimpleStringProperty();
    public SimpleStringProperty codigo = new SimpleStringProperty();
    public SimpleStringProperty descripcion = new SimpleStringProperty();

    public SimpleStringProperty getOrigen() {
        return origen;
    }

    public SimpleStringProperty getCodigo() {
        return codigo;
    }

    public SimpleStringProperty getDescripcion() {
        return descripcion;
    }
}
