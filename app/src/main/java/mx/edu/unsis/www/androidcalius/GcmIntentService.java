package mx.edu.unsis.www.androidcalius;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Elvia on 24/05/2017.
 */

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

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
                sendNotification(extras.getString("mensaje"));
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
    //guardar el mensaje en base de datos y al cargar  la actividad de notificacioones
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, actividadInicio.class), 0);

        android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.contacto_mail_icono)
                .setContentTitle("Notificacion:" + msg)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);



        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
