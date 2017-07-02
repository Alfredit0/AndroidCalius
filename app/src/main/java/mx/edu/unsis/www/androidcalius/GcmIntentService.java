package mx.edu.unsis.www.androidcalius;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import baseDatos.baseDatos;

/**
 * Created by Elvia on 24/05/2017.
 */

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    baseDatos datos;
    parciales gurdarNot=new parciales();
    public GcmIntentService() {
        super("GcmIntentService");
    }
    /**
     * Metodo que recupera el mensaje de la notificación contenida en el intent
     * para luego mostrar dicho mensaje en la barra de notificaciones del dispositivo
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                //Se visualiza el mendaje en la barra de notificaciones
                try {
                    sendNotification(extras.getString("mensaje"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    /**
     * Este metodo lo que hace es visualizar una notificación en la barra de
     * notificaciones con el mensaje pasado por parametro
     *
     * @param msg mensaje que se muestra en la notificación
     */
    private void sendNotification(String msg) throws JSONException {

        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, actividadInicio.class), 0);

        //convirtiendo el mensaje en un objeto json

        JSONObject json = new JSONObject(msg);
        //guadar los datos del json en base de datos
        datos= new baseDatos(this, "calius",null,1);
        datos.guardarNotificaciones(json);

        gurdarNot.setNotificacion(true);

        android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.logo)
                .setContentTitle(json.get("remitente") +"")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(json.get("asunto").toString()))
                .setContentText(json.get("asunto").toString())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

                /*android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.contacto_mail_icono)
                .setContentTitle("Notificacion:" + msg)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)*/

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
