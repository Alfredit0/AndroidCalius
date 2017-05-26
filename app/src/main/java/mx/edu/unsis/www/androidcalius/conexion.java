package mx.edu.unsis.www.androidcalius;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Elvia on 22/03/2017.
 */

public class conexion {
    private static String passcon="12345";
    private static String iduser="";

    /**Para comprovar si hay acceso a internet*/
    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    public HttpsURLConnection con(URL url) throws IOException {
        //URL url = new URL("https://calius.herokuapp.com/loginuser");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);//indica a la conexión que se permite el envío de datos hacia el servidor
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        return conn;
    }

    public JSONObject convertirJson(String mEmail, String mPassword,int actividad) throws JSONException {
        JSONObject dato = new JSONObject();
        dato.put("passcon", passcon);
        if(actividad==1){
            dato.put("iduser",mEmail);
            dato.put("password",mPassword);
            dato.put("usuarioTipo",1);
        }else{
            if(actividad==2){
                dato.put("iduser",mEmail);
                dato.put("phone",mPassword);
            }else{
                if(actividad==3){
                    dato.put("iduser",mEmail);
                    dato.put("code",mPassword);
                }else if(actividad==4){
                    dato.put("iduser",mEmail);
                    dato.put("password",mPassword);
                }
            }
        }


        return dato;
    }

    public void enviarDatos(HttpsURLConnection conn, JSONObject dato) throws IOException {
        OutputStream os = conn.getOutputStream();
        String out = dato.toString();
        os.write(out.getBytes());
        os.flush();
        os.close();
    }
    public JSONObject obtenerRespuesta(HttpsURLConnection conn) throws IOException, JSONException {
        InputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;
        //obtener el resultado
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        JSONObject response = new JSONObject(result.toString());
        return response;
    }


    //metodos para la comparticion de la valiable iduser
    public static String getIduser() {
        return iduser;
    }

    public static void setIduser(String iduser) {
        conexion.iduser = iduser;
    }


    public String getMD5(String cadena) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(cadena.getBytes());

        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16)
            {
                h.append("0").append(Integer.toHexString(u));
            }
            else
            {
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }

    public boolean checkDataBase(String Database_path) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(Database_path, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            Log.e("Error", "No existe la base de datos " + e.getMessage());
        }
        return checkDB != null;
    }

}
