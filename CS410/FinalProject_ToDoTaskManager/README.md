* *************************************************** *
* CS410 - Databases Final Project: To Do Task Manager *
*                                                     *
* Author: Marshall Eddy                               *
* *************************************************** *

## To Do List Manager

#### Environment and Build information

*  This project used Intellij with Maven build for Java
* JSch was used to create a ssh tunnel to connect to the onyx server
* JBdb was used to connect to the MySQL sandbox database on the onyx server


#### File Structure

* SQL files
    * The SQL files: schema.sql and example-data.sql are found in the SQL directory
* Driver
    * Found in the file path: /src/main/java/cs410
    * File name: ToDoListManager
    * cs410 is the package name
* The executable .jar file is created using intellij
    * The dependencies for this executable jar are found in the pom.xml file
    * The executable jar file is found in the target directory
    * File name is: ToDo_ListManager-1.0-SNAPSHOT-jar-with-dependencies.jar

#### Issues with ssh tunneling to connect to onyx

The way the connection is setup, is to use JSch to create a ssh tunnel to onyx
so JBdb could connect to the sandbox database.
I initially had issues with getting the correct local port# for the ssh tunnel.
When the standard 3306 for a SQL connection, was not working. I eventually was able
to get the connection working on intellij via a plugin for executing SQL statements.
Luckily, this plugin allowed me to see the information on this connection which had
the URL, which contained the port number. So, I used the port number from there and it worked.

Due to the nature of how I obtained the port number, I expect that the port number will
be different for someone else attempting to use this application. Furthermore, I assume
the code will need to be changed and thus will need to be re-built.

To rebuild this project, you must first build the ToDoListManager.java. Then create the jar using
intellij 'Maven Projects' panel by running the package script under lifecycle.


#### Comments

This project works for me. I am able to run every command successfully. The commands successfully get information,
write information, and update information within the database. If for some reason you cannot run this project, I assume
it is due to a problem with the connection.

#### Implementation

I based a lot of this code based on the examples provided, and was able to create the structure the code from looking at the
cliche documentation. I had to use JSch to connect to the onyx server properly.

Aside from that most of the implementation was from my knowledge of SQL and a bit of trial an error to make sure the commands
were working properly.