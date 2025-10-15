package payment.decorator;
import payment.Payment;
import shop.Order;
import shop.PaymentResult;
import java.math.BigDecimal;


public class CashbackDecorator extends PaymentDecorator {
    private final int perUnit;

    public CashbackDecorator(Payment base, int perUnit) {
        super(base);
        this.perUnit = perUnit <= 0 ? 50 : perUnit;
    }

    @Override
    public PaymentResult pay(Order order, BigDecimal amount){

    PaymentResult res = super.pay(order, amount);
    if(res.success()) {
        int points = amount.divideToIntegralValue(BigDecimal.valueOf(perUnit)).intValue();
        order.customer().addPoints(points);
    }
    return res;
   }
}
