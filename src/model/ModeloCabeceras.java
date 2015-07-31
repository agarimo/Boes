package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agarimo
 */
public class ModeloCabeceras {

    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleIntegerProperty idOrigen = new SimpleIntegerProperty();
    public SimpleStringProperty cabecera = new SimpleStringProperty();
    public SimpleIntegerProperty tipo = new SimpleIntegerProperty();

    public int getId() {
        return id.get();
    }
    
    public int getIdOrigen(){
        return idOrigen.get();
    }

    public String getCabecera() {
        return cabecera.get();
    }
    
    public int getTipo(){
        return tipo.get();
    }
}
