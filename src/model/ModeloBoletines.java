package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agarimo
 */
public class ModeloBoletines {

    public SimpleStringProperty codigo = new SimpleStringProperty();
    public SimpleStringProperty entidad = new SimpleStringProperty();
    public SimpleStringProperty origen = new SimpleStringProperty();
    public SimpleStringProperty fecha = new SimpleStringProperty();
    public SimpleStringProperty tipo = new SimpleStringProperty();
    public SimpleStringProperty fase = new SimpleStringProperty();
    public SimpleIntegerProperty isFase = new SimpleIntegerProperty();
    public SimpleIntegerProperty isEstructura = new SimpleIntegerProperty();
    public SimpleIntegerProperty idDescarga = new SimpleIntegerProperty();
    public SimpleStringProperty link = new SimpleStringProperty();
    public SimpleIntegerProperty idioma = new SimpleIntegerProperty();

    public String getCodigo(){
        return codigo.get();
    }
    
    public String getEntidad(){
        return entidad.get();
    }
    
    public String getOrigen(){
        return origen.get();
    }
    
    public String getFecha(){
        return fecha.get();
    }
    
    public String getTipo(){
        return tipo.get();
    }
    
    public String getFase(){
        return fase.get();
    }

    public int getIsFase() {
        return isFase.get();
    }

    public int getIsEstructura() {
        return isEstructura.get();
    }
    
    public int getIdDescarga(){
        return idDescarga.get();
    }
    
    public String getLink(){
        return link.get();
    }
    
    public int getIdioma(){
        return idioma.get();
    }

    @Override
    public String toString() {
        return codigo.get();
    }
}
