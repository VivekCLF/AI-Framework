package com.example.cloudframe.ai.tools;

import com.cloudframe.ai.tools.Tool;
import com.cloudframe.ai.tools.execution.ToolExecutor;
import com.cloudframe.ai.tools.registry.ToolRegistry;
import com.cloudframe.ai.tools.result.ToolResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ToolExample {

    public static void main(String[] args) {
        try {
            // Create a registry and register tools
            ToolRegistry registry = new ToolRegistry();

            // Register tools from a tool provider class
            //TODO: I am debating the structure of a ToolProvider (provides a list of small tools). For simplicity if could be POJO tools container which uses registry
            //TODO:  tool should be abstract and can be extended. ToolRegistry - i need to hide and expose only management functions.
            //TODO: Schema - Provide a default JSON schema. For future, make it extentable so non cloudframe agents can call Cloudframe tool throgh cloudframe agents.
            WeatherTools weatherTools = new WeatherTools();
            registry.registerToolsFromObject(weatherTools);


            // Create a tool executor with a listener
            //ToolExecutor executor = new ToolExecutor(registry, new ToolExecutionLogger());
            ToolExecutor executor = new ToolExecutor(registry);

            // Execute tools
            Map<String, Object> weatherParams = new HashMap<>();
            weatherParams.put("city", "Bangalore");
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
        } catch (Exception e) {System.out.println(e.getMessage());}
    }
}