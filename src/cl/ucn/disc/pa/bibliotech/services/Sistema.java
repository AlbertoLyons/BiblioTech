/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech.services;

import cl.ucn.disc.pa.bibliotech.model.Libro;
import cl.ucn.disc.pa.bibliotech.model.Socio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.princeton.cs.stdlib.StdOut;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Sistema.
 *
 * @author Programacion Avanzada.
 */
public final class Sistema {

    /**
     * Procesador de JSON.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * The list of Socios.
     */
    private Socio[] socios;

    /**
     * The list of Libros.
     */
    private Libro[] libros;

    /**
     * Socio en el sistema.
     */
    private Socio socio;

    /**
     * The Sistema.
     */
    public Sistema() throws IOException {

        // no hay socio logeado.
        this.socios = new Socio[10];
        this.libros = new Libro[10];
        this.socio = null;

        // carga de los socios y libros.
        try {
            this.cargarInformacion();
        } catch (FileNotFoundException ex) {
            // no se encontraron datos, se agregar los por defecto.

            // creo un socio y lo agrego al arreglo de socios
            this.socios = Utils.append(this.socios, new Socio("John", "Doe", "john.doe@ucn.cl", 1, "john123"));

            // creo otro socio y lo agrego al arreglo de socios
            this.socios = Utils.append(this.socios, new Socio("Ruben", "Macaya", "ruben.macaya@gmail.com", 2, "ruben123"));

            // creo un libro y lo agrego al arreglo de libros.
            this.libros = Utils.append(this.libros, new Libro("1491910771", "Head First Java: A Brain-Friendly Guide", "Kathy Sierra", "Programming Languages"));

            // creo otro libro y lo agrego al arreglo de libros.
            this.libros = Utils.append(this.libros, new Libro("1491910772", "Effective Java", "Joshua Bloch", "Programming Languages"));

            // creo otro libro y lo agrego al arreglo de libros.
            this.libros = Utils.append(this.libros, new Libro("1234578900", "Esta dificil el taller", "Ruben Macaya", "Programming Languages"));


        } finally {
            // guardo la informacion.
            this.guardarInformacion();
        }

    }

    /**
     * Activa (inicia sesion) de un socio en el sistema.
     *
     * @param numeroDeSocio a utilizar.
     * @param contrasenia   a validar.
     */
    public void iniciarSession(final int numeroDeSocio, final String contrasenia) {

        // el numero de socio siempre es positivo.
        if (numeroDeSocio <= 0) {
            throw new IllegalArgumentException("El numero de socio no es valido!");
        }

        // el numero de socio no puede ser mayor al largo de socios guardados.
        if (numeroDeSocio > this.socios.length) {
            throw new IllegalArgumentException("El numero de socio no es valido!");
        }

        // definimos un auxiliar para comprobar su contraseña.
        Socio socioAux = socios[numeroDeSocio - 1];

        // verificamos que la contraseña ingresada sea la misma que pertenece al socio.
        if (!contrasenia.equals(socioAux.getContrasenia())) {
            throw new IllegalArgumentException(" La contrasenia ingresada no es valida!");
        }

        // si paso todas las validaciones anteriores le asignamos el atributo socio al ingresado.
        this.socio = socios[numeroDeSocio - 1];

        StdOut.println("Inicio de sesion exitoso.");
        StdOut.println("");

    }

    /**
     * Cierra la session del Socio.
     */
    public void cerrarSession() {
        this.socio = null;
    }

    /**
     * Metodo que mueve un libro de los disponibles y lo ingresa a un Socio.
     *
     * @param isbn del libro a prestar.
     */
    public void realizarPrestamoLibro(final String isbn) throws IOException {
        // el socio debe estar activo.
        if (this.socio == null) {
            throw new IllegalArgumentException("Socio no se ha logeado!");
        }

        // busco el libro.
        Libro libro = this.buscarLibro(isbn);

        // si no lo encontre, lo informo.
        if (libro == null) {
            StdOut.println("Libro con isbn " + isbn + " no existe o no se encuentra disponible.");
            return;
        }

        // si el libro ya fue prestado, lo informo.
        if (!libro.isDisponibilidadLibro()) {
            StdOut.println("Libro con isbn " + isbn + " no se encuentra disponible actualmente.");
            return;
        }

        // agrego el libro al socio.
        this.socio.agregarLibro(libro);
        StdOut.println("Libro agregado con exito a tu lista.");

        libro.actualizarDisponibilidad(libro.isDisponibilidadLibro());

        // se actualiza la información de los archivos
        this.guardarInformacion();
    }

    public void disponibilidadInicio() {
        boolean disponible = true;
        for (Libro libro : this.libros) {

            libro.setDisponibilidadLibro(disponible);
        }
    }

    /**
     * Obtiene un String que representa el listado completo de libros disponibles.
     *
     * @return the String con la informacion de los libros disponibles.
     */
    public String obtenerCatalogoLibrosDisponibles() {

        int contadorDisponibilidad = 0;
        StringBuilder sb = new StringBuilder();
        for (Libro libro : this.libros) {

            if (libro.isDisponibilidadLibro()) {
                sb.append("Titulo    : ").append(libro.getTitulo()).append("\n");
                sb.append("Autor     : ").append(libro.getAutor()).append("\n");
                sb.append("ISBN      : ").append(libro.getIsbn()).append("\n");
                sb.append("Categoria : ").append(libro.getCategoria()).append("\n");
                sb.append("\n");
            }

            if (!libro.isDisponibilidadLibro()) {
                contadorDisponibilidad++;
            }
        }

        if (contadorDisponibilidad == this.libros.length) {
            StdOut.println("No hay libros disponibles. Porfavor ingresa un 1 para salir de esta pestania.");
        }
        return sb.toString();
    }

    /**
     * Obtiene un String que representa el listado completo de libros para realizar una reseña.
     *
     * @return the String con la informacion de los libros disponibles a reseñar.
     */
    public String obtenerCatalogoLibrosParaResenias() {

        StringBuilder sb = new StringBuilder();

        for (Libro libro : this.libros) {
            sb.append("Titulo    : ").append(libro.getTitulo()).append("\n");
            sb.append("Autor     : ").append(libro.getAutor()).append("\n");
            sb.append("ISBN      : ").append(libro.getIsbn()).append("\n");
            sb.append("Categoria : ").append(libro.getCategoria()).append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Metodo que busca un libro en los libros disponibles.
     *
     * @param isbn a buscar.
     * @return el libro o null si no fue encontrado.
     */
    private Libro buscarLibro(final String isbn) {
        // recorro el arreglo de libros.
        for (Libro libro : this.libros) {
            // si lo encontre, retorno el libro.
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        // no lo encontre, retorno null.
        return null;
    }

    /**
     * Lee los archivos libros.json y socios.json.
     *
     * @throws FileNotFoundException si alguno de los archivos no se encuentra.
     */
    private void cargarInformacion() throws FileNotFoundException {

        // trato de leer los socios y los libros desde el archivo.
        this.socios = GSON.fromJson(new FileReader("socios.json"), Socio[].class);
        this.libros = GSON.fromJson(new FileReader("libros.json"), Libro[].class);
    }

    /**
     * Guarda los arreglos libros y socios en los archivos libros.json y socios.json.
     *
     * @throws IOException en caso de algun error.
     */
    private void guardarInformacion() throws IOException {

        // guardo los socios.
        try (FileWriter writer = new FileWriter("socios.json")) {
            GSON.toJson(this.socios, writer);
        }

        // guardo los libros.
        try (FileWriter writer = new FileWriter("libros.json")) {
            GSON.toJson(this.libros, writer);
        }

    }

    /**
     * Valida lo que ingreso el usuario.
     *
     * @return el nombre completo junto con el correo electronico.
     * @throws IllegalArgumentException en caso de no tener un socio logeado.
     */
    public String obtenerDatosSocioLogeado() {
        if (this.socio == null) {
            throw new IllegalArgumentException("No hay un Socio logeado");
        }

        return "Nombre: " + this.socio.getNombreCompleto() + "\n"
                + "Correo Electronico: " + this.socio.getCorreoElectronico();
    }

    /**
     * Actualiza el correo.
     *
     * @param correoNuevo
     */
    public void actualizarCorreo(String correoNuevo) {
        Utils.validarEmail(correoNuevo);
        this.socio.setCorreoElectronico(correoNuevo);
    }

    /**
     * Actualiza la contraseña.
     *
     * @param contraseñaNueva
     */
    public void actualizarContraseña(String contraseñaNueva) {
        this.socio.setContrasenia(contraseñaNueva);
    }

    /**
     * Actualiza las calificaciones de los libros y despliega la calificacion global.
     *
     * @param calificacion
     * @param isbn
     */
    public void calificacionesLibros(String isbn, int calificacion) {

        // Buscamos la posicion del libroon un auxiliar.
        int auxPos = 0;
        for (int i = 0; i < this.libros.length; i++) {
            if (isbn.equalsIgnoreCase(this.libros[i].getIsbn())) {
                auxPos = i;
                break;
            }
        }

        // Encontrada su posicion actualizamos los valores utilizando parametros.
        int auxCantCalificaciones = this.libros[auxPos].getCantCalificaciones();
        auxCantCalificaciones++;

        int auxEstrellasTotales = this.libros[auxPos].getEstrellasTotales();
        auxEstrellasTotales += calificacion;

        //Actualiza la cantidad de calificaciones, las estrellas totales, y el promedio de calificacion
        this.libros[auxPos].setCantCalificaciones(auxCantCalificaciones);
        this.libros[auxPos].setEstrellasTotales(auxEstrellasTotales);

        this.libros[auxPos].setPromedioCalificacion(auxEstrellasTotales / auxCantCalificaciones);
        StdOut.println("Calificacion global del libro: " + this.libros[auxPos].getPromedioCalificacion());
    }

}
