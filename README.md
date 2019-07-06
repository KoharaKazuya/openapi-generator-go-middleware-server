# OpenAPI Generator for the go-middleware-server library

A custom generator of [OpenAPI Generator][] for Go server.

## Features

- **A custom generator of OpenAPI Generator** - generates Go HTTP server from OpenAPI document
- **Serialization** - auto-generated code deserializes HTTP requests and serializes HTTP response according to OpenAPI document
- **Validation** - auto-generated code validates HTTP requests and responses according to OpenAPI document
- **Compatible with net/http** - generates the standard middleware of `net/http`
- **Updatable** - you can update OpenAPI document and re-generate server code without breaking of business logic

## Caveats

- **JSON only** - tested only for JSON request/response, so you need to add code for handling non-JSON request/response if you needed

## Usage

### Docker

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
