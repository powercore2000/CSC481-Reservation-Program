package database.queries;

import backend.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserQueries
{

    /* ------------ mapping helpers ------------ */
    private static User map(ResultSet rs) throws SQLException
    {
        User u = new User();
        u.setUserId(rs.getLong("id"));          // model field must exist
        u.setName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPhoneNumber(rs.getString("phone"));
        u.setPasswordHash(rs.getString("password_hash"));
        return u;
    }

    /* ------------------ READ ------------------ */

    public List<User> findAll()
    {
        String sql = "SELECT id, full_name, email, phone, password_hash FROM app_users ORDER BY id";
        List<User> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                out.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    public Optional<User> findById(long id)
    {
        String sql = "SELECT id, full_name, email, phone, password_hash FROM app_users WHERE id = ?";
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

    public Optional<User> findByEmail(String email)
    {
        String sql = "SELECT id, full_name, email, phone, password_hash FROM app_users WHERE email = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    return Optional.of(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /* ----------------- CREATE ----------------- */

    /** Inserts and returns generated id, or -1 on failure. */
    public long insert(User u)
    {
        String sql = "INSERT INTO app_users (full_name, email, phone, password_hash) VALUES (?,?,?,?)";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhoneNumber());
            ps.setString(4, u.getPasswordHash());
            if (ps.executeUpdate() == 1)
            {
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

    /* ----------------- UPDATE ----------------- */

    public boolean update(User u)
    {
        if (u.getUserId() == null) return false;

        String sql = "UPDATE app_users SET full_name = ?, email = ?, phone = ?, password_hash = ? WHERE id = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhoneNumber());
            ps.setString(4, u.getPasswordHash());
            ps.setLong(5, u.getUserId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ----------------- DELETE ----------------- */

    public boolean delete(long id)
    {
        String sql = "DELETE FROM app_users WHERE id = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

