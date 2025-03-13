package com.cloudframe.ai.tools.exception;

/**
 * Exception thrown when a tool execution times out.
 */
public class ToolTimeoutException extends ToolException {
    private final long timeoutMs;

    public ToolTimeoutException(String toolName, long timeoutMs) {
        super(toolName, "Tool execution timed out after " + timeoutMs + "ms: " + toolName, ToolErrorCode.TIMEOUT);
        this.timeoutMs = timeoutMs;
    }

    public long getTimeoutMs() {
        return timeoutMs;
    }
}