package unid.com.asistencias.Herramienta;

import android.widget.ProgressBar;
import com.squareup.picasso.Callback;

/**
 * Created by mrodriguez on 28/03/2015.
 */
public class ImageLoadedCallback implements Callback {
    public ProgressBar progressBar;

    public  ImageLoadedCallback(ProgressBar progBar){
        progressBar = progBar;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}