package com.cloudframe.ai.tools.exception;

/**
 * Exception thrown when tool execution fails.
 */
public class ToolExecutionException extends ToolException {
    public ToolExecutionException(String toolName, String message, Throwable cause) {
        super(toolName, message, cause, ToolErrorCode.EXECUTION_ERROR);
    }
}
