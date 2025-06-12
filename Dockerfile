# descarga la imagen del jdk 17
FROM eclipse-temurin:17-jdk

# puerto
EXPOSE 8080

# definir el directorio de trabajo
WORKDIR /root

# copiar el pom y mvn
COPY ./pom.xml /root
COPY ./mvnw /root
COPY ./.mvn /root/.mvn

# descargar las dependencias
RUN ./mvnw dependency:go-offline

# copiar el fuente
COPY ./src /root/src

# compilar/construir la aplicacion
RUN ./mvnw clean install -DskipTests

# definir el comando de inicio
ENTRYPOINT ["java", "-jar", "/root/target/AlkemyDiego-0.0.1-SNAPSHOT.jar"]