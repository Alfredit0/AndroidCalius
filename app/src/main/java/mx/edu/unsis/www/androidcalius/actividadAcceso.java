package mx.edu.unsis.www.androidcalius;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class actividadAcceso extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private static final int REQUEST_READ_CONTACTS = 0;
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    //intancia de la clase conexion
    conexion con=new conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            BufferedReader opSesion = new BufferedReader(new InputStreamReader(openFileInput("sesion.txt")));
            String texto = opSesion.readLine();
            opSesion.close();
            Intent myIntent = new Intent(actividadAcceso.this,actividadInicio.class);
            actividadAcceso.this.startActivity(myIntent);
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
        setContentView(R.layout.activity_actividad_acceso);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        /**adiciona la escucha del evento ENTER sobre la caja de texto de la contraña se lanaza ala fucnion attemptLogin(); para validar el formulario*/
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        /**evento click en el boton acceder se lanaza ala fucnion attemptLogin(); para validar el formulario*/
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        /**evento clic en el boton registro para llevar a la activity_registro*/
        Button mEmailRegistroButton = (Button) findViewById(R.id.registro);
        mEmailRegistroButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividadRegistro=new Intent(actividadAcceso.this, mx.edu.unsis.www.androidcalius.actividadRegistro.class);
                startActivity(actividadRegistro);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    /**validad los componentes del formulario de accso*/
    private void attemptLogin() {
        /**comprobar si el objeto para el usuario esta vacio o no */
        if (mAuthTask != null) {
            return;
        }

        // Resetea los errores
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Obtiene y guarda los valores respectivos para el email y el password
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        //Bandera evidenciar algun error durante la validación de los datos
        boolean cancel = false;
        //Variable para contener el campo a ser enfocado
        View focusView = null;

        // Comprobar si el password ingresado no es nulo y es valido.
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Comprobar si el campo para el Email esta vacio.
        if (TextUtils.isEmpty(email)) {
            //Envia el error a la caja de Texto
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
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
                mAuthTask = new UserLoginTask(email, password);
                //Lanzar el Hilo para la Autenticación del Usuario
                mAuthTask.execute((Void) null);
            }else{
                Toast.makeText(this,"Sin conexión",Toast.LENGTH_SHORT).show();
            }

        }
    }
    //Comprobar si un email es valido o no
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return isNumeric(email) && email.length()==10;
    }
    //Comprobar si la contraseña ingresada cumple con restricciones establecidas
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    /**
     * Método para validar si el dato introducido en la caja de texto de matrícula es numérico
     * **/
    private boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(actividadAcceso.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * CLASE PARA ALMACENAR LOS USUARIOS Y METODOS ASICRONOS PARA VALIDAR EL USUARIO INGRESADO
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private String mPassword;
        private String passEncripatada;
        private int actividad=1;
        //para recibir el valor del servidor segun el analisis de la amtricula y la contraseña
        String valor;

        //Clase para Almacenar los Usuarios
        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }
        @Override
        protected void onPreExecute(){
            showProgress(true);
        }
        //Hilo para validar si el Correo y contraseña ingresados son correctos

        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
                Log.i("doInBackground","doInBackground");
                //encriptacion del password
                passEncripatada=mPassword;
                mPassword=con.getMD5(passEncripatada);
                //Construimos el objeto cliente en formato JSON
                JSONObject dato=con.convertirJson(mEmail,mPassword,actividad);
                //conexion con el servidor
                URL url = new URL("https://calius.herokuapp.com/loginuser");
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
                        //guardar la sesion
                        ///////////////////////////////////
                        try
                        {
                            OutputStreamWriter Sesion=
                                    new OutputStreamWriter(
                                            openFileOutput("sesion.txt", Context.MODE_PRIVATE));

                            Sesion.write(""+mEmail+":"+mPassword+"");
                            Sesion.close();
                        }
                        catch (Exception exx)
                        {
                            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
                        }
                        return true;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return  false;
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
                Intent myIntent = new Intent(actividadAcceso.this,actividadInicio.class);
                actividadAcceso.this.startActivity(myIntent);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

