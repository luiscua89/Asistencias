package unid.com.asistencias;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import unid.com.asistencias.Actividades.AsignaturaActivity;
import unid.com.asistencias.Objeto.Asignatura;

/**
 * Created by LiMon on 3/9/15.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent openMainActivity= new Intent(SplashScreen.this,AsignaturaActivity.class);
                    startActivity(openMainActivity);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
