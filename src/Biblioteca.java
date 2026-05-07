import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una biblioteca, la cual contiene una colección de libros.
 * <p>
 * Permite añadir libros, buscarlos por título (con o sin acentos) y obtener
 * la lista completa de libros disponibles.
 * </p>
 *
 * @author Alejandro Henriquez
 * @version 1.0
 */
public class Biblioteca {

    /** Lista interna que almacena todos los libros de la biblioteca. */
    private List<Llibre> llibres;

    /**
     * Constructor por defecto. Inicializa la biblioteca sin ningún libro.
     */
    public Biblioteca() {
        this.llibres = new ArrayList<>();
    }

    /**
     * Añade un libro a la biblioteca.
     *
     * @param llibre el libro que se quiere añadir; no puede ser {@code null}.
     */
    public void afegirLlibre(Llibre llibre) {
        llibres.add(llibre);
    }

    /**
     * Busca un libro dentro de la biblioteca por su título.
     * <p>
     * La búsqueda ignora mayúsculas y minúsculas, pero sí tiene en cuenta los acentos.
     * </p>
     *
     * @param titol el título del libro a buscar.
     * @return el {@link Llibre} encontrado, o {@code null} si no existe.
     */
    public Llibre buscarLlibre(String titol) {
        for (Llibre llibre : llibres) {
            if (llibre.getTitol().equalsIgnoreCase(titol)) {
                return llibre;
            }
        }
        return null;
    }

    /**
     * Busca un libro dentro de la biblioteca por su título, ignorando acentos
     * y mayúsculas/minúsculas.
     *
     * @param titol el título del libro a buscar (puede contener o no acentos).
     * @return el {@link Llibre} encontrado, o {@code null} si no existe.
     */
    public Llibre buscarLlibreSenseAccents(String titol) {
        String titolNormalitzat = treureAccents(titol);
        for (Llibre llibre : llibres) {
            if (treureAccents(llibre.getTitol()).equalsIgnoreCase(titolNormalitzat)) {
                return llibre;
            }
        }
        return null;
    }

    /**
     * Devuelve la lista completa de libros de la biblioteca.
     *
     * @return lista de {@link Llibre} actualmente en la biblioteca.
     */
    public List<Llibre> getLlibres() {
        return llibres;
    }

    /**
     * Método auxiliar privado que elimina los acentos de una cadena de texto.
     * Por ejemplo: "Mañana" → "Manana", "café" → "cafe".
     *
     * @param text el texto del cual se quieren eliminar los acentos.
     * @return el texto sin caracteres acentuados.
     */
    private String treureAccents(String text) {
        String normalitzat = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalitzat.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}