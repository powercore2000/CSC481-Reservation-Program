package frontend.clients;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import database.dto.UserDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class UserApiClient {

    private static final String DEFAULT_BASE_URL = "http://localhost:8080";
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private UserApiClient() {}

    public static boolean login(UserDTO credentials) {
        return postBoolean("/users/login", credentials, "login");
    }

    public static boolean signUp(UserDTO newUser) {
        return postBoolean("/users/signUp", newUser, "signUp");
    }

    private static boolean postBoolean(String path, UserDTO payload, String actionName) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri(path))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(OBJECT_MAPPER.writeValueAsString(payload)))
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return Boolean.parseBoolean(response.body());
            }
            logHttpFailure(actionName, response);
        } catch (IOException e) {
            System.err.printf("UserApiClient %s failed: %s%n", actionName, e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.printf("UserApiClient %s interrupted%n", actionName);
        }
        return false;
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

    private static void logHttpFailure(String action, HttpResponse<String> response) {
        System.err.printf(
                "UserApiClient.%s failed with status %d and body: %s%n",
                action,
                response.statusCode(),
                response.body());
    }
}
