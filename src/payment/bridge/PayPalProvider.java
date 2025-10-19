package payment.bridge;
import shop.Order;
import shop.PaymentResult;
import java.math.BigDecimal;

public class PayPalProvider implements PaymentProvider {

    @Override
    public PaymentResult process(Order order, BigDecimal amount) {
        return new PaymentResult(true, "PP-" +System.nanoTime(), amount, "Paid via PayPal");
    }
}
