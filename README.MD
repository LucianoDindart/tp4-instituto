# Trabajo Practico N°4
Catedra : Programacion II

Integrantes : Luciano Dindart, Lucas Sarasola, Simon Delmenico

## Como ejecutar:
Puedes clonar el repositorio:
```
git clone https://github.com/LucianoDindart/tp4-instituto
cd tp4-instituto
```
Primero se necesita [MYSQL server](https://www.mysql.com/downloads). Si no lo tienes puedes descargarlo en ese link.

Se necesita crear la base de datos con un usuario root de la siguiente manera desde la raiz del repositorio:
```
mysql -u root -p
```
o puedes usar [MYSQL workbench](https://dev.mysql.com/downloads/workbench/) para hacerlo.

ya dentro de sql:
```
source instituto.sql
```
Listo, ya tienes la base de datos!

### Archivo de configuracion:
Hay un archivo de ejemplo llamado envejemplo que contiene:
```
jdbc:mysql://localhost:{puerto}/{base_de_datos}
{usuario}
{contraseña}
```
En el cual vas a tener que reemplazar todos los corchetes con tu configuracion, para total control sobre la base de datos se le puede proveer un usuario de administrador.
El puerto por defecto es **3306**, y en este caso la base de datos siempre sera **instituto**
El archivo de configuracion que leera el programa se llama **.env**, si la configuracion esta en un archivo distinto no se leera.

## Archivo driver de mysql:
Se requiere un archivo driver para usar mysql con java, se puede descargar de https://dev.mysql.com/downloads/connector/j/
Es requerido que el archivo driver este en la raiz del proyecto, si no es asi vas a tener que cambiar los comandos de compilacion y ejecucion del programa, los cuales son los siguientes (se reemplazan las X por la version del driver que descargaste):
## Compilacion y ejecucion:
### Linux:
```
javac -d out -cp "out:mysql-connector-j-X.X.X.jar" src/instituto/modelo/*.java src/instituto/app/Main.java
java -cp "out:mysql-connector-j-X.X.X.jar" instituto.app.Main
```
### Windows:
```
javac -d out -cp "out;mysql-connector-j-X.X.X.jar" src/instituto/modelo/*.java src/instituto/app/Main.java
java -cp "out;mysql-connector-j-X.X.X.jar" instituto.app.Main
```

## Preguntas:
1. ¿Qué pasa si dos personas usan el programa al mismo tiempo y se inscriben al último lugar de un curso? ¿Qué hace su implementación?

Respuesta :

En nuestro programa si dos personas intentan inscribirse al mismo curso al mismo tiempo, la base de datos aceptara al primer alumno, y el trigger before insert rechazaria la segunda peticion, debido a que las vacantes del curso habrán llegado a 0. Lo cual hace que tire una excepcion personalizada que se atrapa desde el programa y avisa al usuario que fue rechazado.

2. Muestren una consulta de su programa escrita con PreparedStatement y expliquen qué le pasaría si la hubieran armado concatenando lo que escribe el usuario.

Respuesta:

UPDATE curso SET codigo = ?, idioma = ?, nivel = ?, turno = ?, cupo = ?, cuota = ? WHERE id_curso = ?. Esta consulta si fuese escrita concatenando strings descuidadamente, podria dar multiples errores, como tipos no correspondientes e incluso el WHERE, en el cual podrian poner la string 'X OR '1=1' lo cual afectaria a todos los cursos del sistema, podiendo incluso inutilizarlo gracias a la falta de precaucion de nuestro sistema.

3. ¿Dónde decidieron que viva la regla del cupo: en la ventana, en el acceso a datos o en la base? Justifiquen la decisión.

Respuesta:

Hemos elegido que la regla del cupo para que viva estrictamente en la base de datos, esto es debido a que la base es la que tiene todos los registros actualizados y no hay margen de error ni hay manera de hacerlo a mano, debido a que esta todo validado en un trigger. Por lo que la base se defiende sola y despreocupa al programador de tener que tener tanta precaucion. 
