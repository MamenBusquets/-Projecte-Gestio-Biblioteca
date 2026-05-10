import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map; // Para transformar elemetos, seria parecido a un foreache pero con menos lineas de código
import java.util.HashMap; // Para almacernar pares clave - valor. Igual que PHP , para java hay que "llamar" a esta clase
import java.util.stream.Collectors; 

/**
 * Clase que gestiona los préstamos de la biblioteca.
 * Controla el stock de libros y gestiona el historial de préstamos.
 * 
 * @author Enrique
 * @version 1.0
 */
public class GestorBiblioteca {
    private List<Prestec> prestecs;
    private Map<String, Integer> comptadorPrestecsPerCategoria;
    
    public GestorBiblioteca() {
        this.prestecs = new ArrayList<>();
        this.comptadorPrestecsPerCategoria = new HashMap<>();
    }
    
    /**
     * Realiza el préstamo de un libro a un usuario.
     * @param usuari Usuario que quiere tomar el libro
     * @param llibre Libro a prestar
     * @return true si el préstamo fue exitoso, false en caso contrario
     */
    public boolean prestarLlibre(Usuari usuari, Llibre llibre) {
        // Verificar si el libro está disponible
        if (llibre.esPrestat()) {
            System.out.println("Lo sentimos, el libro '" + llibre.getTitol() + "' ya está prestado.");
            return false;
        }
        
        // Verificar si el usuario puede tomar más libros
        if (!usuari.potAgafarMesLlibres()) {
            System.out.println("ERROR: El usuario '" + usuari.getNom() + 
                             "' ya tiene el máximo de libros prestados (" + 
                             usuari.getNumLlibresPrestats() + "/3).");
            return false;
        }
        
        // Realizar el préstamo
        llibre.prestar();
        Prestec prestec = new Prestec(usuari, llibre, LocalDate.now());
        prestecs.add(prestec);
        usuari.afegirLlibre(llibre);
        
        // Actualizar contador de categoría
        String categoria = llibre.getCategoria();
        comptadorPrestecsPerCategoria.put(categoria, 
            comptadorPrestecsPerCategoria.getOrDefault(categoria, 0) + 1);
        
        System.out.println(usuari.getNom() + " ha tomado el libro: " + llibre.getTitol());
        System.out.println("  Fecha de retorno: " + prestec.getDataRetorn());
        return true;
    }
    
    /**
     * Realiza la devolución de un libro.
     * @param usuari Usuario que devuelve el libro
     * @param llibre Libro a devolver
     * @return true si la devolución fue exitosa, false en caso contrario
     */
    public boolean retornarLlibre(Usuari usuari, Llibre llibre) {
        // Verificar que el libro está prestado
        if (!llibre.esPrestat()) {
            System.out.println("El libro '" + llibre.getTitol() + "' no está prestado.");
            return false;
        }
        
        // Buscar el préstamo activo
        for (Prestec prestec : prestecs) {
            if (prestec.getLlibre().equals(llibre) && 
                prestec.getUsuari().equals(usuari) && 
                !prestec.isRetornat()) {
                
                prestec.marcarRetornat();
                llibre.retornar();
                usuari.retornarLlibre(llibre);
                
                if (prestec.estaVencido()) {
                    System.out.println("AVISO: Devolución con retraso de " + 
                                     prestec.getDiesRetras() + " días.");
                } else {
                    System.out.println(usuari.getNom() + " ha devuelto el libro: " + 
                                     llibre.getTitol());
                }
                return true;
            }
        }
        
        System.out.println("No se encontró un préstamo activo para este usuario y libro.");
        return false;
    }
    
    /**
     * Consulta el historial de préstamos de un usuario.
     * @param usuari Usuario del que se quiere consultar el historial
     */
    public void consultarHistorialUsuari(Usuari usuari) {
        List<Prestec> prestecsUsuari = prestecs.stream()
            .filter(p -> p.getUsuari().equals(usuari)) // p == parametro temporal, revisar en la pregunta numero 32,para profundizar
            .collect(Collectors.toList());
        
        if (prestecsUsuari.isEmpty()) {
            System.out.println(usuari.getNom() + " no tiene historial de préstamos.");
        } else {
            System.out.println("HISTORIAL DE PRÉSTAMOS DE " + usuari.getNom().toUpperCase());
            for (Prestec p : prestecsUsuari) {
                System.out.println(p);
            }
        }
    }
    
    /**
     * Genera estadísticas de la biblioteca.
     */
    public void generarEstadistiques() {
        System.out.println("-------- ESTADÍSTICAS DE LA BIBLIOTECA --------");
        
        // Total de préstamos
        System.out.println("Total de préstamos realizados: " + prestecs.size());
        
        // Préstamos activos
        long actius = prestecs.stream().filter(p -> !p.isRetornat()).count();
        System.out.println("Préstamos activos: " + actius);
        
        // Préstamos vencidos
        long vencidos = prestecs.stream().filter(Prestec::estaVencido).count();
        System.out.println("Préstamos vencidos: " + vencidos);
        
        // Préstamos por categoría
        System.out.println("PRÉSTAMOS POR CATEGORÍA:");
        if (comptadorPrestecsPerCategoria.isEmpty()) {
            System.out.println(" Sin datos aún.");
        } else {
            comptadorPrestecsPerCategoria.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.println("  • " + e.getKey() + ": " + e.getValue() + " préstamos"));
        }
        
        // Usuarios más activos
        Map<Usuari, Long> prestecsPerUsuari = prestecs.stream()
            .collect(Collectors.groupingBy(Prestec::getUsuari, Collectors.counting()));
        
        System.out.println("USUARIOS MÁS ACTIVOS:");
        if (prestecsPerUsuari.isEmpty()) {
            System.out.println("  Sin datos aún.");
        } else {
            prestecsPerUsuari.entrySet().stream()
                .sorted(Map.Entry.<Usuari, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.println("  • " + e.getKey().getNom() + ": " + e.getValue() + " préstamos"));
        }
    }
    
    /**
     * Obtiene todos los préstamos.
     * @return Lista de préstamos
     */
    public List<Prestec> getPrestecs() {
        return prestecs;
    }
}