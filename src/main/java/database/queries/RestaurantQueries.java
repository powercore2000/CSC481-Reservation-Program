package database.queries;

import backend.models.RestaurantModel;
import backend.models.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RestaurantQueries {

	
	private static RestaurantModel map(ResultSet rs) throws SQLException {
	    RestaurantModel r = new RestaurantModel();

	    // ActiveJDBC allows setting any column via set()
	    r.set("id", rs.getLong("id"));
	    r.setName(rs.getString("name"));
	    r.setAddress(rs.getString("address"));
	    r.setCity(rs.getString("city"));
	    r.setState(rs.getString("state"));


	    return r;
	}

    
    /* ---------- LIST ALL RESTAURANTS (returns model) ---------- */


    public static List<RestaurantModel> findAll()
    {
        String sql = "SELECT id, name, address, city, state FROM restaurants ORDER BY name";
        List<RestaurantModel> out = new ArrayList<>();

        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RestaurantModel r = new RestaurantModel();
                // ActiveJDBC Model.set(...) to populate fields
                r.set("id", rs.getLong("id"));
                r.setName(rs.getString("name"));
                r.setAddress(rs.getString("address"));
                r.setCity(rs.getString("city"));
                r.setState(rs.getString("state"));
                out.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return out;
    }


    public static Optional<RestaurantModel> findRestaurantById(long id){
    	
        String sql = """
                SELECT *
                FROM restaurants
                WHERE id = ?
            """;
        try (Connection c = DbManager.getConnection();
                PreparedStatement ps = c.prepareStatement(sql))
           {
               ps.setLong(1, id);
               try (ResultSet rs = ps.executeQuery())
               {
                   if (rs.next())
                   {
                       return Optional.of(map(rs));
                   }

               }
           } catch (SQLException e)
           {
               e.printStackTrace();
           }

           return Optional.empty();

    }

    /* ---------- LIST RESTAURANTS WITH TAGS (flattened rows) ---------- */
    // Each row = one restaurant + one tag (tag_name may be null if no tags)

    public static List<Map<String, Object>> restaurantsWithTags() {
        String sql = """
            SELECT
                r.id        AS restaurant_id,
                r.name      AS restaurant_name,
                r.address,
                r.city,
                r.state,
                t.tag_name
            FROM restaurants r
            LEFT JOIN restaurant_tags t
                ON t.restaurant_id = r.id
            ORDER BY r.name, t.tag_name
        """;

        List<Map<String, Object>> out = new ArrayList<>();

        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("restaurant_id",   rs.getLong("restaurant_id"));
                row.put("restaurant_name", rs.getString("restaurant_name"));
                row.put("address",         rs.getString("address"));
                row.put("city",            rs.getString("city"));
                row.put("state",           rs.getString("state"));
                row.put("tag_name",        rs.getString("tag_name")); // can be null
                out.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return out;
    }

    /* ---------- WEEKLY SCHEDULE FOR A RESTAURANT ---------- */

    public static List<Map<String, Object>> weeklySchedule(long restaurantId) {
        String sql = """
            SELECT weekday, open_time, close_time
            FROM restaurant_schedules
            WHERE restaurant_id = ?
            ORDER BY weekday
        """;

        List<Map<String, Object>> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("weekday",    rs.getInt("weekday"));
                    row.put("open_time",  rs.getString("open_time"));
                    row.put("close_time", rs.getString("close_time"));
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    /* ---------- TAGS FOR A RESTAURANT (Italian, Spicy, etc.) ---------- */


    public static List<String> tagsForRestaurant(long restaurantId)
    {
        String sql = "SELECT tag_name FROM restaurant_tags WHERE restaurant_id = ? ORDER BY tag_name";
        List<String> tags = new ArrayList<>();

        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tags.add(rs.getString("tag_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    /* ---------- MENU (FOOD ITEMS) FOR A RESTAURANT ---------- */
    // Uses new schema: restaurant_menus + menu_food + food

    public static List<Map<String, Object>> menuForRestaurant(long restaurantId)
    {
        String sql = """
            SELECT f.id,
                   f.name,
                   f.description,
                   f.price_cents,
                   f.category
            FROM restaurant_menus rm
            JOIN menu_food mf
                ON mf.restaurant_menu_id = rm.id
            JOIN food f
                ON f.id = mf.food_id
            WHERE rm.restaurant_id = ?
            ORDER BY f.name
        """;

        List<Map<String, Object>> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id",          rs.getLong("id"));
                    row.put("name",        rs.getString("name"));
                    row.put("description", rs.getString("description"));
                    row.put("price_cents", rs.getInt("price_cents"));
                    row.put("category",    rs.getString("category"));
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
}