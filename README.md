# Shop

Requirements
The fully fledged server uses the following:

Spring Framework
SpringBoot
Docker

Dependencies
There are a number of third-party dependencies used in the project. Browse the Maven pom.xml file for details of libraries and versions used.

Building the project
You will need:

Java JDK 11 or higher
Maven 3.6.0 or higher
Git

Clone the project and use Maven to build the server

$ mvn clean install

Deploying to the cloud
You can deploy the server with docker using:

$ docker build 
$ docker run 

Endpoint database
library1.czzxjzvvbj6t.us-east-2.rds.amazonaws.com

Browser URL
Open your browser at the following URL for Swagger UI (giving REST interface details):

http://3.145.193.25:8080/swagger-ui/index.html