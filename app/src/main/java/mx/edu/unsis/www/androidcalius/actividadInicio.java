package mx.edu.unsis.www.androidcalius;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import baseDatos.baseDatos;

import static mx.edu.unsis.www.androidcalius.R.id.drawer_layout;


public class actividadInicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // instanciando parciales para obtener la posicion de la aplicacion en ejecucion
    parciales posicion=new parciales();
    conexion con=new conexion();
    baseDatos datos;
    private View mProgressView;
    private View mLoginFormView;
    //la ui del menu que se mostrara solo en el simulaador
    MenuItem itemSetting;
    // aplicación el dentificador de registro en GCM
    private static final String PROPERTY_REG_ID = "registration_id";

    //metodos para obtener el periodo
    private void obtenerPeriodo() {

        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onCancelled() {showProgress(false);}
            @Override
            protected void onPostExecute(final Object result) {
                //showProgress(false);
            }

            @Override
            protected void onPreExecute() {
                showProgress(true);
            }

            @Override
            protected String doInBackground(final Object... params) {

                URL url = null;
                try {
                    url = new URL("https://calius.herokuapp.com/periodo");
                    HttpsURLConnection  conn = (HttpsURLConnection) url.openConnection();
                    //conn.setRequestProperty("Content-Type", "application/json");
                    //conn.setDoOutput(true);//indica a la conexión que se permite el envío de datos hacia el servidor
                    //conn.setDoInput(true);
                    conn.setRequestMethod("GET");

                    int statusCode = conn.getResponseCode();
                    if(statusCode!=200){

                    }else{
                        JSONObject response=con.obtenerRespuesta(conn);
                        Log.i("Demo getPeriodo", "Periodo "+response.get("periodo").toString());
                        //actualizar la tabla usuario insertando el periodo
                        datos.actualizarUsuario(datos.leerUsuario(),response.get("periodo").toString());
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "";
            }
        }.execute(this, null, null);
    }
    //
    private void obtenerMaterias() {

        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onCancelled() {showProgress(false);}
            @Override
            protected void onPostExecute(final Object result) {
                //showProgress(false);
            }

            @Override
            protected void onPreExecute() {
                showProgress(true);
            }

            @Override
            protected String doInBackground(final Object... params) {
                int actividad=5;
                URL url = null;
                try {
                    //Construimos el objeto cliente en formato JSON
                    JSONObject dato=con.convertirJson(datos.leerUsuario(),datos.leerPeriodo(),actividad);
                    //conexion con el servidor
                    url = new URL("https://calius.herokuapp.com/materias");
                    HttpsURLConnection conn=con.con(url);

                    //creando el envio de datos
                    con.enviarDatos(conn,dato);
                    //verificando el estado del servidor
                    int statusCode = conn.getResponseCode();

                    if(statusCode!=200){

                    }else{
                        JSONObject response=con.obtenerRespuesta(conn);
                        //Log.i("Materias","Obteniendo materias con valor de true");
                        //descomponer el json y guardarlas en la n¡base de datos en la tabla materias
                        if(response.getBoolean("statuscon")){
                            Log.i("Materias","Obteniendo materias con valor de true"+response.length());
                            datos.guardarMaterias(response);
                        }else{
                            Log.i("Materias","Obteniendo materias con valor de falso");
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "";
            }
        }.execute(this, null, null);
    }
    public void obtenerCalificaciones(){

        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onCancelled() {showProgress(false);}

            @Override
            protected void onPostExecute(final Object result) {
                showProgress(false);
            }

            @Override
            protected void onPreExecute() {
                showProgress(true);
            }

            @Override
            protected String doInBackground(final Object... params) {
                int actividad=5;
                URL url = null;
                try {
                    //Construimos el objeto cliente en formato JSON
                    JSONObject dato=con.convertirJson(datos.leerUsuario(),datos.leerPeriodo(),actividad);
                    //conexion con el servidor
                    url = new URL("https://calius.herokuapp.com/calificaciones");
                    HttpsURLConnection conn=con.con(url);
                    //creando el envio de datos
                    con.enviarDatos(conn,dato);
                    //verificando el estado del servidor
                    int statusCode = conn.getResponseCode();

                    if(statusCode!=200){

                    }else{
                        JSONObject response=con.obtenerRespuesta(conn);
                        if(response.getBoolean("statuscon")){
                            Log.i("Calificaciones","Obteniendo calificaiones con valor de true");
                            datos.guardarCalificaciones(response);
                        }else{
                            Log.i("Calificaciones","Obteniendo Calificaciones con valor de  false");
                        }
                        //descomponer el json y guardarlas en la n¡base de datos en la tabla materias
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "";
            }
        }.execute(this, null, null);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mLoginFormView = findViewById(R.id.content_main);
        mProgressView = findViewById(R.id.login_progress);


        //creando el contexto
        Context contexto = this;
        //verificacion de la base de datos
        datos= new baseDatos(this, "calius",null,1);
        datos.abrir();

        if(con.isOnlineNet()){
            if(datos.leerUsuario()!=null){
                if(datos.leerPeriodo()==null){
                    //si falla el pedido del periodo en primera instancia
                    obtenerPeriodo();
                    obtenerMaterias();
                    obtenerCalificaciones();
                }else{
                    try {
                        if(datos.leerMaterias(1)==null){
                            //si falla el pedido de materias en primera intancia
                            obtenerMaterias();
                            obtenerCalificaciones();
                        }else{
                            if(datos.leerMaterias(0)==null){
                                //si falla el pedido de calificaciones en primera instancia
                                obtenerCalificaciones();
                            }else{
                                //No hubo nigun fallo pero se tienen que actualizar las calificaciones
                                datos.leerMaterias(1);
                                obtenerCalificaciones();

                            }
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                //prueba de que este el usuario
                //Toast.makeText(this, "Ya hay usuario"+datos.leerUsuario()+" Periodo "+datos.leerPeriodo(), Toast.LENGTH_SHORT).show();setFragmet(new page_one());

            }else{
                //En primera instacnia se guarda el usuario
                datos.insertarUsuario(con.getIduser());
                try {
                    //pedir el periodo
                    obtenerPeriodo();
                    //pedir las materias
                    obtenerMaterias();
                    //pedir callificaciones
                    obtenerCalificaciones();
                }catch (Exception e){
                    Log.i("Error al obtener datos","");
                }
            }
        }else{
            Toast.makeText(contexto, "Sin conexión", Toast.LENGTH_SHORT).show();
        }

        setFragmet(new page_one());
        //setFragmet(new page_one());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.actividad_inicio, menu);
            itemSetting=menu.findItem(R.id.action_settings);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(posicion.getPosicion().equals("simulador")) {
                //instanciar la base de datos tos para guaradra simulacion
                datos= new baseDatos(this, "calius",null,1);
                //verificar si todos los campos de simulacion estan llenos
                if(posicion.isVerificarCampos()){
                    //guardarSimulacion
                    datos.guardarSimulacion();
                    Toast.makeText(this, "Los datos se guarcaron exitosamente "+posicion.getP1Materia1(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(this, "Algunos campos estan vacíos ", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment;
        if (id == R.id.nav_cal) {
            setFragmet(new page_one());
            itemSetting.setVisible(false);
            getSupportActionBar().setTitle(item.getTitle());
        } else if (id == R.id.nav_sim) {
            setFragmet(new simulador());
            itemSetting.setVisible(true);
            getSupportActionBar().setTitle(item.getTitle());
        } else if (id == R.id.nav_not) {
            setFragmet(new notificaciones());
            itemSetting.setVisible(false);
            getSupportActionBar().setTitle(item.getTitle());
        } else if (id == R.id.nav_acd) {
            setFragmet(new acercaDe());
            itemSetting.setVisible(false);
            getSupportActionBar().setTitle(item.getTitle());
        } else if (id == R.id.nav_ces) {
            //Eliminar base de datos del usuario que cierra sesion
            try {
                actividadInicio.this.deleteDatabase("calius");
            }catch (Exception e){}
            //eliminar del sharePreferences el idRegistration
            final SharedPreferences prefs = getPreferenciasCompartidas();
            //String registrationId = prefs.getString(PROPERTY_REG_ID, "");
            //registrationId="";
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(PROPERTY_REG_ID);
           // editor.putString(PROPERTY_REG_ID, registrationId);
            editor.clear();
            editor.commit();
            //eliminar el indicador de una sesion abierta
            try {
                File dir = getFilesDir();
                File file = new File(dir, "sesion.txt");
                boolean deleted = file.delete();
                Intent inicio=new Intent(actividadInicio.this,actividadAcceso.class);
                actividadInicio.this.startActivity(inicio);
                finish();
            }catch (Exception ex)
            {
                Toast.makeText(this,"Cerrar sesión",Toast.LENGTH_SHORT).show();
            }
        }


        return true;
    }
    public void setFragmet(Fragment fragment){
        if(fragment!=null){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
}
