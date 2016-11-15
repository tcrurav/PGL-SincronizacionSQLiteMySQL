package com.example.tiburcio.ejemploproveedorcontenido.volley;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tiburcio.ejemploproveedorcontenido.aplicacion.AppController;
import com.example.tiburcio.ejemploproveedorcontenido.constantes.G;
import com.example.tiburcio.ejemploproveedorcontenido.pojos.Ciclo;
import com.example.tiburcio.ejemploproveedorcontenido.proveedor.CicloProveedor;
import com.example.tiburcio.ejemploproveedorcontenido.sync.Sincronizacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tiburcio on 13/11/2016.
 */

public class CicloVolley {
    final static String ruta = "http://192.168.1.12:8080/Alumnado/webresources/ciclo";
    //ContentResolver resolvedor;
    //RequestQueue queue;
    //Context context;

    //public CicloVolley(ContentResolver resolvedor, Context context) {
    //    this.resolvedor = resolvedor;
    //    //this.queue = Volley.newRequestQueue(context);
    //}

    public static void getAllCiclo(){
        String tag_json_obj = "getAllCiclo"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta;
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());

                        Sincronizacion.realizarActualizacionesDelServidorUnaVezRecibidas(response);

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );
        // add it to the RequestQueue
        //queue.add(getRequest);
        AppController.getInstance().addToRequestQueue(getRequest, tag_json_obj);
    }

    public static void addCiclo(Ciclo ciclo){
        String tag_json_obj = "addCiclo"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PK_ID", ciclo.getID());
            jsonObject.put("nombre", ciclo.getNombre());
            jsonObject.put("abreviatura", ciclo.getAbreviatura());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );
        //queue.add(postRequest);
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }

    public static void updateCiclo(Ciclo ciclo){
        String tag_json_obj = "updateCiclo"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta + "/" + ciclo.getID();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PK_ID", ciclo.getID());
            jsonObject.put("nombre", ciclo.getNombre());
            jsonObject.put("abreviatura", ciclo.getAbreviatura());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );
        //queue.add(putRequest);
        AppController.getInstance().addToRequestQueue(putRequest, tag_json_obj);
    }

    public static void delCiclo(int id){
        String tag_json_obj = "updateCiclo"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta + "/" + String.valueOf(id);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }
        );
        //queue.add(delRequest);
        AppController.getInstance().addToRequestQueue(delRequest, tag_json_obj);
    }
}
