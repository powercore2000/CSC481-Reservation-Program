package database.queries;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import java.sql.*;
import java.util.*;
import database.queries.DbManager;
import backend.models.*;
import backend.services.ReservationMapper;
import database.dto.ReservationDTO;

public class ReservationQueries {

    /* ---------- LIST RESERVATIONS FOR A USER ---------- */

    public static List<ReservationModel> getAllReservationsForUser(long userId)
    {
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
    
/*
    public static Optional<ReservationDTO> getReservation(long confirmationCode) {
    	try {

    		
            String sql = """
                    SELECT *
                    FROM reservations r
                    JOIN restaurants rest ON rest.id = r.restaurant_id
                    WHERE r.confirmation_code = ?
                """;

                //ReservationModel out = new ReservationModel();
                try (Connection c = DbManager.getConnection();
                     PreparedStatement ps = c.prepareStatement(sql))
                {

                    ps.setLong(1, confirmationCode);
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

                            // Extra, joined column – not in reservations table, but fine as a transient attr


                            return Optional.of(ReservationMapper.toDTO(row));
                        }
                    }
                } 
                catch (SQLException e) {
                    e.printStackTrace();
                    DbManager.closeDatabase();
                } 
    	}          
    	
    	
    	
    	
    }
    
*/
    /* ---------- MASTER METHOD: CREATE RESERVATION + FOOD ---------- */

    /**
     * Creates the reservation record AND adds the food items in one go.
     * Call THIS method from Reservation Controller.
     */
    public static boolean createReservationWithFood(ReservationDTO dto, Map<Long, Integer> foodSelections) {

        // 1. Reuse EXISTING method to create the reservation record
        Optional<ReservationDTO> savedRes = createReservation(dto);

        // 2. If that worked, we get the new ID and save the food
        if (savedRes.isPresent()) {

            // Get the ID generated by the database
            long newReservationId = savedRes.get().getId();

            System.out.println("Reservation created with ID: " + newReservationId);

            // 3. Saves the food using the EXISTING helper
            if (foodSelections != null && !foodSelections.isEmpty()) {
                saveFoodBatch(newReservationId, foodSelections);
            }
            return true;
        }

        return false;
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

    /* ---------- BATCH ADD FOOD (helper method) ---------- */

    /**
     * Helper to add multiple food items to a reservation at once
     * @param reservationId --> The ID of the newly created reservation
     * @param foodItems --> A map where Key = FoodID (Long) and Value = Quality (Int)
     */

    public static void saveFoodBatch(long reservationId, Map<Long, Integer> foodItems) {
        if (foodItems == null || foodItems.isEmpty()) {
            return;
        }

        System.out.println("Processing food batch for Reservation ID: " + reservationId);

        for (Map.Entry<Long, Integer> entry : foodItems.entrySet()) {
            long foodId = entry.getKey();
            int quantity = entry.getValue();

            // Use existing addFood method
            boolean success = addFood(reservationId, foodId, quantity);

            if (!success) {
                System.err.println("Failed to add Food ID: " + foodId);
            }
        }
    }
}