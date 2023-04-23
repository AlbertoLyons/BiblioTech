/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech;

import cl.ucn.disc.pa.bibliotech.services.Sistema;
import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

import java.io.IOException;
import java.util.Objects;

/**
 * The Main.
 *
 * @author Programacion Avanzada.
 */
public final class Main {

    /**
     * The main.
     *
     * @param args to use.
     * @throws IOException en caso de un error.
     */
    public static void main(final String[] args) throws IOException {

        // Programa pensado para volver a sus valores iniciales cada vez que se vuelve a ejecutar.

        // inicializacion del sistema.
        Sistema sistema = new Sistema();
        sistema.disponibilidadInicio();

        StdOut.println(sistema.obtenerCatalogoLibrosDisponibles());

        String opcion = null;
        while (!Objects.equals(opcion, "2")) {

            StdOut.println("""
                    [*] Bienvenido a BiblioTech [*]
                                    
                    [1] Iniciar Sesion
                    [2] Salir
                    """);
            StdOut.print("Escoja una opcion: ");
            //Para evitar problemas, la opcion vuelve a ser null.
            opcion = null;
            opcion = StdIn.readLine();

            switch (opcion) {
                case "1" -> iniciarSesion(sistema);
                case "2" -> StdOut.println("¡Hasta Pronto!");
                default -> StdOut.println("Opcion no valida, intente nuevamente");
            }
        }
    }

    /**
     * Inicia la sesion del Socio en el Sistema.
     *
     * @param sistema a utilizar.
     */
    private static void iniciarSesion(final Sistema sistema) throws IOException {
        //Variable y ciclo para validar que el numero sea valido.
        int numeroSocio;

        while (true) {
            try {

                StdOut.println("[*] Iniciar sesion en BiblioTech [*]");
                StdOut.print("Ingrese su numero de socio: ");
                String numeroSocioString = StdIn.readString();
                numeroSocio = Integer.parseInt(numeroSocioString);
                StdIn.readLine();
                break;

            } catch (Exception e) {
                StdOut.println("El numero de socio ingresado no es valido");
            }
        }

        StdOut.print("Ingrese su contrasenia: ");
        String contrasenia = StdIn.readLine();

        // intento el inicio de session
        try {
            sistema.iniciarSession(numeroSocio, contrasenia);
        } catch (IllegalArgumentException ex) {
            StdOut.println("Ocurrio un error: " + ex.getMessage());
            StdOut.println("Intentelo denuevo.");
            StdOut.println("");
            return;
        }

        // mostrar menu principal
        menuPrincipal(sistema);
    }

    private static void menuPrincipal(final Sistema sistema) throws IOException {

        String opcion = null;
        while (!Objects.equals(opcion, "4")) {
            StdOut.println("""
                    [*] BiblioTech [*]
                                        
                    [1] Prestamo de un libro
                    [2] Editar información
                    [3] Calificar libro
                                        
                    [4] Cerrar sesion
                    """);

            StdOut.print("Escoja una opcion: ");
            opcion = StdIn.readLine();

            switch (opcion) {
                case "1" -> menuPrestamo(sistema);
                case "2" -> editarInformacion(sistema);
                case "3" -> calificarLibro(sistema);
                case "4" -> sistema.cerrarSession();
                default -> StdOut.println("Opcion no valida, intente nuevamente");
            }
        }
    }

    // Metodo para realizar un prestamo de libro.
    private static void menuPrestamo(Sistema sistema) {
        StdOut.println("[*] Préstamo de un Libro [*]");
        StdOut.println(sistema.obtenerCatalogoLibrosDisponibles());

        StdOut.print("Ingrese el ISBN del libro a tomar prestado: ");
        String isbn = StdIn.readLine();

        try {
            sistema.realizarPrestamoLibro(isbn);
        } catch (IOException ex) {
            StdOut.println("Ocurrio un error, intente nuevamente: " + ex.getMessage());
        }
    }

    // Metodo para editar la informacion del usuario.
    private static void editarInformacion(Sistema sistema) throws IOException {

        String opcion = null;
        while (!Objects.equals(opcion, "3")) {

            StdOut.println("[*] Editar Perfil [*]");
            StdOut.println(sistema.obtenerDatosSocioLogeado());
            StdOut.println("""               
                    [1] Editar correo Electronico
                    [2] Editar Contraseña
                                        
                    [3] Volver atrás
                    """);
            StdOut.print("Escoja una opción: ");
            opcion = StdIn.readLine();

            switch (opcion) {
                case "1" -> editarCorreo(sistema);
                case "2" -> cambiarContrasenia(sistema);
                case "3" -> StdOut.println("Volviendo al menú anterior...");
                default -> StdOut.println("Opcion no valida, intente nuevamente");
            }
        }
    }

    // Metodo para calificar y realizar reseña a un libro.
    private static void calificarLibro(Sistema sistema) {

        int calificacion = 0;
        StdOut.println("[*] Calificacion del libro");
        StdOut.println(sistema.obtenerCatalogoLibrosParaResenias());

        StdOut.print("Ingrese el ISBN del libro a calificar: ");

        String isbn = StdIn.readLine();
        sistema.buscarLibroParaResenia(isbn);
        isbn = sistema.obtenerCatalogoLibrosParaResenias();


        StdOut.print("Realice una breve reseña: ");
        String resenia = StdIn.readLine();

        while (true) {
            try {
                StdOut.println("[*] Calificacion de un libro");
                StdOut.print("Califique el libro del 0 al 5: ");
                String calificacionString = StdIn.readLine();
                calificacion = Integer.parseInt(calificacionString);

                if (calificacion > 0 && calificacion < 5) {
                    break;
                }
            } catch (Exception e) {
                StdOut.print("La calificacion ingresada no es valida");
            }
        }
        sistema.calificacionesLibros(isbn, calificacion);
        StdOut.println("/// Libro calificado con exito. ///");
        StdOut.println("");
    }

    // Metodo para actualizar contraseña.
    private static void cambiarContrasenia(Sistema sistema) throws IOException {

        StdOut.print("Ingrese la nueva contraseña: ");
        String contraseñaNueva = StdIn.readString();
        sistema.actualizarContraseña(contraseñaNueva);
        StdOut.println("Contrasenia actualizada con exito.");
        StdOut.println("");
    }

    // Metodo para actualizar correo.
    private static void editarCorreo(Sistema sistema) throws IOException {

        StdOut.print("Ingrese el nuevo correo electronico: ");
        String correoNuevo = StdIn.readString();
        sistema.actualizarCorreo(correoNuevo);
        StdOut.println("Correo actualizado con exito.");
        StdOut.println("");
    }
}
