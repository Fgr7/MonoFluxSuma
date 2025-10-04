# 📌 MonoFluxSuma

Este proyecto demuestra el uso de Reactor con Flux y Mono para trabajar con programación reactiva en Java.

Incluye tres ejercicios:

Flux con filter y map → filtrar números pares y multiplicarlos por 2.

Flux con reduce → calcular la suma de números del 1 al 10.

Mono con onErrorResume → manejar errores en una operación de división.

# 🔹 Punto 1: Flux con filter y map
Explicación

Flux.range(1, 10) → genera los números del 1 al 10.

.filter(numero -> numero % 2 == 0) → se quedan solo los números pares.

.map(numero -> numero * 2) → multiplica cada número par por 2.

.doOnNext(...) → imprime cada resultado.

.subscribe() → ejecuta la secuencia.

Salida esperada
Creando un Flux que emite números del 1 al 10
Filtrando números pares y multiplicándolos por 2:
----------------------------------------
Resultado: 4
Resultado: 8
Resultado: 12
Resultado: 16
Resultado: 20
----------------------------------------
Proceso completado!

# 🔹 Punto 2: Flux con reduce
Explicación

Flux.range(1, 10) → genera los números del 1 al 10.

.doOnNext(...) → imprime cada número emitido.

.reduce(0, (acumulador, numero) -> … ) → va sumando paso a paso:

Paso	Operación	Resultado
1	0 + 1	1
2	1 + 2	3
3	3 + 3	6
…	…	…
10	45 + 10	55

.subscribe(...) → imprime el resultado final.

Salida esperada
Creando un Flux que emite números del 1 al 10
Calculando la suma total con el operador reduce:
----------------------------------------
Emitiendo: 1
Acumulador: 0 + Número: 1 = 1
Emitiendo: 2
Acumulador: 1 + Número: 2 = 3
...
Emitiendo: 10
Acumulador: 45 + Número: 10 = 55
----------------------------------------
Suma total: 55
----------------------------------------
Proceso completado!

# 🔹 Punto 3: Mono con onErrorResume
Explicación

Este ejemplo muestra cómo Mono puede manejar errores de forma reactiva:

Mono.fromSupplier(...) → ejecuta la división.

Si el divisor es 0, lanza una excepción.

.onErrorResume(...) → captura el error y devuelve -1.

Casos:

División exitosa (8 / 2) → retorna 4.

División por cero (8 / 0) → captura el error y retorna -1.

Salida esperada
Ejemplo simple de Mono con manejo de errores
===========================================

1. División exitosa (8 / 2):
Resultado: 4

2. División por cero (8 / 0):
Error: División por cero no permitida
Devolviendo valor por defecto: -1
Resultado: -1

Proceso completado!

# PUNTO 4
Este ejercicio utiliza **Project Reactor** para crear un flujo de números del 1 al 10, aplicando transformaciones, manejo de errores y operaciones asíncronas.

## 🔎 Análisis paso a paso

1. **Generación inicial**  
   Se emiten los números del 1 al 10 con `Flux.range(1, 10)`.

2. **Transformación con `map`**  
   - Si el número es impar, pasa sin cambios.  
   - Si el número es par, se lanza una excepción: `RuntimeException("Número par no permitido")`.

3. **Manejo de errores (`onErrorResume`)**  
   Cada error es capturado y reemplazado con el valor `0`.  
   - Resultado: todos los pares se convierten en `0`.

4. **Filtrado (`filter`)**  
   - Se descartan los valores `0`.  
   - Se eliminan los números menores o iguales a 5.  
   - Permanecen únicamente los impares mayores que 5 → `7` y `9`.

5. **Transformación asíncrona (`flatMap`)**  
   Cada número restante se multiplica por 2 y se simula una operación asíncrona con `delayElements`.

6. **Suscripción (`subscribe`)**  
   - Se imprime cada número procesado.  
   - Se maneja cualquier error general.  
   - Al finalizar, se muestra el mensaje de `"Completado"`.

## ✅ Salida esperada

```text
Número procesado: 14
Número procesado: 18
Completado
