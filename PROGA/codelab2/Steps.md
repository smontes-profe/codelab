#   STEPS


## Notas:
- Vistazo rápido a VS Code
    - Warnings
    - Errores
    - Plugins
    - FindAllReferences
    - Mostrar la documentación de una palabra reservada
    
- Revisar la tareas resueltas (en la rama develop)
- Todo está comentado.

### Ejemplo de try catch

```java

try {
        // Código que podría dar error
        System.out.println("Introduce tu edad: ");
        int edad = sc.nextInt();
        System.out.println("Tienes " + edad + " años.");
    } catch (Exception e) {
        // Código que se ejecuta si hay error
        System.out.println("Error: Debes introducir un número válido.");
        sc.next(); // Limpiar el scanner
    }
```
### Ejemplo de hasNextInt

```java
if (sc.hasNextInt()) {
        int edad = sc.nextInt();
        System.out.println("Tienes " + edad + " años.");
    } else {
        System.out.println("Error: Debes introducir un número válido.");
        sc.next(); // Limpiar el scanner
    }
```

### Sintaxis del switch

```java
switch (opcion) {
    case 1: System.out.println("La opcion facil");
    case 2: ........
}
```

### Sintaxis del do while

```java
do {
        // Código que se ejecuta al menos una vez
        System.out.println("Introduce tu edad: ");
        int edad = sc.nextInt();
        System.out.println("Tienes " + edad + " años.");
    } while (edad < 0 || edad > 120);
```



