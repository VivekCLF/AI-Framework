package com.cloudframe.ai.tools.result;

import com.cloudframe.ai.tools.exception.ToolException.ToolErrorCode;

import java.util.Map;

/**
 * Result of a tool execution, containing the output and metadata which can be passed around to other tools or agents.
 */
public class ToolResult {
    private final String toolName;
    private final Object result;
    private final long executionTimeMs;
    private final boolean success;
    private final String errorMessage;
    private final ToolErrorCode errorCode;
    private final Map<String, String> parameterErrors;

    private ToolResult(String toolName, Object result, long executionTimeMs,
                       boolean success, String errorMessage, ToolErrorCode errorCode,
                       Map<String, String> parameterErrors) {
        this.toolName = toolName;
        this.result = result;
        this.executionTimeMs = executionTimeMs;
        this.success = success;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.parameterErrors = parameterErrors;
    }

    public static ToolResult success(String toolName, Object result, long executionTimeMs) {
        return new ToolResult(toolName, result, executionTimeMs, true, null, null, null);
    }

    public static ToolResult failure(String toolName, String errorMessage, long executionTimeMs, ToolErrorCode errorCode) {
        return new ToolResult(toolName, null, executionTimeMs, false, errorMessage, errorCode, null);
    }

    public static ToolResult parameterFailure(String toolName, Map<String, String> parameterErrors, long executionTimeMs) {
        return new ToolResult(toolName, null, executionTimeMs, false,
                "Invalid parameters for tool: " + toolName,
                ToolErrorCode.INVALID_PARAMETERS, parameterErrors);
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

    public ToolErrorCode getErrorCode() {
        return errorCode;
    }

    public Map<String, String> getParameterErrors() {
        return parameterErrors;
    }

    public int getErrorCodeValue() {
        return errorCode != null ? errorCode.getCode() : 0;
    }

    public String getErrorCodeDescription() {
        return errorCode != null ? errorCode.getDescription() : "";
    }

    @Override
    public String toString() {
        if (success) {
            return String.format("Tool '%s' executed successfully in %dms with result: %s",
                    toolName, executionTimeMs, result);
        } else if (errorCode == ToolErrorCode.INVALID_PARAMETERS && parameterErrors != null) {
            return String.format("Tool '%s' failed after %dms with invalid parameters: %s",
                    toolName, executionTimeMs, parameterErrors);
        } else {
            return String.format("Tool '%s' failed after %dms with error [%d]: %s",
                    toolName, executionTimeMs, getErrorCodeValue(), errorMessage);
        }
    }
}