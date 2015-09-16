package model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agarimo
 */
public class ModeloMultas {

    public SimpleStringProperty expediente = new SimpleStringProperty();
    public SimpleStringProperty sancionado = new SimpleStringProperty();
    public SimpleStringProperty nif = new SimpleStringProperty();
    public SimpleStringProperty localidad = new SimpleStringProperty();
    public SimpleStringProperty fecha = new SimpleStringProperty();
    public SimpleStringProperty matricula = new SimpleStringProperty();
    public SimpleStringProperty cuantia = new SimpleStringProperty();
    public SimpleStringProperty articulo = new SimpleStringProperty();
    public SimpleStringProperty puntos = new SimpleStringProperty();
    public SimpleStringProperty reqObs = new SimpleStringProperty();

    public String getExpediente() {
        return expediente.get();
    }

    public String getSancionado() {
        return sancionado.get();
    }

    public String getNif() {
        return nif.get();
    }

    public String getLocalidad() {
        return localidad.get();
    }

    public String getFecha() {
        return fecha.get();
    }

    public String getMatricula() {
        return matricula.get();
    }

    public String getCuantia() {
        return cuantia.get();
    }

    public String getArticulo() {
        return articulo.get();
    }

    public String getPuntos() {
        return puntos.get();
    }

    public String getReqObs() {
        return reqObs.get();
    }

    
    
    

    

}
