#  Utiliser l'image de base avec Java 11
FROM openjdk:11

#  Définir le répertoire de travail
WORKDIR /app

#  Copier le fichier JAR de l'application Spring Boot dans le conteneur
COPY target/MQRouter-Back-0.0.1-SNAPSHOT.jar MQRouter-Back-0.0.1-SNAPSHOT.jar

#  Exposer le port sur lequel l'application écoute
EXPOSE 8080

#  Lancer l'application Spring Boot avec Java 11
ENTRYPOINT ["java", "-jar", "MQRouter-Back-0.0.1-SNAPSHOT.jar"]
