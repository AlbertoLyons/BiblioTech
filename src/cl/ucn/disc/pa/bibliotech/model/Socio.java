/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech.model;

import cl.ucn.disc.pa.bibliotech.services.Utils;

/**
 * Clase que representa a un Socio.
 *
 * @author Programacion Avanzada.
 */
public final class Socio {

    /**
     * Numero maximo de libros que puede tener el Socio.
     */
    private static final int NUMERO_LIBROS_MAXIMO = 5;

    /**
     * Nombre del socio.
     */
    private String nombre;

    /**
     * Apellido del socio.
     */
    private String apellido;

    /**
     * Email del socio.
     */
    private String correoElectronico;

    /**
     * Numero del socio.
     */
    private int numeroDeSocio;

    /**
     * Contrasenia del socio.
     */
    private String contrasenia;

    /**
     * Libros que el Socio tiene en prestamo (maximo 10).
     */

    // definimos el maximo como 5 porque en la linea 19 el maximo esta definido como 5 y tambien en el metodo agregar libro se compara justamente con ese valor.
    private Libro[] librosEnPrestamo = new Libro[5];

    /**
     * The Constructor.
     *
     * @param nombre            del socio.
     * @param apellido          del socio.
     * @param correoElectronico del socio.
     * @param numeroDeSocio     del socio.
     * @param contrasenia       del socio.
     */
    public Socio(String nombre, String apellido, String correoElectronico, int numeroDeSocio, String contrasenia) {

        // Validacion de nombre.
        if (nombre == null || nombre.length() == 0) {
            throw new IllegalArgumentException("Nombre no valido!");
        }
        this.nombre = nombre;

        // Validacion de apellido.
        if (apellido == null || apellido.length() == 0) {
            throw new IllegalArgumentException("Apellido no valido!");
        }
        this.apellido = apellido;

        // Metodo estatico para validacion de email.
        Utils.validarEmail(correoElectronico);
        this.correoElectronico = correoElectronico;

        // Validacion de numero de socio.
        if (numeroDeSocio == 0) {
            throw new IllegalArgumentException("Numero de socio no valido!");
        }
        this.numeroDeSocio = numeroDeSocio;

        // Validacion de contrasenia.
        if (contrasenia == null || contrasenia.length() == 0) {
            throw new IllegalArgumentException("Contrasenia no valido!");
        }
        this.contrasenia = contrasenia;
    }

    /**
     * @return el nombre del Socio.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Actualiza el valor nombre
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return el apellido del Socio.
     */
    public String getApellido() {
        return this.apellido;
    }

    /**
     * Actualiza el valor apellido
     *
     * @param apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return el nombre completo del Socio.
     */
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    /**
     * @return el correo electronico del Socio.
     */
    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    /**
     * @return el numero del Socio.
     */
    public int getNumeroDeSocio() {
        return this.numeroDeSocio;
    }

    /**
     * @return la contrasenia del Socio.
     */
    public String getContrasenia() {
        return this.contrasenia;
    }

    /**
     * Agrega un libro en prestamo al Socio.
     *
     * @param libro a agregar.
     */
    public void agregarLibro(final Libro libro) {
        // Validacion
        if (this.librosEnPrestamo.length == NUMERO_LIBROS_MAXIMO) {
            throw new IllegalArgumentException("El Socio ya tiene la maxima cantidad de libros en prestamo: " + NUMERO_LIBROS_MAXIMO);
        }
        // Agrega el libro
        Utils.append(this.librosEnPrestamo, libro);
    }

    /**
     * Actualiza el correo electronico dado por el usuario.
     *
     * @param correoElectronico
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Actualiza la contraseña dada por el usuario.
     *
     * @param contrasenia
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}