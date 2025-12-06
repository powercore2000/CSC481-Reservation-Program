package backend.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import database.dto.ReservationDTO;            // from your DTO file
import backend.models.ReservationModel;        // from your ActiveJDBC model

public class ReservationMapper {

    /**
     * Converts a ReservationDTO (client request)
     * into a ReservationModel (ActiveJDBC database model).
     *
     * @param dto the incoming reservation request
     * @return ActiveJDBC ReservationModel ready to save()
     */
    public static ReservationModel toModel(ReservationDTO dto) {

        ReservationModel model = new ReservationModel();

        // Combine LocalDate + String time → Timestamp
        LocalDate date = dto.getDate();
        LocalTime time = LocalTime.parse(dto.getTime()); // assumes "HH:mm"
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        //model.set("user_id", userId);					//Convert from user table
        //model.set("restaurant_id", restaurantId); 	// Convert from restraunt table
        model.set("reservation_at", Timestamp.valueOf(dateTime));
        model.set("party_size", dto.getPartySize());
        model.set("status", dto.getStatus());
        model.set("confirmation_code", dto.getConfirmationCode());

        // Optional: if you want special_requests mapped later
        model.set("special_requests", null);

        return model;
    }

    /**
     * Converts a ReservationModel from the database
     * into a ReservationDTO for returning to the frontend.
     *
     * NOTE: The DTO currently does NOT include certain DB fields
     * such as user_id, restaurant_id, confirmation_code, status, etc.
     * Only fields that exist in the DTO are mapped back.
     */
    public static ReservationDTO toDTO(ReservationModel model) {

        // Extract LocalDate + time string from DB timestamp
        Timestamp ts = model.getReservationAt();
        LocalDateTime dateTime = ts.toLocalDateTime();

        return new ReservationDTO(
            model.getString("name"),                // Get from User table
            model.getString("email"),               // Get from User table
            model.getInteger("party_size"),
            dateTime.toLocalDate(),
            dateTime.toLocalTime().toString(),
            model.getString("status"),
            model.getConfirmationCode()
        );
    }
}
