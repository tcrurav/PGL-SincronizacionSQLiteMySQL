package com.example.tiburcio.ejemploproveedorcontenido.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.tiburcio.ejemploproveedorcontenido.pojos.Ciclo;

/**
 * Created by Tiburcio on 15/10/2016.
 */

public class CicloProveedor {
    static public void insert(ContentResolver resolvedor, Ciclo ciclo){
        Uri uri = Contrato.Ciclo.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Ciclo.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Ciclo.ABREVIATURA, ciclo.getAbreviatura());

        resolvedor.insert(uri, values);
    }

    static public void delete(ContentResolver resolver, int cicloId){
        Uri uri = Uri.parse(Contrato.Ciclo.CONTENT_URI + "/" + cicloId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Ciclo ciclo){
        Uri uri = Uri.parse(Contrato.Ciclo.CONTENT_URI + "/" + ciclo.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Ciclo.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Ciclo.ABREVIATURA, ciclo.getAbreviatura());

        resolver.update(uri, values, null, null);
    }

    static public Ciclo read(ContentResolver resolver, int cicloId) {
        Uri uri = Uri.parse(Contrato.Ciclo.CONTENT_URI + "/" + cicloId);

        String[] projection = {Contrato.Ciclo._ID,
                Contrato.Ciclo.NOMBRE,
                Contrato.Ciclo.ABREVIATURA};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Ciclo ciclo = new Ciclo();
            ciclo.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Ciclo._ID)));
            ciclo.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Ciclo.NOMBRE)));
            ciclo.setAbreviatura(cursor.getString(cursor.getColumnIndex(Contrato.Ciclo.ABREVIATURA)));
            return ciclo;
        }

        return null;

    }
}
