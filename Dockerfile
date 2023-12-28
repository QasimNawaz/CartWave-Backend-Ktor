FROM openjdk:21
EXPOSE 8081:8081
RUN mkdir /app
COPY build/libs/store.cartwave-0.0.1-fat.jar /app/store.cartwave-0.0.1-fat.jar
ENTRYPOINT ["java","-jar","/app/store.cartwave-0.0.1-fat.jar"]