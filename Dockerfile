# Usa una imagen de Java 17
FROM eclipse-temurin:17-jdk

# Crea un directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación (ajusta el nombre si es diferente)
COPY target/imagecounter-0.0.1-SNAPSHOT.jar stable-v1.jar

# Expone el puerto en el que corre Spring Boot
EXPOSE 8080

# Ejecuta la aplicación
CMD ["java", "-Xmx128m", "-Xss512k", "-jar", "app.jar"]

