package com.example.tiburcio.ejemploproveedorcontenido.rest;

import android.util.Log;

import com.example.tiburcio.ejemploproveedorcontenido.pojos.Ciclo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Tiburcio on 11/11/2016.
 */

public class CicloRest {
    final static String ruta = "http://192.168.1.12:8080/Alumnado/webresources/ciclo";

    public static ArrayList<Ciclo> getAllCiclo(){
        ArrayList<Ciclo> registros = new ArrayList<>();

        try {
            URL url = new URL(ruta);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);

            InputStream is = conexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder respuesta = new StringBuilder();
            String datos;

            while((datos = br.readLine()) != null){
                respuesta.append(datos);
            }

            JSONArray jsonArray = new JSONArray(respuesta.toString());

            Log.i("WS: ", respuesta.toString());

            for (int i = 0; i < jsonArray.length(); i++ ){
                JSONObject obj = jsonArray.getJSONObject(i);
                registros.add(new Ciclo(obj.getInt("PK_ID"), obj.getString("nombre"), obj.getString("abreviatura")));
            }

            return registros;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean addCiclo(Ciclo registro){

        try {
            URL url = new URL(ruta);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoInput(true);
            conexion.setDoOutput(false);
            conexion.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //conexion.setRequestProperty("Host", "192.168.1.12");
            conexion.connect();

            //OutputStream os = conexion.getOutputStream();
            //OutputStreamWriter osw = new OutputStreamWriter(os);
            //BufferedWriter bw = new BufferedWriter(osw);

            DataOutputStream printout = new DataOutputStream(conexion.getOutputStream ());
            printout.writeBytes(URLEncoder.encode(registro.toJSON(),"UTF-8"));
            printout.flush ();
            printout.close ();
            //http://stackoverflow.com/questions/13911993/sending-a-json-http-post-request-from-android

            //Se envía petición
            //bw.write(registro.toJSON());
            //bw.flush();
            //bw.close();

            //Se recibe respuesta
/*            InputStream is = conexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder respuesta = new StringBuilder();
            String datos;

            while((datos = br.readLine()) != null){
                respuesta.append(datos);
            }

            Log.i("Respuesta: ", respuesta.toString());
*/
            Log.i("Respuesta: ", "hola");
            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } //catch (JSONException e) {
        //    e.printStackTrace();
        //}

        return false;
    }
}
