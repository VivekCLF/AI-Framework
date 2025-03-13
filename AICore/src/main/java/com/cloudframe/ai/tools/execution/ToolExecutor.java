package com.cloudframe.ai.tools.execution;

import com.cloudframe.ai.tools.Tool;
import com.cloudframe.ai.tools.exception.ToolException.ToolErrorCode;
import com.cloudframe.ai.tools.registry.ToolRegistry;
import com.cloudframe.ai.tools.result.ToolResult;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Tool executor for invoking tools within the AI agent context.
 */
public class ToolExecutor {
    private final ToolRegistry registry;
    private final ToolExecutionListener listener;

    public ToolExecutor(ToolRegistry registry) {
        this(registry, null);
    }

    public ToolExecutor(ToolRegistry registry, ToolExecutionListener listener) {
        this.registry = registry;
        this.listener = listener;
    }

    /**
     * Execute a tool by name with the given parameters.
     */
    public ToolResult execute(String toolName, Map<String, Object> parameters) {
        if (!registry.hasTool(toolName)) {
            return ToolResult.failure(toolName, "Tool not found", 0, ToolErrorCode.TOOL_NOT_FOUND);
        }

        Tool tool = registry.getTool(toolName);

        if (listener != null) {
            listener.beforeToolExecution(toolName, parameters);
        }

        long startTime = System.currentTimeMillis();
        try {
            Object result = tool.execute(parameters);
            long endTime = System.currentTimeMillis();
            ToolResult toolResult = ToolResult.success(toolName, result, endTime - startTime);

            if (listener != null) {
                listener.afterToolExecution(toolResult);
            }

            return toolResult;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            ToolResult toolResult = ToolResult.failure(toolName, e.getMessage(), endTime - startTime, ToolErrorCode.EXECUTION_ERROR);

            if (listener != null) {
                listener.onToolExecutionError(toolResult, e);
            }

            return toolResult;
        }
    }

    /**
     * Execute a tool asynchronously.
     */
    public CompletableFuture<ToolResult> executeAsync(String toolName, Map<String, Object> parameters) {
        return CompletableFuture.supplyAsync(() -> execute(toolName, parameters));
    }
}
