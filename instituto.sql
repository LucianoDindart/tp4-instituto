DROP DATABASE IF EXISTS instituto;
CREATE DATABASE instituto;
USE instituto;

CREATE TABLE curso(
	id_curso INT AUTO_INCREMENT,
	codigo VARCHAR(9) NOT NULL,
	idioma VARCHAR(20) NOT NULL,
	nivel VARCHAR(20) NOT NULL,
	turno VARCHAR(10) NOT NULL,
	cupo INT NOT NULL,
	cuota DECIMAL(10,2) NOT NULL,
	CONSTRAINT pk_curso PRIMARY KEY(id_curso),
	CONSTRAINT uq_curso_codigo UNIQUE(codigo),
	CONSTRAINT chk_curso_turno CHECK(turno IN('mañana','tarde','vespertino')),
	CONSTRAINT chk_curso_nivel CHECK(nivel IN('principiante','intermedio','avanzado'))
);

CREATE TABLE alumno(
	id_alumno INT AUTO_INCREMENT,
	dni VARCHAR(10),
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	correo VARCHAR(60),
	CONSTRAINT pk_alumno PRIMARY KEY(id_alumno),
	CONSTRAINT uq_alumno_dni UNIQUE(dni)
);

CREATE TABLE inscripcion(
	id_curso INT,
	id_alumno INT,
	fecha DATE NOT NULL DEFAULT(NOW()),
	estado_cuota VARCHAR(10) DEFAULT('sin pagar') NOT NULL,
	CONSTRAINT pk_inscripcion PRIMARY KEY(id_curso,id_alumno),
	CONSTRAINT fk_inscripcion_curso FOREIGN KEY(id_curso) REFERENCES curso(id_curso) ON DELETE RESTRICT,
	CONSTRAINT fk_inscripcion_alumno FOREIGN KEY(id_alumno) REFERENCES alumno(id_alumno) ON DELETE CASCADE,
	CONSTRAINT chk_inscripcion_estado_cuota CHECK(estado_cuota IN('pagada','sin pagar'))
);

INSERT INTO curso(codigo,idioma,nivel,turno,cupo,cuota) VALUES
	('MAT-01-A','Español','intermedio','tarde',10,10000),
	('ING-01-M','Ingles','principiante','vespertino',5,300),
	('QUI-03-F','Aleman','avanzado','mañana',12,50000);

INSERT INTO alumno(dni,nombre,apellido,correo) VALUES
	('3742512','Juan','Gomez','juangomez@yahoo.com.ar'),
	('4821512','Penelope','Neuer','neuer1234@gmail.com'),
	('2123525','Horacio','Pilga',NULL),
	('5000000','Goku','García','gokuatentacion@hotmail.com'),
	('3512567','Nina','Suloma',NULL),
	('9999999','Jesus','Ibrahim','holaysoyjesus@utn.gob'),
	('5325734','Baljeet','Yamal','sixseven@yahoo.com.ar'),
	('3212415','Teemo','Yordle',NULL);

INSERT INTO inscripcion(id_curso,id_alumno) VALUES
	(2,1),
	(1,5),
	(2,7),
	(1,6),
	(2,3),
	(2,4),
	(2,8);

INSERT INTO inscripcion(id_curso,id_alumno,fecha,estado_cuota) VALUES
	(1,1,'2026-03-03','pagada'),
	(3,2,'2026-03-21','pagada'),
	(1,4,'2026-07-12','sin pagar'),
	(3,6,'2026-05-03','pagada'),
	(3,3,'2026-06-25','pagada'),
	(3,1,'2026-04-15','sin pagar');

CREATE OR REPLACE VIEW vacante_curso AS
SELECT c.codigo AS 'Curso', c.cupo - COUNT(i.id_curso) AS 'Vacantes' FROM
curso c JOIN inscripcion i ON c.id_curso = i.id_curso GROUP BY c.id_curso;

CREATE OR REPLACE VIEW alumno_cuota AS
SELECT CONCAT(a.nombre,' ',a.apellido) AS 'Alumno', c.codigo AS 'Cuota que deben' FROM 
inscripcion i JOIN alumno a ON i.id_alumno = a.id_alumno
JOIN curso c ON i.id_curso = c.id_curso WHERE i.estado_cuota = 'sin pagar';

DROP USER IF EXISTS 'admin'@'%';
CREATE USER 'admin'@'%' IDENTIFIED BY 'admin1234';
GRANT ALL ON * TO 'admin'@'%';

DROP USER IF EXISTS 'secretaria'@'%';
CREATE USER 'secretaria'@'%' IDENTIFIED BY 'secretaria1235';
GRANT SELECT ON alumno_cuota TO 'secretaria'@'%';
GRANT SELECT ON vacante_curso TO 'secretaria'@'%';
