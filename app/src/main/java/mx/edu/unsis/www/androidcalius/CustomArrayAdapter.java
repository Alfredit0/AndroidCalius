package mx.edu.unsis.www.androidcalius;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Elvia on 04/06/2017.
 */

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private final notificaciones context;
    private final ArrayList<String> asuntos;
    private final ArrayList<String> destinatario;
    private final ArrayList<String> fecha;
    public CustomArrayAdapter(notificaciones context,ArrayList<String> asuntos, ArrayList<String> destinatario,ArrayList<String> fecha) {
        super(context.getContext(),R.layout.rowlayout,asuntos);
        this.context = context;
        this.asuntos = asuntos;
        this.destinatario = destinatario;
        this.fecha=fecha;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;
        if (null==rowView)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView=inflater.inflate(R.layout.rowlayout,null);
            System.out.print("en null=rowView");
        }
        //LayoutInflater inflater=context.getLayoutInflater();
        //rowView=inflater.inflate(R.layout.rowlayout,null);

        TextView txtAsunto=(TextView)rowView.findViewById(R.id.txtitem);
        TextView txtDestinatario=(TextView)rowView.findViewById(R.id.txtitem2);
        TextView txtFecha=(TextView)rowView.findViewById(R.id.txtFecha);

        txtAsunto.setText(asuntos.get(position));
        txtDestinatario.setText(destinatario.get(position));
        txtFecha.setText(fecha.get(position));

        return rowView;
    }
}
