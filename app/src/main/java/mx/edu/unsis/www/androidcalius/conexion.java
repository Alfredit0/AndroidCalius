package mx.edu.unsis.www.androidcalius;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Elvia on 22/03/2017.
 */

public class conexion {

    public HttpsURLConnection con() throws IOException {
        URL url = new URL("https://calius.herokuapp.com/loginuser");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);//indica a la conexión que se permite el envío de datos hacia el servidor
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        return conn;
    }

    public JSONObject convertirJson(String mEmail, String mPassword) throws JSONException {
        JSONObject dato = new JSONObject();
        dato.put("passcon", "12345");
        dato.put("iduser",mEmail);
        dato.put("password",mPassword);
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
    public static String getMD5(String input) {
        return "hola";
    }
}
