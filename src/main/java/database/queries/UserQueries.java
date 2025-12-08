package database.queries;

import backend.models.UserModel;
import database.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.javalite.activejdbc.Base;

public class UserQueries
{

    /* ------------ mapping helpers ------------ */
    private static UserModel map(ResultSet rs) throws SQLException
    {
        UserModel u = new UserModel();
        u.setUserId(rs.getLong("id"));          // model field must exist
        u.setName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPhoneNumber(rs.getString("phone"));
        u.setPasswordHash(rs.getString("password_hash"));
        return u;
    }

    /* ------------------ READ ------------------ */

    public static List<UserModel> findAll()
    {
        String sql = "SELECT * FROM app_users ORDER BY id";
        List<UserModel> out = new ArrayList<>();
        try (Connection c = DbManager.getConnection();
        		
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
        	
        	DbManager.openDatabase();
            while (rs.next())
            {
            	UserModel row = new UserModel();
            	row.setUserId( rs.getLong("id"));
                 row.setName(rs.getString("full_name"));
                 row.setEmail(rs.getString("email"));
                 row.setPhoneNumber(rs.getString("phone"));
                 row.setPasswordHash( rs.getString("password_hash"));
                 row.setCreatedAt(rs.getString("created_at"));

            	System.out.println("User Found: " + row);
                out.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {DbManager.closeDatabase();}
        return out;
    }

    public static Optional<UserModel> findById(long id)
    {
        String sql = "SELECT * FROM app_users WHERE id = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
        	DbManager.openDatabase();
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
        } finally {DbManager.closeDatabase();}

        return Optional.empty();
    }

    public static Optional<UserModel> findByEmail(String email)
    {
        String sql = "SELECT * FROM app_users WHERE email = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
        	DbManager.openDatabase();
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
        } finally {DbManager.closeDatabase();}
        return Optional.empty();
    }
    
    public static Optional<UserModel> findByEmailAndPassword(String email, String password)
    {
    	UserModel foundUser = findByEmail(email).orElse(null);
    	System.out.println("User found is : " + foundUser);
    	if(foundUser == null)
    		return Optional.empty();
    	
    	if(foundUser.doesPasswordMatch(password))   	
    		return Optional.of(foundUser);
    	
    	return Optional.empty();
    	
    }

    /* ----------------- CREATE ----------------- */

    /** Inserts and returns generated id, or -1 on failure. */
    public static long insert(UserDTO u)
    {
        String sql = "INSERT INTO app_users (full_name, email, phone, password_hash) VALUES (?,?,?,?)";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhoneNumber());
            ps.setString(4, u.getPasswordString());
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

    public static boolean update(UserDTO u)

    {
        if (u.getUserId() == null) return false;

        String sql = "UPDATE app_users SET full_name = ?, phone = ?, password_hash = ? WHERE email = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhoneNumber());
            ps.setString(4, u.getPasswordString());
            ps.setLong(5, u.getUserId());
            ps.setString(2, u.getPhoneNumber());
            ps.setString(3, u.getPasswordString());
            ps.setString(4, u.getEmail());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ----------------- DELETE ----------------- */

    public static boolean delete(long id)
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


    /* ----------------- SAVE (INSERT or UPDATE) ----------------- */

    /**
     * Saves a user using email as the unique identifier
    * If no user with that email exist --> INSERT
    * If user exist --> UPDATE
    * */
/*
    public User saveUser(User u)
    {
        Optional<User> existing = findByEmail(u.getEmail());

        if (existing.isEmpty())
        {
            // INSERT NEW USER
            long id = insert(u);
            if (id < 0) return null;
            u.setUserId(id);
            return u;
        } else {
            // UPDATE EXISTING USER (by email)
            boolean ok = updateByEmail(u);
            if (!ok) return null;

            // preserve existing userID
            u.setUserId(existing.get().getUserId());
            return u;
        }
    }
    */
}

