package proyectomonoflux;

import reactor.core.publisher.Flux;

public class SumaFlux {
    public static void main(String[] args) {
        System.out.println("Creando un Flux que emite números del 1 al 10");
        System.out.println("Calculando la suma total con el operador reduce:");
        System.out.println("----------------------------------------");
        
        // Crear un Flux que emite números del 1 al 10
        Flux.range(1, 10)
            // Mostrar cada número que se emite
            .doOnNext(numero -> System.out.println("Emitiendo: " + numero))
            // Usar reduce para calcular la suma total
            .reduce(0, (acumulador, numero) -> {
                int suma = acumulador + numero;
                System.out.println("Acumulador: " + acumulador + " + Número: " + numero + " = " + suma);
                return suma;
            })
            // Suscribirse para obtener el resultado final
            .subscribe(resultado -> {
                System.out.println("----------------------------------------");
                System.out.println("Suma total: " + resultado);
                System.out.println("----------------------------------------");
            });
            
        System.out.println("Proceso completado!");
    }
}
