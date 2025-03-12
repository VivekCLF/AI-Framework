package com.cloudframe.ai.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry of all available tools that can be used by AI agents.
 */
public class ToolRegistry {
    private final Map<String, Tool> tools = new HashMap<>();

    /**
     * Register a tool manually.
     */
    public void registerTool(Tool tool) {
        tools.put(tool.getName(), tool);
    }

    /**
     * Register all tools from an object by scanning for @Tool annotations.
     */
    public void registerToolsFromObject(Object toolProvider) {
        Class<?> clazz = toolProvider.getClass();

        for (Method method : clazz.getMethods()) {
            ToolAnnotation.AITool annotation = method.getAnnotation(ToolAnnotation.AITool.class);
            if (annotation != null) {
                String name = annotation.name().isEmpty() ?
                        method.getName() : annotation.name();

                Tool tool = new Tool(
                        name,
                        annotation.description(),
                        annotation.schema(),
                        annotation.requiresConfirmation(),
                        parameters -> {
                            try {
                                return method.invoke(toolProvider, parameters);
                            } catch (Exception e) {
                                throw new RuntimeException("Failed to execute tool: " + name, e);
                            }
                        }
                );

                registerTool(tool);
            }
        }
    }

    /**
     * Get a tool by name.
     */
    public Tool getTool(String name) {
        Tool tool = tools.get(name);
        if (tool == null) {
            throw new IllegalArgumentException("Tool not found: " + name);
        }
        return tool;
    }

    /**
     * Check if a tool exists.
     */
    public boolean hasTool(String name) {
        return tools.containsKey(name);
    }

    /**
     * Get all available tools.
     */
    public List<Tool> getAllTools() {
        return new ArrayList<>(tools.values());
    }

    /**
     * Get all tool definitions as JSON-compatible structures for AI consumption.
     */
    public List<Map<String, Object>> getToolDefinitionsForAi() {
        List<Map<String, Object>> definitions = new ArrayList<>();

        for (Tool tool : tools.values()) {
            Map<String, Object> def = new HashMap<>();
            def.put("name", tool.getName());
            def.put("description", tool.getDescription());

            if (!tool.getSchema().isEmpty()) {
                def.put("parameters", tool.getSchema());
            }

            definitions.add(def);
        }

        return definitions;
    }
}