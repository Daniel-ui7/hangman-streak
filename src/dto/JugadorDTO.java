package dto;

public class JugadorDTO {
    private int id;
    private String nombre;

    public JugadorDTO(){}

    public JugadorDTO(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
    public JugadorDTO (String nombre){
        this.nombre = nombre;
    }
    public int obtenerId(){
        return id;
    }
    public void establecerId(int id){
        this.id = id;
    }
    public String obtenerNombre(){
        return nombre;
    }
    public void establecerNombre(String nombre){
        this.nombre = nombre;
    }
}
