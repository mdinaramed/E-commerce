package finance;
import db.Database;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PGDepositRepository implements DepositRepository {

    @Override
    public int insert(String email, BigDecimal principal, BigDecimal rate, LocalDate openDate) throws Exception {
        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO customers(email) VALUES (?) ON CONFLICT (email) DO NOTHING")) {
            ps.setString(1, email); ps.executeUpdate();
        }
        String sql = """
            INSERT INTO deposits (customer_email, principal, rate, open_date, active)
            VALUES (?, ?, ?, ?, TRUE)
            RETURNING id
        """;
        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setBigDecimal(2, principal);
            ps.setBigDecimal(3, rate);
            ps.setDate(4, Date.valueOf(openDate));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next(); return rs.getInt(1);
            }
        }
    }

    @Override
    public void close(int id, LocalDate closeDate, BigDecimal profit) throws Exception {
        String sql = "UPDATE deposits SET close_date=?, active=FALSE, profit=? WHERE id=? AND active=TRUE";
        try (Connection c = Database.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(closeDate));
            ps.setBigDecimal(2, profit);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<Deposit> findById(int id) throws Exception {
        String sql = "SELECT * FROM deposits WHERE id=?";
        try (Connection c = Database.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }

    @Override
    public List<Deposit> findByCustomer(String email) throws Exception {
        String sql = "SELECT * FROM deposits WHERE customer_email=? ORDER BY id";
        try (Connection c = Database.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            List<Deposit> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;
        }
    }

    private Deposit map(ResultSet rs) throws SQLException {
        return new Deposit(
                rs.getInt("id"),
                rs.getString("customer_email"),
                rs.getBigDecimal("principal"),
                rs.getBigDecimal("rate"),
                rs.getDate("open_date").toLocalDate(),
                rs.getDate("close_date") == null ? null : rs.getDate("close_date").toLocalDate(),
                rs.getBoolean("active"),
                rs.getBigDecimal("profit")
        );
    }
}