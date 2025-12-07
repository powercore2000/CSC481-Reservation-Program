package backend.services;

import backend.models.RestaurantTagModel;
import database.dto.RestaurantTagDTO;

public class RestaurantTagMapper {

    public static RestaurantTagModel toModel(RestaurantTagDTO dto) {
        RestaurantTagModel model = new RestaurantTagModel();
        model.set("restaurant_id", dto.getRestaurantId());
        model.set("tag_name", dto.getTagName());
        return model;
    }

    public static RestaurantTagDTO toDTO(RestaurantTagModel model) {
        return new RestaurantTagDTO(
                model.getLong("id"),
                model.getLong("restaurant_id"),
                model.getString("tag_name")
        );
    }
}
