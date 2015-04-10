package unid.com.asistencias.Objeto;

/**
 * Created by mrodriguez on 28/03/2015.
 */
public class Asistencia {
    private int id;
    private String nombre;
    private String fecha;
    private boolean isCheck;
    private String img;

    public Asistencia(){

    }
    public Asistencia(int id, String nom,String fec,boolean check,String img){
        this.id = id;
        this.nombre = nom;
        this.fecha = fec;
        this.isCheck = check;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
