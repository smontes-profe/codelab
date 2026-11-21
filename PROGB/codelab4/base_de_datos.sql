-- Borra la base de datos anterior y todas sus tablas, si existe.
DROP DATABASE IF EXISTS codelabs;

-- Crea la base de datos y la selecciona para las siguientes consultas.
CREATE DATABASE codelabs CHARACTER SET utf8mb4;
USE codelabs;

-- Cada registro guarda un identificador y el resultado de una partida.
CREATE TABLE ranking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jugador VARCHAR(100) NOT NULL,
    personaje VARCHAR(100) NOT NULL,
    enemigos_derrotados INT UNSIGNED NOT NULL
);

-- Insertamos uno de ejemplo (ya lo borraremos)
INSERT INTO ranking(jugador, personaje, enemigos_derrotados) 
VALUES ("Sergio Montes", "Conan", 1);