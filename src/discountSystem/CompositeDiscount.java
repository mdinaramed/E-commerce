package discountSystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CompositeDiscount implements DiscountPolicy {
    private final List<DiscountPolicy> policies;
    public CompositeDiscount(List<DiscountPolicy> policies) {
        this.policies = policies == null ? new ArrayList<>() : policies;
    }
    @Override
    public BigDecimal apply(BigDecimal amount) {
        BigDecimal current = amount;
        for (DiscountPolicy p : policies) current = p.apply(current);
        return current;
    }
}