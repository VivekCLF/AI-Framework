package com.cloudframe.ai.tools.exception;

/**
 * Exception thrown when a tool execution is denied due to permissions.
 */
public class ToolPermissionException extends ToolException {
    public ToolPermissionException(String toolName) {
        super(toolName, "Permission denied for tool: " + toolName, ToolErrorCode.PERMISSION_DENIED);
    }
}
