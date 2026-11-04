package dto;

import java.time.LocalDateTime;

public class PartidaDTO {
    private int id;
    private int jugadorId;
    private String palabra;
    private int errores;
    private String resultado;
    private int puntaje;
    private LocalDateTime fecha;

    public PartidaDTO() {}

    public PartidaDTO(int jugadorId, String palabra, int errores, String resultado, int puntaje) {
        this.jugadorId = jugadorId;
        this.palabra = palabra;
        this.errores = errores;
        this.resultado = resultado;
        this.puntaje = puntaje;
        this.fecha = LocalDateTime.now();
    }

    public int obtenerId() { return id; }
    public void establecerId(int id) { this.id = id; }

    public int obtenerJugadorId() { return jugadorId; }
    public void establecerJugadorId(int jugadorId) { this.jugadorId = jugadorId; }

    public String obtenerPalabra() { return palabra; }
    public void establecerPalabra(String palabra) { this.palabra = palabra; }

    public int obtenerErrores() { return errores; }
    public void establecerErrores(int errores) { this.errores = errores; }

    public String obtenerResultado() { return resultado; }
    public void establecerResultado(String resultado) { this.resultado = resultado; }

    public int obtenerPuntaje() { return puntaje; }
    public void establecerPuntaje(int puntaje) { this.puntaje = puntaje; }

    public LocalDateTime obtenerFecha() { return fecha; }
    public void establecerFecha(LocalDateTime fecha) { this.fecha = fecha; }
}