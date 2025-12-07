package database.queries;

import java.sql.*;
import java.util.*;

public class FoodQueries {

	/* ---------- MAP HELPER ---------- */

	private Map<String, Object> mapFood(ResultSet rs) throws SQLException {
		Map<String, Object> row = new HashMap<>();
		row.put("id", rs.getLong("id"));
		row.put("name", rs.getString("name"));
		row.put("description", rs.getString("description"));
		row.put("price_cents", rs.getInt("price_cents"));
		row.put("category", rs.getString("category"));
		return row;
	}

	/* ---------- LIST ALL FOOD ITEMS ---------- */

	public List<Map<String, Object>> findAll() {
		String sql = "SELECT id, name, description, price_cents, category FROM food ORDER BY name";
		List<Map<String, Object>> out = new ArrayList<>();
		try (Connection c = DbManager.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				out.add(mapFood(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	/* ---------- FIND FOOD BY ID ---------- */

	public Optional<Map<String, Object>> findById(long id) {
		String sql = "SELECT id, name, description, price_cents, category FROM food WHERE id = ?";
		try (Connection c = DbManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return Optional.of(mapFood(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	/* ---------- FOOD FOR A GIVEN RESTAURANT ---------- */

	public List<Map<String, Object>> findByRestaurant(long restaurantId) {
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
		try (Connection c = DbManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setLong(1, restaurantId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					out.add(mapFood(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	/* ---------- CREATE NEW FOOD ITEM ---------- */

	public long insert(String name, String description, int priceCents, String category) {
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

	public boolean attachToRestaurant(long restaurantId, long foodId) {
		String sql = """
				    INSERT INTO restaurant_food (restaurant_id, food_id)
				    VALUES (?, ?)
				""";
		try (Connection c = DbManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setLong(1, restaurantId);
			ps.setLong(2, foodId);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}