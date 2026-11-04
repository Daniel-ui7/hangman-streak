package dto;
import java.time.LocalDateTime;

public class RankingDTO {
    private int posicion;
    private String nombreJugador;
    private int puntaje;
    private LocalDateTime fecha;

    public RankingDTO(){}

    public RankingDTO(int posicion, String nombreJugador, int puntaje, LocalDateTime fecha){
        this.posicion = posicion;
        this.nombreJugador = nombreJugador;
        this.puntaje = puntaje;
        this.fecha = fecha;
    }
    public int obtenerPosicion(){
        return posicion;
    }
    public void establecerPosicion(int posicion){
        this.posicion = posicion;
    }
    public String obtenerNombreJugador(){
        return nombreJugador;
    }
    public void establecerNombreJugador(String nombreJugador){
        this.nombreJugador = nombreJugador;
    }
    public int obtenerPuntaje(){
        return puntaje;
    }
    public void establecerPuntaje(int puntaje){
        this.puntaje = puntaje;
    }
    public LocalDateTime obtenerFecha(){
        return fecha;
    }
    public void establecerFecha (LocalDateTime fecha){
        this.fecha = fecha;
    }
}
