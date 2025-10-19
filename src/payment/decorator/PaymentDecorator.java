package payment.decorator;
import payment.Payment;
import shop.Order;
import shop.PaymentResult;

import java.math.BigDecimal;

public abstract class PaymentDecorator implements Payment {
    protected final Payment base;
    protected PaymentDecorator(Payment base) {
        this.base = base;
    }
    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        return base.pay(order, amount);
    }
}
