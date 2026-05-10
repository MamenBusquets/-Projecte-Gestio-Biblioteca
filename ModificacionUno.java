import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que contiene modificaciones y mejoras para la clase Biblioteca.
 * Incluye métodos para ordenar libros y realizar búsquedas avanzadas.
 * 
 * @author Enrique
 * @version 1.0
 */
public class ModificacionUno {

    /**
     * Ordena los libros de la biblioteca por título alfabéticamente.
     * 
     * @param biblioteca Biblioteca a ordenar
     */
    public static void ordenarLibrosPorTitulo(Biblioteca biblioteca) {
        List<Llibre> libros = biblioteca.getLlibres();
        libros.sort(Comparator.comparing(Llibre::getTitol, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Libros ordenados por título.");
    }

    /**
     * Ordena los libros de la biblioteca por autor.
     * 
     * @param biblioteca Biblioteca a ordenar
     */
    public static void ordenarLibrosPorAutor(Biblioteca biblioteca) {
        List<Llibre> libros = biblioteca.getLlibres();
        libros.sort(Comparator.comparing(Llibre::getAutor, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Libros ordenados por autor.");
    }

    /**
     * Obtiene los libros más prestados basándose en el historial del gestor.
     * 
     * @param gestor Gestor de biblioteca con el historial de préstamos
     * @param limite Número máximo de libros a mostrar
     * @return Lista de los libros más prestados
     */
    public static List<Llibre> getLibrosMasPrestados(GestorBiblioteca gestor, int limite) {
        // Crear un mapa con el conteo de préstamos por libro
        java.util.Map<Llibre, Long> conteo = gestor.getPrestecs().stream()
                .collect(Collectors.groupingBy(
                        prestec -> prestec.getLlibre(),
                        Collectors.counting()));

        return conteo.entrySet().stream()
                .sorted(java.util.Map.Entry.<Llibre, Long>comparingByValue().reversed())
                .limit(limite)
                .map(java.util.Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Verifica la disponibilidad de un libro en la biblioteca.
     * 
     * @param biblioteca Biblioteca donde buscar
     * @param titulo     Título del libro
     * @return Mensaje con la disponibilidad
     */
    public static String verificarDisponibilidad(Biblioteca biblioteca, String titulo) {
        Llibre libro = biblioteca.buscarLlibre(titulo);

        if (libro == null) {
            return "El libro '" + titulo + "' no existe en la biblioteca.";
        }

        if (libro.esPrestat()) {
            return "El libro '" + titulo + "' está actualmente PRESTADO.";
        } else {
            return "El libro '" + titulo + "' está DISPONIBLE para préstamo.";
        }
    }

    /**
     * Busca libros que contengan una palabra clave en el título o autor.
     * 
     * @param biblioteca Biblioteca donde buscar
     * @param keyword    Palabra clave a buscar
     * @return Lista de libros que coinciden
     */
    public static List<Llibre> busquedaAvanzada(Biblioteca biblioteca, String keyword) {
        String keywordLower = keyword.toLowerCase();
        List<Llibre> resultados = new ArrayList<>();

        for (Llibre libro : biblioteca.getLlibres()) {
            if (libro.getTitol().toLowerCase().contains(keywordLower) ||
                    libro.getAutor().toLowerCase().contains(keywordLower)) {
                resultados.add(libro);
            }
        }

        return resultados;
    }
}