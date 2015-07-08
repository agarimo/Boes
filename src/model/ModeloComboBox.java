package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agarimo
 */
public class ModeloComboBox {

    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty nombre = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    @Override
    public String toString() {
        return nombre.get();
    }
}
