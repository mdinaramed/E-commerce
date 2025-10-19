package payment.decorator;
import discountSystem.DiscountPolicy;
import discountSystem.PercentOff;
import payment.Payment;
import shop.Order;
import shop.PaymentResult;

import java.math.BigDecimal;

public class DiscountDecorator extends PaymentDecorator {
    private final DiscountPolicy policy;;

    public DiscountDecorator(Payment base, DiscountPolicy policy) {
        super(base);
        this.policy = policy;
    }
    public DiscountDecorator(Payment base, BigDecimal percent) {
        this(base, new PercentOff(percent));
    }
    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        BigDecimal discounted = policy.apply(amount);
        order.setAmount(discounted);
        return super.pay(order, discounted);
    }
}
