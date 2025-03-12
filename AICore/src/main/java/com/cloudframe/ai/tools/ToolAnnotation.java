package com.cloudframe.ai.tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A framework for defining and executing tools that can be used by AI agents.
 * Inspired by Spring AI and Lang4j but with a simplified and flexible approach.
 */
public class ToolAnnotation {

    /**
     * Annotation to mark a method as a tool that can be invoked by an AI.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AITool {
        /**
         * The name of the tool. If empty, the method name will be used.
         */
        String name() default "";

        /**
         * Description of what the tool does, used for AI to understand its purpose.
         */
        String description();

        /**
         * Schema information for the parameters, can be JSON Schema or other format.
         */
        String schema() default "";

        /**
         * Whether this tool requires explicit user confirmation before execution.
         */
        boolean requiresConfirmation() default false;
    }
}