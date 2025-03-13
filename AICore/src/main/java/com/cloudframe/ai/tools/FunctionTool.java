package com.cloudframe.ai.tools;

import com.cloudframe.ai.tools.exception.ToolException;
import com.cloudframe.ai.tools.validation.ParameterValidator;

import java.util.Map;
import java.util.function.Function;

/**
 * Concrete implementation of Tool that uses a Function to execute.
 */
public class FunctionTool extends Tool {
    private final Function<Map<String, Object>, Object> executor;

    public FunctionTool(String name, String description, String schema,
                        boolean requiresConfirmation,
                        Function<Map<String, Object>, Object> executor,
                        ParameterValidator parameterValidator) {
        super(name, description, schema, requiresConfirmation, parameterValidator);
        this.executor = executor;
    }

    public FunctionTool(String name, String description, String schema,
                        boolean requiresConfirmation,
                        Function<Map<String, Object>, Object> executor) {
        super(name, description, schema, requiresConfirmation);
        this.executor = executor;
    }

    @Override
    protected Object executeImpl(Map<String, Object> parameters) throws Exception {
        return executor.apply(parameters);
    }
}

