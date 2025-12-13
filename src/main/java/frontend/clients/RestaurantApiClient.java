package frontend.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.dto.FoodDTO;
import database.dto.RestaurantDTO;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public final class RestaurantApiClient {

    private static final String DEFAULT_BASE_URL = "http://localhost:8080";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    private static final TypeReference<List<RestaurantDTO>> RESTAURANT_LIST_TYPE = new TypeReference<>() {};
    private static final TypeReference<RestaurantDTO> RESTAURANT_TYPE = new TypeReference<>() {};
    private static final TypeReference<List<FoodDTO>> FOOD_LIST_TYPE = new TypeReference<>() {};

    private static volatile String currentTagFilter;

    private RestaurantApiClient() {}

    public static void setCurrentTagFilter(String tag) {
        currentTagFilter = tag;
    }

    public static String getCurrentTagFilter() {
        return currentTagFilter;
    }
    public static void selectRestaurantById(long id)
    {
        System.out.println("Selecting restaurant with ID: " + id);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/restaurants/selectById/"+id))
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                return;
            }
            logHttpFailure("select restaurant", response);
        } catch (IOException e) {
            System.err.println("Failed to fetch menu: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Menu request interrupted");
        }
    }
    public static List<RestaurantDTO> fetchAllRestaurants() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/restaurants/listAll"))
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                List<RestaurantDTO> payload = OBJECT_MAPPER.readValue(response.body(), RESTAURANT_LIST_TYPE);
                return payload == null ? Collections.emptyList() : payload;
            }
            logHttpFailure("fetchAllRestaurants", response);
        } catch (IOException e) {
            System.err.println("Failed to fetch restaurants: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Restaurant request interrupted");
        }
        return Collections.emptyList();
    }

    public static List<RestaurantDTO> fetchRestaurantsByTag(String tag) {
        if (tag == null || tag.isBlank()) {
            return fetchAllRestaurants();
        }

        String encodedTag = URLEncoder.encode(tag, StandardCharsets.UTF_8);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/restaurants/byTag/" + encodedTag))
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                List<RestaurantDTO> payload = OBJECT_MAPPER.readValue(response.body(), RESTAURANT_LIST_TYPE);
                return payload == null ? Collections.emptyList() : payload;
            }
            logHttpFailure("fetchRestaurantsByTag", response);
        } catch (IOException e) {
            System.err.println("Failed to fetch restaurants by tag: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Restaurant-by-tag request interrupted");
        }

        return Collections.emptyList();
    }

    public static void selectRestaurant(RestaurantDTO restaurant) {
        selectRestaurantById(restaurant.getId());

    }

    public static RestaurantDTO getCurrentRestaurant() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/restaurants/getCurrentRestaurant"))
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                RestaurantDTO  payload = OBJECT_MAPPER.readValue(response.body(), RESTAURANT_TYPE);
                return payload;
            }
            logHttpFailure("select restaurant", response);
        } catch (IOException e) {
            System.err.println("Failed to fetch menu: " + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Menu request interrupted");
            return null;
        }
        return null;
    }

    public static List<FoodDTO> fetchCurrentRestaurantFood() {

        if (getCurrentRestaurant() == null) {
            return Collections.emptyList();
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/restaurants/allRestaurantFood"))
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                List<FoodDTO> payload = OBJECT_MAPPER.readValue(response.body(), FOOD_LIST_TYPE);
                return payload == null ? Collections.emptyList() : payload;
            }
            logHttpFailure("fetchCurrentRestaurantFood", response);
        } catch (IOException e) {
            System.err.println("Failed to fetch menu: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Menu request interrupted");
        }

        return Collections.emptyList();
    }

    private static URI buildUri(String path) {
        return URI.create(resolveBaseUrl() + path);
    }

    private static String resolveBaseUrl() {
        String property = System.getProperty("reservation.api.base-url");
        if (property != null && !property.isBlank()) {
            return sanitize(property);
        }

        String env = System.getenv("RESERVATION_API_BASE_URL");
        if (env != null && !env.isBlank()) {
            return sanitize(env);
        }

        return DEFAULT_BASE_URL;
    }

    private static String sanitize(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    private static boolean isSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

    private static void logHttpFailure(String operation, HttpResponse<String> response) {
        System.err.printf(
                "RestaurantApiClient.%s failed with status %d and body: %s%n",
                operation,
                response.statusCode(),
                response.body());
    }
}
