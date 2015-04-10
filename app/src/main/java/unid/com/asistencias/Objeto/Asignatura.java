package unid.com.asistencias.Objeto;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LiMon on 3/28/15.
 */
public class Asignatura implements Parcelable {
    private int IdAsignatura;
    private String Nombre;
    private String Horario;

    public Asignatura(int idAsignatura,String nombre,String horario){
        this.IdAsignatura = idAsignatura;
        this.Nombre = nombre;
        this.Horario = horario;
    }

    public int getIdAsignatura() {
        return IdAsignatura;
    }
    public void setIdAsignatura(int idasignatura) {
        this.IdAsignatura = idasignatura;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }
    public String getHorario() {
        return Horario;
    }
    public void setHorario(String horario) {
        this.Horario = horario;
    }

    public static ArrayList<Character> getAsignatura(JSONArray dat){
        ArrayList<Character> arCharacters = new ArrayList<Character>();
        JSONObject obj = null;
        Character objCharacter = null;
        JSONObject objImg = null;
        try {
            for(int i = 0; i < dat.length() ; i++){
                obj = (JSONObject) dat.get(i);
               /* objImg = (JSONObject)obj.get("thumbnail");
                objCharacter = new Character(Integer.parseInt(obj.getString("id")), obj.getString("name"),
                        obj.getString("description"),
                        objImg.getString("path") +"."+ objImg.getString("extension"));

                arCharacters.add(objCharacter);*/
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arCharacters;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
