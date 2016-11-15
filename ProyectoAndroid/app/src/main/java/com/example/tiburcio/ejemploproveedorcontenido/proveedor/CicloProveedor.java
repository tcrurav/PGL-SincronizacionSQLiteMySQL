package com.example.tiburcio.ejemploproveedorcontenido.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.tiburcio.ejemploproveedorcontenido.constantes.G;
import com.example.tiburcio.ejemploproveedorcontenido.pojos.Bitacora;
import com.example.tiburcio.ejemploproveedorcontenido.pojos.Ciclo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tiburcio on 15/10/2016.
 */

public class CicloProveedor {
    static public Uri insert(ContentResolver resolvedor, Ciclo ciclo) throws Exception {
        Uri uri = Contrato.Ciclo.CONTENT_URI;

        ContentValues values = new ContentValues();
        if(ciclo.getID() != G.SIN_VALOR_INT) values.put(Contrato.Ciclo._ID, ciclo.getID());
        values.put(Contrato.Ciclo.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Ciclo.ABREVIATURA, ciclo.getAbreviatura());

        return resolvedor.insert(uri, values);
    }

    public static Uri insertConBitacora(ContentResolver resolvedor, Ciclo registro) throws Exception {

        Uri uri = insert(resolvedor,registro);
        int id = Integer.valueOf(uri.getLastPathSegment());

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Ciclo(id);
        bitacora.setOperacion(G.OPERACION_INSERTAR);
        BitacoraProveedor.insert(resolvedor,bitacora);
        return uri;
    }

    static public void delete(ContentResolver resolver, int cicloId)  throws Exception{
        Uri uri = Uri.parse(Contrato.Ciclo.CONTENT_URI + "/" + cicloId);
        resolver.delete(uri, null, null);
    }

    public static void deleteConBitacora(ContentResolver resolvedor, int id) throws Exception {
        delete(resolvedor, id);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Ciclo(id);
        bitacora.setOperacion(G.OPERACION_BORRAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
    }

    static public void update(ContentResolver resolver, Ciclo ciclo)  throws Exception{
        Uri uri = Uri.parse(Contrato.Ciclo.CONTENT_URI + "/" + ciclo.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Ciclo.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Ciclo.ABREVIATURA, ciclo.getAbreviatura());

        resolver.update(uri, values, null, null);
    }

    public static void updateConBitacora(ContentResolver resolvedor, Ciclo registro) throws Exception {
        update(resolvedor, registro);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Ciclo(registro.getID());
        bitacora.setOperacion(G.OPERACION_MODIFICAR);
        BitacoraProveedor.insert(resolvedor, bitacora);
    }


    static public Ciclo read(ContentResolver resolver, int cicloId)  throws Exception{
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

    static public ArrayList<Ciclo> readAll(ContentResolver resolver)  throws Exception{
        Uri uri = Contrato.Ciclo.CONTENT_URI;

        String[] projection = {Contrato.Ciclo._ID,
                Contrato.Ciclo.NOMBRE,
                Contrato.Ciclo.ABREVIATURA};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Ciclo> registros = new ArrayList<>();

        while (cursor.moveToNext()){
            Ciclo ciclo = new Ciclo();
            ciclo.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Ciclo._ID)));
            ciclo.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Ciclo.NOMBRE)));
            ciclo.setAbreviatura(cursor.getString(cursor.getColumnIndex(Contrato.Ciclo.ABREVIATURA)));
            registros.add(ciclo);
        }

        return registros;

    }

    static public void insert(ContentResolver resolver, JSONArray jsonArray){
        JSONObject obj = null;
        for (int i = 0; i < jsonArray.length(); i++ ){
            try {
                obj = jsonArray.getJSONObject(i);
                //Intenta añadir el registro pero es posible que ya esté dicho Id en la BD local.
                insert(resolver, new Ciclo(obj.getInt("PK_ID"), obj.getString("nombre"), obj.getString("abreviatura")));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
