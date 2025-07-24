/*
 * Ejemplo de Back Pressure en Project Reactor
 */
package proyectomonoflux.backpressure;

import reactor.core.publisher.Flux;
import java.time.Duration;

public class BackPressureExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Ejemplos de Back Pressure en Reactor");
        System.out.println("===================================");
        
        // Ejemplo 1: Productor rápido vs Consumidor lento
        System.out.println("\n1. Problema: Productor rápido vs Consumidor lento");
        ejemploProblemaBackPressure();
        
        Thread.sleep(3000); // Esperar a que termine
        
        // Ejemplo 2: Solución con Buffer
        System.out.println("\n2. Solución con Buffer");
        ejemploConBuffer();
        
        Thread.sleep(3000); // Esperar a que termine
        
        // Ejemplo 3: Solución con limitación de tasa
        System.out.println("\n3. Solución con limitación de tasa (sample)");
        ejemploConSample();
        
        Thread.sleep(5000); // Esperar a que termine
        
        System.out.println("\nProceso completado!");
    }
    
    /**
     * Demuestra el problema de back pressure
     */
    private static void ejemploProblemaBackPressure() {
        System.out.println("Produciendo elementos rápidamente...");
        
        Flux.range(1, 10)
            .delayElements(Duration.ofMillis(10)) // Productor rápido
            .doOnNext(item -> System.out.println("Producido: " + item))
            .map(item -> {
                // Simular procesamiento lento del consumidor
                try {
                    Thread.sleep(100); // Consumidor lento
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Procesado-" + item;
            })
            .subscribe(
                result -> System.out.println("Consumido: " + result),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Flujo completado")
            );
    }
    
    /**
     * Solución usando buffer para manejar back pressure
     */
    private static void ejemploConBuffer() {
        System.out.println("Usando buffer para manejar back pressure...");
        
        Flux.range(1, 20)
            .delayElements(Duration.ofMillis(10)) // Productor rápido
            .doOnNext(item -> System.out.println("Producido: " + item))
            .buffer(5) // Agrupar en lotes de 5
            .map(batch -> {
                // Procesar el lote completo
                System.out.println("Procesando lote: " + batch);
                try {
                    Thread.sleep(200); // Simular procesamiento del lote
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Lote procesado: " + batch.size() + " elementos";
            })
            .subscribe(
                result -> System.out.println("Resultado: " + result),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Flujo con buffer completado")
            );
    }
    
    /**
     * Solución usando sample para tomar muestras periódicas
     */
    private static void ejemploConSample() {
        System.out.println("Usando sample para tomar muestras periódicas...");
        
        Flux.range(1, 100)
            .delayElements(Duration.ofMillis(50)) // Productor continuo
            .doOnNext(item -> System.out.println("Producido: " + item))
            .sample(Duration.ofMillis(300)) // Tomar muestra cada 300ms
            .map(item -> "Muestra tomada: " + item)
            .take(10) // Tomar solo 10 muestras
            .subscribe(
                result -> System.out.println("Resultado: " + result),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Flujo con sample completado")
            );
    }
}
