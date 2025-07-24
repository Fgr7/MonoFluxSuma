/*
 * Ejemplo de estrategias de Back Pressure
 */
package proyectomonoflux.backpressure;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import java.time.Duration;

public class BackPressureStrategies {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Estrategias de Back Pressure");
        System.out.println("============================");
        
        // Ejemplo 1: DROP - Descartar elementos cuando hay sobrecarga
        System.out.println("\n1. Estrategia DROP:");
        ejemploDrop();
        
        Thread.sleep(2000);
        
        // Ejemplo 2: LATEST - Mantener solo el último elemento
        System.out.println("\n2. Estrategia LATEST:");
        ejemploLatest();
        
        Thread.sleep(2000);
        
        // Ejemplo 3: BUFFER - Almacenar en buffer (puede causar OutOfMemory)
        System.out.println("\n3. Estrategia BUFFER:");
        ejemploBuffer();
        
        Thread.sleep(2000);
        
        System.out.println("\nProceso completado!");
    }
    
    /**
     * Estrategia DROP: descarta elementos cuando el consumidor no puede procesarlos
     */
    private static void ejemploDrop() {
        Flux.create(sink -> {
            // Simular un productor rápido
            for (int i = 1; i <= 10; i++) {
                System.out.println("Enviando: " + i);
                sink.next(i);
                try {
                    Thread.sleep(10); // Productor rápido
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            sink.complete();
        }, FluxSink.OverflowStrategy.DROP)
        .onBackpressureDrop(dropped -> System.out.println("DROPPED: " + dropped))
        .map(item -> {
            // Simular procesamiento lento
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Procesado: " + item;
        })
        .subscribe(result -> System.out.println("Recibido: " + result));
    }
    
    /**
     * Estrategia LATEST: mantiene solo el elemento más reciente
     */
    private static void ejemploLatest() {
        Flux.range(1, 10)
            .delayElements(Duration.ofMillis(10))
            .doOnNext(item -> System.out.println("Producido: " + item))
            .onBackpressureLatest()
            .map(item -> {
                try {
                    Thread.sleep(150); // Consumidor lento
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Procesado: " + item;
            })
            .subscribe(result -> System.out.println("Recibido: " + result));
    }
    
    /**
     * Estrategia BUFFER: almacena elementos en un buffer
     */
    private static void ejemploBuffer() {
        Flux.range(1, 8)
            .delayElements(Duration.ofMillis(10))
            .doOnNext(item -> System.out.println("Producido: " + item))
            .onBackpressureBuffer(3) // Buffer de máximo 3 elementos
            .map(item -> {
                try {
                    Thread.sleep(100); // Consumidor lento
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Procesado: " + item;
            })
            .subscribe(
                result -> System.out.println("Recibido: " + result),
                error -> System.err.println("Error: " + error.getMessage())
            );
    }
}
