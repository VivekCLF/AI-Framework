package com.cloudframe.ai.tools.validation;

import java.util.HashMap;
import java.util.Map;

public abstract class ParameterValidator {

    /**
     * When tools are called by other tools or by AI (LLM's), it is a best practice for tools to publish a schema
     * of how to pass in parameters to it. The tools should also validate the passed in schema is correct.
     * <p>
     * Validates the given parameters. This should be implemented by the tools against their schema definition.
     *
     * @param parameters Map of parameter names and values.
     * @return A map of validation errors (empty if no errors).
     */
    public abstract Map<String, String> validate(Map<String, Object> parameters) ;
}
