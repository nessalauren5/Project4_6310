# Project4 6310

#Project Overview

This project is CS6310 Project 4: Bringing it all together.  It is web application, which is a subset of the system specifications designed during projects 2 and 3 and intended to optimize a student registration schedule based on a set of simple constraints for courses and user types.

#Technical Specifications

At a bare minimum, the development environment for this project needs:

- JRE Version 1.7
- Tomcat 8.0.21 Application Server
- Javascript enabled browsers

The remainder of the files are included in the /lib folder for the project

#Folder Structure
- /src : the source code for the program, implemented using Eclipse Luna IDE, to be compiled using JRE7 or later
- /WebContent: the resources for the front-end, client side interfaces
- /META-INF:  the data for the persistence layer
- /lib: the folder, which contains all of the libraries required to run this program, including Tomcat, hibernate, Spring, javax, and gurobi
- /src/launch: contains the main.java file to run the webapp
- /database : contains the database README file and the two sql files to generate the database tables for this project

#Directions for use
Import the project into the Eclipse Luna IDE for, JAVA EE and make sure the build path has the compiler set to Java 1.7 and all the libraries added from the /lib folder. Add the Tomcat server as a server and make sure the JAVA_HOME variable is set. Open SQL Server and create a project called Project4.  Run the files in the database folder. Configure the project to run on Tomcat or select the project name and Run it on the Tomcat server. 

