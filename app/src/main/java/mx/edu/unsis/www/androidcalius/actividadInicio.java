package mx.edu.unsis.www.androidcalius;

import android.content.Intent;
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
import android.widget.Toast;


import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import baseDatos.baseDatos;

import static mx.edu.unsis.www.androidcalius.R.id.drawer_layout;


public class actividadInicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager mViewPager;
    conexion con=new conexion();
    baseDatos datos;
    private String iduser;
    private String Database_path="/data/data/mx.edu.unsis.www.androidcalius/databases/calius.db";

    //metodos para obtener el periodo
    private void obtenerPeriodo() {

        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPostExecute(final Object result) {
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
            protected void onPostExecute(final Object result) {
            }

            @Override
            protected String doInBackground(final Object... params) {
                int actividad=5;
                URL url = null;
                try {
                    //Construimos el objeto cliente en formato JSON
                    JSONObject dato=con.convertirJson(datos.leerUsuario(),datos.leerPeriodo(),actividad);
                    //conexion con el servidor
                    url = new URL("https://calius.herokuapp.com /materias");
                    HttpsURLConnection conn=con.con(url);

                    //creando el envio de datos
                    con.enviarDatos(conn,dato);
                    //verificando el estado del servidor
                    int statusCode = conn.getResponseCode();

                    if(statusCode!=200){

                    }else{
                        JSONObject response=con.obtenerRespuesta(conn);
                        //descomponer el json y guardarlas en la n¡base de datos en la tabla materias
                        datos.guardarMaterias(response);

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
            protected void onPostExecute(final Object result) {
            }

            @Override
            protected String doInBackground(final Object... params) {
                int actividad=5;
                URL url = null;
                try {
                    //Construimos el objeto cliente en formato JSON
                    JSONObject dato=con.convertirJson(datos.leerUsuario(),datos.leerPeriodo(),actividad);
                    //conexion con el servidor
                    url = new URL("https://calius.herokuapp.com /calificaciones");
                    HttpsURLConnection conn=con.con(url);

                    //creando el envio de datos
                    con.enviarDatos(conn,dato);
                    //verificando el estado del servidor
                    int statusCode = conn.getResponseCode();

                    if(statusCode!=200){

                    }else{
                        JSONObject response=con.obtenerRespuesta(conn);
                        //descomponer el json y guardarlas en la n¡base de datos en la tabla materias
                        datos.guardarCalificaciones(response);
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

        datos= new baseDatos(this, "calius",null,1);
        datos.abrir();
        if(datos.leerUsuario()!=null){
            //si hay un usuario
            //seleccionarla y mandar a pedir las calificaciones
            Toast.makeText(this, "Ya hay usuario"+datos.leerUsuario()+" Periodo "+datos.leerPeriodo(), Toast.LENGTH_SHORT).show();
        }else{
            //es la primera vez guaradar al usuario en la base de datos
            datos.insertarUsuario(con.getIduser(),"");
            //pedir el periodo
            obtenerPeriodo();
            //pedir las materias
            obtenerMaterias();
            //pedir callificaciones
            obtenerCalificaciones();
            Toast.makeText(this, "Peridodo "+datos.leerPeriodo(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "No existe usuario en BD pero el get si "+con.getIduser(), Toast.LENGTH_SHORT).show();
        }
        //si existe
            //identificar la referencia a esa base de datos
            //seleccionar la matricula de la tabla usuario
            //llamar las caificaciones mandando la matricula
        //si no existe
            //crear la base de datos insetar el iduser
            //mandar a llamar el perioso mandando la matricula
            //mandar a llamar las materias mandando la matricula
            //mandar a llamar las calificaciones mandando la matricula
        /*
        try {
            baseDatos datos= new baseDatos(this, "calius",null,1);
            datos.abrir();
            Toast.makeText(this, "matricula en inicio "+datos.leerUsuario(), Toast.LENGTH_SHORT).show();

        }catch (Exception e){

        }*/


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setFragmet(new page_one());
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

        } else if (id == R.id.nav_sim) {
            setFragmet(new simulador());
        } else if (id == R.id.nav_not) {
            Toast.makeText(this,"Notificaciones",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_acd) {
            Toast.makeText(this,"Acerca de",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_ces) {
            try {

                baseDatos db=new baseDatos(this, "calius",null,1);
                //eliminar los registros de la base de datos usuario
                if(db.eliminarDB()){
                    Toast.makeText(this, "SE elimino la base de datos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "No se elimino la base de datos", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){}
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
            //eliminar del sharePreferences el idRegistration
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
}
