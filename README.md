# meep
## Introduction

Meep project. This is a springboot project microservice.
This microservice get the changes that have occurred in the availability of vehicles with respect to their
previous state every 30 seconds

Redis is used to offer persistance to mobility resources.


### ¿Cómo de escalable es tu solución propuesta?
Se ha elegido Redis para la gestión de la información que se obtiene del endpoint porque te permite realizar operaciones de manera 
muy rápida a través de clave y además te permite escalar de manera fácil. Es una base de datos ligera y al no necesitar grandes búsquedas(no se van a gestionar grandes volúmenes de datos)
es ideal para esta solución. También sería una buena solución usar un MongoDB, ya que escala fácilmente de manera horizontal.

### ¿Qué problemas a futuro podría presentar? Si has detectado alguno, ¿Qué alternativa/s propones para solventar dichos problemas?

El mayor problema que he detectado es la posibilidad de añadir nuevas zonas, localizaciones, proveedores. El microservicio debe estar 
preparado para permitir de manera fácil añadir nuevos recursos de diferentes proveedores.
- La url de conexión con el api debería ser configurable para poder ampliar y modificar los parámetros de búsqueda.
  En este caso se ha optado por crear de manera dinámica la url a través de ficheros de configuración. Se podría crear un reposiorio 
centralizado para gestionar y modificar las propiedades usando la herramienta config server de springboot.
Te permite configurar las propiedades por entorno y en caliente sin tener que desplegar cambios en el microservicio
- Otra posibilidad sería tener la posibilidad de persistir diferentes configuraciones en base de datos. Así se podrían añadir, modificar y eliminar 
las propiedades que se requieren para la creación de la url del enpoint.
- Se podría publicar un api rest de administración para gestionar esta base de datos y así poder configurar el microservicio para diferentes 
proveedores sin tener que desplegar o modificar el código.
- Otra mejora que se irá añadiendo es la publicación de resultados en una cola de eventos, para que otros microservicios se puedan subscribir y ser informados del
cambio de estado de los recursos. Se puede implementar con Kafka o RabbitMQ.

### Prerequisites

    - Java 11+
    - Docker
    - maven


### Dependencies

    - Mapstruct
    - Junit 5
    - Lombock
    - redis


### Instalation  and execution
- Docker
  Simply execute the following command:
  
    `docker run --name my-redis -p 6379:6379 -d redis`


- Running Applications with Maven

   `mvn clean spring-boot:run  -Dspring-boot.run.jvmArguments="-Dserver.port=8080"`



