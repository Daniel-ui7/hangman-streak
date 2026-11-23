package logica;

import dto.JugadorDTO;
import dto.PalabraDTO;

import java.util.HashSet;
import java.util.Set;

public class JuegoLogica {
    private JugadorDTO jugadorActual;
    private PalabraDTO palabraSecreta;
    private Set<Character> letrasAdivinadas;
    private int errores;
    private static final int MAX_ERRORES = 6;

    public JuegoLogica(JugadorDTO jugador, PalabraDTO palabra) {
        this.jugadorActual = jugador;
        this.palabraSecreta = palabra;
        this.letrasAdivinadas = new HashSet<>();
        this.errores = 0;
    }

    public boolean intentarLetra(char letra) {
        letra = Character.toUpperCase(letra);
        if (letrasAdivinadas.contains(letra)) {
            return false;
        }
        letrasAdivinadas.add(letra);

        if (!palabraSecreta.obtenerPalabra().contains(String.valueOf(letra))) {
            errores++;
            return false;
        }
        return true;
    }

    public boolean estaTerminada() {
        return esVictoria() || errores >= MAX_ERRORES;
    }

    public boolean esVictoria() {
        for (char c : palabraSecreta.obtenerPalabra().toCharArray()) {
            if (!letrasAdivinadas.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public int calcularPuntaje() {
        if (!esVictoria()) return 0;
        return 100 + ((MAX_ERRORES - errores) * 20) + (palabraSecreta.obtenerPalabra().length() * 10);
    }

    public String obtenerPalabraEnmascarada() {
        StringBuilder sb = new StringBuilder();
        for (char c : palabraSecreta.obtenerPalabra().toCharArray()) {
            if (letrasAdivinadas.contains(c)) {
                sb.append(c).append(" ");
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }

    public JugadorDTO getJugadorActual() { return jugadorActual; }
    public PalabraDTO getPalabraSecreta() { return palabraSecreta; }
    public int obtenerErrores() { return errores; }
    public int obtenerMAX_ERRORES() { return MAX_ERRORES; }
}