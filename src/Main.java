import shop.*;
import facade.CheckoutFacade;
import payment.CreditCardPayment;
import payment.PayPalPayment;
import java.math.BigDecimal;
public class Main {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(new CartItem("1", "Pants", 2, new BigDecimal("32.99")));
        cart.add(new CartItem("2", "Sneakers", 1, new BigDecimal("111.50")));

        Customer customer = new Customer("dinara@gmail.com");
        Customer customer1 = new Customer("aruzhan@gmail.com");
        CheckoutFacade checkoutFacade = new CheckoutFacade();
        CheckoutFacade.Options cc = new CheckoutFacade.Options();
        cc.discount = new BigDecimal("0.15");
        cc.fraud = true;


        var result1 = checkoutFacade.processOrder(cart, customer, new CreditCardPayment("**** **** **** 2345"), cc);
        System.out.println("CreditCard checkout");
        System.out.println(result1.success() ? "SUCCESS" : "FAILURE");
        System.out.println("Transaction number: " + result1.txnId());
        System.out.println("________________________");

        CheckoutFacade.Options pp = new CheckoutFacade.Options();
        pp.cashback = true;
        pp.cashbackUnit = 50;

        var result2 = checkoutFacade.processOrder(cart, customer, new PayPalPayment("ainur@icloud.com"), pp);
        System.out.println("PayPal checkout");
        System.out.println(result2.success() ? "SUCCESS" : "FAILURE");
        System.out.println("Transaction number: " + result2.txnId());
        System.out.println("________________________");
        System.out.println("\nCustomer points: " + customer.points());
    }
}