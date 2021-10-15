# ProyectoFinal-IBM
Repositorio correspondiente al proyecto final de la academia de microservicios de IBM

## ¿Qué hacen las API´s?

### EurekaServerProject

EurekaServerProject permite crear y levantar un servidor Eureka, en dicho servidor, se podrá realizar el registro de las API´s que se deseen, en caso del proyecto final planteado, se registraron los servicios de ServiceGetCard y ServiceSearchATM.

![Img Normalización](../master/Capturas/EvidenciasEurekaServerProject/EurekaServerOpera.JPG)

### ServiceGetCard

El servicio ServiceGetCard tiene como objetivo devolver los tipos de tarjetas de crédito más adecuadas para el cliente en cuestión, para lograr determinar el tipo correcto de tarjeta se necesita la siguiente información:

#### Requisitos

- **Preferencias (Passion):** Corresponde al tipo de preferencia establecida por el cliente.
- **Salario (Monthly salary):** Corresponde al salario mensual del cliente. 
- **Edad (Age):** Corresponde a la edad del cliente.

#### Propuesta de solución

Como información adicional, para resolver el problema, se proporciono una tabla con la relación entre los tres requisitos solicitados al cliente y el tipo de tarjeta que puede obtener.
Para atender el problema, el primer paso consistió en realizar la construcción y normalización de una base de datos (H2) que se cargará en memoria, evitando exportar y cargar el schema y la data. El resultado del procedimiento de normalización hasta la tercera forma normal de la base de datos se puede observar en la siguiente imagen, este proceso se realizó rudimentariamente en una hoja de excel, esto solo como referencia para posteriormente crear los archivos schema.sql y data.sql mencionados.

![Img Normalización](../master/Capturas/NormalizaciónDB.JPG)

La ventaja de establecer la solución a traves de una base de datos consiste en que si alguna de las tablas llega a crecer en un futuro, dada la definición de la estructura, se pueden insertar más valores dentro de las tablas y de esta manera generar más relaciones entre los requisitos de entrada y los tipos de tarjeta que se presentan como salida. 
Con lo anterior, se tiene que la definición de los tipos de tarjeta que se pueden ofrecer a los clientes va a estar ligada a una consulta (query) realizada a la tabla Profiles, la cual contiene todas las relaciones de la tabla proporcionada para el ejercicio.

#### Estructura del Proyecto

El proyecto tiene un paquete base com.ibm.CardService.ServiceGetCard donde se encuentra la aplicación principal, en dicho archivo solo se indico que el servicio sería un cliente del servidor Eureka mencionado al inicio, a continuación se presentan el resto de los paquetes (contenidos dentro del paquete base):

- **constants:** Este paquete se conforma de una clase llamada Constant, la cual almacena constantes String que serán utilizadas a lo largo del proyecto, esto se utilizó debidó a la sugerencia como buena práctica dentro de los servicios, las constantes definidas contienen valores relativamente no tan relevantes como para ser definidos en el archivo de propiedades, sin embargo, se considero una buena alternativa generar dicha clase de constantes.

- **controller:** Contiene la clase CardRestController en la cual se utilizan las anotaciones @Slf4j para poder establecer en el log la información correspondiente a la respuesta obtenida de la petición al servicio, @RestController para denotar al controlador, @Autowired para enlazar con IHttpConsumes (se explicará más adelante), @GetMapping con la cual podemos definir el value que es el endpoint de la API, además de consumes y produces. El controlador contiene una función llamada getCards que recibe un @RequestBody CardRequest que es el modelo de los requisitos solicitados, la ifnormación ingresada en el modelo se valida y al finalizar se obtiene un ResponseEntity<List<String>> con la lista de tarjetas que el cliente puede obtener, de manera adicional, se establece una función que escribe en el log la información correspondiente a la petición.

- **daos:** Este paquete contiene una interfaz llamada ICardDao, la cual define una función publica getCards y la cual se implementa en la clase ICardDaoImpl que utiliza la notación @Repository, dentro de ella se realiza el query a la base de datos, esto con ayuda de un jdbcTemplate, y se obtiene la lista de tarjetas que coinciden con los requisitos establecidos en el objeto CardRequest obtenido.

- **exceptions:** Para el paquete exceptions se definen dos calses de tipo de excepción, una para cuando se obtengan requisitos vacíos (EmptyField) y otra para cuando los requisitos obtenidos sean inválidos (InvalidField), además se cuenta con una clase Enum para los ErrorType y así definir sus codigos de error. Las clases EmptyFieldException y InvalidFieldException extienden de la clase RuntimeException, para definir las clases se utilizó nuevamente Lombok haciendo uso de las anotaciones @AllArgsConstructor y @Getter.

- **globalHandlerException:** Este paquete contiene nuestra clase del mismo nombre, la cual nos permite establecer como se desea que se realice el manejo de las excepciones y personalizar nuestros mensajes de error, esta clase lleva la anotación @ControllerAdvice y hace uso de @Slf4j para agregar en el log, por medio de la función setLog los errores que se lleguen a presentar. Dentro de la clase se utilizan @ExceptionHandler donde se establecer la clase de la excepción correspondiente, también se hace uso del @ResponseStatus con su código correspondiente y el @ResponseBody con la funcion de error ya sea para los campos vacíos o para los valores nulos, ambas regresan un objeto de tipo ErrorResponse que contiene el código y tipo de error que se ha lanzado, de esta manera, como se mencionó en el curso se puede personalizar las excepciones de error que se encuentren.

- **models:** Dentro de este paquete, se establecen los modelos utilizados en el proyecto, los cuales son dos, uno es CardRequest que permite establecer los requisitos o valores de entrada en un objeto y procesarlos de esa manera, y por otra parte, el modelo ErrorResponse que se utiliza en el paquete anterior para el manejo de las excepciones personalizadas. Para estas clases se utilizó nuevamente Lombok (@AllArgsConstructor, @NoArgsConstructor, @Getter, @Setter).

- **services:** Por último, el paquete services contiene una interfaz llamada IHttpConsumes, en la cual se encuentra la función sendGetCards, esta es implementada en la clase IHttpConsumesImpl denotada con el esteriotipo @Service, la cual define un atributo ICardDao para poder utilizar la función getCards de su respectiva implementación, de esta forma la función sendGetCards hace uso, por medio del atributo ICardDao, de la función getCards que obtiene la lista de tarjetas de la base de datos. También recordar que la función sendGetCards es utilizada dentro de la función del controlador y así realizar todo el proceso de consulta, para obtener el resultado deseado.

#### Resources

En src/main/resources se tiene el archivo de configuración application.yml en el cual se establece la configuración para Eureka, el Server, Spring conectando con H2 y algunas propiedades extra para el endpoint. 
De igual manera, se tienen los archivos data.sql y schema.sql con los cuales se crea y pobla la base de datos.

### ServiceSearchATM

ServiceSearchATM tiene como objetivo consumir un servicio de 3ros desde la liga https://www.banamex.com/localizador/jsonP/json5.json, el cual genera un Json con una serie de datos (información) ligados a cajeros automáticos, el JSON debe ser tratado para poder hacer uso de la información que contiene. Al finalizar el servicio debe ser capaz de obtener la ubicación de cajeros automáticos de Citibanamex cercanos a cualquiera de los siguientes datos:

#### Requisitos

- Coordenadas GPS
- Código Postal 
- Delegación / estado 

En otras palabras, a partir de los requisitos proporcionados por el cliente y con apoyo del la información presente en el Json se deberá obtener la lista de cajeros / sucursales cercanos.  

#### Propuesta de solución

Para abodar correctamente el problema, el primer paso que se siguió fue el tratamiento del Json obtenido del servicio de 3ros, dado que, si no se realizaba el correcto tratamiento no habría información con la cual hacer los filtros/búsquedas posteriormente.
  
Para el tratamiento se utilizó Postman en primera instancia para ver como estaba definida la estructura del Json como estaban definidos sus indices y que información podía componer cada campo. Dentro de estas pruebas, se encontro información relacionada con colonia, ciudad, C.P., calles principales y de referencia, horarios de atención (inicio y fin, excepto cajeros 24x7), latitud, longitud, estado y tipo (Cajero/Sucursal). Para poder extrer esta información se realizó un parseo del Json, se obtuvo la información correspondiente a cada sub-indice del Json, sin embargo, existía información que estaba compuesta por ejemplo la colonia, ciudad y C.P. se obtenían en un solo String y se tuvo que separar utilizando la función split, parte de la difícultad del ejercicio radica en verificar y obtener la información contenida dentro el Json proporcionado. Como apoyo se definió un modelo ATM que contuviera como atributos la información que se recuperaba del Json y así obtener al final una lista de cajeros automáticos, la cual ya se podrá filtrar de acuerdo a los parámetros establecidos en el ejercicio, dentro de la solución solicitada solo se indica la entrada de los requisitos, los cuales se agregan al modelo ATMRequest que es la clase que permite obtener un objeto del request para procesar así la información, en la solución se establece que se debe realizar el filtro de acuerdo a los datos de entrada, sin embargo, no se define la cantidad de filtros que se deberán realizar precisamente.
Como propuesta de solución se realizan 4 endpoints el primero recibe un objeto ATMRequest y establece una prioridad de búsqueda de la siguiente forma:

1.- Por Coordenadas GPS
  
2.- Por zipCode
  
3.- Por Ciudad / Estado

Si en el ATMRequest estan presentes las coordenadas se realiza la búsqueda a partir de ellas, debido a que, es el dato que límita a una distancia más reducida los posibles cajeros automáticos más cercanos para el cliente. En dado caso de que las coordenadas no se encuentren, se pasa al filtro por zipCode que es el segundo parámetro que proporciona una distancia adecuada entre la ubicacación dada por el cliente y los cajeros existentes y por último en caso de que las coordenadas y el zipCode no se encuentren presentes se ampliaría el filtrado de cajeros a ciudad / estado. 
Para el segundo endpoint, solo se pasan como parámetro las coordenas GPS, sin embargo, este endpoint no admitirá valores vacíos o inválidos para las coordenadas, por ejemplo, una coordenada no puede tener un valor mayor a 3 digitos antes del punto decimal y debe ser un valor que contenga puntos decimales (flotante).
Esto mismo sucede con el tercer endpoint que esta dedicado a una búsqueda solo por código postal (zipCode), donde no acepta valores vacíos o en caso de México los códigos que no sean de 5 dígitos, por decir un ejemplo.
Y el cuarto endpoint realiza la búsqueda a través de ciudad/estado validando que no sea un campo vacío, nulo o que el valor que se obtiene tenga al menos 6 letras que formen el nombre de alguna ciudad/estado.

#### Estructura del Proyecto

El proyecto tiene como base el paquete com.ibm.ATMService.ServiceSearchATM, dentro se encuentra la clase principal a la cual se le agregaron las anotaciones @EnableDiscoveryClient
@EnableFeignClients, para poder registrar el servicio dentro del servidor Eureka y habilitar el proyecto para el uso de Feign respectivamente. El resto de paquetes se definen a continuación:

- **constants:** Retoma el objetivo del proyecto anterior definir una clase llamada Constant, para definir el valor de constantes String que se usan a lo largo del proyecto como buena práctica.

- **controller:** El paquete contiene la clase ATMRestController que tiene las siguientes anotaciones @Slf4j, @RestController, @RequestMapping las cuales se utilizaron en el proyecto anterior y su utilidad fue descrita a lo largo de la academia, hacemos uso de @Autowired para enlazar con ATMServiceI y ValidateI (se detallarán más adelante), dentro del controlador tenemos definidos los 4 endpoints, se hace uso de @GetMapping para definir su value, params, produces tal y cómo se describió anteriormente, para el primer endpoint utilizamos @RequestBody utilizando el modelo ATMRequest que permite obtener los requisitos ingresados por el cliente y para el resto de endpoints los requisitos son ingresados en forma de parámetros. Aparte de los 4 enpoints se tiene una función setLog que establece la información relacionada con la petición y el resultado provisto por cada endpoint, además cada uno también realiza validaciones que se verán más adelante. Como resultado de la petición se establece un ResponseEntity<List<ATM>>, de esta forma, se devuelve la lista de cajeros encontrados en la búsqueda/filtro y el estatus correspondiente.

- **exceptions:** Dentro de este paquete se contiene las clases de excepciones, en este caso se decidió utilizar nuevamente EmptyFieldException, InvalidFieldException y la clase Enum ErrorType.

- **feign:** Contiene el contrato (interfaz) con la cual se hará el enlace a la url que proporciona el Json de los cajeros automáticos, se utiliza @FeignClient proporcionando dicha url y el name, de esta manera, se logra consumir el servicio de 3ero y recuperar el Json (sin tratamiento) con la información de los cajeros.

- **globalHandlerException:** Tiene un comportamiento similar al del proyecto anterior, permite realizar el manejo de las excepciones y personalizar los errores que se muestran, hace uso nuevamente del modelo ErrorResponse y establece en el log con la función setLog cualquier error que se pueda ser encontrado.

- **models:** El paquete contiene tres modelos ATM para poder cargar todos los datos que se obtengan del tratamiento del json, ATMRequest se usa para recabar los datos ingresados por el usuario para el primer endpoint y ErrorResponse para definir los tipos de errores que se lleguen a presentar. Dentro de ATMRequest se realiza una validación para verificar si existen valores nulos.

- **service:** Dentro del service se incluye una clase llamada ATMJsonParse la cual esta dedicada a limpiar y tratar el Json, dentro de dicha clase se realiza un parse para obtener una lista de objetos ATM, en esta parte se hizo el trabajo fuerte para poder definir de la mejor manera los objetos ATM y los valores de sus atributos. También se tiene la interfaz ATMServiceI que establece las búsquedas/filtros que se deben seguir para obtener los cajeros automáticos más cercanos y en la clase ATMServiceImpl se realiza la implementación de dichos filtros, como complemento se establce una función que verifica que no haya duplicidad entre los objetos de la lista de cajeros ya filtrados, de esta manera, se realizan las funciones que se invocarán para realizar cada tipo de búsqueda, recordemos que aunque existen 4 endpoints el primero solo realiza o establece una prioridad sobre los tres tipos de búsqueda definidos.

- **validate:** Finalmente el paquete validate contiene una interfaz con el nombre ValidateI que establece tres funciones de validación una para cada tipo de búsqueda a patir de los parámetros que se requieren respectivamente, la clase ValidateImpl implementa dichas validaciones para que se puedan usar en el controller y ante cualquier error mandarlo al manejador de excepciones.

#### Resources

En el src/main/resources encontramos solamente el archivo application.yml con las configuraciones pertinentes para Spring, Server, Eureka, Feign y los valores correspondientes a los endpoints que no se exponen en el controller directamente.

## EndPoints

### ServiceGetCard

El endpoint para ServiceGetCard es el siguiente:
-  GET http://localhost:8082/card/getCards
  
Se debe mandar un body (Json) con el siguiente formato (Ejemplo):
  
    {

      "passion": "Sports",

      "salary": 10000,

      "age" : 25

    }

### ServiceSearchATM

Los endpoints para ServiceSearchATM son los siguientes:
  
- GET http://localhost:8081/search/priority
  
Se debe mandar un body (Json) con el siguiente formato (Ejemplo):
  
    {

      "latitude": "19.169144",

      "longitude": "-96.226812",

      "zipCode": "91697",

      "city": "Veracruz"

    }

- GET http://localhost:8081/search/GPS?latitude=19.169144&longitude=-96.226812
  
Los valores de los parámetros latitude y longitude son un ejemplo.

- GET http://localhost:8081/search/zipCode?zipCode=91697
  
El valor del parámetro zipCode es un ejemplo.

- GET http://localhost:8081/search/city?city=Veracruz
  
El valor del parámetro city (hace referencia a estado/city) es un ejemplo.

## Postman

### ServiceGetCard
A continuación, se presentan las imagenes correspondientes a las peticiones en postman para ServiceGetCard:

- Request 200 OK:
![Img1](../master/Capturas/EvidenciasServiceGetCard/Request_200_OK.JPG)

- Request Error:
![Img2](../master/Capturas/EvidenciasServiceGetCard/Request_Error_1_passion_empty.JPG)
![Img2](../master/Capturas/EvidenciasServiceGetCard/Request_Error_2_salary_invalid.JPG)
![Img4](../master/Capturas/EvidenciasServiceGetCard/Request_Error_3_salary_invalid.JPG)
![Img5](../master/Capturas/EvidenciasServiceGetCard/Request_Error_4_age_invalid.JPG)
![Img6](../master/Capturas/EvidenciasServiceGetCard/Request_Error_5_age_invalid.JPG)

- Console Log:
![Img6](../master/Capturas/EvidenciasServiceGetCard/Console_Log.JPG)

### ServiceSearchATM

A continuación se presentan las imagenes correspondientes a las peticiones en postman para ServiceSearchATM:

- Request 200 OK (Get By Priority):
![Img7](../master/Capturas/EvidenciasServiceSearchATM/Request_200_OK_1_Get_By_Priority.JPG)
![Img8](../master/Capturas/EvidenciasServiceSearchATM/Request_200_OK_2_Get_By_Priority_longitud_empty.JPG)
![Img9](../master/Capturas/EvidenciasServiceSearchATM/Request_200_OK_3_Get_By_Priority_latitud_empty.JPG)
![Img10](../master/Capturas/EvidenciasServiceSearchATM/Request_200_OK_4_Get_By_Priority_latitud_and_longitud_empty.JPG)
![Img11](../master/Capturas/EvidenciasServiceSearchATM/Request_200_OK_5_Get_By_Priority_latitud_longitud_and_zipCode_empty.JPG)

- Request 200 OK (Get By GPS):
![Img13](../master/Capturas/EvidenciasServiceSearchATM/Request_200_OK_Get_By_GPS.JPG)

- Request 200 OK (Get By ZipCode)
![Img14](../master/Capturas/EvidenciasServiceSearchATM/Request_200_OK_Get_By_ZipCode.JPG)

- Request 200 OK (Get By City):
![Img12](../master/Capturas/EvidenciasServiceSearchATM/Request_200_OK_Get_By_City.JPG)

- Request 404 NOT FOUND
![Img15](../master/Capturas/EvidenciasServiceSearchATM/Request_404_Not_Found_Get_By_Priority_latitud_longitud_zipCode_and_city_empty.JPG)

- Request Error (Get By Priority)
![Img16](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_1_Get_By_Priority_latitude_null.JPG)
![Img17](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_2_Get_By_Priority_longitude_null.JPG)
![Img18](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_3_Get_By_Priority_zipCode_null.JPG)
![Img19](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_4_Get_By_Priority_city_null.JPG)

- Request Error (Get By GPS)
![Img20](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_1_Get_By_GPS_latitude_empty.JPG)
![Img21](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_2_Get_By_GPS_longitude_empty.JPG)
![Img22](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_3_Get_By_GPS_latitude_invalid.JPG)
![Img23](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_4_Get_By_GPS_longitude_invalid.JPG)
![Img24](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_5_Get_By_GPS_latitude_null.JPG)
![Img25](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_6_Get_By_GPS_longitude_null.JPG)

- Request Error (Get By ZipCode)
![Img26](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_1_Get_By_ZipCode_zipCode_empty.JPG)
![Img27](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_2_Get_By_ZipCode_zipCode_null.JPG)
![Img28](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_3_Get_By_Priority_zipCode_null.JPG)

- Request Error (Get By City)
![Img29](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_1_Get_By_City_city_empty.JPG)
![Img30](../master/Capturas/EvidenciasServiceSearchATM/Request_Error_2_Get_By_City_city_null.JPG)

- Console Log:
![Img31](../master/Capturas/EvidenciasServiceSearchATM/Console_Log.JPG)
  
## Conclusiones y Observaciones
En conclusión se considera que ambos servicios cumplen con sus respectivos objetivos, de igual forma, es importante señalar que como parte de la solución se consideró importante tal vez manejar un chaché para no pedir en cada solicitud el Json a tratar o no hacer una consulta a la base de datos cada vez que se búscan el tipo de tarjertas de un cliente, sin embargo, no se define con que temporalidad tanto el Json como los valores de la Base de Datos fueran a cambiar, aún así se sigue considerando como una buena alternativa que se puede agregar posteriormente.
De igual forma, se consideró en un inicio utilizar un ConfigServer para cargar los archivos de propiedades desde otro repositorio en GitHub, sin embargo, por la naturaleza de los proyectos no era necesario agregar una configuración de dicha naturaleza, ya que con definir el archivo de propiedades yml fue más que suficiente.
  
De esta manera, y sin nada más que agregar finaliza la especificación requerida para los servicios que componen el proyecto final de la academia de microservicios de IBM.
