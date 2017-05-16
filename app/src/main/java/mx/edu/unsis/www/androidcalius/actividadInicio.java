package mx.edu.unsis.www.androidcalius;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


import java.io.File;

import static mx.edu.unsis.www.androidcalius.R.id.drawer_layout;


public class actividadInicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager mViewPager;

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        FragmentManager fragmentManager=getSupportFragmentManager();

        Fragment fragment;


        if (id == R.id.nav_cal) {
            setFragmet(new page_one());


            // Handle the camera action
            //Toast.makeText(this,"Calificaciones y id "+id+"",Toast.LENGTH_SHORT).show();
           // Intent prueba=new Intent(actividadInicio.this, prueba2.class);
            //startActivity(prueba);

        } else if (id == R.id.nav_sim) {
            Toast.makeText(this,"simulador  y id "+id+"",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_not) {
            Toast.makeText(this,"Notificaciones",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_acd) {
            Toast.makeText(this,"Acerca de",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_ces) {
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
}
