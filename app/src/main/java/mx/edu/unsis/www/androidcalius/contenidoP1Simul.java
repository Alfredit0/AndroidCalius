package mx.edu.unsis.www.androidcalius;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import baseDatos.baseDatos;


public class contenidoP1Simul extends Fragment  {
            //EditText et;

            //IU de los textView
            private TextView materia1, materia2, materia3, materia4, materia5;
            //IU de los botones
            private EditText p1Mt1,p1Mt2,p1Mt3,p1Mt4,p1Mt5,promedio;
            baseDatos datos;
            parciales par1=new parciales();
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                View view = inflater.inflate(R.layout.fragment_contenido_p1_simul,
                        container, false);
                //mandar a llamr los elementos del fragmentos text view y notones
                materia1=(TextView)view.findViewById(R.id.materia1);
                materia2=(TextView)view.findViewById(R.id.materia2);
                materia3=(TextView)view.findViewById(R.id.materia3);
                materia4=(TextView)view.findViewById(R.id.materia4);
                materia5=(TextView)view.findViewById(R.id.materia5);
                //imprimir dentro de ellas la selecccion de la base de datos
                p1Mt1=(EditText)view.findViewById(R.id.calif1);
                p1Mt2=(EditText)view.findViewById(R.id.calif2);
                p1Mt3=(EditText)view.findViewById(R.id.calif3);
                p1Mt4=(EditText)view.findViewById(R.id.calif4);
                p1Mt5=(EditText)view.findViewById(R.id.calif5);
                promedio=(EditText)view.findViewById(R.id.prom);
                //LLamada del procedimiento para obtener las calificaciònes, asignar color y guardar los valores.
                obtenerdatos();
                //recorer la tabla materias de la base de datos
                datos= new baseDatos(getContext(), "calius",null,1);
                datos.abrir();
                String[] matCal;
                try {
                    matCal=datos.materiasEnVistas(1,1);
                    materia1.setText(matCal[0]);
                    p1Mt1.setText(matCal[1]);

                    matCal=datos.materiasEnVistas(2,1);
                    materia2.setText(matCal[0]);
                    p1Mt2.setText(matCal[1]);

                    matCal=datos.materiasEnVistas(3,1);
                    materia3.setText(matCal[0]);
                    p1Mt3.setText(matCal[1]);

                    matCal=datos.materiasEnVistas(4,1);
                    materia4.setText(matCal[0]);
                    p1Mt4.setText(matCal[1]);

                    matCal=datos.materiasEnVistas(5,1);
                    materia5.setText(matCal[0]);
                    p1Mt5.setText(matCal[1]);

                }catch (Exception e){
                }
                //Obteniendo promedio en caso de que ya existieran datos en la BD
                obtenerdatos();
                // creaciòn de metodos para obtener los cambios en las cajas de texto
                p1Mt1.addTextChangedListener(new TextWatcher() {
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
                p1Mt2.addTextChangedListener(new TextWatcher() {
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
                p1Mt3.addTextChangedListener(new TextWatcher() {
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
                p1Mt4.addTextChangedListener(new TextWatcher() {
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
                p1Mt5.addTextChangedListener(new TextWatcher() {
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
                //return inflater.inflate(R.layout.fragment_contenido_p1_simul, container, false);
            }

// Procedimiento para las operaciones del simulador, para el primer parcial
    public void obtenerdatos() {
        // Declaraciòn de variables locales para guardar las calificaciònes
        double materia1P1 = -1.0;
        double materia2P1 = -1.0;
        double materia3P1 = -1.0;
        double materia4P1 = -1.0;
        double materia5P1 = -1.0;
        int totalMate = 0;//Guarda el total de las calificaciònes como se van registrando
        double sumaCalif = 0;//Guarda la suma de las calificaciones
        double promedioparcial = 0;//Guarda el promedio de los parciales
        String aux="";// Sirve para validar si la caja esta vacia

        try {
            //Se Obtiene lo que contiene la caja de texto
            aux=p1Mt1.getText().toString();
            if(aux.equals(""))
            {//Si la caja esta vacia se le asigna el colo de inicio
                p1Mt1.setBackgroundResource(R.drawable.boton_azulclaro);
            }else
                {//Si la caja no esta vacia el valor se le asigna a mataria1p1
                 materia1P1=Double.parseDouble(p1Mt1.getText().toString());
                    //validando que el valor este entre el rango de 0-10
                if(par1.validarCali(materia1P1)==1)
                {
                    //si el valor no se encuentra dentro del rango se vacia la caja
                    p1Mt1.setText("");
                    materia1P1=-1;
                    p1Mt1.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    //Si la calificacion es valida se le aumenta al contador totalMate
                    totalMate = totalMate + 1;
                    //funciòn para asignar el color a la caja de texto segun la calificaciòn
                    asignarColor(materia1P1,p1Mt1);
                }
            }

            aux=p1Mt2.getText().toString();
            if(aux.equals(""))
            {
                p1Mt2.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia2P1=Double.parseDouble(p1Mt2.getText().toString());
                if(par1.validarCali(materia2P1)==1)
                {
                    p1Mt2.setText("");
                    materia2P1=-1;
                    p1Mt2.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia2P1,p1Mt2);
                }
            }

            aux=p1Mt3.getText().toString();
            if(aux.equals("")){
                p1Mt3.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia3P1=Double.parseDouble(p1Mt3.getText().toString());
                if(par1.validarCali(materia3P1)==1)
                {
                    p1Mt3.setText("");
                    materia3P1=-1;
                    p1Mt3.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia3P1,p1Mt3);
                }
            }

            aux=p1Mt4.getText().toString();
            if (aux.equals("")){
                p1Mt4.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia4P1=Double.parseDouble(p1Mt4.getText().toString());
                if(par1.validarCali(materia4P1)==1)
                {
                    p1Mt4.setText("");
                    materia4P1=-1;
                    p1Mt4.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia4P1,p1Mt4);
                }
            }

            aux=p1Mt5.getText().toString();
            if(aux.equals(""))
            {
                p1Mt5.setBackgroundResource(R.drawable.boton_azulclaro);
            }else {
                materia5P1=Double.parseDouble(p1Mt5.getText().toString());
                if(par1.validarCali(materia5P1)==1)
                {
                    p1Mt5.setText("");
                    materia1P1=-1;
                    p1Mt5.setBackgroundResource(R.drawable.boton_azulclaro);
                }else {
                    totalMate = totalMate + 1;
                    asignarColor(materia5P1,p1Mt5);
                }
            }
            //Enviando las calificaciònes para mantener los datos
            par1.setP1Materia1(materia1P1);
            par1.setP1Materia2(materia2P1);
            par1.setP1Materia3(materia3P1);
            par1.setP1Materia4(materia4P1);
            par1.setP1Materia5(materia5P1);

        } catch (NumberFormatException nfe) {
            System.out.println("Error de contenido P1 sumulador  " + nfe);
        }
        //Suma de las calificaciones obtenidas en cada caja
        sumaCalif = materia1P1 + materia2P1 + materia3P1 + materia4P1 + materia5P1;
        if (totalMate == 0) {// Si no hay calificaciònes inici al promedio con 0.0
            promedioparcial = 0.0;
        } else {

            //Se optiene la division para el promedio
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
//Procedimiento para asignar color a las cajas de texto segun la calificaciòn
    private void asignarColor(double calif, EditText caja) {
        if(calif<6.0){
            caja.setBackgroundResource(R.drawable.boton_rojo);
        }else {
            caja.setBackgroundResource(R.drawable.boton_azulclaro);
        }
    }
}
