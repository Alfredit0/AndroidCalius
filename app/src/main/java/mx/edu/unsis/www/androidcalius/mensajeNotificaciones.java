package mx.edu.unsis.www.androidcalius;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import baseDatos.baseDatos;

public class mensajeNotificaciones extends AppCompatActivity {
    conexion con =new conexion();
    baseDatos datos;
    int idNotif;
    String[] mensajeNotif;
    TextView remitente;
    TextView mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje_notificaciones);
        //añadir boton regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //obtener el id de notificacion atraves de la varible estatica idNotificacion de la clase conexion
        idNotif=con.getIdNotificacion();
        //obtener el mesaje de este id de notificacion
        datos= new baseDatos(this, "calius",null,1);
        mensajeNotif=datos.leerMensajeRemitente(idNotif);
        //Toast.makeText(this, "remitente "+mensajeNotif[0] + "mensaje "+mensajeNotif[1], Toast.LENGTH_SHORT).show();
        remitente=(TextView)findViewById(R.id.remitente);
        mensaje=(TextView)findViewById(R.id.mensaje);
        remitente.setText(mensajeNotif[0]+":");
        mensaje.setText(mensajeNotif[1]);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
