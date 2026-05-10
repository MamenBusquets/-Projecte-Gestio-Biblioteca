import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un usuario de la biblioteca.
 * Un usuario puede tomar prestados varios libros.
 * 
 * @author Enrique
 * @version 1.0
 */
public class Usuari {
    private String nom;
    private List<Llibre> llibresPrestats;
    private static final int MAX_LLIBRES = 3; // Máximo de libros que puede tomar un usuario

    /**
     * Constructor para crear un nuevo usuario.
     * 
     * @param nom Nombre del usuario
     */
    public Usuari(String nom) {
        this.nom = nom;
        this.llibresPrestats = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Llibre> getLlibresPrestats() {
        return llibresPrestats;
    }

    /**
     * Añade un libro a la lista de préstamos del usuario.
     * 
     * @param llibre Libro a añadir
     * @return true si se pudo añadir, false si ya alcanzó el máximo
     */
    public boolean afegirLlibre(Llibre llibre) {
        if (llibresPrestats.size() < MAX_LLIBRES) {
            llibresPrestats.add(llibre);
            return true;
        }
        return false;
    }

    /**
     * Retorna un libro de la lista de préstamos del usuario.
     * 
     * @param llibre Libro a retornar
     * @return true si el libro estaba en préstamo, false si no
     */
    public boolean retornarLlibre(Llibre llibre) {
        return llibresPrestats.remove(llibre);
    }

    /**
     * Verifica si el usuario puede tomar más libros.
     * 
     * @return true si puede tomar más, false si ya alcanzó el máximo
     */
    public boolean potAgafarMesLlibres() {
        return llibresPrestats.size() < MAX_LLIBRES;
    }

    /**
     * Obtiene el número de libros que el usuario tiene en préstamo.
     * 
     * @return Número de libros prestados
     */
    public int getNumLlibresPrestats() {
        return llibresPrestats.size();
    }

    @Override
    public String toString() {
        return nom + " (Llibres prestats: " + llibresPrestats.size() + "/" + MAX_LLIBRES + ")";
    }
}