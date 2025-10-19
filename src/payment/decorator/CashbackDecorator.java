package payment.decorator;

import payment.Payment;
import shop.Order;
import shop.PaymentResult;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CashbackDecorator extends PaymentDecorator {
    private final int perUnit; // 1 балл за каждые X денег

    public CashbackDecorator(Payment base, int perUnit) {
        super(base);
        this.perUnit = perUnit <= 0 ? 50 : perUnit;
    }

    @Override
    public PaymentResult pay(Order order, BigDecimal amount) {
        // сначала пройдём внутрь: тут применится Discount и order.amount() станет «после скидки»
        PaymentResult res = super.pay(order, amount);

        if (res.success()) {
            BigDecimal basis = order.amount(); // уже дисконтированная сумма
            int points = basis
                    .divide(BigDecimal.valueOf(perUnit), 0, RoundingMode.DOWN) // только целая часть
                    .intValue();
            order.customer().addPoints(points);
        }
        return res;
    }
}