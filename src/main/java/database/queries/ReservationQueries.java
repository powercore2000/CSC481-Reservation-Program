package database.queries;
import org.javalite.activejdbc.Base;
import java.sql.*;
import java.util.*;

import backend.models.*;
import database.dto.ReservationDTO;

public class ReservationQueries {

    /* ---------- LIST RESERVATIONS FOR A USER ---------- */

    public static List<Map<String, Object>> forUser(long userId)
    {
        String sql = """
            SELECT r.id,
                   r.reservation_at,
                   r.party_size,
                   r.status,
                   r.confirmation_code,
                   rest.name AS restaurant_name
            FROM reservations r
            JOIN restaurants rest ON rest.id = r.restaurant_id
            WHERE r.user_id = ?
            ORDER BY r.reservation_at
        """;

        List<Map<String, Object>> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", rs.getLong("id"));
                    row.put("reservation_at", rs.getString("reservation_at"));
                    row.put("party_size", rs.getInt("party_size"));
                    row.put("status", rs.getString("status"));
                    row.put("confirmation_code", rs.getString("confirmation_code"));
                    row.put("restaurant_name", rs.getString("restaurant_name"));
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    /* ---------- LIST ALL RESERVATIONS -------------------*/
    public static List<Map<String, Object>> listAll()
    {
        String sql = """
            SELECT *
            FROM reservations r
            ORDER BY r.reservation_at
        """;

        List<Map<String, Object>> out = new ArrayList<>();
        
        System.out.println("Print all db entries");
        
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", rs.getLong("id"));
                    row.put("reservation_at", rs.getString("reservation_at"));
                    row.put("party_size", rs.getInt("party_size"));
                    row.put("status", rs.getString("status"));
                    row.put("confirmation_code", rs.getString("confirmation_code"));

                    // ⚠️ restaurant_name does not exist in SELECT * unless you join restaurants
                    // So this will remain null unless that column exists in your table.


                    // 🔥 LOG THE ENTRY
                    System.out.println("Reservation Found: " + row);

                    out.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

   
    
    /* ---------- CREATE A RESERVATION ---------- */

    public static void createReservation(ReservationDTO res) {
    	try {
    		
    		Base.open("org.sqlite.JDBC", DbManager.JDBC_URL, "", "");
    		
    		Base.exec("PRAGMA foreign_keys = ON;");
    		
    		ReservationModel resMod = ReservationMapper.toModel(res);
    		
    		if(!resMod.saveIt()) {
    			throw new RuntimeException("Could not save reservation: " + resMod.errors());
    		}
    		
    	}
	     catch (Exception e) {
	        e.printStackTrace();
	    }
    	finally {
    		Base.close();
    	}
    }
    
    public static long saveReservation(ReservationDTO res) {
    	UUID uuid = UUID.randomUUID();
    	System.out.println("Saving reservation");
    	return create(1L,1L,res.getTime(),res.getPartySize(),"Yes",uuid.toString(), "");
    }
    public static long create(long userId,
                       long restaurantId,
                       String reservationAt,   // 'YYYY-MM-DD HH:MM:SS'
                       int partySize,
                       String status,
                       String confirmationCode,
                       String specialRequests)
    {

        String sql = """
            INSERT INTO reservations
                (user_id, restaurant_id, reservation_at, party_size, status, confirmation_code, special_requests)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {

            ps.setLong(1, userId);
            ps.setLong(2, restaurantId);
            ps.setString(3, reservationAt);
            ps.setInt(4, partySize);
            ps.setString(5, status);
            ps.setString(6, confirmationCode);
            ps.setString(7, specialRequests);

            if (ps.executeUpdate() == 1) {
                try (ResultSet keys = ps.getGeneratedKeys())
                {
                    if (keys.next())
                    {
                        return keys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1L;
    }

    /* ---------- CANCEL A RESERVATION ---------- */

    public static boolean cancel(long reservationId)
    {
        String sql = "UPDATE reservations SET status = 'CANCELLED' WHERE id = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            ps.setLong(1, reservationId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ---------- ADD FOOD TO A RESERVATION ---------- */

    public static boolean addFood(long reservationId, long foodId, int quantity)
    {
        String sql = """
            INSERT INTO reservation_food (reservation_id, food_id, quantity)
            VALUES (?, ?, ?)
        """;
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            ps.setLong(1, reservationId);
            ps.setLong(2, foodId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ---------- LIST FOOD ITEMS FOR A RESERVATION ---------- */

    public static List<Map<String, Object>> foodForReservation(long reservationId)
    {
        String sql = """
            SELECT f.id,
                   f.name,
                   f.description,
                   f.price_cents,
                   f.category,
                   rf.quantity
            FROM reservation_food rf
            JOIN food f ON f.id = rf.food_id
            WHERE rf.reservation_id = ?
        """;

        List<Map<String, Object>> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            ps.setLong(1, reservationId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", rs.getLong("id"));
                    row.put("name", rs.getString("name"));
                    row.put("description", rs.getString("description"));
                    row.put("price_cents", rs.getInt("price_cents"));
                    row.put("category", rs.getString("category"));
                    row.put("quantity", rs.getInt("quantity"));
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
}