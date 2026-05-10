import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que ejecuta el sistema de gestión de biblioteca.
 * Proporciona un menú interactivo para gestionar libros, usuarios y préstamos.
 * 
 * @author Enrique
 * @version 1.0
 */
public class Main {
    private static Biblioteca biblioteca = new Biblioteca();
    private static List<Usuari> usuaris = new ArrayList<>();
    private static GestorBiblioteca gestor = new GestorBiblioteca();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Datos de ejemplo para pruebas
        cargarDatosEjemplo();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    gestionarLibros();
                    break;
                case 2:
                    gestionarUsuarios();
                    break;
                case 3:
                    gestionarPrestamos();
                    break;
                case 4:
                    consultasYEstadisticas();
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("------ SISTEMA DE GESTIÓN DE BIBLIOTECA ------");
        System.out.println("1. Gestionar libros");
        System.out.println("2. Gestionar usuarios");
        System.out.println("3. Gestionar préstamos");
        System.out.println("4. Consultas y estadísticas");
        System.out.println("0. Salir");
    }

    private static void gestionarLibros() {
        int opcion;
        do {
            System.out.println("------ GESTIÓN DE LIBROS ------");
            System.out.println("1. Añadir libro");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Buscar libro por título");
            System.out.println("4. Buscar libro por autor");
            System.out.println("5. Buscar libro por categoría");
            System.out.println("6. Eliminar libro");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1:
                    añadirLibro();
                    break;
                case 2:
                    biblioteca.llistarLlibres();
                    break;
                case 3:
                    buscarLibroPorTitulo();
                    break;
                case 4:
                    buscarLibroPorAutor();
                    break;
                case 5:
                    buscarLibroPorCategoria();
                    break;
                case 6:
                    eliminarLibro();
                    break;
            }
        } while (opcion != 0);
    }

    private static void añadirLibro() {
        System.out.println("------ AÑADIR NUEVO LIBRO ------");
        String titol = leerTexto("Título: ");
        String autor = leerTexto("Autor: ");
        System.out.println("Categorías disponibles: Novel·la, Història, Ciència, General");
        String categoria = leerTexto("Categoría (dejar vacío para 'General'): ");

        if (categoria.trim().isEmpty()) {
            categoria = "General";
        }

        Llibre llibre = new Llibre(titol, autor, categoria);
        biblioteca.afegirLlibre(llibre);
        System.out.println(" Libro añadido correctamente.");
    }

    private static void buscarLibroPorTitulo() {
        String titol = leerTexto("Título del libro a buscar: ");
        Llibre llibre = biblioteca.buscarLlibre(titol);

        if (llibre != null) {
            System.out.println("Libro encontrado: " + llibre);
        } else {
            System.out.println("No se encontró el libro.");

            // Búsqueda sin acentos
            System.out.println("Intentando búsqueda sin acentos...");
            llibre = biblioteca.buscarLlibreSenseAccents(titol);
            if (llibre != null) {
                System.out.println("Encontradop por búsqueda sin acentos: " + llibre);
            } else {
                System.out.println("No se encontró el libro.");
            }
        }
    }

    private static void buscarLibroPorAutor() {
        String autor = leerTexto("Autor a buscar: ");
        List<Llibre> resultados = biblioteca.buscarLibroPorAutor(autor);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros de '" + autor + "'.");
        } else {
            System.out.println(" Libros de " + autor + ":");
            for (Llibre l : resultados) {
                System.out.println("  • " + l);
            }
        }
    }

    private static void buscarLibroPorCategoria() {
        String categoria = leerTexto("Categoría a buscar: ");
        List<Llibre> resultados = biblioteca.buscarLibroPorCategoria(categoria);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros de la categoría '" + categoria + "'.");
        } else {
            System.out.println(" Libros de categoría '" + categoria + "':");
            for (Llibre l : resultados) {
                System.out.println("  • " + l);
            }
        }
    }

    private static void eliminarLibro() {
        String titol = leerTexto("Título del libro a eliminar: ");
        if (biblioteca.buscarLibroPorTitulo(titol)) {
            System.out.println("Libro eliminado correctamente.");
        } else {
            System.out.println("No se encontró el libro.");
        }
    }
    

    private static void gestionarUsuarios() {
        int opcion;
        do {
            System.out.println(" === GESTIÓN DE USUARIOS ===");
            System.out.println("1. Añadir usuario");
            System.out.println("2. Listar todos los usuarios");
            System.out.println("3. Buscar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1:
                    añadirUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    buscarUsuario();
                    break;
                case 4:
                    eliminarUsuario();
                    break;
            }
        } while (opcion != 0);
    }

    private static void añadirUsuario() {
        String nom = leerTexto("Nombre del usuario: ");
        Usuari usuari = new Usuari(nom);
        usuaris.add(usuari);
        System.out.println("Usuario añadido correctamente.");
    }

    private static void listarUsuarios() {
        if (usuaris.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("------ LISTA DE USUARIOS ------");
            for (int i = 0; i < usuaris.size(); i++) {
                System.out.println((i + 1) + ". " + usuaris.get(i));
            }
        }
    }

    private static void buscarUsuario() {
        String nom = leerTexto("Nombre del usuario a buscar: ");
        Usuari usuari = encontrarUsuario(nom);

        if (usuari != null) {
            System.out.println("Usuario encontrado: " + usuari);
        } else {
            System.out.println("No se encontró el usuario.");
        }
    }

    private static void eliminarUsuario() {
        String nom = leerTexto("Nombre del usuario a eliminar: ");
        Usuari usuari = encontrarUsuario(nom);

        if (usuari != null) {
            if (usuari.getNumLlibresPrestats() > 0) {
                System.out.println("No se puede eliminar el usuario porque tiene libros pendientes de devolver.");
            } else {
                usuaris.remove(usuari);
                System.out.println("Usuario eliminado correctamente.");
            }
        } else {
            System.out.println("No se encontró el usuario.");
        }
    }

    private static void gestionarPrestamos() {
        int opcion;
        do {
            System.out.println("------ GESTIÓN DE PRÉSTAMOS ------");
            System.out.println("1. Prestar libro");
            System.out.println("2. Devolver libro");
            System.out.println("3. Ver libros disponibles");
            System.out.println("4. Ver préstamos activos");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1:
                    prestarLibro();
                    break;
                case 2:
                    devolverLibro();
                    break;
                case 3:
                    verLibrosDisponibles();
                    break;
                case 4:
                    verPrestamosActivos();
                    break;
            }
        } while (opcion != 0);
    }

    private static void prestarLibro() {
        String nomUsuari = leerTexto("Nombre del usuario: ");
        Usuari usuari = encontrarUsuario(nomUsuari);

        if (usuari == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        String titolLlibre = leerTexto("Título del libro: ");
        Llibre llibre = biblioteca.buscarLlibre(titolLlibre);

        if (llibre == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        gestor.prestarLlibre(usuari, llibre);
    }

    private static void devolverLibro() {
        String nomUsuari = leerTexto("Nombre del usuario: ");
        Usuari usuari = encontrarUsuario(nomUsuari);

        if (usuari == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        String titolLlibre = leerTexto("Título del libro a devolver: ");
        Llibre llibre = biblioteca.buscarLlibre(titolLlibre);

        if (llibre == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        gestor.retornarLlibre(usuari, llibre);
    }

    private static void verLibrosDisponibles() {
        List<Llibre> disponibles = biblioteca.getLlibresDisponibles();

        if (disponibles.isEmpty()) {
            System.out.println("No hay libros disponibles en este momento.");
        } else {
            System.out.println("LIBROS DISPONIBLES:");
            for (Llibre l : disponibles) {
                System.out.println("  • " + l);
            }
        }
    }

    private static void verPrestamosActivos() {
        System.out.println("PRÉSTAMOS ACTIVOS:");
        boolean hayActivos = false;

        for (Prestec p : gestor.getPrestecs()) {
            if (!p.isRetornat()) {
                System.out.println("  • " + p);
                hayActivos = true;
            }
        }

        if (!hayActivos) {
            System.out.println(" No hay préstamos activos.");
        }
    }

    private static void consultasYEstadisticas() {
        int opcion;
        do {
            System.out.println("------ CONSULTAS Y ESTADÍSTICAS ------");
            System.out.println("1. Historial de préstamos de un usuario");
            System.out.println("2. Ver estadísticas generales");
            System.out.println("3. Libros más populares");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1:
                    historialUsuario();
                    break;
                case 2:
                    gestor.generarEstadistiques();
                    break;
                case 3:
                    verLibrosMasPrestados();
                    break;
            }
        } while (opcion != 0);
    }

    private static void historialUsuario() {
        String nom = leerTexto("Nombre del usuario: ");
        Usuari usuari = encontrarUsuario(nom);

        if (usuari != null) {
            gestor.consultarHistorialUsuari(usuari);
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private static void verLibrosMasPrestados() {
        System.out.println("LIBROS MÁS PRESTADOS:");
        // Esta funcionalidad se puede implementar mejor con un mapa de conteo
        // Por ahora mostramos un mensaje informativo
        System.out.println("  Próximamente en la versión 2.0.");
    }

    // Métodos auxiliares
    private static Usuari encontrarUsuario(String nom) {
        for (Usuari u : usuaris) {
            if (u.getNom().equalsIgnoreCase(nom)) {
                return u;
            }
        }
        return null;
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, introduzca un número: ");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return numero;
    }

    private static void cargarDatosEjemplo() {
        // Libros de ejemplo
        Llibre llibre1 = new Llibre("1984", "George Orwell", "Novel·la");
        Llibre llibre2 = new Llibre("El petit princep", "Antoine de Saint-Exupéry", "Novel·la");
        Llibre llibre3 = new Llibre("Història de Espanya", "Juan Pérez", "Història");
        Llibre llibre4 = new Llibre("Física Cuántica", "Stephen Hawking", "Ciència");
        Llibre llibre5 = new Llibre("Cien años de soledad", "Gabriel García Márquez", "Novel·la");

        biblioteca.afegirLlibre(llibre1);
        biblioteca.afegirLlibre(llibre2);
        biblioteca.afegirLlibre(llibre3);
        biblioteca.afegirLlibre(llibre4);
        biblioteca.afegirLlibre(llibre5);

        // Usuarios de ejemplo
        usuaris.add(new Usuari("Carla"));
        usuaris.add(new Usuari("Marc"));
        usuaris.add(new Usuari("Laura"));
    }
}