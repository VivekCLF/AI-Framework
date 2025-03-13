package com.cloudframe.ai.tools.exception;

/**
 * Custom ToolCompletionException to wrap ToolExceptions in async execution.
 */
public class ToolCompletionException extends RuntimeException {
    public ToolCompletionException(ToolException cause) {
        super(cause);
    }

    public ToolException getToolException() {
        return (ToolException) getCause();
    }
}