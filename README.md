# ProyectoFinal-IBM
Repositorio correspondiente al proyecto final de la academia de microservicios de IBM

**Tabla de Contenido**

[TOCM]

[TOC]

##¿Qué hacen las API´s?
###EurekaServerProject
EurekaServerProject permite crear y levantar un servidor Eureka, en dicho servidor, se podrá realizar el registro de las API´s que se deseen, en caso del proyecto final planteado, se registraron los servicios de ServiceGetCard y ServiceSearchATM.
###ServiceGetCard

El servicio ServiceGetCard tiene como objetivo devolver los tipos de tarjetas de credito más adecuadas para el cliente en cuestión, para lograr determinar el tipo correcto de tarjeta se necesita la siguiente información:

####Requisitos
- Preferencias (Passion): Corresponde al tipo de preferencia establecida por el cliente.
- Salario (Monthly salary): Corresponde al salario mensual del cliente. 
- Edad (Age): Corresponde a la edad del cliente.

####Propuesta de solución
Como información adicional, para resolver el problema, se proporciono una tabla con la relación entre los tres requisitos solicitados al cliente y el tipo de tarjeta que puede obtenerner.
Para atender el problema el primer paso que se siguió fue realizar la construcción y normalización de una base de datos (H2) que se cargará en memoria, evitando exportar y cargar el schema y la data. El resultado del procedimiento de normalización se puede observar en la siguiente imagen.

![Img Normalización](../master/EurekaServerProject/EvidenciasEurekaServerProject/EurekaServerOpera.JPG)

###ServiceSearchATM
##EndPoints
###ServiceGetCard
###ServiceSearchATM
##Postman
###ServiceGetCard
###ServiceSearchATM
