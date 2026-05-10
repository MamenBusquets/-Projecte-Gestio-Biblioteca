import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una biblioteca, la cual contiene una colección de
 * libros.
 * 
 * @author Alejandro Henriquez / Enrique
 * @version 1.1
 */
public class Biblioteca {

    private List<Llibre> llibres;

    public Biblioteca() {
        this.llibres = new ArrayList<>();
    }

    public void afegirLlibre(Llibre llibre) {
        llibres.add(llibre);
    }

    /**
     * Elimina un libro de la biblioteca por su título.
     * 
     * @param titol Título del libro a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminarLlibrePerTitol(String titol) {
        Llibre llibre = buscarLlibre(titol);
        if (llibre != null) {
            return llibres.remove(llibre);
        }
        return false;
    }

    public Llibre buscarLlibre(String titol) {
        for (Llibre llibre : llibres) {
            if (llibre.getTitol().equalsIgnoreCase(titol)) {
                return llibre;
            }
        }
        return null;
    }

    public boolean buscarLibroPorTitulo(String titol) {
        return buscarLlibre(titol) != null;
    }

    public boolean eliminarLlibre(String titol) {
        Llibre llibre = buscarLlibre(titol);
        if (llibre != null) {
            return llibres.remove(llibre);
        }
        return false;
    }

    /**
     * Busca libros por autor.
     * 
     * @param autor Autor a buscar
     * @return Lista de libros del autor
     */
    public List<Llibre> buscarLibroPorAutor(String autor) {
        List<Llibre> resultats = new ArrayList<>();
        for (Llibre llibre : llibres) {
            if (llibre.getAutor().equalsIgnoreCase(autor)) {
                resultats.add(llibre);
            }
        }
        return resultats;
    }

    /**
     * Busca libros por categoría.
     * 
     * @param categoria Categoría a buscar
     * @return Lista de libros de la categoría
     */
    public List<Llibre> buscarLibroPorCategoria(String categoria) {
        List<Llibre> resultats = new ArrayList<>();
        for (Llibre llibre : llibres) {
            if (llibre.getCategoria().equalsIgnoreCase(categoria)) {
                resultats.add(llibre);
            }
        }
        return resultats;
    }

    public Llibre buscarLlibreSenseAccents(String titol) {
        String titolNormalitzat = treureAccents(titol);
        for (Llibre llibre : llibres) {
            if (treureAccents(llibre.getTitol()).equalsIgnoreCase(titolNormalitzat)) {
                return llibre;
            }
        }
        return null;
    }

    public List<Llibre> getLlibres() {
        return llibres;
    }

    /**
     * Muestra todos los libros por pantalla.
     */
    public void llistarLlibres() {
        if (llibres.isEmpty()) {
            System.out.println("No hay libros en la biblioteca.");
        } else {
            System.out.println("\n=== LISTA DE LIBROS ===");
            for (int i = 0; i < llibres.size(); i++) {
                System.out.println((i + 1) + ". " + llibres.get(i));
            }
        }
    }

    /**
     * Obtiene los libros disponibles (no prestados).
     * 
     * @return Lista de libros disponibles
     */
    public List<Llibre> getLlibresDisponibles() {
        List<Llibre> disponibles = new ArrayList<>();
        for (Llibre llibre : llibres) {
            if (!llibre.esPrestat()) {
                disponibles.add(llibre);
            }
        }
        return disponibles;
    }

    private String treureAccents(String text) {
        String normalitzat = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalitzat.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}