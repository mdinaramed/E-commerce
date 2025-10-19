package payment.bridge;
import payment.Payment;
import shop.Order;
import shop.PaymentResult;

import java.math.BigDecimal;

public interface PaymentProvider {
    PaymentResult process(Order order, BigDecimal amount);
}
