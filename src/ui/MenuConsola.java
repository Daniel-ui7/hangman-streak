package ui;

import conexion.ConexionH2;
import dto.*;
import logica.GestorJuego;
import logica.JuegoLogica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuConsola extends JFrame {

    // Paleta de colores exacta de tus diseños
    private static final Color BG_OSCURO = new Color(19, 30, 58);      // #131E3A
    private static final Color CARD_AZUL = new Color(27, 41, 75);      // #1B294B
    private static final Color INPUT_BG = new Color(36, 52, 92);       // #24345C
    private static final Color AMARILLO = new Color(255, 193, 59);     // #FFC13B
    private static final Color VERDE = new Color(40, 199, 111);        // #28C76F
    private static final Color ROJO = new Color(234, 84, 85);          // #EA5455
    private static final Color CIAN_BORDE = new Color(56, 142, 168);   // #388EA8
    private static final Color TEXTO_GRIS = new Color(160, 174, 192);

    private GestorJuego gestorJuego;
    private JuegoLogica juegoActual;
    private JugadorDTO jugadorActual;

    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;

    // Componentes Pantalla 1 (Menú)
    private JTextField txtNombreJugador;
    private JComboBox<String> cmbCategoria;

    // Componentes Pantalla 2 (Partida)
    private JLabel lblJugadorPartida, lblCategoriaPartida, lblPalabraOculta, lblErroresTexto;
    private PanelHorca panelHorca;
    private JPanel panelIndicadoresErrores;
    private Map<Character, JButton> botonesTeclado = new HashMap<>();

    // Componentes Pantalla 3 (Fin de partida)
    private JLabel lblTituloFin, lblPalabraFin, lblDetallesFin, lblPuntajeFin;

    // Componentes Pantalla 4 (Ranking / Historial)
    private JTable tablaRanking;
    private DefaultTableModel modeloTabla;
    private JButton btnTabTop10, btnTabHistorial;

    public MenuConsola() {
        ConexionH2.inicializarBaseDeDatos();
        this.gestorJuego = new GestorJuego();
        cargarPalabrasIniciales();

        setTitle("AHORCADO - Hangman Streak");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        contenedorPrincipal = new JPanel(cardLayout);
        contenedorPrincipal.setBackground(BG_OSCURO);

        contenedorPrincipal.add(crearPantallaMenu(), "MENU");
        contenedorPrincipal.add(crearPantallaJuego(), "JUEGO");
        contenedorPrincipal.add(crearPantallaFin(), "FIN");
        contenedorPrincipal.add(crearPantallaRanking(), "RANKING");

        add(contenedorPrincipal);
        cardLayout.show(contenedorPrincipal, "MENU");
    }

    // =========================================================================
    // 1. PANTALLA MENÚ PRINCIPAL
    // =========================================================================
    private JPanel crearPantallaMenu() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 40, 0));
        panel.setBackground(BG_OSCURO);
        panel.setBorder(new EmptyBorder(60, 80, 60, 80));

        // Columna Izquierda: Logo de la horca + Título
        JPanel izq = new JPanel();
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setBackground(BG_OSCURO);
        izq.add(Box.createVerticalGlue());

        PanelHorca logoHorca = new PanelHorca();
        logoHorca.setErrores(0);
        logoHorca.setModoLogo(true);
        logoHorca.setPreferredSize(new Dimension(200, 220));
        logoHorca.setMaximumSize(new Dimension(200, 220));
        logoHorca.setAlignmentX(Component.CENTER_ALIGNMENT);
        izq.add(logoHorca);

        JLabel lblTitulo = new JLabel("AHORCADO");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 38));
        lblTitulo.setForeground(AMARILLO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        izq.add(Box.createRigidArea(new Dimension(0, 15)));
        izq.add(lblTitulo);

        JLabel lblSub = new JLabel("<html><center>Adivina la palabra antes de<br>completar el dibujo</center></html>");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(TEXTO_GRIS);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        izq.add(Box.createRigidArea(new Dimension(0, 8)));
        izq.add(lblSub);
        izq.add(Box.createVerticalGlue());

        // Columna Derecha: Tarjeta de formulario
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_AZUL);
        card.setBorder(new EmptyBorder(30, 35, 30, 35));

        JLabel lblNombre = new JLabel("Nombre del jugador");
        lblNombre.setForeground(TEXTO_GRIS);
        lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNombreJugador = new JTextField();
        txtNombreJugador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtNombreJugador.setBackground(INPUT_BG);
        txtNombreJugador.setForeground(Color.WHITE);
        txtNombreJugador.setCaretColor(Color.WHITE);
        txtNombreJugador.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(60, 80, 120), 1),
                new EmptyBorder(5, 10, 5, 10)));

        JLabel lblCat = new JLabel("Categoría");
        lblCat.setForeground(TEXTO_GRIS);
        lblCat.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblCat.setAlignmentX(Component.LEFT_ALIGNMENT);

        cmbCategoria = new JComboBox<>(new String[]{"Todas", "Programación", "General", "Ciencia", "Raro"});
        cmbCategoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        cmbCategoria.setBackground(INPUT_BG);
        cmbCategoria.setForeground(Color.WHITE);

        JButton btnJugar = crearBotonSolido("Jugar", AMARILLO, BG_OSCURO);
        JButton btnRanking = crearBotonBorde("Ver ranking", CIAN_BORDE);
        JButton btnHistorial = crearBotonBorde("Mi historial", AMARILLO);
        JButton btnSalir = crearBotonBorde("Salir", ROJO);

        btnJugar.addActionListener(e -> iniciarNuevaPartida());
        btnRanking.addActionListener(e -> {
            cargarDatosRanking(false);
            cardLayout.show(contenedorPrincipal, "RANKING");
        });
        btnHistorial.addActionListener(e -> {
            String nombre = txtNombreJugador.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escribe tu nombre primero para ver tu historial.");
                return;
            }
            jugadorActual = gestorJuego.registrarJugador(nombre);
            cargarDatosRanking(true);
            cardLayout.show(contenedorPrincipal, "RANKING");
        });
        btnSalir.addActionListener(e -> System.exit(0));

        card.add(lblNombre);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(txtNombreJugador);
        card.add(Box.createRigidArea(new Dimension(0, 16)));
        card.add(lblCat);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(cmbCategoria);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(btnJugar);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(btnRanking);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(btnHistorial);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(btnSalir);

        panel.add(izq);
        panel.add(card);
        return panel;
    }

    // =========================================================================
    // 2. PANTALLA PARTIDA EN CURSO
    // =========================================================================
    private JPanel crearPantallaJuego() {
        JPanel panel = new JPanel(new BorderLayout(25, 20));
        panel.setBackground(BG_OSCURO);
        panel.setBorder(new EmptyBorder(30, 45, 30, 45));

        // Header superior: Jugador a la izquierda, Categoría a la derecha
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_OSCURO);

        lblJugadorPartida = new JLabel("Jugador: ");
        lblJugadorPartida.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblJugadorPartida.setForeground(Color.WHITE);

        lblCategoriaPartida = new JLabel("Categoría: General");
        lblCategoriaPartida.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblCategoriaPartida.setForeground(TEXTO_GRIS);
        lblCategoriaPartida.setOpaque(true);
        lblCategoriaPartida.setBackground(CARD_AZUL);
        lblCategoriaPartida.setBorder(new EmptyBorder(6, 14, 6, 14));

        header.add(lblJugadorPartida, BorderLayout.WEST);
        header.add(lblCategoriaPartida, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        // Panel Izquierdo: Tarjeta con dibujo de la horca + bolitas de errores
        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.setBackground(CARD_AZUL);
        panelIzq.setPreferredSize(new Dimension(260, 0));
        panelIzq.setBorder(new EmptyBorder(20, 20, 20, 20));

        panelHorca = new PanelHorca();
        panelIzq.add(panelHorca, BorderLayout.CENTER);

        JPanel footerErrores = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 5));
        footerErrores.setBackground(CARD_AZUL);
        lblErroresTexto = new JLabel("Errores 0/6");
        lblErroresTexto.setForeground(TEXTO_GRIS);
        lblErroresTexto.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footerErrores.add(lblErroresTexto);

        panelIndicadoresErrores = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        panelIndicadoresErrores.setBackground(CARD_AZUL);
        footerErrores.add(panelIndicadoresErrores);

        panelIzq.add(footerErrores, BorderLayout.SOUTH);
        panel.add(panelIzq, BorderLayout.WEST);

        // Panel Derecho: Palabra oculta + Teclado A-Z + Botones inferiores
        JPanel panelDer = new JPanel(new BorderLayout(0, 25));
        panelDer.setBackground(BG_OSCURO);

        lblPalabraOculta = new JLabel("_ _ _ _ _", SwingConstants.CENTER);
        lblPalabraOculta.setFont(new Font("Monospaced", Font.BOLD, 34));
        lblPalabraOculta.setForeground(Color.WHITE);
        lblPalabraOculta.setBorder(new EmptyBorder(20, 0, 10, 0));
        panelDer.add(lblPalabraOculta, BorderLayout.NORTH);

        // Teclado en cuadrícula 3 filas x 9 columnas (A-Z)
        JPanel gridTeclado = new JPanel(new GridLayout(3, 9, 8, 8));
        gridTeclado.setBackground(BG_OSCURO);
        String alfabeto = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        for (char c : alfabeto.toCharArray()) {
            JButton btnLetra = new JButton(String.valueOf(c));
            btnLetra.setFont(new Font("SansSerif", Font.BOLD, 14));
            btnLetra.setFocusPainted(false);
            btnLetra.setBackground(CARD_AZUL);
            btnLetra.setForeground(Color.WHITE);
            btnLetra.setBorder(new LineBorder(new Color(50, 70, 110), 1));
            btnLetra.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnLetra.addActionListener(e -> procesarClickLetra(c, btnLetra));
            botonesTeclado.put(c, btnLetra);
            gridTeclado.add(btnLetra);
        }
        panelDer.add(gridTeclado, BorderLayout.CENTER);

        // Botones inferiores: Abandonar y Nueva palabra
        JPanel panelAcciones = new JPanel(new GridLayout(1, 2, 20, 0));
        panelAcciones.setBackground(BG_OSCURO);
        JButton btnAbandonar = crearBotonBorde("Abandonar", ROJO);
        JButton btnNuevaPalabra = crearBotonBorde("Nueva palabra", CIAN_BORDE);

        btnAbandonar.addActionListener(e -> cardLayout.show(contenedorPrincipal, "MENU"));
        btnNuevaPalabra.addActionListener(e -> iniciarNuevaPartida());

        panelAcciones.add(btnAbandonar);
        panelAcciones.add(btnNuevaPalabra);
        panelDer.add(panelAcciones, BorderLayout.SOUTH);

        panel.add(panelDer, BorderLayout.CENTER);
        return panel;
    }

    // =========================================================================
    // 3. PANTALLA FIN DE PARTIDA
    // =========================================================================
    private JPanel crearPantallaFin() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG_OSCURO);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_AZUL);
        card.setPreferredSize(new Dimension(460, 420));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(CIAN_BORDE, 2),
                new EmptyBorder(35, 50, 35, 50)));

        lblTituloFin = new JLabel("¡Ganaste!");
        lblTituloFin.setFont(new Font("SansSerif", Font.BOLD, 34));
        lblTituloFin.setForeground(VERDE);
        lblTituloFin.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPalabraFin = new JLabel("P R O G R A M A");
        lblPalabraFin.setFont(new Font("Monospaced", Font.BOLD, 24));
        lblPalabraFin.setForeground(Color.WHITE);
        lblPalabraFin.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDetallesFin = new JLabel("Errores: 2 de 6    Posición: #2");
        lblDetallesFin.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblDetallesFin.setForeground(TEXTO_GRIS);
        lblDetallesFin.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPuntajeFin = new JLabel("260");
        lblPuntajeFin.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblPuntajeFin.setForeground(AMARILLO);
        lblPuntajeFin.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnJugarDeNuevo = crearBotonSolido("Jugar de nuevo", AMARILLO, BG_OSCURO);
        JButton btnVerRanking = crearBotonBorde("Ver ranking", CIAN_BORDE);
        JButton btnMenuPrincipal = crearBotonBorde("Menú principal", AMARILLO);

        btnJugarDeNuevo.addActionListener(e -> iniciarNuevaPartida());
        btnVerRanking.addActionListener(e -> {
            cargarDatosRanking(false);
            cardLayout.show(contenedorPrincipal, "RANKING");
        });
        btnMenuPrincipal.addActionListener(e -> cardLayout.show(contenedorPrincipal, "MENU"));

        card.add(lblTituloFin);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(lblPalabraFin);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(lblDetallesFin);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(lblPuntajeFin);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(btnJugarDeNuevo);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(btnVerRanking);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(btnMenuPrincipal);

        wrapper.add(card);
        return wrapper;
    }

    // =========================================================================
    // 4. PANTALLA RANKING
    // =========================================================================
    private JPanel crearPantallaRanking() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(BG_OSCURO);
        panel.setBorder(new EmptyBorder(35, 55, 35, 55));

        // Header Ranking + Botón Volver
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_OSCURO);

        JPanel tituloYTABS = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        tituloYTABS.setBackground(BG_OSCURO);
        JLabel lblRanking = new JLabel("Ranking   ");
        lblRanking.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblRanking.setForeground(AMARILLO);

        btnTabTop10 = crearBotonSolido("Top 10", AMARILLO, BG_OSCURO);
        btnTabTop10.setPreferredSize(new Dimension(100, 32));
        btnTabHistorial = crearBotonBorde("Mi historial", AMARILLO);
        btnTabHistorial.setPreferredSize(new Dimension(120, 32));

        btnTabTop10.addActionListener(e -> cargarDatosRanking(false));
        btnTabHistorial.addActionListener(e -> {
            if (jugadorActual != null) {
                cargarDatosRanking(true);
            } else {
                JOptionPane.showMessageDialog(this, "Inicia una partida primero para ver tu historial.");
            }
        });

        tituloYTABS.add(lblRanking);
        tituloYTABS.add(btnTabTop10);
        tituloYTABS.add(btnTabHistorial);

        JButton btnVolver = crearBotonBorde("Volver al menú", TEXTO_GRIS);
        btnVolver.setPreferredSize(new Dimension(140, 36));
        btnVolver.addActionListener(e -> cardLayout.show(contenedorPrincipal, "MENU"));

        topBar.add(tituloYTABS, BorderLayout.WEST);
        topBar.add(btnVolver, BorderLayout.EAST);
        panel.add(topBar, BorderLayout.NORTH);

        // Tabla estilizada
        String[] columnas = {"Pos.", "Jugador", "Puntaje", "Errores", "Fecha"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaRanking = new JTable(modeloTabla);
        tablaRanking.setBackground(CARD_AZUL);
        tablaRanking.setForeground(Color.WHITE);
        tablaRanking.setRowHeight(38);
        tablaRanking.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaRanking.setShowGrid(false);
        tablaRanking.setIntercellSpacing(new Dimension(0, 2));
        tablaRanking.getTableHeader().setBackground(BG_OSCURO);
        tablaRanking.getTableHeader().setForeground(TEXTO_GRIS);
        tablaRanking.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnas.length; i++) {
            tablaRanking.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scroll = new JScrollPane(tablaRanking);
        scroll.getViewport().setBackground(CARD_AZUL);
        scroll.setBorder(new LineBorder(new Color(45, 65, 105), 1));
        panel.add(scroll, BorderLayout.CENTER);

        JLabel lblNota = new JLabel("Orden: mayor puntaje; si hay empate, la fecha más antigua.");
        lblNota.setForeground(TEXTO_GRIS);
        lblNota.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(lblNota, BorderLayout.SOUTH);

        return panel;
    }

    // =========================================================================
    // LÓGICA DE CONTROL ENTRE INTERFAZ Y BACKEND
    // =========================================================================
    private void iniciarNuevaPartida() {
        String nombre = txtNombreJugador.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa tu nombre de jugador.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        jugadorActual = gestorJuego.registrarJugador(nombre);
        String catSeleccionada = (String) cmbCategoria.getSelectedItem();

        PalabraDTO palabra = gestorJuego.seleccionarPalabra(
                "Todas".equals(catSeleccionada) ? "General" : catSeleccionada, "Fácil");

        if (palabra == null) {
            JOptionPane.showMessageDialog(this, "No hay palabras registradas en esa categoría.");
            return;
        }

        juegoActual = new JuegoLogica(jugadorActual, palabra);

        // Resetear vista del juego
        lblJugadorPartida.setText("<html>Jugador: <font color='#FFC13B'>" + jugadorActual.obtenerNombre() + "</font></html>");
        lblCategoriaPartida.setText("Categoría: " + palabra.obtenerCategoria());
        lblPalabraOculta.setText(juegoActual.obtenerPalabraEnmascarada());
        panelHorca.setErrores(0);
        actualizarIndicadoresErrores(0);

        for (JButton btn : botonesTeclado.values()) {
            btn.setEnabled(true);
            btn.setBackground(CARD_AZUL);
            btn.setForeground(Color.WHITE);
            btn.setBorder(new LineBorder(new Color(50, 70, 110), 1));
        }

        cardLayout.show(contenedorPrincipal, "JUEGO");
    }

    private void procesarClickLetra(char letra, JButton btn) {
        btn.setEnabled(false);
        boolean acierto = juegoActual.intentarLetra(letra);

        if (acierto) {
            btn.setBackground(VERDE);
            btn.setForeground(BG_OSCURO);
        } else {
            btn.setBackground(INPUT_BG);
            btn.setBorder(new LineBorder(ROJO, 2));
            btn.setForeground(ROJO);
        }

        lblPalabraOculta.setText(juegoActual.obtenerPalabraEnmascarada());
        int err = juegoActual.obtenerErrores();
        panelHorca.setErrores(err);
        actualizarIndicadoresErrores(err);

        if (juegoActual.estaTerminada()) {
            PartidaDTO resumen = gestorJuego.finalizarPartida(juegoActual);
            mostrarPantallaFinal(resumen);
        }
    }

    private void mostrarPantallaFinal(PartidaDTO resumen) {
        boolean gano = juegoActual.esVictoria();
        lblTituloFin.setText(gano ? "¡Ganaste!" : "¡Perdiste!");
        lblTituloFin.setForeground(gano ? VERDE : ROJO);

        // Espaciar letras de la palabra final
        String palabraEspaciada = String.join(" ", juegoActual.getPalabraSecreta().obtenerPalabra().split(""));
        lblPalabraFin.setText(palabraEspaciada);

        lblDetallesFin.setText("Errores: " + resumen.obtenerErrores() + " de 6");
        lblPuntajeFin.setText("<html><font size='3' color='#A0AEC0'>Puntaje </font>" + resumen.obtenerPuntaje() + "</html>");

        cardLayout.show(contenedorPrincipal, "FIN");
    }

    private void actualizarIndicadoresErrores(int errores) {
        lblErroresTexto.setText("Errores " + errores + "/6");
        panelIndicadoresErrores.removeAll();
        for (int i = 1; i <= 6; i++) {
            JLabel dot = new JLabel("●");
            dot.setFont(new Font("SansSerif", Font.BOLD, 14));
            dot.setForeground(i <= errores ? ROJO : new Color(70, 90, 130));
            panelIndicadoresErrores.add(dot);
        }
        panelIndicadoresErrores.revalidate();
        panelIndicadoresErrores.repaint();
    }

    private void cargarDatosRanking(boolean soloHistorialJugador) {
        modeloTabla.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (soloHistorialJugador && jugadorActual != null) {
            List<PartidaDTO> historial = gestorJuego.verHistorial(jugadorActual.obtenerId());
            int pos = 1;
            for (PartidaDTO p : historial) {
                modeloTabla.addRow(new Object[]{
                        pos++,
                        jugadorActual.obtenerNombre(),
                        p.obtenerPuntaje(),
                        p.obtenerErrores(),
                        p.obtenerFecha().format(fmt)
                });
            }
        } else {
            List<RankingDTO> top = gestorJuego.verRanking();
            for (RankingDTO r : top) {
                modeloTabla.addRow(new Object[]{
                        r.obtenerPosicion(),
                        r.obtenerNombreJugador(),
                        r.obtenerPuntaje(),
                        "-",
                        r.obtenerFecha().format(fmt)
                });
            }
        }
    }

    // =========================================================================
    // HELPERS VISUALES Y COMPONENTE DE DIBUJO VECTORIAL DE LA HORCA
    // =========================================================================
    private JButton crearBotonSolido(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton crearBotonBorde(String texto, Color bordeColor) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(CARD_AZUL);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(bordeColor, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    private void cargarPalabrasIniciales() {
        List<PalabraDTO> existentes = gestorJuego.getPalabraDAO().listar();

        // Si hay menos de 10 palabras, cargamos el catálogo completo
        if (existentes.size() < 10) {
            String[][] catalogo = {
                    {"PROGRAMA", "Programación", "Fácil"},
                    {"JAVA", "Programación", "Fácil"},
                    {"ALGORITMO", "Programación", "Media"},
                    {"POLIMORFISMO", "Programación", "Difícil"},
                    {"ATOMO", "Ciencia", "Fácil"},
                    {"GRAVEDAD", "Ciencia", "Media"},
                    {"FOTOSINTESIS", "Ciencia", "Difícil"},
                    {"AHORCADO", "General", "Fácil"},
                    {"UNIVERSO", "General", "Media"},
                    {"ARQUITECTURA", "General", "Difícil"},
                    {"PARANGARICUTIRIMICUARO", "Raro", "Difícil"}
            };

            for (String[] item : catalogo) {
                boolean yaExiste = existentes.stream()
                        .anyMatch(p -> p.obtenerPalabra().equalsIgnoreCase(item[0]));
                if (!yaExiste) {
                    gestorJuego.getPalabraDAO().agregar(new PalabraDTO(item[0], item[1], item[2]));
                }
            }
        }
    }

    // Lienzo personalizado que dibuja la horca exactamente como tus mockups
    private static class PanelHorca extends JPanel {
        private int errores = 0;
        private boolean modoLogo = false;

        public PanelHorca() {
            setOpaque(false);
        }

        public void setErrores(int errores) {
            this.errores = errores;
            repaint();
        }

        public void setModoLogo(boolean modoLogo) {
            this.modoLogo = modoLogo;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            int w = getWidth();
            int h = getHeight();
            int baseX = w / 2 - 45;
            int baseY = h - 30;

            // Estructura de la horca (Blanca)
            g2.setColor(Color.WHITE);
            g2.drawLine(baseX - 25, baseY, baseX + 55, baseY);           // Base
            g2.drawLine(baseX, baseY, baseX, baseY - 170);               // Poste vertical
            g2.drawLine(baseX, baseY - 170, baseX + 75, baseY - 170);    // Travesaño
            g2.drawLine(baseX + 75, baseY - 170, baseX + 75, baseY - 145); // Cuerda

            if (modoLogo) {
                g2.setColor(AMARILLO);
                g2.drawOval(baseX + 60, baseY - 145, 30, 30);
                return;
            }

            // Muñeco (Rojo coral según el número de errores 1..6)
            g2.setColor(ROJO);
            if (errores >= 1) g2.drawOval(baseX + 60, baseY - 145, 30, 30);          // Cabeza
            if (errores >= 2) g2.drawLine(baseX + 75, baseY - 115, baseX + 75, baseY - 65); // Torso
            if (errores >= 3) g2.drawLine(baseX + 75, baseY - 105, baseX + 52, baseY - 82); // Brazo izq
            if (errores >= 4) g2.drawLine(baseX + 75, baseY - 105, baseX + 98, baseY - 82); // Brazo der
            if (errores >= 5) g2.drawLine(baseX + 75, baseY - 65, baseX + 55, baseY - 35);  // Pierna izq
            if (errores >= 6) g2.drawLine(baseX + 75, baseY - 65, baseX + 95, baseY - 35);  // Pierna der
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuConsola().setVisible(true));
    }
}