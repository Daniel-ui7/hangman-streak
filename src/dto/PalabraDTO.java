package dto;

public class PalabraDTO {
    private int id;
    private String palabra;
    private String categoria;
    private String dificultad;

    public PalabraDTO(){}

    public PalabraDTO(int id, String palabra, String categoria, String dificultad){
        this.id = id;
        this.palabra = palabra;
        this.categoria = categoria;
        this.dificultad = dificultad;
    }
    public PalabraDTO(String palabra, String categoria, String dificultad){
        this.palabra = palabra;
        this.categoria = categoria;
        this.dificultad = dificultad;
    }
    public int obtenerId(){
        return id;
    }
    public void establecerId(int id){
        this.id = id;
    }
    public String obtenerPalabra(){
        return palabra;
    }
    public void establecerPalabra(String palabra){
        this.palabra = palabra;
    }
    public String obtenerCategoria() {
        return categoria;
    }
    public void establecerCategoria(String categoria){
        this.categoria = categoria;
    }
    public String obtenerDificultad(){
        return dificultad;
    }
    public void setDificultad(String dificultad){
        this.dificultad = dificultad;
    }
}
