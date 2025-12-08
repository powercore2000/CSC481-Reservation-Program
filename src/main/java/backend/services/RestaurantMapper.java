package backend.services;

import backend.models.RestaurantModel;
import database.dto.RestaurantDTO;

public class RestaurantMapper {

    /**
     * Map a RestaurantDTO to an ActiveJDBC RestaurantModel.
     */
    public static RestaurantModel toModel(RestaurantDTO dto) {
        if (dto == null) {
            return null;
        }

        RestaurantModel model = new RestaurantModel();

        // Only set id if it exists (useful when updating existing rows)
        if (dto.getId() != null) {
            model.set("id", dto.getId());
        }

        model.set("name", dto.getName());
        model.set("address", dto.getAddress());
        model.set("city", dto.getCity());
        model.set("state", dto.getState());

        return model;
    }

    /**
     * Map an ActiveJDBC RestaurantModel to a RestaurantDTO.
     */
    public static RestaurantDTO toDTO(RestaurantModel model) {
        if (model == null) {
            return null;
        }

        Long id       = model.getLong("id");
        String name   = model.getString("name");
        String addr   = model.getString("address");
        String city   = model.getString("city");
        String state  = model.getString("state");

        return new RestaurantDTO(id, name, addr, city, state);
    }
}
