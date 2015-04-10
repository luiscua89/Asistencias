package unid.com.asistencias.ItemList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import unid.com.asistencias.Objeto.Asignatura;
import unid.com.asistencias.R;


/**
 * Created by LiMon on 3/28/15.
 */
public class ItemListAsignatura extends ArrayAdapter<Asignatura> {
    private ArrayList<Asignatura> Asignaturas;
    private FragmentActivity activity;

    public int idLayout;

    public ItemListAsignatura(FragmentActivity Acti, int Layout,
                             ArrayList<Asignatura> Asignaturas) {
        super(Acti, Layout, Asignaturas);
        this.Asignaturas = Asignaturas;
        activity = Acti;
        idLayout = Layout;


    }

    public class ViewHolder{
        public TextView Nombre;
        public TextView Horario;
        //public ImageView Img;
       // public ProgressBar progress;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index = position;
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(idLayout, null);
            final ViewHolder holder = new ViewHolder();
            holder.Nombre = (TextView) v.findViewById(R.id.txtNombreAsig);
            holder.Horario = (TextView) v.findViewById(R.id.txtHorarioAsig);
            //holder.Img    = (ImageView) v.findViewById(R.id.imgCharacter);
            //holder.progress = (ProgressBar) v.findViewById(R.id.progressBar);

            v.setTag(holder);
        }

        ViewHolder holder=(ViewHolder)v.getTag();
        final Asignatura ap = Asignaturas.get(position);
        if (ap != null) {
            if (holder.Nombre != null) {
                holder.Nombre.setText(ap.getNombre());
            }
            if(holder.Horario != null) {
                holder.Horario.setText(ap.getHorario());
            }
           /* if(holder.Img != null) {
                holder.Img.setTag(ap.getImg());
                imageManager.displayImage(ap.getImg(), activity, holder.Img,holder.progress); //CHANGED
                //Img.setImageBitmap(getBitmap(Lug.getUrlImagen()));
                //holder.Img.setImageBitmap(ima);
            }*/
        }
        return v;
    }



}
