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


public class contenidoP3Simul extends Fragment {
    //IU de los textView
    private TextView materia1, materia2, materia3, materia4, materia5;
    //IU de los botones
    private EditText p3Mt1,p3Mt2,p3Mt3,p3Mt4,p3Mt5,promedio;
    baseDatos datos;
    parciales par3=new parciales();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_contenido_p3_simul, container, false);
        //mandar a llamr los elementos del fragmentos text view y notones
        materia1=(TextView)view.findViewById(R.id.materia1);
        materia2=(TextView)view.findViewById(R.id.materia2);
        materia3=(TextView)view.findViewById(R.id.materia3);
        materia4=(TextView)view.findViewById(R.id.materia4);
        materia5=(TextView)view.findViewById(R.id.materia5);
        //imprimir dentro de ellas la selecccion de la base de datos
        p3Mt1=(EditText)view.findViewById(R.id.calif1);
        p3Mt2=(EditText)view.findViewById(R.id.calif2);
        p3Mt3=(EditText)view.findViewById(R.id.calif3);
        p3Mt4=(EditText)view.findViewById(R.id.calif4);
        p3Mt5=(EditText)view.findViewById(R.id.calif5);
        promedio=(EditText)view.findViewById(R.id.prom);

        //recorer la tabla materias de la base de datos
        datos= new baseDatos(getContext(), "calius",null,1);
        datos.abrir();
        String[] matCal;
        try {
            if(datos.isSimulacion()){
                matCal=datos.materiasEnVistas(1,3);
                materia1.setText(matCal[0]);
                p3Mt1.setText(datos.leerSimulacion(13));

                matCal=datos.materiasEnVistas(2,3);
                materia2.setText(matCal[0]);
                p3Mt2.setText(datos.leerSimulacion(14));

                matCal=datos.materiasEnVistas(3,3);
                materia3.setText(matCal[0]);
                p3Mt3.setText(datos.leerSimulacion(15));

                matCal=datos.materiasEnVistas(4,3);
                materia4.setText(matCal[0]);
                p3Mt4.setText(datos.leerSimulacion(16));

                matCal=datos.materiasEnVistas(5,3);
                materia5.setText(matCal[0]);
                p3Mt5.setText(datos.leerSimulacion(17));

            }else{
                matCal=datos.materiasEnVistas(1,3);
                materia1.setText(matCal[0]);
                p3Mt1.setText(matCal[1]);

                matCal=datos.materiasEnVistas(2,3);
                materia2.setText(matCal[0]);
                p3Mt2.setText(matCal[1]);

                matCal=datos.materiasEnVistas(3,3);
                materia3.setText(matCal[0]);
                p3Mt3.setText(matCal[1]);

                matCal=datos.materiasEnVistas(4,3);
                materia4.setText(matCal[0]);
                p3Mt4.setText(matCal[1]);

                matCal=datos.materiasEnVistas(5,3);
                materia5.setText(matCal[0]);
                p3Mt5.setText(matCal[1]);
            }

        }catch (Exception e){}
        obtenerdatos();
        p3Mt1.addTextChangedListener(new TextWatcher() {
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
        p3Mt2.addTextChangedListener(new TextWatcher() {
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
        p3Mt3.addTextChangedListener(new TextWatcher() {
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
        p3Mt4.addTextChangedListener(new TextWatcher() {
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
        p3Mt5.addTextChangedListener(new TextWatcher() {
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
        double materia1P3 = -1.0;
        double materia2P3 = -1.0;
        double materia3P3 = -1.0;
        double materia4P3 = -1.0;
        double materia5P3 = -1.0;
        int totalMate = 0;
        double sumaCalif = 0;
        double promedioparcial = 0;
        String aux="";

        try {
            aux=p3Mt1.getText().toString();
            if(aux.equals(""))
            {
                p3Mt1.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia1P3=Double.parseDouble(p3Mt1.getText().toString());
                if (par3.validarCali(materia1P3)==1){
                    p3Mt1.setText("");
                    materia1P3=-1.0;
                    p3Mt1.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia1P3,p3Mt1);
                }
            }

            aux=p3Mt2.getText().toString();
            if(aux.equals(""))
            {
                p3Mt2.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia2P3=Double.parseDouble(p3Mt2.getText().toString());
                if (par3.validarCali(materia2P3)==1)
                {
                    p3Mt2.setText("");
                    materia2P3=-1.0;
                    p3Mt2.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia2P3,p3Mt2);
                }
            }

            aux=p3Mt3.getText().toString();
            if(aux.equals("")){
                p3Mt3.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia3P3=Double.parseDouble(p3Mt3.getText().toString());
                if (par3.validarCali(materia3P3)==1)
                {
                    p3Mt3.setText("");
                    materia3P3=-1.0;
                    p3Mt3.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia3P3,p3Mt3);
                }
            }

            aux=p3Mt4.getText().toString();
            if (aux.equals("")){
                p3Mt4.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia4P3=Double.parseDouble(p3Mt4.getText().toString());
                if(par3.validarCali(materia4P3)==1)
                {
                    p3Mt4.setText("");
                    materia4P3=-1.0;
                    p3Mt4.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia4P3,p3Mt4);
                }
            }

            aux=p3Mt5.getText().toString();
            if(aux.equals(""))
            {
                p3Mt5.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia5P3=Double.parseDouble(p3Mt5.getText().toString());
                if (par3.validarCali(materia5P3)==1)
                {
                    p3Mt5.setText("");
                    materia5P3=-1.0;
                    p3Mt5.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia5P3,p3Mt5);
                }
            }
            par3.setP3Materia1(materia1P3);
            par3.setP3Materia2(materia2P3);
            par3.setP3Materia3(materia3P3);
            par3.setP3Materia4(materia4P3);
            par3.setP3Materia5(materia5P3);

        } catch (NumberFormatException nfe) {
            System.out.println("Guardadndo calificaciones... " + nfe);
        }

        sumaCalif = materia1P3 + materia2P3 + materia3P3 + materia4P3 + materia5P3;
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
            par3.setPromParcial3(promedioparcial);
        }else
        {//Si se borra la calificacion de alguna caja se queda vacìo la caja del promedio final y se regresa a su color de inicio
            promedio.setText("");
            promedio.setBackgroundResource(R.drawable.boton_azul);
        }
    }
    //Procedimiento para asignar color a las cajas de texto
    private void asignarColor(double calif, EditText caja) {
        if(calif<6.0){
            caja.setBackgroundResource(R.drawable.boton_rojo);
        }else {
            caja.setBackgroundResource(R.drawable.boton_azulclaro);
        }
    }
}
