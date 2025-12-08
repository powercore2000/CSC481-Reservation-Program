package backend.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import backend.models.UserModel;
import database.dto.UserDTO;

public class UserMapper {

    /**
     * Convert a UserModel (DB entity) to a UserDTO (client-facing object).
     * 
     * NOTE: By default this does NOT expose the password hash to the client.
     * If you really intend to send something back, you can explicitly set it below.
     */
    public static UserDTO toDTO(UserModel model) {
        if (model == null) {
            return null;
        }

        UserDTO dto = new UserDTO();

        dto.setUserId(model.getUserId());
        dto.setName(model.getName());
        dto.setEmail(model.getEmail());
        dto.setPhoneNumber(model.getPhoneNumber());

        // Optional / dangerous: only do this if you really want to send back
        // the password/hash-like value to the client.
        // dto.setPasswordString(model.getPasswordHash());

        return dto;
    }


    /**
     * Convert UserDTO (incoming client data) to a UserModel (DB entity).
     * 
     * Typically used when creating/updating a user.
     * You can decide where to hash the password:
     *  - either this method accepts an already-hashed password, or
     *  - you hash dto.getPasswordString() elsewhere and then call setPasswordHash.
     */
    public static UserModel toModel(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        UserModel model = new UserModel();

        model.setUserId(dto.getUserId());
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setPhoneNumber(dto.getPhoneNumber());

        // If passwordString is plain text from the client, you probably want to hash it
        // BEFORE setting it here. For now this just copies it over:
        if (!isStringNullOrEmpty(dto.getPasswordString())) {
            model.setPasswordHash(dto.getPasswordString());
        }

        return model;
    }

    /**
     * Utility method mirroring ReservationMapper.isStringNullOrEmpty(...)
     */
    public static Boolean isStringNullOrEmpty(String str) {
        return str == null || str.isBlank();
    }
}
