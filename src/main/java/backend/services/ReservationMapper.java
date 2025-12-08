package backend.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.UUID;

import database.dto.ReservationDTO;            // from your DTO file
import database.queries.RestaurantQueries;
import database.queries.UserQueries;
import backend.controllers.RestaurantController;
import backend.models.ReservationModel;        // from your ActiveJDBC model
import backend.models.RestaurantModel;
import backend.models.UserModel;

public class ReservationMapper {

    /**
     * Converts a ReservationDTO (client request)
     * into a ReservationModel (ActiveJDBC database model)
     * to be parsed into the database.
     * * @param resDTO the incoming reservation request
     * @return ActiveJDBC ReservationModel ready to save()
     */
    public static ReservationModel toModel(ReservationDTO resDTO) {

        ReservationModel model = new ReservationModel();

        DateTimeFormatter fmt = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("H:mm")
                .optionalStart()
                .appendPattern(" a")
                .optionalEnd()
                .toFormatter();
        LocalDate date = resDTO.getDate();
        LocalTime time = LocalTime.parse(resDTO.getTime(), fmt);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        Timestamp ts = Timestamp.valueOf(dateTime);

        try {
            UserModel attatchedUser = UserQueries.findByEmail(resDTO.getEmail()).get();

            model.set("user_id",attatchedUser.getUserId());
            model.set("restaurant_id", RestaurantController.getSelectedRestaurantID());
            model.set("reservation_at",ts);
            model.set("party_size", resDTO.getPartySize());
            model.set("status", resDTO.getStatus());
            if( isStringNullOrEmpty( model.getConfirmationCode())) {
                model.set("confirmation_code", UUID.randomUUID().toString().subSequence(0, 10));
            }
            else {
                model.set("confirmation_code", model.getConfirmationCode());
            }

            // Optional: if you want special_requests mapped later
            model.set("special_requests", null);

            return model;
        } catch(Exception e) {
            System.out.println("Stopping model conversion: " + e.toString());
            return null;
        }
    }

    /**
     * Converts a ReservationModel from the database
     * into a ReservationDTO for returning to the frontend and backend.
     *
     * NOTE: The DTO currently does NOT include certain DB fields
     * such as user_id, restaurant_id, confirmation_code, status, etc.
     * Only fields that exist in the DTO are mapped back.
     */
    public static ReservationDTO toDTO(ReservationModel model) {

        // Extract LocalDate + time string from DB timestamp
        Timestamp ts = model.getReservationAt();
        LocalDateTime dateTime = ts.toLocalDateTime();
        UserModel attatchedUser = UserQueries.findById(model.getUserId()).get();
        RestaurantModel attatchedRestaurant = RestaurantQueries.findRestaurantById(model.getRestaurantId()).get();

        ReservationDTO newDTO =  new ReservationDTO(
                attatchedUser.getName(),                // Get from User table
                attatchedUser.getEmail(),               // Get from User table
                model.getPartySize(),
                dateTime.toLocalDate(),
                dateTime.toLocalTime().toString(),
                model.getStatus(),
                model.getConfirmationCode()
        );

        newDTO.setRestaurantLocation(attatchedRestaurant.getAddress());
        newDTO.setRestaurantName(attatchedRestaurant.getName());

        // --- NEW: Map ID fields so the DTO tracks the database keys ---
        newDTO.setId(model.getLong("id"));
        newDTO.setUserId(model.getLong("user_id"));
        newDTO.setRestaurantId(model.getLong("restaurant_id"));

        // --- NEW: Fetch and map food items for the cache ---
        java.util.List<java.util.Map<String, Object>> foodRows =
                database.queries.ReservationQueries.foodForReservation(model.getLong("id"));

        java.util.Map<Long, Integer> foodMap = new java.util.HashMap<>();
        for (java.util.Map<String, Object> row : foodRows) {
            Long foodId = (Long) row.get("id");
            Integer qty = (Integer) row.get("quantity");
            foodMap.put(foodId, qty);
        }
        newDTO.setFoodSelections(foodMap);

        return newDTO;
    }


    public static Boolean isStringNullOrEmpty(String str) {

        return  str == null || str.isBlank();
    }


}