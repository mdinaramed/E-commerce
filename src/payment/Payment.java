package payment;

import shop.Order;
import shop.PaymentResult;

import java.math.BigDecimal;

public interface Payment {
    PaymentResult pay(Order order, BigDecimal amount);
}