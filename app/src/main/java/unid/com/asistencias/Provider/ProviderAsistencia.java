package unid.com.asistencias.Provider;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import unid.com.asistencias.Objeto.Asignatura;
import unid.com.asistencias.Objeto.Asistencia;

/**
 * Created by LiMon on 3/28/15.
 */
public class ProviderAsistencia {
    AsyncHttpClient client;
    RequestParams params;
    private String Response;
    JSONArray dataArray;
    ArrayList<Asistencia> arr;



    public ProviderAsistencia(){
        params = new RequestParams();
        client = new AsyncHttpClient();
    }

    public void AddAsistencia(String fecha,String idAsignatura, String idProfesor,String alumnos){

        params.add("Fecha",fecha);
        params.add("IdAsignatura", idAsignatura);
        params.add("IdProfesor",idProfesor);
        params.add("Alumnos",alumnos);

        client.post("http://asistencias.esy.es/get_asignatura.php?cId=",
                params, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        Log.i("ON_FAILURE", "" + errorResponse.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable throwable) {
                        Log.i("ON_FAILURE", res + statusCode + throwable.getMessage());
                    }


                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }

                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
/*
                        JSONObject suc = null;
                        try {
                            suc = (JSONObject) response.get("data");

                            setResponse(response.getString("status"));
                            Log.d("RESPONSE JSON",response.toString());
                            if (getResponse() != null && getResponse().equals("Ok")) {
                                //setRespuesta(true);
                                dataArray = suc.getJSONArray("results");
                                if (dataArray != null) {
                                    // setRespuesta(true);
                                    arr = new ArrayList<Asignatura>();
                                    arr = Character.getCharacters(dataArray);
                                    adaptador = new ItemListCharacter(MainActivity.this, R.layout.row_character, arr);
                                    lsCharac.setAdapter(adaptador);
                                }

                                // if(Action != null){
                                //  Action.Dowload();
                                //}
                            } else {

                            }


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                        }
                        */
                    }
                });

    }
}
