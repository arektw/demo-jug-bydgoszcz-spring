FROM maven:3.8.6-amazoncorretto-17 as build

WORKDIR /build
# Copy the source code into the image for building
COPY . /build

RUN mvn --no-transfer-progress clean package

FROM amazoncorretto:17
COPY --from=build /build/target/demojug-0.0.1.jar /srv/demojug.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar /srv/demojug.jar"]