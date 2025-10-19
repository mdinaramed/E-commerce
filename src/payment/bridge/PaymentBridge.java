package payment.bridge;

import payment.Payment;
import shop.Order;
import shop.PaymentResult;
import java.math.BigDecimal;

public abstract class PaymentBridge {
    protected final PaymentProvider provider;
    protected PaymentBridge(PaymentProvider provider) {
        this.provider = provider;
    }
    public abstract PaymentResult pay(Order order, BigDecimal amount);
}
