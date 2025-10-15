package payment;
import shop.Order;
import shop.PaymentResult;
import java.math.BigDecimal;
import java.util.UUID;

public class CreditCardPayment implements Payment {
    private final String masterCard;

    public CreditCardPayment(String masterCard) {
        this.masterCard = masterCard;
    }

    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        return new PaymentResult(true,"CC-"+UUID.randomUUID(),amount,"Paid via  "+masterCard);
    }
}
