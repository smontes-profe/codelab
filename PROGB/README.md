# Programación B — propuesta de code labs

Cinco proyectos Java independientes y ejecutables, uno por sesión de una hora. Cada sesión parte de la aventura de consola de Programación A y añade un paso hacia la exploración de un laberinto. El código de cada carpeta es autocontenido para poder abrirlo y ejecutarlo por separado.

| Sesión | Tema | Reto del juego |
| --- | --- | --- |
| **1. Preparar la expedición** | Repaso de POO | Retomar `Personaje` y hacer que cada objeto gestione su propio estado y comportamiento: mostrar ficha, recibir daño, curarse o atacar. Afianzar constructores, atributos y métodos sin volver a explicar desde cero la sintaxis de una clase. |
| **2. Crear tipos de aventurero** | Herencia | Añadir subclases como `Guerrero` y `Mago`. Usar `super(...)` para reutilizar la inicialización y dar a cada tipo una característica propia, manteniendo el número de clases bajo control. |
| **3. Resolver un encuentro con distintos personajes** | Sobreescritura y polimorfismo | Sobrescribir una acción como `atacar()` o `usarHabilidad()`. Invocar el mismo método mediante una referencia de tipo `Personaje` y observar cómo responde cada subtipo, evitando preguntar continuamente por el tipo con `if` o `switch`. |
| **4. Explorar el laberinto** | Arrays unidimensionales y multidimensionales | Usar un `Personaje[]` para un grupo de tamaño fijo y un `String[][]` o `char[][]` para un mapa pequeño. Recorrer el mapa, consultar posiciones y localizar una casilla. Mantenerlo acotado para que el foco sean los índices y los recorridos. |
| **5. Ampliar el grupo** | `ArrayList` | Sustituir el grupo fijo por una lista que permita reclutar y retirar personajes durante la partida. Practicar `add`, `remove`, `size` y los recorridos; los personajes de distintos subtipos pueden seguir actuando polimórficamente. Cerrar comparando arrays y listas dinámicas. |

## Estructura y ejecución

Cada carpeta contiene `src/Main.java`; las clases auxiliares están en el mismo archivo para conservar la estructura sencilla de los proyectos de Programación A.

```text
PROGB/
├── codelab1/src/Main.java
├── codelab2/src/Main.java
├── codelab3/src/Main.java
├── codelab4/src/Main.java
└── codelab5/src/Main.java
```

Desde cualquiera de las carpetas `codelabN`, compilar y ejecutar con:

```text
javac -d out src/Main.java
java -cp out Main
```

## Alcance y notas

- Cada sesión debería tener un resultado mínimo alcanzable en una hora; las funciones adicionales pueden quedar como ampliación.
- `ArrayList` ya aparece en los labs de Programación A, así que en B conviene profundizar en su uso con objetos y colecciones dinámicas, no presentarla necesariamente como un primer contacto.
- Esta propuesta es una selección práctica y no pretende cubrir todo Programación B. La guía también incluye otros contenidos, como interfaces, métodos abstractos y finales, iteradores, otras colecciones y bases de datos.
- El código se ha probado con Java 17.