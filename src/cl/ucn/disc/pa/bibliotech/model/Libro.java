/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech.model;

/**
 * Clase que representa un Libro.
 *
 * @author Programacion Avanzada.
 */
public final class Libro {

    /**
     * The ISBN.
     */
    private String isbn;

    /**
     * The Titulo.
     */
    private String titulo;

    /**
     * The Author.
     */
    private String autor;

    /**
     * The Categoria.
     */
    private String categoria;

    // TODO: Agregar la calificacion.

    // Atributos para la calificacion
    /**
     * The cantCalificaciones
     */
    private int cantCalificaciones;
    /**
     * The estrellasTotales
     */
    private int estrellasTotales;

    /**
     * The promedio de estrellas.
     */
    private double promedioCalificacion;

    /**
     * The disponibildiadLibro
     */
    private boolean disponibilidadLibro;

    /**
     * The Constructor.
     *
     * @param isbn      del libro.
     * @param titulo    del libro.
     * @param autor     del libro
     * @param categoria del libro.
     */
    public Libro(final String isbn, final String titulo, final String autor, final String categoria) {

        // Validacion del isbn.
        if (isbn == null || isbn.length() == 0) {
            throw new IllegalArgumentException("isbn no valido!");
        }
        this.isbn = isbn;

        // Validacion del título.
        if (titulo == null || titulo.length() == 0) {
            throw new IllegalArgumentException("Titulo no valido!");
        }
        this.titulo = titulo;

        // Validacion del autor
        if (autor == null || autor.length() == 0) {
            throw new IllegalArgumentException("Autor no valido!");
        }
        this.autor = autor;

        // Validacion de la categoria.
        if (categoria == null || categoria.length() == 0) {
            throw new IllegalArgumentException("Categoria no valido!");
        }
        this.categoria = categoria;

        // Variable de la disponibilidad del libro.
        this.disponibilidadLibro = true;

        // Variables relacionadas a la calificacion de un libro.
        this.promedioCalificacion = 0.0;
        this.cantCalificaciones = 0;
        this.estrellasTotales = 0;

    }

    /**
     * @return the ISBN.
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * @return the titulo.
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * @return the autor.
     */
    public String getAutor() {
        return this.autor;
    }

    /**
     * @return the categoria.
     */
    public String getCategoria() {
        return this.categoria;
    }

    /**
     * @return the promedio de calificaiones.
     */
    public double getPromedioCalificacion() {
        return this.promedioCalificacion;
    }

    /**
     * @return the cantidad de calificaciones.
     */
    public int getCantCalificaciones() {
        return cantCalificaciones;
    }

    /**
     * @return the estrellas totales.
     */
    public int getEstrellasTotales() {
        return estrellasTotales;
    }

    /**
     * Actualiza la cantidad de calificaciones.
     *
     * @param cantCalificaciones
     */
    public void setCantCalificaciones(int cantCalificaciones) {
        this.cantCalificaciones = cantCalificaciones;
    }

    /**
     * Actualiza la cantidad de estrellas del libro.
     *
     * @param estrellasTotales
     */
    public void setEstrellasTotales(int estrellasTotales) {
        this.estrellasTotales = estrellasTotales;
    }

    /**
     * Genera y actualiza el promedio de calificaiones del libro.
     *
     * @param promedioCalificacion
     */
    public void setPromedioCalificacion(double promedioCalificacion) {
        this.promedioCalificacion = promedioCalificacion;
    }

    /**
     * @return the disponibilidadLibro.
     */
    public boolean isDisponibilidadLibro() {
        return disponibilidadLibro;
    }

    /**
     * Genera la disponibilidad del libro.
     *
     * @param disponibilidadLibro
     */
    public void setDisponibilidadLibro(boolean disponibilidadLibro) {
        this.disponibilidadLibro = disponibilidadLibro;
    }

    /**
     * Actualiza la disponibilidad del libro.
     *
     * @param disponibilidadLibro
     */
    public void actualizarDisponibilidad(boolean disponibilidadLibro) {
        if (disponibilidadLibro) {
            this.disponibilidadLibro = false;
        }
    }
}