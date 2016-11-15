/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ieselrincon.convalidaciones.pojos;

/**
 *
 * @author Tiburcio
 */
public class Ciclo {
    int PK_ID;
    String nombre;
    String abreviatura;

    public Ciclo() {
    }

    public Ciclo(int PK_ID, String nombre, String abreviatura) {
        this.PK_ID = PK_ID;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
    }

    public int getPK_ID() {
        return PK_ID;
    }

    public void setPK_ID(int PK_ID) {
        this.PK_ID = PK_ID;
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
