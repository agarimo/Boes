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
    public SimpleIntegerProperty estado = new SimpleIntegerProperty();
    public SimpleIntegerProperty idDescarga = new SimpleIntegerProperty();
    public SimpleStringProperty link = new SimpleStringProperty();

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
    
    public String getEstado() {
        String aux = "ERROR";

        switch (estado.get()) {
            case 0:
                aux = "SIN DESCARGAR";
                break;
            case 1:
                aux = "PENDIENTE FASES";
                break;
            case 2:
                aux = "LISTO";
                break;
            case 3:
                aux = "FASE NO ENCONTRADA";
                break;
        }
        return aux;
    }
    
    public int getIdDescarga(){
        return idDescarga.get();
    }
    
    public String getLink(){
        return link.get();
    }

    @Override
    public String toString() {
        return codigo.get();
    }
}
