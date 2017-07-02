package mx.edu.unsis.www.androidcalius;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class actividadVerificarCodigo extends AppCompatActivity {
    //referecnia a los layouts IU
    private Registrar registrar = null;

    private AutoCompleteTextView viewCodigo;
    private View mProgressView;
    private View mLoginFormView;
    //intancia de la conexion
    conexion con=new conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_verificar_codigo);
        //obtener la referencia a la IU
        viewCodigo = (AutoCompleteTextView) findViewById(R.id.codigo);
        getSupportActionBar().setTitle("Registro");
        viewCodigo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //validar si es vacio y el formato que debe cumplir

                    return true;
                }
                return false;
            }
        });
        Button verificar = (Button) findViewById(R.id.verificar);
        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validar que no esten vacios los campos
                validarCampo();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    //metodo para validar el campo del código
    private void validarCampo(){
        /**comprobar si el objeto para el usuario esta vacio o no */
        if (registrar != null) {
            return;
        }
        //resetea loa errores
        viewCodigo.setError(null);

        // Obtiene y guarda los valores respectivos para codigo
        String codigo = viewCodigo.getText().toString();

        //Bandera evidenciar algun error durante la validación de los datos
        boolean cancel = false;
        //Variable para contener el campo a ser enfocado
        View focusView = null;

        if (TextUtils.isEmpty(codigo)) {
            viewCodigo.setError(getString(R.string.error_field_required));
            focusView = viewCodigo;
            cancel = true;
        }else if (codigo.length()!=4) {
            viewCodigo.setError(getString(R.string.error_invalid_cod));
            focusView = viewCodigo;
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
                registrar = new Registrar(codigo);
                //Lanzar el Hilo para la Autenticación del Usuario
                registrar.execute((Void) null);
            }else{
                Toast.makeText(this,"Sin conexión",Toast.LENGTH_SHORT).show();
            }

        }
    }
    public class Registrar extends AsyncTask<Void, Void, Boolean> {

        private final String codigo;
        private String iduser;

        private int actividad=3;

        //Clase para Almacenar el registro
        Registrar(String cod) {
            this.codigo = cod;
        }
        @Override
        protected void onPreExecute(){
            showProgress(true);
        }
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
                Log.i("doInBackground","doInBackground");
                iduser=con.getIduser();
                //Construimos el objeto cliente en formato JSON
                JSONObject dato=con.convertirJson(iduser,codigo,actividad);
                //conexion con el servidor
                URL url = new URL("https://calius.herokuapp.com/verifyusercode");
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
                        //guardar la sesion en alguna parte
                        ///////////////////////////////////
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
            }
            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            registrar = null;
            showProgress(false);

            if (success) {
                finish();
                Intent myIntent = new Intent(actividadVerificarCodigo.this,actividadEnviarContrasena.class);
                actividadVerificarCodigo.this.startActivity(myIntent);
            } else {
                viewCodigo.setError(getString(R.string.codigoIncorrecto));
                viewCodigo.requestFocus();
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
