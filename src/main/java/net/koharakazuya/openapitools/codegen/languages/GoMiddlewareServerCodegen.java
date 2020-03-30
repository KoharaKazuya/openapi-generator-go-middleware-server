package net.koharakazuya.openapitools.codegen.languages;

import org.openapitools.codegen.*;
import org.openapitools.codegen.languages.AbstractGoCodegen;

import java.util.List;
import java.util.Map;

public class GoMiddlewareServerCodegen extends AbstractGoCodegen {

    // source folder where to write the files
    protected String sourceFolder = "src";

    /**
     * Configures the type of generator.
     *
     * @return the CodegenType for this generator
     * @see org.openapitools.codegen.CodegenType
     */
    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    /**
     * Configures a friendly name for the generator.  This will be used by the generator
     * to select the library with the -g flag.
     *
     * @return the friendly name for the generator
     */
    public String getName() {
        return "go-middleware-server";
    }

    /**
     * Returns human-friendly help for the generator.  Provide the consumer with help
     * tips, parameters here
     *
     * @return A string value for the help message
     */
    public String getHelp() {
        return "Generates a go-middleware-server server library.";
    }

    public GoMiddlewareServerCodegen() {
        super();

        // set the output folder here
        outputFolder = "generated-code/go-middleware-server";

        /*
         * Models.  You can write model files using the modelTemplateFiles map.
         * if you want to create one template for file, you can do so here.
         * for multiple files for model, just put another entry in the `modelTemplateFiles` with
         * a different extension
         */
        modelTemplateFiles.put(
            "openapi/model.mustache",
            ".go");

        /*
         * Api classes.  You can write classes for each Api file with the apiTemplateFiles map.
         * as with models, add multiple entries with different extensions for multiple files per
         * class
         */
        apiTemplateFiles.put(
            "openapi/api.mustache",   // the template to use
            ".go");       // the extension for each file to write

        /**
         * Template Location.  This is the location which templates will be read from.  The generator
         * will use the resource stream to attempt to read the templates.
         */
        templateDir = "go-middleware-server";
    }

    @Override
    public void processOpts() {
        super.processOpts();

        if (additionalProperties.containsKey(CodegenConstants.PACKAGE_NAME)) {
            setPackageName((String) additionalProperties.get(CodegenConstants.PACKAGE_NAME));
        } else {
            setPackageName("openapi");
        }

        /*
         * Additional Properties.  These values can be passed to the templates and
         * are available in models, apis, and supporting files
         */
        additionalProperties.put(CodegenConstants.PACKAGE_NAME, packageName);

        if (!additionalProperties.containsKey("moduleName")) {
            additionalProperties.put("moduleName", "example.com/my/module");
        }

        modelPackage = packageName;
        apiPackage = packageName;

        /*
         * Supporting Files.  You can write single files for the generator with the
         * entire object tree available.  If the input file has a suffix of `.mustache
         * it will be processed by the template engine.  Otherwise, it will be copied
         */
        supportingFiles.add(new SupportingFile("go.mustache", "", "go.mod"));
        supportingFiles.add(new SupportingFile("main.mustache", "", "main.go"));
        supportingFiles.add(new SupportingFile("openapi/action.mustache", packageName, "action.go"));
        supportingFiles.add(new SupportingFile("openapi/cast.mustache", packageName, "cast.go"));
        supportingFiles.add(new SupportingFile("openapi/error.mustache", packageName, "error.go"));
        supportingFiles.add(new SupportingFile("openapi/middleware.mustache", packageName, "middleware.go"));
        supportingFiles.add(new SupportingFile("openapi/response.mustache", packageName, "response.go"));
        supportingFiles.add(new SupportingFile("openapi/response_writer.mustache", packageName, "response_writer.go"));
        supportingFiles.add(new SupportingFile("openapi/spec.mustache", packageName, "spec.go"));
    }

    @Override
    public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {
        objs = super.postProcessOperationsWithModels(objs, allModels);

        @SuppressWarnings("unchecked")
        Map<String, Object> objectMap = (Map<String, Object>) objs.get("operations");
        @SuppressWarnings("unchecked")
        List<CodegenOperation> operations = (List<CodegenOperation>) objectMap.get("operation");
        for (CodegenOperation operation : operations) {
            String operationName = operation.nickname;
            for (CodegenResponse response : operation.responses) {
                String responseDataType = operationName + "ActionResponse" + response.code;
                response.vendorExtensions.put("x-responseDataType", responseDataType);
            }
        }

        return objs;
    }

    @Override
    public void generateYAMLSpecFile(Map<String, Object> objs) {
        super.generateYAMLSpecFile(objs);

        @SuppressWarnings("unchecked")
        String yaml = (String) objs.get("openapi-yaml");
        if (yaml != null) {
            String[] lines = yaml
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .split("\n");
            objs.put("openapi-yaml-escaped-lines", lines);
        }
    }
}
