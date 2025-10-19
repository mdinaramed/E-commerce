package payment.adapter;
import payment.Payment;
import shop.Order;
import shop.PaymentResult;
import java.math.BigDecimal;

public class StripeAdapter implements Payment{
    static class StripeSDK {
        String charge(String account, double amount) {
            return "STRP-" + System.nanoTime();
        }
    }

    private final StripeSDK sdk = new StripeSDK();
    private final String account;

    public StripeAdapter(String account) {
        this.account = account;
    }

    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        String txn = sdk.charge(account, amount.doubleValue());
        return new PaymentResult(true, txn, amount, "Paid via Stripe " + account);
    }
}
