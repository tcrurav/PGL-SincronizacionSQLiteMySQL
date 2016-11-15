/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ieselrincon.convalidaciones.pojos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tiburcio
 */
@XmlRootElement
public class Alumno {
    int PK_ID;
    String nombre;
    String apellidos;

    public Alumno() { //Obligatorio para que funcione JAXB
    }

    public Alumno(int PK_ID, String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public int getPK_ID() {
        return PK_ID;
    }

    public void setPK_ID(int PK_ID) {
        this.PK_ID = PK_ID;
    }
}
