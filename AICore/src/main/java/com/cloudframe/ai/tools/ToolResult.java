package com.cloudframe.ai.tools;

/**
 * Result of a tool execution, containing the output and metadata.
 */
public class ToolResult {
    private final String toolName;
    private final Object result;
    private final long executionTimeMs;
    private final boolean success;
    private final String errorMessage;

    private ToolResult(String toolName, Object result, long executionTimeMs,
                       boolean success, String errorMessage) {
        this.toolName = toolName;
        this.result = result;
        this.executionTimeMs = executionTimeMs;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static ToolResult success(String toolName, Object result, long executionTimeMs) {
        return new ToolResult(toolName, result, executionTimeMs, true, null);
    }

    public static ToolResult failure(String toolName, String errorMessage, long executionTimeMs) {
        return new ToolResult(toolName, null, executionTimeMs, false, errorMessage);
    }

    public String getToolName() {
        return toolName;
    }

    public Object getResult() {
        return result;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        if (success) {
            return String.format("Tool '%s' executed successfully in %dms with result: %s",
                    toolName, executionTimeMs, result);
        } else {
            return String.format("Tool '%s' failed after %dms with error: %s",
                    toolName, executionTimeMs, errorMessage);
        }
    }
}
