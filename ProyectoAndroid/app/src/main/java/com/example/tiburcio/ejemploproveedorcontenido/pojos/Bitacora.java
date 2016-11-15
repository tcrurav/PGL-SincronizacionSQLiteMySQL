package com.example.tiburcio.ejemploproveedorcontenido.pojos;

import com.example.tiburcio.ejemploproveedorcontenido.constantes.G;

/**
 * Created by Tiburcio on 25/09/2015.
 */
public class Bitacora {
    private int ID;
    private int ID_Ciclo;
    private int operacion;

    public Bitacora() {
        this.setID(G.SIN_VALOR_INT);
        this.setID_Ciclo(G.SIN_VALOR_INT);
        this.setOperacion(G.SIN_VALOR_INT);
    }

    public Bitacora(int ID, int ID_Ciclo, int operacion) {
        this.setID(ID);
        this.setID_Ciclo(ID_Ciclo);
        this.setOperacion(operacion);
    }

    public int getID_Ciclo() {
        return ID_Ciclo;
    }

    public void setID_Ciclo(int ID_Ciclo) {
        this.ID_Ciclo = ID_Ciclo;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
