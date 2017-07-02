package mx.edu.unsis.www.androidcalius;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

public class actividadEnviarContrasena extends AppCompatActivity {

    //referecnia a los layouts IU
    private Registrar registrar = null;

    private AutoCompleteTextView viewContraseña;
    private AutoCompleteTextView viewConfirm;

    private View mProgressView;
    private View mLoginFormView;

    //intancia de la conexion
    conexion con=new conexion();

    /////////////////////////////////GCM/////////////////////////////////////////////////////
    // Url del servicio REST que se invoca para el envio del identificador de
    // registro a la aplicación jee
    public static final String URL_REGISTRO_ID = "https://calius.herokuapp.com/registeridnotification";
    // Seña númerica que se utiliza cuando se verifica la disponibilidad de los
    // google play services
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    // Una simple Tag utilizada en los logs
    private static final String TAG = "Demo GCM";

    public static final String EXTRA_MESSAGE = "message";
    // Clave que permite recuperar de las preferencias compartidas de la
    // aplicación el dentificador de registro en GCM
    private static final String PROPERTY_REG_ID = "registration_id";
    // Clave que permite recuperar de las preferencias compartidas de la
    // aplicación el dentificador de la versión de la aplicación
    private static final String PROPERTY_APP_VERSION = "appVersion";
    // Identificador de la instancia del servicio de GCM al cual accedemos
    private static final String SENDER_ID = "178041065057";
    // Clase que da acceso a la api de GCM
    private GoogleCloudMessaging gcm;
    // Identificador de registro
    private String regid;
    // Contexto de la aplicación
    private Context contexto;
    /////////////////////////////////GCM/////////////////////////////////////////////////////
    protected void registrarTelefono(String iduser) {

        contexto = this;
        // Se comprueba que Play Services APK estan disponibles, Si lo esta se
        // proocede con el registro en GCM
        if (chequearPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(contexto);
            // Se recupera el "registration Id" almacenado en caso que la
            // aplicación ya se hubiera registrado previamente
            regid = obtenerIdentificadorDeRegistroAlmacenado();
            // Si no se ha podido recuperar el id del registro procedemos a
            // obtenerlo mediante el proceso de registro
            if (regid.isEmpty()) {
                // Se inicia el proceso de registro
                registroEnSegundoPlano(iduser);
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }
    /**
     * Este metodo comprueba si Google Play Services esta disponible, ya que
     * este requiere que el terminal este asociado a una cuenta de google.Esta
     * verificación es necesaria porque no todos los dispositivos Android estan
     * asociados a una cuenta de Google ni usan sus servicios, por ejemplo, el
     * Kindle fire de Amazon, que es una tablet Android pero no requiere de una
     * cuenta de Google.
     *
     * @return Indica si Google Play Services esta disponible.
     */
    private boolean chequearPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(contexto);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "Dispositivo no soportado.");
                finish();
            }
            return false;
        }
        return true;
    }
    /**
     * Metodo que recupera el registration ID que fue almacenado la ultima vez
     * que la aplicación se registro, En caso que la aplicación este
     * desactualizada o no se haya registrado previamente no se recuperara
     * ningón registration ID
     *
     * @return identificador del registro, o vacio("") si no existe o esta
     *         desactualizado dicho registro
     */
    private String obtenerIdentificadorDeRegistroAlmacenado() {
        final SharedPreferences prefs = getPreferenciasCompartidas();
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Comprueba si la aplicación esta actualizada
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
                Integer.MIN_VALUE);
        int currentVersion = getVersionDeLaAplicacion();
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }
    /**
     * En este método se procede al registro de la aplicación obteniendo el
     * identificador de registro que se almacena en la tarjeta de memoria para
     * no tener que repetir el mismo proceso la próxima vez. Adicionalmente se
     * envía el identificador de registro al a la aplicación jee , invocando un
     * servicio REST.
     */
    private void registroEnSegundoPlano(String iduser) {
        final String id;
        id = iduser;
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPostExecute(final Object result) {
                Log.i(TAG, result.toString());
            }

            @Override
            protected String doInBackground(final Object... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(contexto);
                    }
                    // En este metodo se invoca al servicio de registro de los
                    // servicios GCM
                    regid = gcm.register(SENDER_ID);
                    msg = "Dispositivo registrado, registration ID=" + regid;
                    Log.i(TAG, msg);
                    // Una vez se tiene el identificador de registro se manda a
                    // la aplicacion jee
                    // ya que para que esta envie el mensaje de la notificación
                    // a los servidores
                    // de GCM es necesario dicho identificador
                    enviarIdentificadorRegistroALaAplicacionJ2ee(id);
                    // Se persiste el identificador de registro para que no sea
                    // necesario repetir el proceso de
                    // registro la proxima vez
                    almacenarElIdentificadorDeRegistro(regid);
                } catch (Exception e) {
                    msg = "Error :" + e.getMessage();
                    e.printStackTrace();
                }
                return msg;
            }

        }.execute(this, null, null);
    }
    /**
     * Metodo que sirve para recupera las preferencias compartidas en modo privado
     *
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getPreferenciasCompartidas() {
        return getSharedPreferences(actividadAcceso.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    /**
     * Recupera la versión aplicación que identifica a cada una de las
     * actualizaciones de la misma.
     *
     * @return La versión del codigo de la aplicación
     */
    private int getVersionDeLaAplicacion() {
        try {
            PackageInfo packageInfo = contexto.getPackageManager()
                    .getPackageInfo(contexto.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    /**
     * Se envía el identificador de registro de GCM mediante la invocación de un
     * servicio REST por el método POST, pasándole por parámetro un objeto json
     * que envuelve dicho identificador
     *
     * @param
     *
     * @param
     *
     * @return Devuelve un objeto json que contiene un mensaje de confirmación
     *         del envio del identificador del registro
     * @throws Exception
     */

    private void enviarIdentificadorRegistroALaAplicacionJ2ee(String email)
            throws Exception {
        JSONObject requestRegistrationId = new JSONObject();
        requestRegistrationId.put("passcon", "1234");
        requestRegistrationId.put("iduser", email);
        requestRegistrationId.put("registrationId", regid);
        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(URL_REGISTRO_ID));
            httpPost.setEntity(new StringEntity(requestRegistrationId
                    .toString(), "UTF-8"));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("content-type", "application/json");

            HttpResponse response = client.execute(httpPost);
            InputStreamReader lectura = new InputStreamReader(response.getEntity().getContent());
            in = new BufferedReader(lectura);
            StringBuffer sb = new StringBuffer("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            Log.i("INFO", sb.toString());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * Se almacena el identificador de registro de "Google Cloud Message" y la
     * versión de la aplicación
     *
     * @param regId identificador de registro en GCM
     */
    private void almacenarElIdentificadorDeRegistro(String regId) {
        final SharedPreferences prefs = getPreferenciasCompartidas();
        int appVersion = getVersionDeLaAplicacion();
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    ///////////////////////////////////GCM///////////////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_enviar_contrasena);
        //obtener el contendo de la IU
        viewContraseña = (AutoCompleteTextView) findViewById(R.id.contraseña);
        viewConfirm = (AutoCompleteTextView) findViewById(R.id.confirmContraseña);
        getSupportActionBar().setTitle("Registro");
        Button guardar = (Button) findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validar que no esten vacios los campos
                validarCampos();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void validarCampos(){

        //validar formato del primero solamente
        //validar comparacion

        // Resetea los errores
        viewContraseña.setError(null);
        viewConfirm.setError(null);

        // Obtiene y guarda los valores respectivos para el email y el password
        String contraseña = viewContraseña.getText().toString();
        String confirm = viewConfirm.getText().toString();

        //Bandera evidenciar algun error durante la validación de los datos
        boolean cancel = false;
        //Variable para contener el campo a ser enfocado
        View focusView = null;

        // Comprobar si el campo para el Email esta vacio.
        if (TextUtils.isEmpty(contraseña)) {
            viewContraseña.setError(getString(R.string.error_field_required));
            focusView = viewContraseña;
            cancel = true;
        }else if(TextUtils.isEmpty(confirm)){
            viewConfirm.setError(getString(R.string.error_field_required));
            focusView = viewConfirm;
            cancel = true;
        } else if(contraseña.length()>10 || contraseña.length()<6){
            viewContraseña.setError(getString(R.string.error_invalid_pass));
            focusView = viewContraseña;
            cancel = true;
        }else if(!contraseña.equals(confirm)){
            viewConfirm.setError(getString(R.string.error_coincidencia));
            focusView = viewConfirm;
            cancel = true;
        }


        //Comprobar si hubo un fallo durante el ingreso de datos
        if (cancel) {
            //Enfocar el Campo del Error
            focusView.requestFocus();
        } else {
            if(con.isOnlineNet()){
                //Cargar Animación con una barra de progreso
                //showProgress(true);
                //Crea un nuevo Usuario a partir de la clase  mAuthTask
                registrar = new Registrar(contraseña);
                //Lanzar el Hilo para la Autenticación del Usuario
                registrar.execute((Void) null);
            }else{
                Toast.makeText(this,"Sin conexión",Toast.LENGTH_SHORT).show();
            }

        }
    }
    public class Registrar extends AsyncTask<Void, Void, Boolean> {

        private String password;
        private String iduser;
        private String paass;

        private int actividad=4;

        //Clase para Almacenar el registro
        Registrar(String pass) {
            this.password = pass;
        }
        @Override
        protected void onPreExecute(){
            showProgress(true);
        }
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
                Log.i("doInBackground","doInBackground");
                //obntener el iduser
                iduser=con.getIduser();
                //encriptar contraseña
                paass=password;
                password=con.getMD5(paass);
                //Construimos el objeto cliente en formato JSON
                JSONObject dato=con.convertirJson(iduser,password,actividad);
                //conexion con el servidor
                URL url = new URL("https://calius.herokuapp.com/saveuserpass");
                HttpsURLConnection conn=con.con(url);
                //creando el envio de datos
                con.enviarDatos(conn,dato);
                //verificando el estado del servidor
                int statusCode = conn.getResponseCode();



                if(statusCode!=200){
                    return false;
                }else{
                    //obtenee la resuesta
                    JSONObject response=con.obtenerRespuesta(conn);
                    if( !response.getBoolean("statuscon") || !response.getBoolean("status")){
                        return false;
                    }else{
                        return true;
                    }
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
                return  false;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            registrar = null;
            showProgress(false);

            if (success) {
                //obntener el iduser
                iduser=con.getIduser();
                registrarTelefono(iduser);
                finish();
                Intent myIntent = new Intent(actividadEnviarContrasena.this,actividadInicio.class);
                actividadEnviarContrasena.this.startActivity(myIntent);
            } else {
                viewConfirm.setError(getString(R.string.contraseñaFallida));
                viewConfirm.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            registrar = null;
            showProgress(false);
        }

    }
    /**
     * CARGAR ANIMACION DE UNA BARRA DE PROGRESO CIRCULAR
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
