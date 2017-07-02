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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mx.edu.unsis.www.androidcalius.conexion;
import mx.edu.unsis.www.androidcalius.parciales;

/**
 * Created by Elvia on 23/05/2017. clase encargada de realizar aperaiones en la base de datos local SQLITE
 */


/**
 * Creacióin de base de datos
 */
public class baseDatos extends SQLiteOpenHelper {
    //creacion de la base de datos
    private static final String SQL = "create table USUARIO (MATRICULA TEXT PRIMARY KEY,PERIODO TEXT);";
    private static final String SQL2 = "create table MATERIAS (IDMATERIA TEXT PRIMARY KEY,MATERIA TEXT,P1 REAL,P2 REAL, P3 REAL, ORD REAL);";
    private static final String SQL3 = "create table NOTIFICACIONES(ID INTEGER PRIMARY KEY AUTOINCREMENT,IDSERVIDOR TEXT, REMITENTE TEXT,DESTINATARIO TEXT,MENSAJE TEXT,FECHA TEXT,ASUNTO TEXT);";
    private static final String SQL4 ="create table SIMULACION (ID INTEGER PRIMARY KEY AUTOINCREMENT,P1MAT1 REAL,P1MAT2 REAL,P1MAT3 REAL,P1MAT4 REAL,P1MAT5 REAL,PROMP1 REAL," +
            "P2MAT1 REAL,P2MAT2 REAL,P2MAT3 REAL,P2MAT4 REAL,P2MAT5 REAL,PROMP2 REAL," +
            "P3MAT1 REAL,P3MAT2 REAL,P3MAT3 REAL,P3MAT4 REAL,P3MAT5 REAL,PROMP3 REAL," +
            "ORDMAT1 REAL,ORDMAT2 REAL,ORDMAT3 REAL,ORDMAT4 REAL,ORDMAT5 REAL,PROMORD REAL," +
            "PROMMAT1 REAL,PROMMAT2 REAL,PROMMAT3 REAL,PROMMAT4 REAL,PROMMAT5 REAL,PROMFINAL REAL);";


    public baseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL);
        db.execSQL(SQL2);
        db.execSQL(SQL3);
        db.execSQL(SQL4);
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
    /**
     * Creacióin de base de datos
     */
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
            System.out.println("Id de materia del json... "+materiaId );
            System.out.println("Nombre de materia del json... " +nombreMateria);
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
            /*System.out.println("IDMATERIA " +  cursor.getString(0));
            System.out.println("MATERIA " +  cursor.getString(1));
            System.out.println("P1 " +  cursor.getString(2));
            System.out.println("P2 " + cursor.getString(3));
            System.out.println("P3 " +  cursor.getString(4));
            System.out.println("ORD " +  cursor.getString(5));*/
        }
        if(cursor.moveToFirst()){
            if (cursor != null){
                cursor.moveToFirst();
                /*System.out.println("IDMATERIA " +  cursor.getString(0) );
                System.out.println("MATERIA " +  cursor.getString(1) );
                System.out.println("P1 " +  cursor.getString(2) );
                System.out.println("P2 " +  cursor.getString(3) );
                System.out.println("P3 " +  cursor.getString(4) );
                System.out.println("ORD " +  cursor.getString(5) );*/
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
        double cal;
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
                //formato para las aclificaciones
                if(p==1){
                    cal=cursor.getDouble(1);
                    if(cal<0){matCal[1]="";}else{matCal[1]=cursor.getString(1);}
                    //matCal[1]=cursor.getString(1);
                }else if(p==2){
                    cal=cursor.getDouble(2);
                    if(cal<0){matCal[1]="";}else{matCal[1]=cursor.getString(2);}
                    //matCal[1]=cursor.getString(2);
                }else if(p==3){
                    cal=cursor.getDouble(3);
                    if(cal<0){matCal[1]="";}else{matCal[1]=cursor.getString(3);}
                    //matCal[1]=cursor.getString(3);
                } else if(p==4){
                    cal=cursor.getDouble(4);
                    if(cal<0){matCal[1]="";}else{matCal[1]=cursor.getString(4);}
                    //matCal[1]=cursor.getString(4);
                }else{
                    //FINAL
                    double p1,p2,p3,ord,fin;
                    p1=cursor.getDouble(1);
                    p2=cursor.getDouble(2);
                    p3=cursor.getDouble(3);
                    ord=cursor.getDouble(4);
                    if(p1<0 || p2<0 || p3<0 || ord<0){
                        matCal[1]="";
                    }else{
                        //calculo de los parciales y ordinario
                        fin=(((p1+p2+p3)/3)+ord)/2;
                        matCal[1]=String.valueOf(fin);
                    }
                }
                break;
            }
        }
        return matCal;
    }
    //metodo para acortar el nombre de la materia
    public String cortarMateria(String materia){
        String nuevoNombre = materia.charAt(0)+"";
        for (int x=1; x < materia.length(); x++) {
            if(Character.isUpperCase(materia.charAt(x))){
                nuevoNombre=nuevoNombre+materia.charAt(x);
            }

        }
        return nuevoNombre;
    }
    public void guardarNotificaciones(JSONObject json) throws JSONException {
        Date date=new Date();
        SimpleDateFormat fecc=new SimpleDateFormat("dd/MM/yyyy");
        String fecha = fecc.format(date);
        //IDSERVIDOR TEXT, REMITENTE TEXT,DESTINATARIO TEXT,MENSAJE TEXT,FECHA TEXT,ASUNTO TEXT
        ContentValues valores =new ContentValues();
        valores.put("IDSERVIDOR",json.getString("id"));
        System.out.print(json.getString("id"));
        valores.put("REMITENTE",json.getString("remitente"));
        System.out.print(json.getString("remitente"));
        valores.put("DESTINATARIO",json.getString("destinatario"));
        System.out.print(json.getString("destinatario"));
        valores.put("MENSAJE",json.getString("mensaje"));
        System.out.print(json.getString("mensaje"));
        valores.put("FECHA",json.getString("fecha"));
        System.out.print(json.getString("fecha"));
        valores.put("ASUNTO",json.getString("asunto"));
        System.out.print(json.getString("asunto"));
        this.getWritableDatabase().insert("NOTIFICACIONES",null,valores);

    }

    public ArrayList<String> leerNotificaciones(int a){
        ArrayList<String> Asuntos=new ArrayList<>();
        ArrayList<String> destinatario=new ArrayList<>();
        ArrayList<String> fecha=new ArrayList<>();
        int i=0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor;
        String id="ID";
        cursor=db.query("NOTIFICACIONES",new String []{"ID","IDSERVIDOR","REMITENTE","DESTINATARIO","MENSAJE","FECHA","ASUNTO"},null,null,null,null,id+" DESC");
        while (cursor.moveToNext()){
            System.out.println("ID " +  cursor.getString(0));
            System.out.println("IDSERVIDOR " +  cursor.getString(1));
            System.out.println("REMITENTE " +  cursor.getString(2));
            System.out.println("DESTINATARIO " +  cursor.getString(3));
            System.out.println("MENSAJE " + cursor.getString(4));
            System.out.println("FECHA " +  cursor.getString(5));
            System.out.println("ASUNTO " +  cursor.getString(6));
            destinatario.add(cursor.getString(3));
            fecha.add(cursor.getString(5));
            Asuntos.add(cursor.getString(6));
            i++;
        }
        if (a==1){
            return Asuntos;
        }else if(a==2){
            return destinatario;
        }else{
            return fecha;
        }

    }
    public String[] leerMensajeRemitente(int id) {
        String[] mensajeRemitente = {null, null};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.query("NOTIFICACIONES", new String[]{"REMITENTE", "MENSAJE"}, "ID = " + id, null, null, null, null);
        if (cursor.moveToFirst()) {
            if (cursor != null) {
                mensajeRemitente[0] = cursor.getString(0);
                mensajeRemitente[1] = cursor.getString(1);
            }
        }
        return mensajeRemitente;
    }
    public void guardarSimulacion(){
        parciales simulacion=new parciales();

        ContentValues valores =new ContentValues();
        valores.put("P1MAT1",simulacion.getP1Materia1().toString());
        valores.put("P1MAT2",simulacion.getP1Materia2().toString());
        valores.put("P1MAT3",simulacion.getP1Materia3().toString());
        valores.put("P1MAT4",simulacion.getP1Materia4().toString());
        valores.put("P1MAT5",simulacion.getP1Materia5().toString());
        valores.put("PROMP1",simulacion.getPromParcial1().toString());

        valores.put("P2MAT1",simulacion.getP2Materia1().toString());
        valores.put("P2MAT2",simulacion.getP2Materia2().toString());
        valores.put("P2MAT3",simulacion.getP2Materia3().toString());
        valores.put("P2MAT4",simulacion.getP2Materia4().toString());
        valores.put("P2MAT5",simulacion.getP2Materia5().toString());
        valores.put("PROMP2",simulacion.getPromParcial2().toString());

        valores.put("P3MAT1",simulacion.getP3Materia1().toString());
        valores.put("P3MAT2",simulacion.getP3Materia2().toString());
        valores.put("P3MAT3",simulacion.getP3Materia3().toString());
        valores.put("P3MAT4",simulacion.getP3Materia4().toString());
        valores.put("P3MAT5",simulacion.getP3Materia5().toString());
        valores.put("PROMP3",simulacion.getPromParcial3().toString());

        valores.put("ORDMAT1",simulacion.getOrMateria1().toString());
        valores.put("ORDMAT2",simulacion.getOrMateria2().toString());
        valores.put("ORDMAT3",simulacion.getOrMateria3().toString());
        valores.put("ORDMAT4",simulacion.getOrMateria4().toString());
        valores.put("ORDMAT5",simulacion.getOrMateria5().toString());
        valores.put("PROMORD",simulacion.getPromOrd().toString());

        valores.put("PROMMAT1",simulacion.getPromMateria1().toString());
        valores.put("PROMMAT2",simulacion.getPromMateria2().toString());
        valores.put("PROMMAT3",simulacion.getPromMateria3().toString());
        valores.put("PROMMAT4",simulacion.getPromMateria4().toString());
        valores.put("PROMMAT5",simulacion.getPromMateria5().toString());
        valores.put("PROMFINAL",simulacion.getPromFinal().toString());
        this.getWritableDatabase().insert("SIMULACION",null,valores);
    }
    public boolean isSimulacion(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor;
        cursor=db.query("SIMULACION",new String []{"P1MAT1"},"",null,null,null,null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }
    public String leerSimulacion(int p) throws UnsupportedEncodingException {
        String dato = null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor;
        cursor=db.query("SIMULACION",new String []{"P1MAT1","P1MAT2","P1MAT3","P1MAT4","P1MAT5","PROMP1",
                "P2MAT1","P2MAT2","P2MAT3","P2MAT4","P2MAT5","PROMP2",
                "P3MAT1","P3MAT2","P3MAT3","P3MAT4","P3MAT5","PROMP3",
                "ORDMAT1","ORDMAT2","ORDMAT3","ORDMAT4","ORDMAT5","PROMORD",
                "PROMMAT1","PROMMAT2","PROMMAT3","PROMMAT4","PROMMAT5","PROMFINAL"},"",null,null,null,null);
        while (cursor.moveToNext()){
                if(p==1){
                    dato=cursor.getString(0);
                }else if(p==2){
                    dato=cursor.getString(1);
                }else if(p==3){
                    dato=cursor.getString(2);
                }else if(p==4){
                    dato=cursor.getString(3);
                }else if(p==5){
                    dato=cursor.getString(4);
                }else if(p==6){
                    dato=cursor.getString(5);
                }else if(p==7){
                    dato=cursor.getString(6);
                }else if(p==8){
                    dato=cursor.getString(7);
                }else if(p==9){
                    dato=cursor.getString(8);
                }else if(p==10){
                    dato=cursor.getString(9);
                }else if(p==11){
                    dato=cursor.getString(10);
                }else if(p==12){
                    dato=cursor.getString(11);
                }else if(p==13){
                    dato=cursor.getString(12);
                }else if(p==14){
                    dato=cursor.getString(13);
                }else if(p==15){
                    dato=cursor.getString(14);
                }else if(p==16){
                    dato=cursor.getString(15);
                }else if(p==17){
                    dato=cursor.getString(16);
                }else if(p==18){
                    dato=cursor.getString(17);
                }else if(p==19){
                    dato=cursor.getString(18);
                }else if(p==20){
                    dato=cursor.getString(19);
                }else if(p==21){
                    dato=cursor.getString(20);
                }else if(p==22){
                    dato=cursor.getString(21);
                }else if(p==23){
                    dato=cursor.getString(22);
                }else if(p==24){
                    dato=cursor.getString(23);
                }else if(p==25){
                    dato=cursor.getString(24);
                }else if(p==26){
                    dato=cursor.getString(25);
                }else if(p==27){
                    dato=cursor.getString(26);
                }else if(p==28){
                    dato=cursor.getString(27);
                }else if(p==29){
                    dato=cursor.getString(28);
                }else{
                    dato=cursor.getString(29);
                }
        }
        return dato;
    }
}
