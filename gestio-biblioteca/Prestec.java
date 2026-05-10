import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa el préstec d'un llibre a un usuari.
 * Gestiona la data del préstec i la data de devolució.
 * 
 * @author Mamen
 * @version 1.0
 */
public class Prestec {
    private Usuari usuari;
    private Llibre llibre;
    private LocalDate dataPrestec;
    private LocalDate dataRetorn;
    private boolean retornat;

    /**
     * Constructor per crear un nou préstec.
     * 
     * @param usuari      Usuari que agafa el llibre
     * @param llibre      llibre prestat
     * @param dataPrestec Data del préstec
     */
    public Prestec(Usuari usuari, Llibre llibre, LocalDate dataPrestec) {
        this.usuari = usuari;
        this.llibre = llibre;
        this.dataPrestec = dataPrestec;
        // Afegeix 2 setmanes a la data de retorn si no s'ha retornat el llibre a temps
        this.dataRetorn = dataPrestec.plusWeeks(2); 
        this.retornat = false;
    }

    /**
     * Métode ToString
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String estat = retornat ? "RETORNAT" : (estaVensut() ? "HA CADUCAT" : "PRESTAT"); // Ús dels operadors ternaris
                                                                                           // com a PHP
        return "Préstamo: " + usuari.getNom() + " → " + llibre.getTitol() +
                " | Data del préstec: " + dataPrestec.format(formatter) +
                " |Data de retorn: " + dataRetorn.format(formatter) +
                " | Estat: " + estat +
                (estaVensut() ? " (Retard: " + getDiesRetard() + " dies)" : "");
    }

    /**
     * Métode que marca el préstec com retornat.
     */
    public void marcarRetornat() {
        this.retornat = true;
    }

    /**
     * Verifica si el préstec ha caducat.
     * 
     * @return true si la data actual es posterior a la data de retorn.
     */
    public boolean estaVensut() {
        return LocalDate.now().isAfter(dataRetorn) && !retornat;
    }

    /**
     * Obté els dies de retard si el préstec ha caducat.
     * 
     * @return Número de dies de retard, 0 si no ha caducat
     */
    public long getDiesRetard() {
        if (!estaVensut())
            return 0;

        // Compta tots els dies des de 1/1/1970 fins ara i resta la diferència
        return LocalDate.now().toEpochDay() - dataRetorn.toEpochDay();
    }

    // Getters
    public Usuari getUsuari() {
        return usuari;
    }

    public Llibre getLlibre() {
        return llibre;
    }

    public LocalDate getDataPrestec() {
        return dataPrestec;
    }

    public LocalDate getDataRetorn() {
        return dataRetorn;
    }

    public boolean isRetornat() {
        return retornat;
    }

}
