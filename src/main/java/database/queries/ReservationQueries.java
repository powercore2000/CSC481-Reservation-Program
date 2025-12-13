package database.queries;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import java.sql.*;
import java.util.*;
import database.queries.DbManager;
import backend.models.*;
import backend.services.ReservationMapper;
import database.dto.FoodDTO;
import database.dto.ReservationDTO;

public class ReservationQueries {

    /* ---------- LIST RESERVATIONS FOR A USER ---------- */

    public static List<ReservationModel> getAllReservationsForUser(long userId)
    {
    	System.out.println("Grabbing reservations for user: " + userId);
        String sql = """
            SELECT *
            FROM reservations r
            JOIN restaurants rest ON rest.id = r.restaurant_id
            WHERE r.user_id = ?
            ORDER BY r.reservation_at
        """;

        List<ReservationModel> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery())
            {
        		DbManager.openDatabase();
                while (rs.next())
                {
                	ReservationModel row = new ReservationModel();

                    // Required columns (match your validatePresenceOf + PK)
                    row.set("id",             rs.getLong("id"));
                    row.set("user_id",        rs.getLong("user_id"));
                    row.set("restaurant_id",  rs.getLong("restaurant_id"));
                    row.set("reservation_at", rs.getTimestamp("reservation_at"));
                    row.set("party_size",     rs.getInt("party_size"));
                    row.set("status",         rs.getString("status"));
                    row.set("confirmation_code", rs.getString("confirmation_code"));
                    System.out.println("Found reservation: " + row);
                    // Extra, joined column – not in reservations table, but fine as a transient attr


                    out.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {DbManager.closeDatabase();}
        return out;
    }

    /* ---------- LIST ALL RESERVATIONS -------------------*/
    public static List<ReservationModel> listAll()
    {
        String sql = """
            SELECT *
            FROM reservations r
            ORDER BY r.reservation_at
        """;

        List<ReservationModel> out = new ArrayList<>();
        
        System.out.println("Print all db entries");
        
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {

            try (ResultSet rs = ps.executeQuery())
            {
            	DbManager.openDatabase();
                while (rs.next())
                {
                	ReservationModel row = new ReservationModel();

                    // Required columns (match your validatePresenceOf + PK)
                    row.set("id",             rs.getLong("id"));
                    row.set("user_id",        rs.getLong("user_id"));
                    row.set("restaurant_id",  rs.getLong("restaurant_id"));
                    row.set("reservation_at", rs.getTimestamp("reservation_at"));
                    row.set("party_size",     rs.getInt("party_size"));
                    row.set("status",         rs.getString("status"));
                    row.set("confirmation_code", rs.getString("confirmation_code"));

                    // ⚠️ restaurant_name does not exist in SELECT * unless you join restaurants
                    // So this will remain null unless that column exists in your table.
					ReservationDTO dto = ReservationMapper.toDTO(row);

                    // 🔥 LOG THE ENTRY
                    System.out.println("Reservation Found: " + row);
                    System.out.println("Display Resv Here: " + dto);

                    out.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {DbManager.closeDatabase();}
        return out;
    }

   
    
    /* ---------- CREATE A RESERVATION ---------- */

    public static Optional<ReservationDTO> createReservation(ReservationDTO res) {
    	try {

    		System.out.println("Creating a reservation!");
    		ReservationModel resMod = ReservationMapper.toModel(res);
    		
    		
    		DbManager.openDatabase();
    		if(resMod == null) {
    			System.out.println("Not adding reservation to db");
    			return Optional.empty();
    		}
    		if(!resMod.saveIt()) {
    			throw new RuntimeException("Could not save reservation: " + resMod.errors());
    		}
    		// Create succeeded
    		else {
    			ReservationDTO filledDTO = ReservationMapper.toDTO(resMod);
    			System.out.println("Created reservation: " + filledDTO);
    			return Optional.of(filledDTO);
    		}
    		
    	}
	     catch (Exception e) {
	        e.printStackTrace();
	        return Optional.empty();
	    }
    	finally {
    		DbManager.closeDatabase();
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


}