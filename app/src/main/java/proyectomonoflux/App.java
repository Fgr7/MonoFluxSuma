package proyectomonoflux;

import reactor.core.publisher.Flux;

public class App {
    public static void main(String[] args) {
        System.out.println("Creando un Flux que emite números del 1 al 10");
        System.out.println("Filtrando números pares y multiplicándolos por 2:");
        System.out.println("----------------------------------------");
        
        // Crear un Flux que emite números del 1 al 10
        Flux.range(1, 10)
            // Filtrar solo los números pares
            .filter(numero -> numero % 2 == 0)
            // Multiplicar cada número par por 2
            .map(numero -> numero * 2)
            // Imprimir cada resultado
            .doOnNext(resultado -> System.out.println("Resultado: " + resultado))
            // Suscribirse para ejecutar la secuencia
            .subscribe();
            
        System.out.println("----------------------------------------");
        System.out.println("Proceso completado!");
    }
}
