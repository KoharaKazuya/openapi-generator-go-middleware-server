# OpenAPI Generator for the go-middleware-server library

A custom generator of [OpenAPI Generator][] for Go server.

## Docker

Example

```console
$ docker run --rm -v ${PWD}:/local koharakazuya/openapi-generator-go-middleware-server generate \
    -i https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/2_0/petstore.yaml \
    -g go-middleware-server \
    -o /local/out/go-middleware-server \
    --additional-properties="moduleName=github.com/KoharaKazuya/openapi-generator-go-middleware-server-sample,packageName=openapisample"
$ cd ./out/go-middleware-server/
$ go fmt ./...
$ goreturns -w ./**/*.go
$ echo '/*\n!/openapisample/' > .openapi-generator-ignore
```

[OpenAPI Generator]: https://openapi-generator.tech/
