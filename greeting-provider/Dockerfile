FROM anapsix/alpine-java

COPY ./target/greeting-provider-1.0-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch greeting-provider-1.0-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","greeting-provider-1.0-SNAPSHOT.jar"]