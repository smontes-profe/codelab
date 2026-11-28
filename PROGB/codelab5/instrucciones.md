# Ranking con SQLite

El ranking se guarda en `ranking.db`, un archivo SQLite local. No hace falta
instalar ni arrancar un servidor de base de datos. Para conectarse, el juego
necesita el controlador SQLite JDBC:

1. Descarga [SQLite JDBC 3.54.1.0](https://repo.maven.apache.org/maven2/org/xerial/sqlite-jdbc/3.54.1.0/sqlite-jdbc-3.54.1.0.jar).
2. Copia el archivo JAR en la carpeta `lib` de `codelab5`.
3. Abre `codelab5` en VS Code con las extensiones de Java instaladas. El JAR
   debería aparecer en **Java Projects > Referenced Libraries**; si no aparece,
   usa el botón **+** de esa sección para seleccionarlo.

No es necesario instalar Maven para utilizar el JAR de esta forma.

Para iniciar el juego, ejecuta `Main.java` desde VS Code. El archivo `ranking.db`
se crea automáticamente en el directorio de trabajo desde el que se inicia el
juego: al abrir `codelab5` en VS Code, normalmente será esa carpeta. Mantén ese
archivo para conservar los resultados; cada copia tiene su propio ranking.

Si empaquetas el juego, distribuye también el JAR de SQLite y asegúrate de
ejecutarlo desde una carpeta con permisos de escritura para guardar `ranking.db`.

El código se organiza en tres paquetes dentro de `src`:

- `modelo`: personajes, combates y datos del ranking.
- `controlador`: coordina las acciones de la partida y el acceso al ranking.
- `vista`: muestra la ventana y recoge las decisiones del jugador.
