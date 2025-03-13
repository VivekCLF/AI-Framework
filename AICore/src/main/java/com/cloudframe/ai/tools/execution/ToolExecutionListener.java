package com.cloudframe.ai.tools.execution;

import com.cloudframe.ai.tools.result.ToolResult;

import java.util.Map;

/**
 * Listener interface for tool execution events.
 */
public abstract class ToolExecutionListener {
    /**
     * Called before a tool is executed.
     */
    public abstract void beforeToolExecution(String toolName, Map<String, Object> parameters);

    /**
     * Called after a tool is successfully executed.
     */
    public abstract void afterToolExecution(ToolResult result);

    /**
     * Called when a tool execution fails.
     */
    public abstract void onToolExecutionError(ToolResult result, Exception exception);
}
