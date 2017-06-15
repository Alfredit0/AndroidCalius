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


public class contenidoOrdSimul extends Fragment {
    //IU de los textView
    private TextView materia1, materia2, materia3, materia4, materia5;
    //IU de los botones
    private EditText orMt1,orMt2,orMt3,orMt4,orMt5,promedio;
    baseDatos datos;
    parciales ord=new parciales();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contenido_ord_simul, container, false);


        //mandar a llamr los elementos del fragmentos text view y notones
        materia1=(TextView)view.findViewById(R.id.materia1);
        materia2=(TextView)view.findViewById(R.id.materia2);
        materia3=(TextView)view.findViewById(R.id.materia3);
        materia4=(TextView)view.findViewById(R.id.materia4);
        materia5=(TextView)view.findViewById(R.id.materia5);
        //imprimir dentro de ellas la selecccion de la base de datos
        orMt1=(EditText)view.findViewById(R.id.calif1);
        orMt2=(EditText)view.findViewById(R.id.calif2);
        orMt3=(EditText)view.findViewById(R.id.calif3);
        orMt4=(EditText)view.findViewById(R.id.calif4);
        orMt5=(EditText)view.findViewById(R.id.calif5);
        promedio=(EditText)view.findViewById(R.id.prom);
        obtenerdatos();
        //recorer la tabla materias de la base de datos
        datos= new baseDatos(getContext(), "calius",null,1);
        datos.abrir();
        String[] matCal;
        try {
            if(datos.isSimulacion()){
                matCal=datos.materiasEnVistas(1,4);
                materia1.setText(matCal[0]);
                orMt1.setText(datos.leerSimulacion(19));

                matCal=datos.materiasEnVistas(2,4);
                materia2.setText(matCal[0]);
                orMt2.setText(datos.leerSimulacion(20));

                matCal=datos.materiasEnVistas(3,4);
                materia3.setText(matCal[0]);
                orMt3.setText(datos.leerSimulacion(21));

                matCal=datos.materiasEnVistas(4,4);
                materia4.setText(matCal[0]);
                orMt4.setText(datos.leerSimulacion(22));

                matCal=datos.materiasEnVistas(5,4);
                materia5.setText(matCal[0]);
                orMt5.setText(datos.leerSimulacion(23));
            }else{
                matCal=datos.materiasEnVistas(1,4);
                materia1.setText(matCal[0]);
                orMt1.setText(matCal[1]);

                matCal=datos.materiasEnVistas(2,4);
                materia2.setText(matCal[0]);
                orMt2.setText(matCal[1]);

                matCal=datos.materiasEnVistas(3,4);
                materia3.setText(matCal[0]);
                orMt3.setText(matCal[1]);

                matCal=datos.materiasEnVistas(4,4);
                materia4.setText(matCal[0]);
                orMt4.setText(matCal[1]);

                matCal=datos.materiasEnVistas(5,4);
                materia5.setText(matCal[0]);
                orMt5.setText(matCal[1]);
            }

        }catch (Exception e){}
        obtenerdatos();
        orMt1.addTextChangedListener(new TextWatcher() {
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
        orMt2.addTextChangedListener(new TextWatcher() {
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
        orMt3.addTextChangedListener(new TextWatcher() {
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
        orMt4.addTextChangedListener(new TextWatcher() {
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
        orMt5.addTextChangedListener(new TextWatcher() {
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
        obtenerdatos();
        return view;
    }
    public void obtenerdatos() {
        double materia1or = -1.0;
        double materia2or = -1.0;
        double materia3or = -1.0;
        double materia4or = -1.0;
        double materia5or = -1.0;
        int totalMate = 0;
        double sumaCalif = 0;
        double promedioparcial = 0;
        String aux="";

        try {
            aux=orMt1.getText().toString();
            if(aux.equals(""))
            {
                orMt1.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia1or=Double.parseDouble(orMt1.getText().toString());
                if(ord.validarCali(materia1or)==1)
                {
                    orMt1.setText("");
                    materia1or=-1.0;
                    orMt1.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia1or,orMt1);
                }
            }

            aux=orMt2.getText().toString();
            if(aux.equals(""))
            {
                orMt2.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia2or=Double.parseDouble(orMt2.getText().toString());
                if (ord.validarCali(materia2or)==1)
                {
                    orMt2.setText("");
                    materia2or=-1.0;
                    orMt2.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia2or,orMt2);
                }
            }

            aux=orMt3.getText().toString();
            if(aux.equals("")){
                orMt3.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia3or=Double.parseDouble(orMt3.getText().toString());
                if(ord.validarCali(materia3or)==1)
                {
                    orMt3.setText("");
                    materia3or=-1.0;
                    orMt3.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia3or,orMt3);
                }
            }

            aux=orMt4.getText().toString();
            if (aux.equals("")){
                orMt4.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia4or=Double.parseDouble(orMt4.getText().toString());
                if (ord.validarCali(materia4or)==1)
                {
                    orMt4.setText("");
                    materia4or=-1.0;
                    orMt4.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia4or,orMt4);
                }
            }

            aux=orMt5.getText().toString();
            if(aux.equals(""))
            {
                orMt5.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia5or=Double.parseDouble(orMt5.getText().toString());
                if (ord.validarCali(materia5or)==1)
                {
                    orMt5.setText("");
                    materia5or=-1.0;
                    orMt5.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia5or,orMt5);
                }
            }
            ord.setOrMateria1(materia1or);
            ord.setOrMateria2(materia2or);
            ord.setOrMateria3(materia3or);
            ord.setOrMateria4(materia4or);
            ord.setOrMateria5(materia5or);

        } catch (NumberFormatException nfe) {
            System.out.println("Guardadndo calificaciones... " + nfe);
        }

        sumaCalif = materia1or + materia2or + materia3or + materia4or + materia5or;
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
            ord.setPromOrd(promedioparcial);
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
