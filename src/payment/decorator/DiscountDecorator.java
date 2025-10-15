package payment.decorator;
import payment.Payment;
import shop.Order;
import shop.PaymentResult;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountDecorator extends PaymentDecorator {
    private final BigDecimal percent;

    public DiscountDecorator(Payment base, BigDecimal percent) {
        super(base);
        if(percent.compareTo(BigDecimal.ZERO) < 0 || percent.compareTo(BigDecimal.ONE) > 0) throw new IllegalArgumentException("percentage must be between 0 and 1");
        this.percent = percent;
    }
    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        BigDecimal discounted = amount.subtract(amount.multiply(percent)).setScale(2, RoundingMode.HALF_UP);
        order.amount(discounted);
        return super.pay(order, discounted);
    }
}
