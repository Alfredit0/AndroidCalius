package baseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;

import mx.edu.unsis.www.androidcalius.conexion;

/**
 * Created by Elvia on 23/05/2017.
 */

public class baseDatos extends SQLiteOpenHelper {
    //creacion de la base de datos
    private static final String SQL = "create table USUARIO (MATRICULA TEXT PRIMARY KEY,PERIODO TEXT);";
    private static final String SQL2 = "create table MATERIAS (IDMATERIA TEXT PRIMARY KEY,MATERIA TEXT,P1 REAL,P2 REAL, P3 REAL, ORD REAL);";
    public baseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL);
        db.execSQL(SQL2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void abrir(){
        this.getWritableDatabase();
    }
    public void cerrar(){
        this.close();
    }
    //inserar registro
    public  void insertarUsuario(String matricula){
        ContentValues valores =new ContentValues();
        valores.put("MATRICULA",matricula);
        //valores.put("PERIODO",periodo);
        this.getWritableDatabase().insert("USUARIO",null,valores);
    }

    public String leerUsuario(){
            String matricula=null;
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor cursor;
            cursor=db.query("USUARIO",new String []{"MATRICULA","PERIODO"},"",null,null,null,null);
            if(cursor.moveToFirst()){
                if (cursor != null){
                    cursor.moveToFirst();
                    matricula=cursor.getString(0);
                    System.out.println("El nombre es " +  cursor.getString(1) );}
            }
            //db.close();
        return matricula;
    }

    public void actualizarUsuario(String usu,String periodo){
        ContentValues values = new ContentValues();
        values.put("PERIODO", periodo);
        this.getWritableDatabase().update("USUARIO",values,"MATRICULA = '"+usu+"'",null);
    }
    public String leerPeriodo(){
        String periodo=null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor;
        cursor=db.query("USUARIO",new String []{"MATRICULA","PERIODO"},"",null,null,null,null);
        if(cursor.moveToFirst()){
            if (cursor != null){
                cursor.moveToFirst();
                periodo=cursor.getString(1);
                System.out.println("El nombre es " +  cursor.getString(1) );}
        }
        //db.close();
        System.out.print("el periodo es "+periodo);
        return periodo;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Boolean eliminarDB(){
        return SQLiteDatabase.deleteDatabase(new File("/data/data/mx.edu.unsis.www.androidcalius/databases/calius.db"));
    }


    public void guardarMaterias(JSONObject materias) throws JSONException {
        //System.out.println("En guardar materias... " );
        //recorrer el json con un for e irlas inseratndo hasta que se leea todp
        //convertir el objeto en array
        JSONArray materia = new JSONArray(materias.getJSONArray("materias").toString());

        int i = 0;
        String materiaId = "";
        String nombreMateria="";
        System.out.println("Antes del for... "+  materia.length());
        //Recorrer el Array
        for( i = 0; i < materia.length(); i++) {
            //Creamos el objeto para leer lo que viene en la posición i
            ContentValues valores =new ContentValues();
            JSONObject orden = materia.getJSONObject(i);
            materiaId = orden.getString("idMateria");
            nombreMateria = orden.getString("nombreMateria");
            System.out.println("Despues del idMateria... "+materiaId );
            System.out.println("Despues del Materia... " +nombreMateria);
            valores.put("IDMATERIA",materiaId);
            valores.put("MATERIA",nombreMateria);
           this.getWritableDatabase().insert("MATERIAS",null,valores);
            System.out.println("Guardadndo materias... " );
        }
    }
    public void guardarCalificaciones(JSONObject calif) throws JSONException {
        System.out.println("En guardar calificaciones... " );
        //convertir el objeto en array
        JSONArray calificacion = new JSONArray(calif.getJSONArray("calificaciones").toString());
        int i = 0;
        String materiaId = "";
        Double parcial1 = null,parcial2= null,parcial3= null,ordinario= null;
        //Recorrer el Array
        for( i = 0; i < calificacion.length(); i++){
            ContentValues valores =new ContentValues();
            //Creamos el objeto para leer lo que viene en la posición i
            JSONObject orden = calificacion.getJSONObject(i);
            materiaId = orden.getString("materiaId");

            parcial1 = orden.getDouble("parcial1");
            parcial2= orden.getDouble("parcial2");
            parcial3 = orden.getDouble("parcial3");
            ordinario= orden.getDouble("ordinario");
            valores.put("P1",parcial1);
            valores.put("P2",parcial2);
            valores.put("P3",parcial3);
            valores.put("ORD",ordinario);
            this.getWritableDatabase().update("MATERIAS",valores,"IDMATERIA = '"+materiaId+"'",null);
            System.out.println("Guardadndo calificaciones... " );
        }

    }
    public String leerMaterias(int var) throws UnsupportedEncodingException {
        String idMateria=null;
        String calif1=null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor;
        cursor=db.query("MATERIAS",new String []{"IDMATERIA","MATERIA","P1","P2","P3","ORD"},"",null,null,null,null);
        while (cursor.moveToNext()){
            System.out.println("IDMATERIA " +  cursor.getString(0));
            System.out.println("MATERIA " +  cursor.getString(1).getBytes());
            System.out.println("P1 " +  cursor.getString(2).getBytes());
            System.out.println("P2 " + cursor.getString(3).getBytes());
            System.out.println("P3 " +  cursor.getString(4).getBytes());
            System.out.println("ORD " +  cursor.getString(5).getBytes());
        }
        if(cursor.moveToFirst()){
            if (cursor != null){
                cursor.moveToFirst();
                System.out.println("IDMATERIA " +  cursor.getString(0) );
                System.out.println("MATERIA " +  cursor.getString(1) );
                System.out.println("P1 " +  cursor.getString(2) );
                System.out.println("P2 " +  cursor.getString(3) );
                System.out.println("P3 " +  cursor.getString(4) );
                System.out.println("ORD " +  cursor.getString(5) );
                idMateria=cursor.getString(0);
                calif1=cursor.getString(2);
            }
        }
        //db.close();
        if(var==1){
            return idMateria;
        }else{
            return  calif1;
        }

    }
    public String[] materiasEnVistas(int i,int p) throws UnsupportedEncodingException {
        String[] matCal={null,null};
        String materia;
        int cont=0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor;
        cursor=db.query("MATERIAS",new String []{"MATERIA","P1","P2","P3","ORD"},"",null,null,null,null);
        while (cursor.moveToNext()){
            cont++;
            if(cont==i){
                matCal[0]=cursor.getString(0);
                materia=cursor.getString(0);
                if(materia.length()<=25){
                    matCal[0]=materia;
                }else{
                    matCal[0]=cortarMateria(materia);
                }
                if(p==1){
                    matCal[1]=cursor.getString(1);
                }else if(p==2){
                    matCal[1]=cursor.getString(2);
                }else if(p==3){
                    matCal[1]=cursor.getString(3);
                } else{
                    matCal[1]=cursor.getString(4);
                }
                break;
            }
        }
        return matCal;
    }
    public String cortarMateria(String materia){
        String nuevoNombre = materia.charAt(0)+"";
        for (int x=1; x < materia.length(); x++) {
            if(Character.isUpperCase(materia.charAt(x))){
                nuevoNombre=nuevoNombre+materia.charAt(x);
            }

        }
        return nuevoNombre;
    }
}
