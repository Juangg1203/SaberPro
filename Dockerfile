FROM eclipse-temurin:21-jdk

WORKDIR /saberpro

COPY target/Saber-Pro_Parcial-0.0.1-SNAPSHOT.jar "app.jar"

EXPOSE 8107

ENTRYPOINT ["java","-jar","app.jar"]