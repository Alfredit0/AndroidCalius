package mx.edu.unsis.www.androidcalius;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import baseDatos.baseDatos;

public class contenidoFinal extends Fragment {
    View view;
    //IU de los textView
    private TextView materia1, materia2, materia3, materia4, materia5;
    baseDatos datos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_contenido_final, container, false);
        //Toast.makeText(getContext(), "Hola contenido Final", Toast.LENGTH_SHORT).show();
        //mandar a llamr los elementos del fragmentos text view y notones
        materia1=(TextView)view.findViewById(R.id.materia1);
        materia2=(TextView)view.findViewById(R.id.materia2);
        materia3=(TextView)view.findViewById(R.id.materia3);
        materia4=(TextView)view.findViewById(R.id.materia4);
        materia5=(TextView)view.findViewById(R.id.materia5);
        //recorer la tabla materias de la base de datos
        try {
            datos= new baseDatos(getContext(), "calius",null,1);
            datos.abrir();
            String[] matCal;
            matCal=datos.materiasEnVistas(1,0);
            materia1.setText(matCal[0]);
            matCal=datos.materiasEnVistas(2,0);
            materia2.setText(matCal[0]);
            matCal=datos.materiasEnVistas(3,0);
            materia3.setText(matCal[0]);
            matCal=datos.materiasEnVistas(4,0);
            materia4.setText(matCal[0]);
            matCal=datos.materiasEnVistas(5,0);
            materia5.setText(matCal[0]);
        }catch (Exception e){}
        return  view;
    }
}
