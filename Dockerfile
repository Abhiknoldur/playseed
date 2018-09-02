FROM java
COPY target/universal/seed-1.0-SNAPSHOT.zip /
RUN unzip seed-1.0-SNAPSHOT.zip
EXPOSE 9000
CMD ./seed-1.0-SNAPSHOT/bin/seed
