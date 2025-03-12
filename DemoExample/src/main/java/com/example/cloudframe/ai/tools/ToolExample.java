package com.example.cloudframe.ai.tools;

import com.cloudframe.ai.tools.Tool;
import com.cloudframe.ai.tools.ToolExecutor;
import com.cloudframe.ai.tools.ToolRegistry;
import com.cloudframe.ai.tools.ToolResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ToolExample {

    public static void main(String[] args) {
        // Create a registry and register tools
        ToolRegistry registry = new ToolRegistry();

        // Register tools from a tool provider class
        WeatherTool weatherTool = new WeatherTool();
        registry.registerToolsFromObject(weatherTool);

        // Register a tool manually
        registry.registerTool(new Tool(
                "calculator",
                "Performs basic arithmetic operations",
                "{\"type\":\"object\",\"properties\":{\"operation\":{\"type\":\"string\",\"enum\":[\"add\",\"subtract\",\"multiply\",\"divide\"]},\"a\":{\"type\":\"number\"},\"b\":{\"type\":\"number\"}},\"required\":[\"operation\",\"a\",\"b\"]}",
                false,
                params -> {
                    String op = (String) params.get("operation");
                    double a = ((Number) params.get("a")).doubleValue();
                    double b = ((Number) params.get("b")).doubleValue();

                    return switch (op) {
                        case "add" -> a + b;
                        case "subtract" -> a - b;
                        case "multiply" -> a * b;
                        case "divide" -> {
                            if (b == 0) throw new IllegalArgumentException("Division by zero");
                            yield a / b;
                        }
                        default -> throw new IllegalArgumentException("Unknown operation: " + op);
                    };
                }
        ));

        // Create a tool executor with a listener
        ToolExecutor executor = new ToolExecutor(registry, new ToolExecutionLogger());

        // Execute tools
        Map<String, Object> weatherParams = new HashMap<>();
        weatherParams.put("city", "New York");
        ToolResult weatherResult = executor.execute("getWeather", weatherParams);
        System.out.println(weatherResult);

        Map<String, Object> calculatorParams = new HashMap<>();
        calculatorParams.put("operation", "add");
        calculatorParams.put("a", 5);
        calculatorParams.put("b", 3);
        ToolResult calcResult = executor.execute("calculator", calculatorParams);
        System.out.println(calcResult);

        // Execute asynchronously
        CompletableFuture<ToolResult> futureResult = executor.executeAsync("searchWeb",
                Map.of("query", "Java AI tools"));

        futureResult.thenAccept(result -> {
            System.out.println("Async result: " + result);
        });

        // Get tool definitions for AI consumption
        List<Map<String, Object>> toolDefs = registry.getToolDefinitionsForAi();
        System.out.println("Tool definitions for AI: " + toolDefs);
    }
}