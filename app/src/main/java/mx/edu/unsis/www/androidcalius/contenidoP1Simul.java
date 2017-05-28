package mx.edu.unsis.www.androidcalius;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


        public class contenidoP1Simul extends Fragment  {
            EditText et;
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                View view = inflater.inflate(R.layout.fragment_contenido_p1_simul,
                        container, false);
               /* Button button = (Button) view.findViewById(R.id.calif2);
                button.setOnClickListener(this);
                et=(EditText) view.findViewById(R.id.EditView2);*/
                return view;
                //return inflater.inflate(R.layout.fragment_contenido_p1_simul, container, false);
            }




        }
