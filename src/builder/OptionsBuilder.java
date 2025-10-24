package builder;

import discountSystem.DiscountPolicy;
import facade.CheckoutFacade;
import java.math.BigDecimal;
import java.util.List;

public class OptionsBuilder {
    private List<DiscountPolicy> discounts = List.of();
    private BigDecimal discount;

    private boolean fraud;
    private BigDecimal fraudThreshold;

    private boolean cashback;
    private int cashbackUnit = 50;

    public OptionsBuilder discounts(List<DiscountPolicy> list) {
        this.discounts = list;
        return this;
    }

    public OptionsBuilder discount(BigDecimal p) {
        this.discount = p;
        return this;
    }

    public OptionsBuilder fraud(BigDecimal threshold) {
        this.fraud = true;
        this.fraudThreshold = threshold;
        return this;
    }

    public OptionsBuilder cashback() {
        this.cashback = true;
        this.cashbackUnit = 50;
        return this;
    }

    public OptionsBuilder cashbackUnit(int unit) {
        this.cashback = true;
        this.cashbackUnit = (unit <= 0 ? 50 : unit);
        return this;
    }

    public CheckoutFacade.Options build() {
        return new CheckoutFacade.Options(discounts, discount, fraud, fraudThreshold, cashback, cashbackUnit);
    }
}