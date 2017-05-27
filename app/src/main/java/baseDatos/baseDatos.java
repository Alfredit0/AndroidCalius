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
    public  void insertarUsuario(String matricula,String periodo){
        ContentValues valores =new ContentValues();
        valores.put("MATRICULA",matricula);
        valores.put("PERIODO",periodo);
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
        //recorrer el json con un for e irlas inseratndo hasta que se leea todp

        ContentValues valores =new ContentValues();
        //convertir el objeto en array
        JSONArray materia = new JSONArray(materias.getJSONArray("materias").toString());
        int i = 0;
        String materiaId = "";
        String nombreMateria="";
        //Recorrer el Array
        for( i = 0; i < materia.length(); i++) {
            //Creamos el objeto para leer lo que viene en la posición i
            JSONObject orden = materia.getJSONObject(i);
            materiaId = orden.getString("idMateria");
            nombreMateria = orden.getString("materiaId");
            valores.put("IDMATERIA",materiaId);

            valores.put("MATERIA",nombreMateria);
            this.getWritableDatabase().insert("MATERIAS",null,valores);
        }
    }
    public void guardarCalificaciones(JSONObject calif) throws JSONException {
        //recorrer el json añadido
        ContentValues valores =new ContentValues();
        //convertir el objeto en array
        JSONArray calificacion = new JSONArray(calif.getJSONArray("calificaciones").toString());
        int i = 0;
        String materiaId = "";
        Double parcial1 = null,parcial2= null,parcial3= null,ordinario= null;
        //Recorrer el Array
        for( i = 0; i < calificacion.length(); i++){
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
        }

    }

}
