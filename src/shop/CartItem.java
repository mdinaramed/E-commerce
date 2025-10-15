package shop;
import java.math.BigDecimal;

public class CartItem {
    private final String num;
    private final String name;
    private final int quantity;
    private final BigDecimal price;

public CartItem (String num,String name, int quantity, BigDecimal price) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be positive");
        if (price == null) throw new IllegalArgumentException("price is null");
        this.num = num;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public BigDecimal subTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    public String num() {
    return num;
    }
    public String name() {
    return name;
    }
    public int quantity() {
    return quantity;
    }
    public BigDecimal price() {
    return price;
    }
}
