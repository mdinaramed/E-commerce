package facade;

import discountSystem.*;
import payment.Payment;
import payment.decorator.CashbackDecorator;
import payment.decorator.DiscountDecorator;
import payment.decorator.FraudDetectionDecorator;
import shop.*;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutFacade {

    private final CustomerRepository customerRepo;

    public CheckoutFacade(CustomerRepository repo) {
        this.customerRepo = repo;
    }

    public static final class Options {
        private final List<DiscountPolicy> discounts;
        private final BigDecimal discount;
        private final boolean fraud;
        private final BigDecimal fraudThreshold;
        private final boolean cashback;
        private final int cashbackUnit;

        public Options(List<DiscountPolicy> discounts, BigDecimal discount, boolean fraud, BigDecimal fraudThreshold, boolean cashback, int cashbackUnit) {
            this.discounts = discounts;
            this.discount = discount;
            this.fraud = fraud;
            this.fraudThreshold = fraudThreshold;
            this.cashback = cashback;
            this.cashbackUnit = cashbackUnit;
        }
        public List<DiscountPolicy> discounts(){
            return discounts;
        }
        public BigDecimal discount(){
            return discount;
        }
        public boolean fraud(){
            return fraud;
        }
        public BigDecimal fraudThreshold(){
            return fraudThreshold;
        }
        public boolean cashback(){
            return cashback;
        }
        public int cashbackUnit(){
            return cashbackUnit;
        }
    }

    public PaymentResult processOrder(Cart cart, Customer customer, Payment base, Options opt) {
        Order order = new Order(customer, cart.subTotal());
        Payment decorated = decorate(base, opt, order.amount());
        PaymentResult result = decorated.pay(order, order.amount());

        System.out.println(buildReceipt(order, result));

        if (result.success()) {
            try { customerRepo.save(customer); } catch (Exception ignore) {}
        }
        return result;
    }

    private Payment decorate(Payment base, Options opt, BigDecimal amount) {
        Payment p = base;
        if (opt != null) {
            if (opt.discounts()!=null && !opt.discounts().isEmpty()) {
                p = new DiscountDecorator(p, new CompositeDiscount(opt.discounts()));
            } else if (opt.discount()!=null) {
                p = new DiscountDecorator(p, new PercentOff(opt.discount()));
            }
            if (opt.cashback()) p = new CashbackDecorator(p, opt.cashbackUnit());
            if (opt.fraud()) {
                BigDecimal thr = opt.fraudThreshold()==null ? new BigDecimal("1000000") : opt.fraudThreshold();
                p = new FraudDetectionDecorator(p, thr);
            }
        }
        return p;
    }

    private String buildReceipt(Order order, PaymentResult result) {
        return "Order " + order.id() + "\n"
                + "Amount: " + order.amount() + "\n"
                + "Status: " + (result.success() ? "PAID" : "FAILED") + "\n"
                + "Message: " + result.message();
    }
}