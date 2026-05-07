/**
 * STUB temporal de la clase Llibre.
 * <p>
 * Esta clase es solo una versión provisional para que la clase Biblioteca
 * pueda compilar mientras el compañero Enrique implementa la versión completa.
 * Una vez Enrique haga merge de su trabajo a la rama main, esta versión
 * será reemplazada por la suya.
 * </p>
 *
 * @author Enrique (versión final), Alejandro (stub temporal)
 * @version 0.1 (provisional)
 */
public class Llibre {

    /** Título del libro. */
    private String titol;

    /** Autor del libro. */
    private String autor;

    /** Indica si el libro está prestado o no. */
    private boolean prestat;

    /**
     * Constructor que crea un libro con título y autor.
     *
     * @param titol título del libro.
     * @param autor autor del libro.
     */
    public Llibre(String titol, String autor) {
        this.titol = titol;
        this.autor = autor;
        this.prestat = false;
    }

    /**
     * Devuelve el título del libro.
     *
     * @return el título.
     */
    public String getTitol() {
        return titol;
    }

    /**
     * Devuelve el autor del libro.
     *
     * @return el autor.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Indica si el libro está prestado.
     *
     * @return {@code true} si está prestado, {@code false} si no.
     */
    public boolean esPrestat() {
        return prestat;
    }

    /**
     * Marca el libro como prestado.
     */
    public void prestar() {
        this.prestat = true;
    }

    /**
     * Marca el libro como devuelto.
     */
    public void retornar() {
        this.prestat = false;
    }
}