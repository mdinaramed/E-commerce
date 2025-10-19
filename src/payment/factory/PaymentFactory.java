package payment.factory;

import payment.Payment;

public interface PaymentFactory {
    Payment createCreditCard(String label);
    Payment createPayPal(String account);
}