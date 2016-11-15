package com.example.tiburcio.ejemploproveedorcontenido.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.tiburcio.ejemploproveedorcontenido.constantes.G;
import com.example.tiburcio.ejemploproveedorcontenido.pojos.Bitacora;

import java.util.ArrayList;

/**
 * Created by Tiburcio on 15/10/2016.
 */

public class BitacoraProveedor {
    static public void insert(ContentResolver resolvedor, Bitacora bitacora){
        Uri uri = Contrato.Bitacora.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Bitacora.ID_CICLO, bitacora.getID_Ciclo());
        values.put(Contrato.Bitacora.OPERACION, bitacora.getOperacion());

        resolvedor.insert(uri, values);
    }
    
    static public void delete(ContentResolver resolver, int bitacoraId){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + bitacoraId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Bitacora bitacora){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + bitacora.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Bitacora.ID_CICLO, bitacora.getID_Ciclo());
        values.put(Contrato.Bitacora.OPERACION, bitacora.getOperacion());

        resolver.update(uri, values, null, null);
    }


    static public Bitacora read(ContentResolver resolver, int bitacoraId) {
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + bitacoraId);

        String[] projection = {Contrato.Bitacora._ID,
                Contrato.Bitacora.ID_CICLO,
                Contrato.Bitacora.OPERACION};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Bitacora bitacora = new Bitacora();
            bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora._ID)));
            bitacora.setID_Ciclo(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.ID_CICLO)));
            bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.OPERACION)));
            return bitacora;
        }

        return null;

    }

    static public ArrayList<Bitacora> readAll(ContentResolver resolver) {
        Uri uri = Contrato.Bitacora.CONTENT_URI;

        String[] projection = {Contrato.Bitacora._ID,
                Contrato.Bitacora.ID_CICLO,
                Contrato.Bitacora.OPERACION};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Bitacora> bitacoras = new ArrayList<>();

        while (cursor.moveToNext()){
            Bitacora bitacora = new Bitacora();
            bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora._ID)));
            bitacora.setID_Ciclo(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.ID_CICLO)));
            bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.OPERACION)));
            bitacoras.add(bitacora);
        }

        return bitacoras;

    }
}
