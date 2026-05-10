import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene modificaciones y mejoras para la gestión de biblioteca.
 * Incluye sistema de multas, notificaciones y control de límites.
 * 
 * @author Enrique
 * @version 1.0
 */
public class ModificacionDos {
    private static final double MULTA_POR_DIA = 0.50; // 0.50€ por día de retraso
    private static List<Multa> multasPendientes = new ArrayList<>();

    /**
     * Clase interna para representar una multa.
     */
    public static class Multa {
        private Usuari usuari;
        private double importe;
        private LocalDate fecha;
        private boolean pagada;

        public Multa(Usuari usuari, double importe, LocalDate fecha) {
            this.usuari = usuari;
            this.importe = importe;
            this.fecha = fecha;
            this.pagada = false;
        }

        public Usuari getUsuari() {
            return usuari;
        }

        public double getImporte() {
            return importe;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public boolean isPagada() {
            return pagada;
        }

        public void pagar() {
            this.pagada = true;
        }

        @Override
        public String toString() {
            return "Multa: " + importe + "€ | Fecha: " + fecha +
                    (pagada ? " [PAGADA]" : " [PENDIENTE]");
        }
    }

    /**
     * Calcula y registra multa por retraso en la devolución.
     * 
     * @param prestec Préstamo vencido
     * @return Importe de la multa
     */
    public static double calcularMulta(Prestec prestec) {
        if (!prestec.estaVencido()) {
            return 0;
        }

        long diasRetraso = prestec.getDiesRetras();
        double importe = diasRetraso * MULTA_POR_DIA;

        Multa multa = new Multa(prestec.getUsuari(), importe, LocalDate.now());
        multasPendientes.add(multa);

        System.out.println(" Multa generada: " + importe + "€ por " +
                diasRetraso + " días de retraso.");
        return importe;
    }

    /**
     * Verifica si un usuario tiene multas pendientes.
     * 
     * @param usuari Usuario a verificar
     * @return true si tiene multas pendientes
     */
    public static boolean tieneMultasPendientes(Usuari usuari) {
        return multasPendientes.stream()
                .anyMatch(m -> m.getUsuari().equals(usuari) && !m.isPagada());
    }

    /**
     * Muestra todas las multas de un usuario.
     * 
     * @param usuari Usuario a consultar
     */
    public static void mostrarMultasUsuario(Usuari usuari) {
        System.out.println("MULTAS DE " + usuari.getNom().toUpperCase());
        boolean encontradas = false;

        for (Multa m : multasPendientes) {
            if (m.getUsuari().equals(usuari)) {
                System.out.println("  • " + m);
                encontradas = true;
            }
        }

        if (!encontradas) {
            System.out.println("No tiene multas pendientes.");
        }
    }

    /**
     * Paga todas las multas pendientes de un usuario.
     * 
     * @param usuari Usuario que paga
     * @return Importe total pagado
     */
    public static double pagarMultas(Usuari usuari) {
        double total = 0;

        for (Multa m : multasPendientes) {
            if (m.getUsuari().equals(usuari) && !m.isPagada()) {
                m.pagar();
                total += m.getImporte();
            }
        }

        if (total > 0) {
            System.out.println("Se han pagado " + total + "€ en multas.");
        } else {
            System.out.println("No hay multas pendientes para este usuario.");
        }

        return total;
    }

    /**
     * Envía notificación de recordatorio para préstamos próximos a vencer.
     * 
     * @param prestecs Lista de préstamos a verificar
     */
    public static void enviarRecordatorios(List<Prestec> prestecs) {
        LocalDate hoy = LocalDate.now();
        int diasRecordatorio = 2; // Recordatorio 2 días antes

        System.out.println("ECORDATORIOS DE PRÉSTAMOS:");
        boolean enviados = false;

        for (Prestec p : prestecs) {
            if (!p.isRetornat()) {
                long diasRestantes = p.getDataRetorn().toEpochDay() - hoy.toEpochDay();

                if (diasRestantes == diasRecordatorio) {
                    System.out.println("  • Recordatorio para " + p.getUsuari().getNom() +
                            ": El libro '" + p.getLlibre().getTitol() +
                            "' vence en " + diasRestantes + " días.");
                    enviados = true;
                } else if (diasRestantes == 0) {
                    System.out.println("  • URGENTE: " + p.getUsuari().getNom() +
                            " debe devolver '" + p.getLlibre().getTitol() +
                            "' HOY.");
                    enviados = true;
                }
            }
        }

        if (!enviados) {
            System.out.println("  No hay recordatorios pendientes.");
        }
    }

    /**
     * Controla el límite de libros por categoría que puede tomar un usuario.
     * 
     * @param usuari       Usuario a verificar
     * @param categoria    Categoría del libro
     * @param limiteMaximo Límite máximo de libros de esa categoría
     * @return true si puede tomar el libro
     */
    public static boolean controlLimitePorCategoria(Usuari usuari, String categoria, int limiteMaximo) {
        long librosEnCategoria = usuari.getLlibresPrestats().stream()
                .filter(l -> l.getCategoria().equalsIgnoreCase(categoria))
                .count();

        if (librosEnCategoria >= limiteMaximo) {
            System.out.println("El usuario ya tiene " + librosEnCategoria +
                    " libros de la categoría '" + categoria +
                    "' (límite: " + limiteMaximo + ").");
            return false;
        }

        return true;
    }

    /**
     * Genera un informe completo de la biblioteca.
     * 
     * @param biblioteca Biblioteca
     * @param gestor     Gestor de préstamos
     * @param usuarios   Lista de usuarios
     */
    public static void generarInformeCompleto(Biblioteca biblioteca, GestorBiblioteca gestor, List<Usuari> usuarios) {
        System.out.println("------ INFORME COMPLETO DE LA BIBLIOTECA ------");
        System.out.println("Fecha: " + LocalDate.now());

        // Resumen de libros
        int totalLibros = biblioteca.getLlibres().size();
        int disponibles = biblioteca.getLlibres().size();
        int prestados = totalLibros - disponibles;

        System.out.println("RESUMEN DE LIBROS:");
        System.out.println("  Total: " + totalLibros);
        System.out.println("  Disponibles: " + disponibles);
        System.out.println("  Prestados: " + prestados);

        // Resumen de usuarios
        System.out.println("RESUMEN DE USUARIOS:");
        System.out.println("  Total usuarios: " + usuarios.size());

        int totalPrestamosActivos = 0;
        for (Usuari u : usuarios) {
            totalPrestamosActivos += u.getNumLlibresPrestats();
        }
        System.out.println("  Préstamos activos: " + totalPrestamosActivos);

        // Multas totales pendientes
        double totalMultas = multasPendientes.stream()
                .filter(m -> !m.isPagada())
                .mapToDouble(Multa::getImporte)
                .sum();

        System.out.println("MULTAS:");
        System.out.println("  Total pendiente: " + totalMultas + "€");

        // Llamar a estadísticas del gestor
        gestor.generarEstadistiques();
    }
}