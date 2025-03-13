package com.cloudframe.ai.tools;

import com.cloudframe.ai.tools.exception.ToolException;
import com.cloudframe.ai.tools.exception.ToolExecutionException;
import com.cloudframe.ai.tools.exception.ToolParameterException;
import com.cloudframe.ai.tools.validation.ParameterValidator;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Abstract base class for tools that can be invoked by an AI agent.
 */
public abstract class Tool {
    private final String name;
    private final String description;
    private final String schema;
    private final boolean requiresConfirmation;
    private final ParameterValidator parameterValidator;

    protected Tool(String name, String description, String schema,
                   boolean requiresConfirmation, ParameterValidator parameterValidator) {
        this.name = name;
        this.description = description;
        this.schema = schema;
        this.requiresConfirmation = requiresConfirmation;
        this.parameterValidator = parameterValidator;
    }

    protected Tool(String name, String description, String schema,
                   boolean requiresConfirmation) {
        this(name, description, schema, requiresConfirmation, null);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSchema() {
        return schema;
    }

    public boolean requiresConfirmation() {
        return requiresConfirmation;
    }

    /**
     * Abstract method to execute the tool with given parameters.
     * Must be implemented by concrete tool classes.
     * @param parameters Input parameters for the tool
     * @return Result of the tool execution
     * @throws Exception If execution fails
     */
    protected abstract Object executeImpl(Map<String, Object> parameters) throws Exception;

    /**
     * Execute the tool with validation and error handling.
     * @param parameters Input parameters for the tool
     * @return Result of the tool execution
     * @throws ToolException If execution fails
     */
    public final Object execute(Map<String, Object> parameters) throws ToolException {
        // Validate parameters if a validator is provided
        if (parameterValidator != null) {
            Map<String, String> validationErrors = parameterValidator.validate(parameters);
            if (!validationErrors.isEmpty()) {
                throw new ToolParameterException(name, validationErrors);
            }
        }

        try {
            return executeImpl(parameters);
        } catch (ToolException e) {
            throw e;
        } catch (Exception e) {
            throw new ToolExecutionException(name, "Failed to execute tool: " + name, e);
        }
    }

    /**
     * Execute the tool asynchronously.
     * @param parameters Input parameters for the tool
     * @return A CompletableFuture that will complete with the tool result
     */
    public CompletableFuture<Object> executeAsync(Map<String, Object> parameters) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(parameters);
            } catch (ToolException e) {
                throw new CompletionException(e);
            }
        });
    }
}