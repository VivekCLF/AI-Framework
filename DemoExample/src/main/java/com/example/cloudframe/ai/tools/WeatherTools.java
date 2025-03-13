package com.example.cloudframe.ai.tools;

import com.cloudframe.ai.tools.annotation.ToolAnnotation.AITool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that provides weather-related tools.
 */
public class WeatherTools {

    @AITool(
            description = "Get the current weather for a city",
            schema = "{\"type\":\"object\",\"properties\":{\"city\":{\"type\":\"string\"}},\"required\":[\"city\"]}"
    )
    public Map<String, Object> getWeather(Map<String, Object> params) {
        String city = (String) params.get("city");
        // In a real implementation, this would call a weather API
        Map<String, Object> result = new HashMap<>();
        result.put("city", city);
        result.put("temperature", 22.5);
        result.put("conditions", "Partly Cloudy");
        result.put("humidity", 65);
        return result;
    }

    @AITool(
            name = "getForecast",
            description = "Get a 5-day weather forecast for a city",
            schema = "{\"type\":\"object\",\"properties\":{\"city\":{\"type\":\"string\"},\"days\":{\"type\":\"integer\",\"default\":5}},\"required\":[\"city\"]}",
            requiresConfirmation = true
    )
    public List<Map<String, Object>> getWeatherForecast(Map<String, Object> params) {
        // Implementation would call a forecast API
        return List.of(
                Map.of("day", 1, "temperature", 23.0, "conditions", "Sunny"),
                Map.of("day", 2, "temperature", 22.0, "conditions", "Cloudy"),
                Map.of("day", 3, "temperature", 21.0, "conditions", "Rain"),
                Map.of("day", 4, "temperature", 20.0, "conditions", "Partly Cloudy"),
                Map.of("day", 5, "temperature", 22.0, "conditions", "Sunny")
        );
    }

    @AITool(
            description = "Search the web for information",
            schema = "{\"type\":\"object\",\"properties\":{\"query\":{\"type\":\"string\"}},\"required\":[\"query\"]}"
    )
    public List<Map<String, Object>> searchWeb(Map<String, Object> params) {
        String query = (String) params.get("query");
        // Implementation would call a search API
        return List.of(
                Map.of("title", "Result 1", "url", "https://example.com/1", "snippet", "..."),
                Map.of("title", "Result 2", "url", "https://example.com/2", "snippet", "..."),
                Map.of("title", "Result 3", "url", "https://example.com/3", "snippet", "...")
        );
    }
}
