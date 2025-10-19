package payment.decorator;
import payment.Payment;
import shop.Order;
import shop.PaymentResult;

import java.math.BigDecimal;

public class FraudDetectionDecorator extends PaymentDecorator {
    private final BigDecimal threshold;

    public FraudDetectionDecorator(Payment base, BigDecimal threshold) {
        super(base);
        this.threshold = threshold == null ? new BigDecimal("5000") : threshold;
    }

    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        if (amount.compareTo(threshold) > 0) {
            System.out.println("FRAUD suspicious amount " + amount + " for order " + order.id());
        }
        return super.pay(order, amount);
    }
}