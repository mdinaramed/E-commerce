package payment.bridge;
import shop.Order;
import shop.PaymentResult;
import java.math.BigDecimal;

public class VisaProvider implements PaymentProvider {

    @Override
    public PaymentResult process(Order order, BigDecimal amount) {
        return new PaymentResult(true, "VISA-" +System.nanoTime(), amount, "Paid via VISA");
    }
}
