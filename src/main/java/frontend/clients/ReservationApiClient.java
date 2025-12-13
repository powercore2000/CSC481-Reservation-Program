package frontend.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import database.dto.FoodDTO;
import database.dto.ReservationDTO;
import database.dto.RestaurantDTO;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public final class ReservationApiClient {

    private static final String DEFAULT_BASE_URL = "http://localhost:8080";
    private static final String BASE_URL = resolveBaseUrl();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final TypeReference<List<FoodDTO>> FOOD_LIST_TYPE = new TypeReference<>() {};
    private static final TypeReference<List<ReservationDTO>> RESERVATION_LIST_TYPE = new TypeReference<>() {};
    private static final TypeReference<ReservationDTO> RESERVATION_TYPE = new TypeReference<>() {};

    private static volatile ReservationDTO cachedReservation;

    private ReservationApiClient() {
    }

    public static boolean addFoodToCurrentReservation(FoodDTO food) {
        if (food == null) {
            return false;
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/reservations/addFoodCurrentReservation"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(OBJECT_MAPPER.writeValueAsString(food)))
                    .build();

            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                return Boolean.parseBoolean(response.body());
            }

            logHttpFailure("addFoodToCurrentReservation", response);
        } catch (IOException e) {
            System.err.println("Failed to add food to reservation: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Request interrupted while adding food to reservation");
        }

        return false;
    }

    public static List<FoodDTO> getAllFoodFromCurrentReservation() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/reservations/getFoodCurrentReservations"))
                    .GET()
                    .build();

            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                List<FoodDTO> payload = OBJECT_MAPPER.readValue(response.body(), FOOD_LIST_TYPE);
                return payload == null ? Collections.emptyList() : payload;
            }

            logHttpFailure("getAllFoodFromCurrentReservation", response);
        } catch (IOException e) {
            System.err.println("Failed to fetch reservation food: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Request interrupted while fetching reservation food");
        }

        return Collections.emptyList();
    }

    public static List<ReservationDTO> getCurrentUserReservations() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/reservations/currentsReservations"))
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                List<ReservationDTO> payload = OBJECT_MAPPER.readValue(response.body(), RESERVATION_LIST_TYPE);
                return payload == null ? Collections.emptyList() : payload;
            }
            logHttpFailure("getCurrentUserReservations", response);
        } catch (IOException e) {
            System.err.println("Failed to fetch reservations: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Reservation request interrupted");
        }

        return Collections.emptyList();
    }


    public static ReservationDTO getCachedReservation() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/reservations/getCache"))
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                ReservationDTO payload = OBJECT_MAPPER.readValue(response.body(), RESERVATION_TYPE);
                System.out.println("Successfully retrived reservation.");
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
    public static void setCachedReservation(ReservationDTO reservation)
    {
        System.out.println("Selecting restaurant with ID: " + reservation.getConfirmationCode());
        try {String json = OBJECT_MAPPER.writeValueAsString(reservation);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildUri("/reservations/setCache"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (isSuccessful(response.statusCode())) {
                System.out.println("Successfully cached reservation.");
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

    private static URI buildUri(String path) {
        return URI.create(BASE_URL + path);
    }

    private static boolean isSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

    private static void logHttpFailure(String operation, HttpResponse<String> response) {
        System.err.printf(
                "ReservationApiClient.%s failed with status %d and body: %s%n",
                operation,
                response.statusCode(),
                response.body());
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
}
