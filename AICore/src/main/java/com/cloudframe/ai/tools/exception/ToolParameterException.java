package com.cloudframe.ai.tools.exception;

import java.util.Map;

/**
 * Exception thrown when parameters are invalid for a tool.
 */
public class ToolParameterException extends ToolException {
    private final Map<String, String> parameterErrors;

    public ToolParameterException(String toolName, Map<String, String> parameterErrors) {
        super(toolName, "Invalid parameters for tool: " + toolName, ToolErrorCode.INVALID_PARAMETERS);
        this.parameterErrors = parameterErrors;
    }

    public Map<String, String> getParameterErrors() {
        return parameterErrors;
    }
}
