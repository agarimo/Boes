package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agarimo
 */
public class ModeloFases {

    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleIntegerProperty idOrigen = new SimpleIntegerProperty();
    public SimpleStringProperty codigo = new SimpleStringProperty();
    public SimpleIntegerProperty tipo = new SimpleIntegerProperty();
    public SimpleStringProperty texto1 = new SimpleStringProperty();
    public SimpleStringProperty texto2 = new SimpleStringProperty();
    public SimpleStringProperty texto3 = new SimpleStringProperty();
    public SimpleIntegerProperty dias = new SimpleIntegerProperty();

    public int getId() {
        return id.get();
    }

    public int getIdOrigen() {
        return idOrigen.get();
    }

    public String getCodigo() {
        return codigo.get();
    }
    
    public int getIdTipo(){
        return tipo.get();
    }

    public String getTipo() {
        if (tipo.get() == 1) {
            return "Notificación de Denuncias";
        } else if (tipo.get() == 2) {
            return "Resolución de Sanciones";
        } else if (tipo.get() == 3) {
            return "Resolución de Recursos";
        } else {
            return "Desconocido";
        }
    }

    public String getTexto1() {
        return texto1.get();
    }

    public String getTexto2() {
        return texto2.get();
    }

    public String getTexto3() {
        return texto3.get();
    }

    public int getDias() {
        return dias.get();
    }

    public String getFase() {
        return "(" + codigo.get() + ")" + dias.get() + tipoToString();
    }
    
    private String tipoToString() {
        if (tipo.get() == 1) {
            return "ND";
        } else if (tipo.get() == 2) {
            return "RS";
        } else if (tipo.get() == 3) {
            return "RR";
        } else {
            return "Desconocido";
        }
    }
    
}
