package unid.com.asistencias.Actividades;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import unid.com.asistencias.ItemList.ItemListAsignatura;
import unid.com.asistencias.ItemList.ItemListAsistencia;
import unid.com.asistencias.JSONParser;
import unid.com.asistencias.Objeto.Asignatura;
import unid.com.asistencias.Objeto.Asistencia;
import unid.com.asistencias.R;

public class AsistenciaActivity extends ActionBarActivity implements
        DatePickerDialog.OnDateSetListener
{
    ArrayList<Asistencia> arAsis,arSelect;
    ItemListAsistencia adap;
    ListView lsAsistencia;
    LinearLayout btnCalendar;
    TextView txtFecha;
    Button btnEnviar;
    String enlace="";
    String fechaasistencia="";
    String alumnos="";
    private ProgressDialog pDialog;
    // Clase JSONParser
    private JSONParser jsonParser = null;
    private static String LOGIN_URL = "";
    // La respuesta del JSON es
    private static String TAG_SUCCESS = "";
    private static String TAG_MESSAGE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);

        LOGIN_URL = getResources().getString(R.string.loginurl);
        TAG_SUCCESS = getResources().getString(R.string.jsonexito);
        TAG_MESSAGE = getResources().getString(R.string.jsonmensaje);
        enlace=LOGIN_URL+"get_alumnos.php";
        jsonParser = new JSONParser();

        txtFecha = (TextView) findViewById(R.id.txtFecha);
        btnCalendar =  (LinearLayout) findViewById(R.id.btnCalendar);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                btnEnviar.setEnabled(false);
                new GrabarAsistencias().execute();
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AsistenciaActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        lsAsistencia = (ListView) findViewById(R.id.lsAsistencia);
        arSelect = new ArrayList<Asistencia>();
        /*
        arAsis = new ArrayList<Asistencia>();
        arAsis.add(new Asistencia(1,"Mario Rodriguez Lara","",false,"https://lh5.googleusercontent.com/P2-9qUXT51zsxTcqXote6NMPDTfRqPUS3Qlqz88jP2K0aGb-6IZZDEDogr_RO2_QeAr4sg=w1342-h561"));
        arAsis.add(new Asistencia(1,"Ligia Montejo Chan","",false,""));
        arAsis.add(new Asistencia(1,"Gelsy Cejudo ","",false,""));
        adap = new ItemListAsistencia(this,R.layout.row_alumno,arAsis);
        lsAsistencia.setAdapter(adap);

        adap.setCheckListener(new ItemListAsistencia.CheckListener() {
            @Override
            public void Check(Asistencia asis) {
                arSelect.add(asis);
            }
        });
        */
        new ListarAlumnos().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asistencia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = " "+dayOfMonth+"/"+monthOfYear+"/"+year;
        fechaasistencia = year+"-"+monthOfYear+"-"+dayOfMonth;
        txtFecha.setText(date);
    }
    private String leerAsistenciaAlumnos()
    {
        this.alumnos="";
        //No. 1
        /*for( Asistencia asistencia : this.arSelect)
        {
            this.alumnos+=this.alumnos.length()>0?",":"";
            this.alumnos+=asistencia.getId();
        }*/
        //No. 2
        /*
        for(int i=0;i<lsAsistencia.getCount();i++)
        {
            Asistencia asistencia= (Asistencia) lsAsistencia.getItemAtPosition(i);
            //ItemListAsistencia.ViewHolder holder=(ItemListAsistencia.ViewHolder)lsAsistencia.getTag(i);
            //holder.chkSend.isChecked();
            if(asistencia.isCheck())
            {
                this.alumnos+=this.alumnos.length()>0?",":"";
                this.alumnos+=asistencia.getId();
            }
        }*/
        //No. 3
        CheckBox asistio = null;
        for(int i=0;i<lsAsistencia.getChildCount();i++)
        {
            asistio = (CheckBox) lsAsistencia.getChildAt(i).findViewById(R.id.chkSend);
            if(asistio.isChecked())
            {
                Asistencia asistencia= (Asistencia) lsAsistencia.getItemAtPosition(i);
                this.alumnos+=this.alumnos.length()>0?",":"";
                this.alumnos+=asistencia.getId();
            }
        }

        return this.alumnos;
    }

    class ListarAlumnos extends AsyncTask<String, String, JSONArray>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(AsistenciaActivity.this);
            pDialog.setMessage("Leyendo alumnos de la materia...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONArray doInBackground(String... args)
        {
            int success;
            JSONObject json=null;
            JSONArray datosjson=null;
            try
            {
                Log.d("request!", "starting");
                // Leyendo alumnos a través de HTTP request
                json = jsonParser.makeHttpRequest(enlace, "POST", null);
                // check your log for json response
                Log.d("Iniciando lectura", json.toString());
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Lectura Exitosa", json.toString());
                    datosjson = json.getJSONArray("alumnos");
                }
                else
                {
                    Log.d("¡Lectura fallida!", json.getString(TAG_MESSAGE));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return datosjson;
        }

        protected void onPostExecute(JSONArray _datos)
        {
            JSONObject obj = null;
            AsistenciaActivity.this.lsAsistencia = (ListView) findViewById(R.id.lsAsistencia);
            AsistenciaActivity.this.arAsis = new ArrayList<Asistencia>();
            try
            {
                if (_datos != null)
                {
                    for (int i = 0; i < _datos.length(); i++)
                    {
                        obj = (JSONObject) _datos.get(i);
                        //arAsig.add(new Asignatura(obj.getInt("id"), obj.getString("nombre"), obj.getString("horario")));
                        String nombre = obj.getString("apellidos") +" "+ obj.getString("nombre");
                        arAsis.add(new Asistencia(obj.getInt("id"),nombre,"",false,""));
                        obj = null;
                    }
                    AsistenciaActivity.this.arSelect.clear();
                    fechaasistencia="";
                    AsistenciaActivity.this.adap = new ItemListAsistencia(AsistenciaActivity.this,R.layout.row_alumno,arAsis);
                    AsistenciaActivity.this.lsAsistencia.setAdapter(adap);
                    AsistenciaActivity.this.adap.setCheckListener(new ItemListAsistencia.CheckListener()
                    {
                        @Override
                        public void Check(Asistencia asis)
                        {
                            arSelect.add(asis);
                        }
                    });
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            pDialog.dismiss();
        }
    }

    class GrabarAsistencias extends AsyncTask<String, String, Integer>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(AsistenciaActivity.this);
            pDialog.setMessage("Grabando asistencias...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected Integer doInBackground(String... args)
        {
            int success=0;
            String enlacegrab=LOGIN_URL+"create_asistencia.php";;
            JSONObject json=null;
            JSONArray datosjson=null;
            AsistenciaActivity.this.alumnos=AsistenciaActivity.this.leerAsistenciaAlumnos();


            if(AsistenciaActivity.this.fechaasistencia.length()==0)
            {
                return 0;
            }
            if(AsistenciaActivity.this.alumnos.length()==0)
            {
                return 0;
            }

            try
            {
                List params = new ArrayList();
                /*Se manda la materia 2*/
                params.add(new BasicNameValuePair("idp", "2"));
                params.add(new BasicNameValuePair("alumnos", AsistenciaActivity.this.alumnos));
                params.add(new BasicNameValuePair("fecha", AsistenciaActivity.this.fechaasistencia));
                Log.d("request!", "starting");
                // Grabando alumnos a través de HTTP request
                json = jsonParser.makeHttpRequest(enlacegrab, "POST", params);
                // check your log for json response
                Log.d("Iniciando lectura", json.toString());
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Lectura Exitosa", json.toString());
                }
                else
                {
                    Log.d("¡Lectura fallida!", json.getString(TAG_MESSAGE));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return success;
        }
        protected void onPostExecute(Integer _resultado)
        {
            pDialog.dismiss();
            AsistenciaActivity.this.btnEnviar.setEnabled(true);
            if(_resultado==0 &&  AsistenciaActivity.this.alumnos.length()==0)
            {
                Toast.makeText(AsistenciaActivity.this, "Favor de seleccionar los alumnos que asistieron.", Toast.LENGTH_LONG).show();
            }
            if(_resultado==0 &&  AsistenciaActivity.this.fechaasistencia.length()==0)
            {
                Toast.makeText(AsistenciaActivity.this, "Favor de especificar la fecha de asistencias.", Toast.LENGTH_LONG).show();
            }
            if(_resultado==1)
            {
                Toast.makeText(AsistenciaActivity.this, "La lista de asistencia se grabó exitosamente.", Toast.LENGTH_LONG).show();
                AsistenciaActivity.this.finish();
            }
        }
    }
}
