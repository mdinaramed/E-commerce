package shop;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void add(CartItem item) {
        items.add(item);
    }
    public BigDecimal subTotal() {
        return items.stream().map(CartItem::subTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
