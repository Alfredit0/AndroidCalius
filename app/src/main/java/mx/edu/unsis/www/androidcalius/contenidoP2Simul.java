package mx.edu.unsis.www.androidcalius;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import baseDatos.baseDatos;


public class contenidoP2Simul extends Fragment {
    //IU de los textView
    private TextView materia1, materia2, materia3, materia4, materia5;
    //IU de los botones
    private EditText p2Mt1,p2Mt2,p2Mt3,p2Mt4,p2Mt5,promedio;
    baseDatos datos;
    parciales par2=new parciales();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contenido_p2_simul, container, false);
        // Inflate the layout for this fragment
        //mandar a llamr los elementos del fragmentos text view y notones
        materia1=(TextView)view.findViewById(R.id.materia1);
        materia2=(TextView)view.findViewById(R.id.materia2);
        materia3=(TextView)view.findViewById(R.id.materia3);
        materia4=(TextView)view.findViewById(R.id.materia4);
        materia5=(TextView)view.findViewById(R.id.materia5);
        //imprimir dentro de ellas la selecccion de la base de datos
        p2Mt1=(EditText)view.findViewById(R.id.calif1);
        p2Mt2=(EditText)view.findViewById(R.id.calif2);
        p2Mt3=(EditText)view.findViewById(R.id.calif3);
        p2Mt4=(EditText)view.findViewById(R.id.calif4);
        p2Mt5=(EditText)view.findViewById(R.id.calif5);
        promedio=(EditText)view.findViewById(R.id.prom);


        //recorer la tabla materias de la base de datos
        datos= new baseDatos(getContext(), "calius",null,1);
        datos.abrir();
        String[] matCal;
        try {
            matCal=datos.materiasEnVistas(1,2);
            materia1.setText(matCal[0]);
           p2Mt1.setText(matCal[1]);
           // p2Mt1.setText("0.0");

            matCal=datos.materiasEnVistas(2,2);
            materia2.setText(matCal[0]);
            p2Mt2.setText(matCal[1]);
            //p2Mt2.setText("0.0");

            matCal=datos.materiasEnVistas(3,2);
            materia3.setText(matCal[0]);
            p2Mt3.setText(matCal[1]);
            //p2Mt3.setText("0.0");

            matCal=datos.materiasEnVistas(4,2);
            materia4.setText(matCal[0]);
            p2Mt4.setText(matCal[1]);
            //p2Mt4.setText("0.0");

            matCal=datos.materiasEnVistas(5,2);
            materia5.setText(matCal[0]);
            p2Mt5.setText(matCal[1]);
            //p2Mt5.setText("0.0");

        }catch (Exception e){}
        //Obteniendo promedio en caso de que ya existieran datos en la BD
            obtenerdatos();
        p2Mt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                obtenerdatos();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    p2Mt2.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            obtenerdatos();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });
    p2Mt3.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            obtenerdatos();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });

    p2Mt4.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            obtenerdatos();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });
            p2Mt5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    obtenerdatos();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        return view;
    }
    public void obtenerdatos() {
        double materia1P2 = -1.0;
        double materia2P2 = -1.0;
        double materia3P2 = -1.0;
        double materia4P2 = -1.0;
        double materia5P2 = -1.0;
        int totalMate = 0;
        double sumaCalif = 0;
        double promedioparcial = 0;
        String aux="";

        try {
            aux=p2Mt1.getText().toString();
            if(aux.equals(""))
            {
                p2Mt1.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia1P2=Double.parseDouble(p2Mt1.getText().toString());
                if(par2.validarCali(materia1P2)==1)
                {
                    p2Mt1.setText("");
                    materia1P2=-1.0;
                    p2Mt1.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia1P2,p2Mt1);
                }
            }

            aux=p2Mt2.getText().toString();
            if(aux.equals(""))
            {
                p2Mt2.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia2P2=Double.parseDouble(p2Mt2.getText().toString());
                if (par2.validarCali(materia2P2)==1)
                {
                    p2Mt2.setText("");
                    materia2P2=-1.0;
                    p2Mt2.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia2P2,p2Mt2);
                }
            }

            aux=p2Mt3.getText().toString();
            if(aux.equals("")){
                p2Mt3.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia3P2=Double.parseDouble(p2Mt3.getText().toString());
                if (par2.validarCali(materia3P2)==1)
                {
                    p2Mt3.setText("");
                    materia3P2=-1.0;
                    p2Mt3.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia3P2,p2Mt3);
                }
            }

            aux=p2Mt4.getText().toString();
            if (aux.equals("")){
                p2Mt4.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia4P2=Double.parseDouble(p2Mt4.getText().toString());
                if (par2.validarCali(materia4P2)==1)
                {
                    p2Mt4.setText("");
                    materia4P2=-1.0;
                    p2Mt4.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia4P2,p2Mt4);
                }
            }

            aux=p2Mt5.getText().toString();
            if(aux.equals(""))
            {
                p2Mt5.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia5P2=Double.parseDouble(p2Mt5.getText().toString());
                if (par2.validarCali(materia5P2)==1)
                {
                    p2Mt5.setText("");
                    materia5P2=-1.0;
                    p2Mt5.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia5P2,p2Mt5);
                }
            }
            par2.setP2Materia1(materia1P2);
            par2.setP2Materia2(materia2P2);
            par2.setP2Materia3(materia3P2);
            par2.setP2Materia4(materia4P2);
            par2.setP2Materia5(materia5P2);

        } catch (NumberFormatException nfe) {
            System.out.println("Guardadndo calificaciones... " + nfe);
        }

        sumaCalif = materia1P2 + materia2P2 + materia3P2 + materia4P2 + materia5P2;
        if (totalMate == 0) {
            promedioparcial = 0.0;
        } else {
            promedioparcial = sumaCalif / totalMate;
        }
        if(totalMate==5){//Si ya estan todas las calificaciònes se imprime el promedio final
            promedio.setText(String.valueOf(promedioparcial).substring(0,3));
            if (promedioparcial<6){//Seleccionando color para las cajas de texto
                promedio.setBackgroundResource(R.drawable.boton_rojo);
            }else {
                promedio.setBackgroundResource(R.drawable.boton_azul);
            }
        }else
        {//Si se borra la calificacion de alguna caja se queda vacìo la caja del promedio final y se regresa a su color de inicio
            promedio.setText("");
            promedio.setBackgroundResource(R.drawable.boton_azul);
        }
    }

    private void asignarColor(double calif, EditText caja) {
        if(calif<6.0){
            caja.setBackgroundResource(R.drawable.boton_rojo);
        }else {
            caja.setBackgroundResource(R.drawable.boton_azulclaro);
        }
    }
}
