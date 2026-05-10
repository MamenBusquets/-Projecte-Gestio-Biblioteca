/**
 * Classe que representa un llibre a la biblioteca.
 * Conté informació bàsica com: títol, autor, disponibilitat i categoria.
 * 
 * @author Mamen
 * @version 1.0
 */
public class Llibre {
    private String titol;
    private String autor;
    private boolean prestat;
    private String categoria;

    /**
     * Constructor per crear un nou llibre sense categoria
     * 
     * @param titol Títol del llibre
     * @param autor Autor del llibre
     */
    public Llibre(String titol, String autor) {
        this.titol = titol;
        this.autor = autor;
        this.prestat = false;
        this.categoria = "General";
    }

    /**
     * Constructor amb la categoria.
     * 
     * @param titol     Títol del llibre
     * @param autor     Autor del llibre
     * @param categoria Categoria del llibre (Novel·la, Història, Ciència, etc.)
     */
    public Llibre(String titol, String autor, String categoria) {
        this.titol = titol;
        this.autor = autor;
        this.prestat = false;
        this.categoria = categoria;
    }

    /**
     * Métode ToString
     */
    @Override
    public String toString() {
        return titol + " de " + autor + " [" + categoria + "]" +
                (prestat ? " (En préstec)" : " (Disponible)");
    }

    /**
     * Métode que dicta si el llibre s'ha prestat o no
     */
    public void prestar() {
        this.prestat = true;
    }

    /**
     * Métode que dicta si el llibre s'ha retornat o no
     */
    public void retornar() {
        this.prestat = false;
    }

    // Getters y Setters
    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean esPrestat() {
        return prestat;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
