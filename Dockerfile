FROM openjdk:21
EXPOSE 8081:8081
RUN mkdir /app
COPY build/libs/store.cartwave-docker-image-001-fat.jar /app/store.cartwave-docker-image-001-fat.jar
ENTRYPOINT ["java","-jar","/app/store.cartwave-docker-image-001-fat.jar"]