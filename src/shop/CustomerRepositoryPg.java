package shop;
import db.Database;
import shop.Customer;
import shop.CustomerRepository;
import java.sql.*;
import java.util.Optional;

public class CustomerRepositoryPg implements CustomerRepository {
    @Override
    public void save(Customer c) throws Exception {
        String sql = """
            INSERT INTO customers (email, name, points) VALUES (?, ?, ?)
            ON CONFLICT (email)
            DO UPDATE SET name = EXCLUDED.name, points = EXCLUDED.points
        """;
        try (Connection conn = Database.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.email());
            ps.setString(2, c.name());
            ps.setInt(3, c.points());
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) throws Exception {
        String sql = "SELECT email, name, points FROM customers WHERE email = ?";
        try (Connection conn = Database.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Customer(
                            rs.getString("email"),
                            rs.getString("name"),
                            rs.getInt("points")
                    ));
                }
                return Optional.empty();
            }
        }
    }
}