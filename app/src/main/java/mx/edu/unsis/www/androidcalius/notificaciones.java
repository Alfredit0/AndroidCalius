package mx.edu.unsis.www.androidcalius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import baseDatos.baseDatos;

import static android.R.attr.descendantFocusability;
import static android.R.attr.resource;

public class notificaciones extends ListFragment {
    ArrayList<String> asuntos;
    ArrayList<String> destinatario;
    ArrayList<String> fecha;
    baseDatos datos;
    conexion con =new conexion();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=(View) inflater.inflate(R.layout.fragment_notificaciones, container, false);

        //llenar los string para el hostorial de notificac  iones
        datos= new baseDatos(getContext(), "calius",null,1);
        asuntos=datos.leerNotificaciones(1);
        destinatario=datos.leerNotificaciones(2);
        fecha=datos.leerNotificaciones(3);
        //array adapter
        CustomArrayAdapter adapter=new CustomArrayAdapter(this,asuntos,destinatario,fecha);
        //bind adapter to the listFragmet
        setListAdapter(adapter);
        //instancia de la clase que guarada la posicion en el que esta
        parciales guaradarPos=new parciales();
        //Guardando la posicion de la aplicacion
        guaradarPos.setPosicion("notificacion");
        return view;
    }
    //handlig item click
    public void onListItemClick(ListView l,View view,int position, long id){
        //calcular el tamaño de algun array
        int tamaño,idNotif;
        tamaño=asuntos.size();
        //ViewGroup viewGroup =(ViewGroup)view;
        //TextView txt=(TextView)viewGroup.findViewById(R.id.txtitem);
        //Toast.makeText(getActivity(), txt.getText().toString()+" posicion "+position +" tamaño "+tamaño, Toast.LENGTH_SHORT).show();

        //calcular comparando la posicion con el tamaño y asignarlo en una varible estatica
        idNotif=tamaño-position;
        con.setIdNotificacion(idNotif);
        //mandar a llamar a otra actividad
        startActivity(new Intent(getContext(), mensajeNotificaciones.class));
    }

}
