-- Creamos la base de datos AkihabaraDB
CREATE DATABASE IF NOT EXISTS akihabara_db;
-- Usamos la base de datos akihabara_db
USE akihabara_db;
-- Creamos la tabla llamada productos
CREATE TABLE IF NOT EXISTS productos(
id int primary key auto_increment,
nombre varchar(255) not null,
categoria varchar(100),
precio decimal(10,2),
stock int
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT (CURDATE())
);

select * from productos;