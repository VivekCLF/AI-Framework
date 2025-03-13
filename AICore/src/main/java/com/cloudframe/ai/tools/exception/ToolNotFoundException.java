package com.cloudframe.ai.tools.exception;


/**
 * Exception thrown when a tool is not found in the registry.
 */
public class ToolNotFoundException extends ToolException {
    public ToolNotFoundException(String toolName) {
        super(toolName, "Tool not found: " + toolName, ToolErrorCode.TOOL_NOT_FOUND);
    }
}