FROM maven:3.6.3-openjdk-15-slim as maven

COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY ./src ./src
RUN mvn package -DskipTests=true

COPY infra_entrypoint /infra_entrypoint
RUN chmod 0555 /infra_entrypoint

FROM jetty

WORKDIR $JETTY_BASE

COPY --from=maven /infra_entrypoint /infra_entrypoint
COPY --from=maven target/order-import-poc-*.war ./webapps/order-import-poc.war

ENTRYPOINT ["/infra_entrypoint"]
CMD ["/docker-entrypoint.sh", "java", "-jar", "/usr/local/jetty/start.jar"]
HEALTHCHECK --start-period=5s --interval=15s --timeout=5s \
    CMD curl --fail http://localhost:8080/order-import-poc/import || exit 1
