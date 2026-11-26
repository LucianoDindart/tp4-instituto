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


