package discountSystem;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentOff implements DiscountPolicy {
    private final BigDecimal percent;

    public PercentOff(BigDecimal percent) {
        if (percent == null || percent.signum() < 0 || percent.compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("PercentOff must be 0 or 1");
        this.percent = percent;
    }
    @Override
    public BigDecimal apply(BigDecimal amount) {
        return amount.subtract(amount.multiply(percent)).setScale(2, RoundingMode.HALF_UP);
    }
}
