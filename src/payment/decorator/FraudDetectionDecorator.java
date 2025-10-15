package payment.decorator;
import payment.Payment;
import shop.Order;
import shop.PaymentResult;
import java.math.BigDecimal;

public class FraudDetectionDecorator extends PaymentDecorator {
    public FraudDetectionDecorator(Payment base) {
        super(base);
    }
    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        if(amount.compareTo(new BigDecimal("5000")) >0){
            System.out.println("suspicious amount for order " + order.id());
        }
        return super.pay(order, amount);
    }
}
