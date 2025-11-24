package database;

import java.sql.*;
import java.util.*;

public class ReservationQueries
{

    public List<Map<String,Object>> forUser(long userId)
    {
        String sql = """
            SELECT r.id, r.reservation_at, r.party_size, r.status, r.confirmation_code,
                   rest.name AS restaurant_name
            FROM reservations r
            JOIN restaurants rest ON rest.id = r.restaurant_id
            WHERE r.user_id = ?
            ORDER BY r.reservation_at
        """;
        List<Map<String,Object>> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    Map<String,Object> row = new HashMap<>();
                    row.put("id", rs.getLong("id"));
                    row.put("reservation_at", rs.getString("reservation_at"));
                    row.put("party_size", rs.getInt("party_size"));
                    row.put("status", rs.getString("status"));
                    row.put("confirmation_code", rs.getString("confirmation_code"));
                    row.put("restaurant_name", rs.getString("restaurant_name"));
                    out.add(row);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    public long create(long userId, long restaurantId, String reservationAt,
                       int partySize, String status, String confirmationCode, String specialRequests)
    {
        String sql = """
            INSERT INTO reservations (user_id, restaurant_id, reservation_at, party_size, status, confirmation_code, special_requests)
            VALUES (?,?,?,?,?,?,?)
        """;
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setLong(1, userId);
            ps.setLong(2, restaurantId);
            ps.setString(3, reservationAt);   // 'YYYY-MM-DD HH:MM:SS'
            ps.setInt(4, partySize);
            ps.setString(5, status);          // e.g., PENDING
            ps.setString(6, confirmationCode);
            ps.setString(7, specialRequests);
            if (ps.executeUpdate() == 1) {
                try (ResultSet keys = ps.getGeneratedKeys())
                {
                    if (keys.next()) return keys.getLong(1);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return -1L;
    }

    public boolean cancel(long reservationId)
    {
        String sql = "UPDATE reservations SET status = 'CANCELLED' WHERE id = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setLong(1, reservationId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}