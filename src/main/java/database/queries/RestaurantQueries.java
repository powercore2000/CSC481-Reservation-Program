package database.queries;

import backend.models.RestaurantModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantQueries {

    /* ---------- LIST ALL RESTAURANTS (returns model) ---------- */

    public List<RestaurantModel> findAll()
    {
        String sql = "SELECT id, name, address, city, state FROM restaurants ORDER BY name";
        List<RestaurantModel> out = new ArrayList<>();

        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {

            while (rs.next())
            {
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

    /* ---------- WEEKLY SCHEDULE FOR A RESTAURANT ---------- */

    public List<Map<String, Object>> weeklySchedule(long restaurantId)
    {
        String sql = """
            SELECT weekday, open_time, close_time
            FROM restaurant_schedules
            WHERE restaurant_id = ?
            ORDER BY weekday
        """;

        List<Map<String, Object>> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            ps.setLong(1, restaurantId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("weekday", rs.getInt("weekday"));
                    row.put("open_time", rs.getString("open_time"));
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

    public List<String> tagsForRestaurant(long restaurantId)
    {
        String sql = "SELECT tag_name FROM restaurant_tags WHERE restaurant_id = ? ORDER BY tag_name";
        List<String> tags = new ArrayList<>();

        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            ps.setLong(1, restaurantId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    tags.add(rs.getString("tag_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    /* ---------- MENU (FOOD ITEMS) FOR A RESTAURANT ---------- */

    public List<Map<String, Object>> menuForRestaurant(long restaurantId)
    {
        String sql = """
            SELECT f.id,
                   f.name,
                   f.description,
                   f.price_cents,
                   f.category
            FROM food f
            JOIN restaurant_food rf ON rf.food_id = f.id
            WHERE rf.restaurant_id = ?
            ORDER BY f.name
        """;

        List<Map<String, Object>> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            ps.setLong(1, restaurantId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", rs.getLong("id"));
                    row.put("name", rs.getString("name"));
                    row.put("description", rs.getString("description"));
                    row.put("price_cents", rs.getInt("price_cents"));
                    row.put("category", rs.getString("category"));
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
}