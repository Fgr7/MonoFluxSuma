package proyectomonoflux;

import reactor.core.publisher.Mono;

public class MonoSimple {
    public static void main(String[] args) {
        System.out.println("Ejemplo simple de Mono con manejo de errores");
        System.out.println("===========================================");
        
        // Caso 1: División exitosa
        System.out.println("\n1. División exitosa (8 / 0):");
        dividir(8, 0)
            .subscribe(resultado -> System.out.println("Resultado: " + resultado));
        
        // Caso 2: División por cero con manejo de error
        System.out.println("\n2. División por cero (8 / 0):");
        dividir(8, 0)
            .subscribe(resultado -> System.out.println("Resultado: " + resultado));
        
        System.out.println("\nProceso completado!");
    }
    
    /**
     * Método que simula una división que puede fallar
     * Usa onErrorResume para devolver -1 en caso de error
     */
    private static Mono<Integer> dividir(int dividendo, int divisor) {
        return Mono.fromSupplier(() -> {
            if (divisor == 0) {
                throw new ArithmeticException("División por cero no permitida");
            }
            return dividendo / divisor;
        })
        .onErrorResume(error -> {
            System.out.println("Error: " + error.getMessage());
            System.out.println("Devolviendo valor por defecto: -1");
            return Mono.just(-1);
        });
    }
}
