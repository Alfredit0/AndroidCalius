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
import android.widget.Toast;

import baseDatos.baseDatos;


public class contenidoFinalSimul extends Fragment {
    //IU de los textView
    private TextView materia1, materia2, materia3, materia4, materia5;
    private EditText Mt1,Mt2,Mt3,Mt4,Mt5,promedio,prom;

    baseDatos datos;
    double mat1;
    parciales calFinal=new parciales();
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


        Mt1=(EditText)view.findViewById(R.id.calif1);
        Mt2=(EditText)view.findViewById(R.id.calif2);
        Mt3=(EditText)view.findViewById(R.id.calif3);
        Mt4=(EditText)view.findViewById(R.id.calif4);
        Mt5=(EditText)view.findViewById(R.id.calif5);
        promedio=(EditText)view.findViewById(R.id.promedioFin);
        prom=(EditText)view.findViewById(R.id.prom);
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
        prom.addTextChangedListener(new TextWatcher() {
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
        double parMat1;
        double parMat2;
        double parMat3;
        double ord;
        double aux;
        double prom = 0.0;
        double sumaCalif=0.0;
        int mostrar=0;
        double promedioFinal=0.0;
        int totalMate=0;
        try{
            parMat1=calFinal.getP1Materia1();
            parMat2=calFinal.getP2Materia1();
            parMat3=calFinal.getP3Materia1();
            ord=calFinal.getOrMateria1();
            aux=(parMat1+parMat2+parMat3)/3;
            prom=(aux+ord)/2;
            sumaCalif=prom;
            mostrar=ValidarCajas(parMat1,parMat2,parMat3,ord);
            if(mostrar==1) {
                Mt1.setText(String.valueOf(prom));
                totalMate=totalMate+1;
            }else {
                Mt1.setText("");
            }

            parMat1=calFinal.getP1Materia2();
            parMat2=calFinal.getP2Materia2();
            parMat3=calFinal.getP3Materia2();
            ord=calFinal.getOrMateria2();
            aux=(parMat1+parMat2+parMat3)/3;
            prom=(aux+ord)/2;
            sumaCalif=sumaCalif+prom;
            mostrar=ValidarCajas(parMat1,parMat2,parMat3,ord);
            if(mostrar==1){
                Mt2.setText(String.valueOf(prom));
                totalMate=totalMate+1;
            }else {
                Mt2.setText("");
            }


            parMat1=calFinal.getP1Materia3();
            parMat2=calFinal.getP2Materia3();
            parMat3=calFinal.getP3Materia3();
            ord=calFinal.getOrMateria3();
            aux=(parMat1+parMat2+parMat3)/3;
            prom=(aux+ord)/2;
            sumaCalif=sumaCalif+prom;
            mostrar=ValidarCajas(parMat1,parMat2,parMat3,ord);
            if(mostrar==1){
                Mt3.setText(String.valueOf(prom));
                totalMate=totalMate+1;
            }else {
                Mt3.setText("");
            }


            parMat1=calFinal.getP1Materia4();
            parMat2=calFinal.getP2Materia4();
            parMat3=calFinal.getP3Materia4();
            ord=calFinal.getOrMateria4();
            aux=(parMat1+parMat2+parMat3)/3;
            prom=(aux+ord)/2;
            sumaCalif=sumaCalif+prom;
            mostrar=ValidarCajas(parMat1,parMat2,parMat3,ord);
            if(mostrar==1){
                Mt4.setText(String.valueOf(prom));
                totalMate=totalMate+1;
            }else {
                Mt4.setText("");
            }


            parMat1=calFinal.getP1Materia5();
            parMat2=calFinal.getP2Materia5();
            parMat3=calFinal.getP3Materia5();
            ord=calFinal.getOrMateria5();
            aux=(parMat1+parMat2+parMat3)/3;
            prom=(aux+ord)/2;
            sumaCalif=sumaCalif+prom;
            mostrar=ValidarCajas(parMat1,parMat2,parMat3,ord);
            if(mostrar==1){
                Mt5.setText(String.valueOf(prom));
                totalMate=totalMate+1;
            }else {
                Mt5.setText("");
            }

            //Calcular el promedio final
            promedioFinal=sumaCalif/5;
            promedio.setText(String.valueOf(promedioFinal));

            if(totalMate==5){
                promedio.setText(String.valueOf(promedioFinal));
            }else
            {
                promedio.setText("");
            }
        } catch (Exception nfe) {
            System.out.println("Error en contenidoFinalSimul... " + nfe);
        }
    }

    private int ValidarCajas(double parMat1, double parMat2, double parMat3, double ord) {
        int regresar=0;
        if(parMat1!=-1.0 && parMat2!=-1.0 && parMat3!=-1.0&&ord!=-1.0)
        {
            regresar=1;
        }
        else {
            regresar =-1;
        }
        return regresar;
    }


}
