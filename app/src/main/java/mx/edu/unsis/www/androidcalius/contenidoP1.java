package mx.edu.unsis.www.androidcalius;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import baseDatos.baseDatos;


public class contenidoP1 extends Fragment {
    View view;
    Activity activity;
    //IU de los textView
    private TextView materia1, materia2, materia3, materia4, materia5;
    //IU de los botones
    private Button p3Mt1,p3Mt2,p3Mt3,p3Mt4,p3Mt5,buttonPromedio;
    baseDatos datos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_contenido_p1, container, false);
        activity=getActivity();
        double promedio=0.0;
        //Toast.makeText(activity, "Hola contenido parcial 1", Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        //mandar a llamr los elementos del fragmentos text view y notones
        materia1=(TextView)view.findViewById(R.id.materia1);
        materia2=(TextView)view.findViewById(R.id.materia2);
        materia3=(TextView)view.findViewById(R.id.materia3);
        materia4=(TextView)view.findViewById(R.id.materia4);
        materia5=(TextView)view.findViewById(R.id.materia5);
        //imprimir dentro de ellas la selecccion de la base de datos
        p3Mt1=(Button)view.findViewById(R.id.calif1);
        p3Mt2=(Button)view.findViewById(R.id.calif2);
        p3Mt3=(Button)view.findViewById(R.id.calif3);
        p3Mt4=(Button)view.findViewById(R.id.calif4);
        p3Mt5=(Button)view.findViewById(R.id.calif5);
        //recorer la tabla materias de la base de datos
        datos= new baseDatos(getContext(), "calius",null,1);
        datos.abrir();
        String[] matCal;
        try {
            matCal=datos.materiasEnVistas(1,1);
            materia1.setText(matCal[0]);
            p3Mt1.setText(matCal[1]);
            promedio=Double.parseDouble(matCal[1])+promedio;
            matCal=datos.materiasEnVistas(2,1);
            materia2.setText(matCal[0]);
            p3Mt2.setText(matCal[1]);
            promedio=Double.parseDouble(matCal[1])+promedio;
            matCal=datos.materiasEnVistas(3,1);
            materia3.setText(matCal[0]);
            p3Mt3.setText(matCal[1]);
            promedio=Double.parseDouble(matCal[1])+promedio;
            matCal=datos.materiasEnVistas(4,1);
            materia4.setText(matCal[0]);
            p3Mt4.setText(matCal[1]);
            promedio=Double.parseDouble(matCal[1])+promedio;
            matCal=datos.materiasEnVistas(5,1);
            materia5.setText(matCal[0]);
            p3Mt5.setText(matCal[1]);
            promedio=Double.parseDouble(matCal[1])+promedio;
            promedio=promedio/5;
            //para promedio tengo que sumar
            buttonPromedio=(Button)view.findViewById(R.id.prom);
            buttonPromedio.setText(String.valueOf(promedio).substring(0,3));
        }catch (Exception e){

        }
        return view;
    }

}
