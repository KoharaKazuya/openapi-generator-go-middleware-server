FROM maven:3.6.0-jdk-8-alpine AS builder

RUN mkdir /work
WORKDIR /work
COPY . /work/
RUN mvn package


FROM openapitools/openapi-generator-cli:v4.0.0

COPY --from=builder /work/target/openapi-generator-go-middleware-server-0.2.2.jar /opt/

ENTRYPOINT ["java", "-classpath", "/opt/openapi-generator/modules/openapi-generator-cli/target/openapi-generator-cli.jar:/opt/openapi-generator-go-middleware-server-0.2.2.jar", "org.openapitools.codegen.OpenAPIGenerator"]
CMD ["help"]
