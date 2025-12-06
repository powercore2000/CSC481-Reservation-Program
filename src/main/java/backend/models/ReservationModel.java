package backend.models;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import org.javalite.activejdbc.annotations.IdName;

@Table("reservations")  // optional, but explicit is nice
@IdName("id")           // primary key column
public class ReservationModel extends Model {

    static {
        // RELATIONSHIPS (based on foreign keys in schema)
        validatePresenceOf("user_id", "restaurant_id", "reservation_at",
                           "party_size", "status", "confirmation_code");
    }
    
    // --- Optional helper methods for nicer accessors ---

    public Long getUserId() {
        return getLong("user_id");
    }

    public Long getRestaurantId() {
        return getLong("restaurant_id");
    }

    public java.sql.Timestamp getReservationAt() {
        return getTimestamp("reservation_at");
    }

    public int getPartySize() {
        return getInteger("party_size");
    }

    public String getStatus() {
        return getString("status");
    }

    public String getConfirmationCode() {
        return getString("confirmation_code");
    }

    public String getSpecialRequests() {
        return getString("special_requests");
    }
    

}