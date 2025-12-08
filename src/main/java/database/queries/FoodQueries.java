package database.queries;

import database.dto.FoodDTO;
import database.dto.ReservationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import backend.models.FoodModel;

public class FoodQueries {

    /* ---------- MAP HELPER ---------- */

    private static FoodDTO mapFoodDTO(ResultSet rs) throws SQLException {
        return new FoodDTO(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("price_cents"),
                rs.getString("category")
        );
    }
    
    private static FoodModel mapFoodModel(ResultSet rs) throws SQLException {
        FoodModel food = new FoodModel(              );
        food.setFoodId(rs.getLong("id"));
        food.setName(rs.getString("name"));
        food.setDescription(rs.getString("description"));
        food.setPriceCents( rs.getInt("price_cents"));
        food.setCategory(rs.getString("category"));
        return food;
    }

    /* ---------- LIST ALL FOOD ITEMS ---------- */

    public static List<FoodDTO> findAll() {
        String sql = "SELECT id, name, description, price_cents, category FROM food ORDER BY name";
        List<FoodDTO> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(mapFoodDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
    
    public static List<FoodModel> findAllReservationFood(ReservationDTO res) {
        String sql = "SELECT * FROM Reservation_Food WHERE reservation_id = ? ORDER BY name";
        List<FoodModel> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(mapFoodModel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    /* ---------- FIND FOOD BY ID ---------- */

    public static  Optional<FoodDTO> findById(long id) {
        String sql = "SELECT id, name, description, price_cents, category FROM food WHERE id = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapFoodDTO(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /* ---------- FOOD FOR A GIVEN RESTAURANT ---------- */

    public static List<FoodDTO> findByRestaurant(long restaurantId) {
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

        List<FoodDTO> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(mapFoodDTO(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    /* ---------- CREATE NEW FOOD ITEM ---------- */

    public static long insert(String name, String description, int priceCents, String category) {
        String sql = "INSERT INTO food (name, description, price_cents, category) VALUES (?, ?, ?, ?)";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, priceCents);
            ps.setString(4, category);

            if (ps.executeUpdate() == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /* ---------- ATTACH FOOD TO RESTAURANT MENU ---------- */

    public static boolean attachToReservation(long reservationId, long foodId) {
        String sql = """
                INSERT INTO restaurant_food (reservation_id, food_id)
                VALUES (?, ?)
                """;
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, reservationId);
            ps.setLong(2, foodId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}