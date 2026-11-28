# Añadir el conector de MySQL

Para que el juego pueda conectarse a la base de datos, necesitamos añadir
MySQL Connector/J al proyecto:

1. Abre la [página de versiones anteriores de MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).
2. Selecciona la versión **26.10.0**.
3. En el sistema operativo, selecciona **Platform Independent**.
4. Descarga el archivo **ZIP Archive**. Si aparece una pantalla para iniciar
   sesión, puedes usar la opción **No thanks, just start my download**.
5. Descomprime el ZIP y busca el archivo `.jar` que haya dentro.
6. Crea una carpeta llamada `lib` dentro de la carpeta `codelab4` y copia el
   archivo JAR en ella.

7. Abre la carpeta `codelab4` en VS Code con las extensiones de Java instaladas.
   El JAR debería aparecer en **Java Projects > Referenced Libraries**.
   Si no aparece, usa el botón **+** de esa sección para seleccionarlo.

No es necesario instalar Maven para utilizar el JAR de esta forma.
