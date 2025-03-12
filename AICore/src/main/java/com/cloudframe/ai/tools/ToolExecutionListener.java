package com.cloudframe.ai.tools;

import java.util.Map;

/**
 * Listener interface for tool execution events.
 */
public interface ToolExecutionListener {
    /**
     * Called before a tool is executed.
     */
    void beforeToolExecution(String toolName, Map<String, Object> parameters);

    /**
     * Called after a tool is successfully executed.
     */
    void afterToolExecution(ToolResult result);

    /**
     * Called when a tool execution fails.
     */
    void onToolExecutionError(ToolResult result, Exception exception);
}
