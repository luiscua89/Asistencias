package unid.com.asistencias.ItemList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import unid.com.asistencias.Herramienta.ImageLoadedCallback;
import unid.com.asistencias.Objeto.Asistencia;
import unid.com.asistencias.R;

/**
 * Created by mrodriguez on 28/03/2015.
 */
public class ItemListAsistencia extends ArrayAdapter<Asistencia> {
    private ArrayList<Asistencia> Asistencias;
    private Activity activity;
    public Boolean isDetalle = false;

    //private int[] colors = new int[] { 0x30ffffff, Color.parseColor("#00b6ef") };
    public ItemListAsistencia(Activity a, int textViewResourceId, ArrayList<Asistencia> Asistencias) {
        super(a, textViewResourceId, Asistencias);
        this.Asistencias = Asistencias;
        activity = a;


    }
    public class ViewHolder{
        public TextView txtNombre,txtEspecialidad;
        public ImageView imgAlumno;
        public ProgressBar prAlum;
        public CheckBox chkSend;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = null;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_alumno, null);
            holder = new ViewHolder();
            holder.txtNombre = (TextView) v.findViewById(R.id.txtNombre);
            holder.txtEspecialidad = (TextView) v.findViewById(R.id.txtEspecialidad);
            holder.imgAlumno = (ImageView) v.findViewById(R.id.imgAlumno);
            holder.prAlum = (ProgressBar)v.findViewById(R.id.prAlum);
            holder.chkSend = (CheckBox)v.findViewById(R.id.chkSend);
            v.setTag(holder);
        }else
            holder=(ViewHolder)v.getTag();

        final Asistencia mon = Asistencias.get(position);
        if (mon != null) {

            if (holder.txtNombre != null) {
                holder.txtNombre.setText(mon.getNombre());
            }
            if (holder.txtEspecialidad != null) {
                //holder.txtEspecialidad.setText(mon.getEspecialidad());
            }
            if(holder.imgAlumno != null) {
                Picasso.with(activity)
                        .load("http://conoceyucatan.com/Fotos/Lugares/cenote-kinkirixche-mucuyche.jpg")
                        .into(holder.imgAlumno, new ImageLoadedCallback(holder.prAlum) {
                            @Override
                            public void onSuccess() {
                                if (this.progressBar != null) {
                                    this.progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        }
        return v;
    }
    private CheckListener ActionCheck;

    public interface CheckListener {
        public void Check(Asistencia asis);
    }
    public void setCheckListener(CheckListener listener) {
        this.ActionCheck = listener;
    }

}