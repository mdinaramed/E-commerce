package payment.factory;

import payment.CreditCardPayment;
import payment.PayPalPayment;
import payment.Payment;

public class DefaultPaymentFactory implements PaymentFactory {
    @Override public Payment createCreditCard(String label) {
        return new CreditCardPayment(label);
    }
    @Override public Payment createPayPal(String account) {
        return new PayPalPayment(account);
    }
}