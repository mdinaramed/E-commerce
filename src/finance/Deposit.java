package finance;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Deposit {
    private final int id;
    private final String customerEmail;
    private final BigDecimal principal;
    private final BigDecimal rate;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private final boolean active;
    private final BigDecimal profit;

    public Deposit(int id, String customerEmail, BigDecimal principal, BigDecimal rate, LocalDate openDate, LocalDate closeDate, boolean active, BigDecimal profit) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.principal = principal;
        this.rate = rate;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.active = active;
        this.profit = profit;
    }

    public int id() {
        return id;
    }
    public String customerEmail() {
        return customerEmail;
    }
    public BigDecimal principal() {
        return principal;
    }
    public BigDecimal rate() {
        return rate;
    }
    public LocalDate openDate() {
        return openDate;
    }
    public LocalDate closeDate() {
        return closeDate;
    }
    public boolean isActive() {
        return active;
    }
    public BigDecimal profit() {
        return profit;
    }
}
