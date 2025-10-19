package payment.bridge;

import payment.Payment;
import shop.Order;
import shop.PaymentResult;
import java.math.BigDecimal;

public class OnlinePayment extends PaymentBridge {
    public OnlinePayment(PaymentProvider provider) {
        super(provider);
    }
    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        return provider.process(order, amount);
    }
}
