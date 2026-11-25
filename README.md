# 🎯 Hangman Streak

**Hangman Streak** es una aplicación de consola desarrollada en **Java** que implementa el juego clásico del Ahorcado bajo estándares de ingeniería de software, incorporando gestión de jugadores únicos, banco de palabras categorizado, cálculo dinámico de puntuación y una tabla de clasificación global (*Ranking*) con persistencia relacional.

---

## ✨ Características Principales

* **Gestión de Jugadores:** Registro e inicio de sesión mediante nombre de usuario único y no vacío.
* **Banco de Palabras Dinámico:** Selección aleatoria de palabras almacenadas en base de datos, con soporte para filtrar por categoría y dificultad, además de permitir agregar nuevas palabras.
* **Mecánica y Reglas de Puntuación:**
  * Límite de hasta **6 errores** por partida.
  * Validación de letras individuales sin repetición y manejo de palabras en mayúsculas sin tildes ni espacios.
  * **Fórmula de Puntaje (Victoria):** `100 + (6 – errores) × 20 + (longitud de la palabra × 10)`.
  * **Derrota:** Suma `0` puntos.
* **Persistencia Relacional Embebida:** Almacenamiento automático de jugadores, historial de partidas y resultados en **H2 Database** mediante **JDBC** sin requerir servidores externos.
* **Ranking Global e Historial:** Consulta de los mejores puntajes ordenados de mayor a menor (desempatando por fecha más antigua) e historial individual por jugador.

---

## 🏗️ Arquitectura del Proyecto

El sistema sigue una arquitectura limpia en capas desacopladas, implementando los patrones de diseño **DAO** (*Data Access Object*) y **DTO** (*Data Transfer Object*):

```text
src/
├── main/
│   └── java/
│       ├── conexion/      # Gestión de conexión e inicialización de tablas H2 (ConexionH2)
│       ├── dto/           # Objetos de transferencia de datos (JugadorDTO, PalabraDTO, PartidaDTO, RankingDTO)
│       ├── dao/           # Interfaces DAO e implementaciones JDBC (JugadorDAO, PalabraDAO, PartidaDAO)
│       ├── logica/        # Reglas del juego y orquestación (JuegoLogica, GestorJuego)
│       └── ui/            # Interfaz de usuario por consola y punto de entrada (MenuConsola)
└── test/
    └── java/
        ├── dto/           # Pruebas unitarias de los modelos DTO con JUnit 5
        └── dao/           # Pruebas de persistencia JDBC con JUnit 5
```

---

## 🛠️ Tecnologías y Requisitos

* **Lenguaje:** Java (JDK 17 o superior)
* **Base de Datos:** H2 Database (`com.h2database:h2:2.2.224`) vía JDBC
* **Pruebas Unitarias:** JUnit 5 (`org.junit.jupiter:junit-jupiter:5.10.0`)
* **Entorno de Desarrollo:** IntelliJ IDEA

---

## 🚀 Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/Daniel-ui7/hangman-streak.git](https://github.com/Daniel-ui7/hangman-streak.git)
   cd hangman-streak
   ```

2. **Abrir en IntelliJ IDEA y configurar librerías:**
   * Abre el proyecto en IntelliJ IDEA.
   * Ve a **File $\rightarrow$ Project Structure $\rightarrow$ Modules $\rightarrow$ Dependencies**.
   * Asegúrate de tener agregadas desde Maven las librerías:
     * `com.h2database:h2:2.2.224` (Scope: *Compile*)
     * `org.junit.jupiter:junit-jupiter:5.10.0` (Scope: *Test*)

3. **Ejecutar la aplicación:**
   * Ubica la clase `ui.MenuConsola` y ejecuta su método `main`.
   * Al iniciar por primera vez, la aplicación creará automáticamente el archivo de base de datos local en `./data/hangman_db` junto con sus tablas y palabras iniciales.

4. **Ejecutar las pruebas unitarias:**
   * Haz clic derecho sobre la carpeta `src/test/java` y selecciona **Run 'All Tests'**.

---

## 📚 Documentación Técnica (Wiki)

Para explorar el análisis completo, especificaciones y modelado del sistema, consulta la **[Wiki del Repositorio](https://github.com/Daniel-ui7/hangman-streak/wiki)**:

* **[Home](https://github.com/Daniel-ui7/hangman-streak/wiki):** Visión general y stack tecnológico.
* **[Requerimientos](https://github.com/Daniel-ui7/hangman-streak/wiki/Requerimientos):** Requerimientos Funcionales (`RF-01` a `RF-16`), Reglas de Negocio (`RN-01` a `RN-06`) y Requerimientos No Funcionales (`RNF-01` a `RNF-08`).
* **[Diagrama de Clases](https://github.com/Daniel-ui7/hangman-streak/wiki/Diagrama-de-Clases):** Diagrama UML en Mermaid detallando todos los paquetes, interfaces, clases y relaciones.

---

## 👤 Autor

* **Daniel Alejandro Romero Murcia** – [@Daniel-ui7](https://github.com/Daniel-ui7)
* 
