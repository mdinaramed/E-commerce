package payment;
import shop.Order;
import shop.PaymentResult;

import java.math.BigDecimal;
import java.util.UUID;

public class CreditCardPayment implements Payment {
    private final String label;

    public CreditCardPayment(String label) {
        this.label = label;
    }

    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        return new PaymentResult(true,"CC-"+UUID.randomUUID(),amount,"Paid via  " + label);
    }
}
