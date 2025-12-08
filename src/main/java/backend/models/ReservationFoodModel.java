package backend.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("reservation_food")
public class ReservationFoodModel extends Model {
    // ActiveJDBC handles the getters/setters automatically
    // Columns: id, reservation_id, food_id, quantity

    static {
        validatePresenceOf("reservation_id", "food_id", "quantity");
    }
}