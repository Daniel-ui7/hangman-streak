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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getJugadorId() { return jugadorId; }
    public void setJugadorId(int jugadorId) { this.jugadorId = jugadorId; }

    public String getPalabra() { return palabra; }
    public void setPalabra(String palabra) { this.palabra = palabra; }

    public int getErrores() { return errores; }
    public void setErrores(int errores) { this.errores = errores; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}