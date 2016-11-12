package com.example.tiburcio.ejemploproveedorcontenido.pojos;

import com.example.tiburcio.ejemploproveedorcontenido.constantes.G;

/**
 * Created by Tiburcio on 31/07/2016.
 */
public class Ciclo {
    private int ID;
    private String nombre;
    private String abreviatura;

    public Ciclo(){
        this.ID = G.SIN_VALOR_INT;
        this.nombre = G.SIN_VALOR_STRING;
        this.setAbreviatura(G.SIN_VALOR_STRING);
    };

    public Ciclo(int ID, String nombre, String abreviatura) {
        this.ID = ID;
        this.nombre = nombre;
        this.setAbreviatura(abreviatura);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}
