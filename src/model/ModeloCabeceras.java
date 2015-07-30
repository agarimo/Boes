package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agarimo
 */
public class ModeloCabeceras {

    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty cabecera = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public String getCabecera() {
        return cabecera.get();
    }

}
