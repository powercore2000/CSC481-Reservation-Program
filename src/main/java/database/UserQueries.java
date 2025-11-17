package database;

import main.java.backend.models.User;
import java.sql.*;
import java.util.*;

public class UserQueries
{
    public static final String JDBC_URL = "jdbc:h2:mem:resdb";
    public static final String DB_USER = "temp";
    public static final String DB_PASSWORD = "strongpassword";

    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();

        String sql = "SELECT full_name, email, phone, password_hash FROM app_users";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs  = stmt.executeQuery())
        {
            while (rs.next())
            {
                User user = new User();
                user.setName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone"));
                user.setPasswordHash(rs.getString("password_hash"));
                users.add(user);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserByEmail(String email)
    {
        String sql = "SELECT full_name, email, phone, password_hash FROM app_users WHERE email = ?";
        User user = null;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                user = new User();
                user.setName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone"));
                user.setPasswordHash(rs.getString("password_hash"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return user;
    }


}
