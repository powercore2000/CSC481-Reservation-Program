package backend.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.IdName;

@Table("restaurants")
@IdName("id")
public class RestaurantModel extends Model {

    // Convenience getters / setters

    public Long getId() {
        return getLong("id");
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String address) {
        set("address", address);
    }

    public String getCity() {
        return getString("city");
    }

    public void setCity(String city) {
        set("city", city);
    }

    public String getState() {
        return getString("state");
    }

    public void setState(String state) {
        set("state", state);
    }

    public java.sql.Timestamp getCreatedAt() {
        return getTimestamp("created_at");
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        set("created_at", createdAt);
    }

    public java.sql.Timestamp getUpdatedAt() {
        return getTimestamp("updated_at");
    }

    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        set("updated_at", updatedAt);
    }
}
