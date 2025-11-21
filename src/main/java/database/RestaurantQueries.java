package database;

import java.sql.*;
import java.util.*;

public class RestaurantQueries
{

    public List<Map<String,Object>> findAll()
    {
        String sql = "SELECT id, name, address, city, state FROM restaurants ORDER BY name";
        List<Map<String,Object>> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Map<String,Object> row = new HashMap<>();
                row.put("id", rs.getLong("id"));
                row.put("name", rs.getString("name"));
                row.put("address", rs.getString("address"));
                row.put("city", rs.getString("city"));
                row.put("state", rs.getString("state"));
                out.add(row);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    public List<Map<String,Object>> weeklySchedule(long restaurantId)
    {
        String sql = """
            SELECT weekday, open_time, close_time
            FROM restaurant_schedules
            WHERE restaurant_id = ?
            ORDER BY weekday
        """;
        List<Map<String,Object>> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setLong(1, restaurantId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    Map<String,Object> row = new HashMap<>();
                    row.put("weekday", rs.getInt("weekday"));
                    row.put("open_time", rs.getString("open_time"));
                    row.put("close_time", rs.getString("close_time"));
                    out.add(row);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }
}