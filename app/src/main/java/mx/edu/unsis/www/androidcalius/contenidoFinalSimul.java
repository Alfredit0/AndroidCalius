package mx.edu.unsis.www.androidcalius;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import baseDatos.baseDatos;


public class contenidoFinalSimul extends Fragment {
    //IU de los textView
    private TextView materia1, materia2, materia3, materia4, materia5;
    baseDatos datos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contenido_final_simul, container, false);

        //mandar a llamr los elementos del fragmentos text view y notones
        materia1=(TextView)view.findViewById(R.id.materia1);
        materia2=(TextView)view.findViewById(R.id.materia2);
        materia3=(TextView)view.findViewById(R.id.materia3);
        materia4=(TextView)view.findViewById(R.id.materia4);
        materia5=(TextView)view.findViewById(R.id.materia5);

        //recorer la tabla materias de la base de datos
        datos= new baseDatos(getContext(), "calius",null,1);
        datos.abrir();
        String[] matCal;
        try {
            matCal=datos.materiasEnVistas(1,1);
            materia1.setText(matCal[0]);


            matCal=datos.materiasEnVistas(2,1);
            materia2.setText(matCal[0]);


            matCal=datos.materiasEnVistas(3,1);
            materia3.setText(matCal[0]);


            matCal=datos.materiasEnVistas(4,1);
            materia4.setText(matCal[0]);


            matCal=datos.materiasEnVistas(5,1);
            materia5.setText(matCal[0]);


        }catch (Exception e){}


        return view;
    }
}
