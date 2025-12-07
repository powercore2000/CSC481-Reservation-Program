package backend.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;

@Table("restaurant_tags")
@IdName("id")   // primary key
public class RestaurantTagModel extends Model {

    public Long getId() {
        return getLong("id");
    }

    public Long getRestaurantId() {
        return getLong("restaurant_id");
    }

    public void setRestaurantId(Long restaurantId) {
        set("restaurant_id", restaurantId);
    }

    public String getTagName() {
        return getString("tag_name");
    }

    public void setTagName(String tagName) {
        set("tag_name", tagName);
    }
}
