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

## Empaquetar el juego en Windows

### Crear el ZIP automáticamente

Necesitas un JDK, PowerShell y el controlador SQLite de `lib`. Cierra el juego
antes de empaquetar para copiar el ranking sin cambios pendientes. Abre
PowerShell en `codelab5` y ejecuta:

```powershell
.\empaquetar.ps1
```

El script compila el código y crea `LeyendasDelCodigo.zip`, con el JAR del
juego, el controlador SQLite y `iniciar-juego.bat`. Si existe `ranking.db` en
`codelab5`, incluye una copia; si no, el juego creará uno vacío al ejecutarse.
Para no sobrescribir un ZIP anterior, mueve o renombra ese archivo antes de
empaquetar de nuevo.

Para jugar, descomprime el ZIP en una carpeta con permisos de escritura y abre
`iniciar-juego.bat`. El equipo de destino necesita Java compatible con la
versión usada para compilar. Cada carpeta descomprimida tiene su propio ranking.

### Crear el JAR manualmente

Para crear el JAR necesitas un JDK instalado. Abre PowerShell en la carpeta
`codelab5` y comprueba que existe `lib\sqlite-jdbc-3.54.1.0.jar`.

1. Crea una carpeta `build` **vacía** para los archivos compilados. Si ya
   existe, usa otra carpeta vacía y cambia su nombre en los comandos siguientes.

   ```powershell
   New-Item -ItemType Directory -Path build
   ```

2. Compila todas las clases, incluidas las de los paquetes:

   ```powershell
   $archivos = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
   javac -encoding UTF-8 -d build $archivos
   ```

3. Si la compilación no ha mostrado errores, crea el JAR:

   ```powershell
   jar cfe LeyendasDelCodigo.jar Main -C build .
   ```

Para distribuir el juego, copia `LeyendasDelCodigo.jar` y la carpeta `lib` con
el JAR de SQLite. La carpeta de destino necesita permisos de escritura para el
ranking. Desde esa carpeta, abre PowerShell y ejecuta:

```powershell
java -cp "LeyendasDelCodigo.jar;lib\*" Main
```

El JAR del juego no incluye el controlador SQLite ni Java: el equipo de destino
necesita el JAR de SQLite en `lib` y una versión de Java compatible con la usada
para compilar. No uses `java -jar` ni abras el JAR con doble clic para este
empaquetado: así no se añadiría el controlador al classpath.

El ranking se crea en `ranking.db` dentro de la carpeta desde la que se ejecuta
el comando. Si quieres llevarte los resultados actuales, copia también ese
archivo; si no, se creará uno nuevo y vacío.
