package unid.com.asistencias.Actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import unid.com.asistencias.ItemList.ItemListAsignatura;
import unid.com.asistencias.ItemList.ItemListAsistencia;
import unid.com.asistencias.JSONParser;
import unid.com.asistencias.Objeto.Asignatura;
import unid.com.asistencias.Objeto.Asistencia;
import unid.com.asistencias.R;

public class AsignaturaActivity extends ActionBarActivity {

    ArrayList<Asignatura> arAsig;
    ItemListAsignatura adap;
    ListView lsAsignatura;
    Asignatura asignatura;
    Bundle bundle;

    String enlace="";
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
        setContentView(R.layout.activity_asignatura);

        LOGIN_URL = getResources().getString(R.string.loginurl);
        TAG_SUCCESS = getResources().getString(R.string.jsonexito);
        TAG_MESSAGE = getResources().getString(R.string.jsonmensaje);
        enlace=LOGIN_URL+"get_asignatura.php";
        jsonParser = new JSONParser();

        lsAsignatura = (ListView) findViewById(R.id.lsAsignatura);
        lsAsignatura.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                asignatura = arAsig.get(position);
                bundle = new Bundle();
                bundle.putParcelable("Asignatura", asignatura);

                Intent mainIntent = new Intent(AsignaturaActivity.this, AsistenciaActivity.class);
                //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);

            }
        });

        new ListarAsignaturas().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asistencia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

    class ListarAsignaturas extends AsyncTask<String, String, JSONArray>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(AsignaturaActivity.this);
            pDialog.setMessage("Leyendo asignaturas...");
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
                // Building Parameters
                List params = new ArrayList();
                params.add(new BasicNameValuePair("cid", "1"));
                Log.d("request!", "starting");
                // Leyendo las asignaturas a través de HTTP request
                json = jsonParser.makeHttpRequest(enlace, "POST", params);
                // check your log for json response
                Log.d("Iniciando lectura", json.toString());
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Lectura Exitosa", json.toString());
                    datosjson = json.getJSONArray("asignat");
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
            AsignaturaActivity.this.arAsig = new ArrayList<Asignatura>();
            try
            {
                if (_datos != null)
                {
                    for (int i = 0; i < _datos.length(); i++)
                    {
                        obj = (JSONObject) _datos.get(i);
                        arAsig.add(new Asignatura(obj.getInt("id"), obj.getString("nombre"), obj.getString("horario")));
                        obj = null;
                    }
                    AsignaturaActivity.this.adap = new ItemListAsignatura(AsignaturaActivity.this,R.layout.row_asignatura,arAsig);
                    AsignaturaActivity.this.lsAsignatura.setAdapter(adap);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            pDialog.dismiss();
            /*if (_datos != null)
            {
                Toast.makeText(AsignaturaActivity.this, "Exito", Toast.LENGTH_LONG).show();
            }*/
        }
    }
}
