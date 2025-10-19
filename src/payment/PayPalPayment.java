package payment;
import shop.Order;
import shop.PaymentResult;

import java.math.BigDecimal;
import java.util.UUID;

public class PayPalPayment implements Payment {
    private final String account;

    public PayPalPayment(String account) {
        this.account = account;
    }

    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        return new PaymentResult(true,"PP-"+UUID.randomUUID(), amount,"Paid via PayPal "+account);
    }
}
