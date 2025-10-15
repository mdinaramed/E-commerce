package shop;
import java.math.BigDecimal;
import java.util.UUID;

public class Order {
    private final String id = UUID.randomUUID().toString();
    private final Customer customer;
    private BigDecimal amount;

    public Order(Customer customer, BigDecimal amount) {
        this.customer = customer;
        this.amount = amount;
    }
    public String id() {
        return id;
    }
    public Customer customer() {
        return customer;
    }
    public BigDecimal amount() {
        return amount;
    }
    public void amount(BigDecimal a) {
        amount = a;
    }
}
