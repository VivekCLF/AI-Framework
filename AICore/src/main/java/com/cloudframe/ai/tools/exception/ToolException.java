package com.cloudframe.ai.tools.exception;

/**
 * Base exception for all tool-related errors.
 */
public class ToolException extends Exception {
    private final String toolName;
    private final ToolErrorCode errorCode;

    public ToolException(String toolName, String message, ToolErrorCode errorCode) {
        super(message);
        this.toolName = toolName;
        this.errorCode = errorCode;
    }

    public ToolException(String toolName, String message, Throwable cause, ToolErrorCode errorCode) {
        super(message, cause);
        this.toolName = toolName;
        this.errorCode = errorCode;
    }


    public String getToolName() {
        return toolName;
    }

    public ToolErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Enumeration of possible error codes for tool operations.
     */
    public enum ToolErrorCode {
        TOOL_NOT_FOUND(1001, "The requested tool was not found in the registry"),
        EXECUTION_ERROR(1002, "An error occurred during tool execution"),
        INVALID_PARAMETERS(1003, "The parameters provided for the tool are invalid"),
        PERMISSION_DENIED(1004, "Permission to execute the tool was denied"),
        TIMEOUT(1005, "The tool execution timed out"),
        VALIDATION_FAILED(1006, "The tool input validation failed"),
        CONFIRMATION_REQUIRED(1007, "User confirmation is required before execution"),
        CONFIRMATION_DENIED(1008, "User denied confirmation for tool execution"),
        INTERNAL_ERROR(1099, "An internal error occurred in the tool system");

        private final int code;
        private final String description;

        ToolErrorCode(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}