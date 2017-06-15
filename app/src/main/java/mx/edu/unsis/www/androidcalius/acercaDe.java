package mx.edu.unsis.www.androidcalius;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class acercaDe extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_acerca_de, container, false);
        parciales guaradarPos=new parciales();
        // Guardando la la posicion de la aplicacion
        guaradarPos.setPosicion("acercade");
        return view;
    }
}
