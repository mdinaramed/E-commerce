package discountSystem;
import java.math.BigDecimal;

public interface DiscountPolicy {
    BigDecimal apply(BigDecimal amount);
}
