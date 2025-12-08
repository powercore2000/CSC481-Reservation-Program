package backend.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import backend.models.FoodModel;
import database.dto.FoodDTO;

public class FoodMapper {

    /**
     * Convert FoodModel (backend model) to FoodDTO (database/client DTO).
     */
    public static FoodDTO toDTO(FoodModel model) {
        if (model == null) {
            return null;
        }

        FoodDTO dto = new FoodDTO(
        		model.getFoodId(),
        		model.getName(),
        		model.getDescription(),
        		model.getPriceCents(),
        		model.getCategory()
        		);


        return dto;
    }

    /**
     * Convert list of FoodModel to list of FoodDTO.
     */
    public static List<FoodDTO> toDTOList(List<FoodModel> models) {
        if (models == null || models.isEmpty()) {
            return Collections.emptyList();
        }

        return models.stream()
                     .map(FoodMapper::toDTO)
                     .collect(Collectors.toList());
    }

    /**
     * Convert FoodDTO (e.g. from DB or request) to FoodModel (backend model).
     */
    public static FoodModel toModel(FoodDTO dto) {
        if (dto == null) {
            return null;
        }

        FoodModel model = new FoodModel();

        model.setFoodId(dto.getId());
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setPriceCents(dto.getPriceCents());
        model.setCategory(dto.getCategory());

        return model;
    }

    /**
     * Convert list of FoodDTO to list of FoodModel.
     */
    public static List<FoodModel> toModelList(List<FoodDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return Collections.emptyList();
        }

        return dtos.stream()
                   .map(FoodMapper::toModel)
                   .collect(Collectors.toList());
    }

    /**
     * Utility method (mirrors the pattern from ReservationMapper).
     */
    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.isBlank();
    }
}
