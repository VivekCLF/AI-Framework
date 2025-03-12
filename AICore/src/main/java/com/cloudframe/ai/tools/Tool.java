package com.cloudframe.ai.tools;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Represents a tool that can be invoked by an AI agent.
 */
public class Tool {
    private final String name;
    private final String description;
    private final String schema;
    private final boolean requiresConfirmation;
    private final Function<Map<String, Object>, Object> executor;

    public Tool(String name, String description, String schema,
                          boolean requiresConfirmation,
                          Function<Map<String, Object>, Object> executor) {
        this.name = name;
        this.description = description;
        this.schema = schema;
        this.requiresConfirmation = requiresConfirmation;
        this.executor = executor;
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

    public Object execute(Map<String, Object> parameters) {
        return executor.apply(parameters);
    }

    public CompletableFuture<Object> executeAsync(Map<String, Object> parameters) {
        return CompletableFuture.supplyAsync(() -> executor.apply(parameters));
    }
}
