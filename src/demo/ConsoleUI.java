package demo;

import builder.OptionsBuilder;
import facade.CheckoutFacade;
import finance.Deposit;
import finance.DepositService;
import payment.Payment;
import payment.adapter.StripeAdapter;
import payment.factory.PaymentFactory;
import shop.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;


public class ConsoleUI {

    private final Scanner sc = new Scanner(System.in);
    private final CheckoutFacade checkout;
    private final DepositService deposits;
    private final PaymentFactory factory;
    private final CustomerRepository customerRepo;

    public ConsoleUI(CheckoutFacade checkout, DepositService deposits, PaymentFactory factory, CustomerRepository customerRepo) {
        this.checkout = checkout;
        this.deposits = deposits;
        this.factory = factory;
        this.customerRepo = customerRepo;
    }

    public void start() throws Exception {
        System.out.println(" Welcome to Di E-Commerce system");

        Customer customer = setupCustomer();

        while (true) {
            System.out.println("\n MAIN MENU ");
            System.out.println("1) Checkout (Buy products)");
            System.out.println("2) Manage Deposits");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            int choice = readInt(0, 2);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> checkoutMenu(customer);
                case 2 -> depositMenu(customer);
            }
        }
        System.out.println("Bye Bye :) ");
        sc.close();
    }

    // Customer
    private Customer setupCustomer() throws Exception {
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Your Email: ");
        String email = sc.nextLine().trim();

        Customer c = customerRepo.findByEmail(email).orElseGet(() -> new Customer(email, name));
        customerRepo.save(c);
        System.out.println("Profile is ready. Points you've got: " + c.points());
        return c;
    }

    //  Checkout flow (изолирован)
    private void checkoutMenu(Customer customer) throws Exception {
        System.out.println("\n CHECKOUT ");
        Cart cart = buildCart();
        if (cart.subTotal().compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("Cart is empty - nothing to buy.");
            return;
        }

        // выбор способа оплаты
        System.out.println("Payment type: 1) Card  2) PayPal  3) Stripe");
        int pm = readInt(1, 3);
        Payment payment = switch (pm) {
            case 2 -> {
                System.out.print("PayPal account: "); yield factory.createPayPal(sc.nextLine().trim());
            }
            case 3 -> {
                System.out.print("Stripe account: "); yield new StripeAdapter(sc.nextLine().trim());
            }
            default -> factory.createCreditCard("MasterCard **** **** **** 5678");
        };

        // опции Checkout
        OptionsBuilder ob = new OptionsBuilder();

        System.out.print("Apply discount? (y/n): ");
        if (yes()) {
            ob.discount(new BigDecimal("0.10"));
            System.out.println(" A 10% discount has been applied to your order");
        }
        ob.cashback();
        System.out.println("💰 Cashback enabled: 1 point per each 50 spent.");

        System.out.print("Enable fraud detection? (y/n): ");
        if (yes()) {
            System.out.print("Threshold: ");
            ob.fraud(readBig());
        }

        var options = ob.build();

        int before = customer.points();
        var res = checkout.processOrder(cart, customer, payment, options);
        int added = customer.points() - before;

        printReceipt(customer, res, added);
    }

    private Cart buildCart() {
        Cart cart = new Cart();
        int idx = 1;
        while (true) {
            System.out.print("Enter item price (Enter to finish): ");
            String s = sc.nextLine().trim();
            if (s.isEmpty()) break;
            try {
                BigDecimal p = new BigDecimal(s);
                cart.add(new CartItem(String.valueOf(idx++), "Item" + idx, 1, p));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
        System.out.println("Total: " + cart.subTotal());
        return cart;
    }

    private void printReceipt(Customer customer, shop.PaymentResult res, int added) throws Exception {
        System.out.println("\n------ RECEIPT ------");
        System.out.println("Customer: " + customer.name());
        System.out.println("Email:    " + customer.email());
        System.out.println("Charged:  " + res.charged());
        System.out.println("Status:   " + res.message());
        System.out.println("Txn ID:   " + res.txnId());
        if (added > 0) {
            System.out.println("Cashback: +" + added + " | total points: " + customer.points());
        }
        System.out.println("Date:     " + LocalDateTime.now());
        System.out.println("---------------------");

        customerRepo.save(customer);
    }

    //  Deposits flow изолированный
    private void depositMenu(Customer customer) throws Exception {
        while (true) {
            System.out.println("\n DEPOSITS ");
            System.out.println("1) Open deposit");
            System.out.println("2) Preview bonus for X days");
            System.out.println("3) Close deposit");
            System.out.println("4) List my deposits");
            System.out.println("0) Back to Main");
            System.out.print("Choose: ");
            int m = readInt(0, 4);
            if (m == 0) return;

            switch (m) {
                case 1 -> {
                    System.out.print("Amount: "); BigDecimal a = readBig();
                    System.out.print("Annual rate (0.15 for 15%): "); BigDecimal r = readBig();
                    int id = deposits.open(customer.email(), a, r);
                    System.out.println(" Opened deposit ID = " + id);
                }
                case 2 -> {
                    System.out.print("Amount: "); BigDecimal a = readBig();
                    System.out.print("Rate: "); BigDecimal r = readBig();
                    System.out.print("Days: "); int d = readInt(1, 3650);
                    System.out.println(" Bonus: " + deposits.previewBonusForDays(a, r, d));
                }
                case 3 -> {
                    System.out.print("Deposit ID: "); int id = readInt(1, Integer.MAX_VALUE);
                    System.out.println("💰 Total paid out: " + deposits.closeAndGetTotal(id));
                }
                case 4 -> {
                    var list = deposits.list(customer.email());
                    if (list.isEmpty()) System.out.println("(no deposits)");
                    else for (Deposit dep : list) {
                        System.out.println("#" + dep.id() + " "
                                + (dep.isActive() ? "ACTIVE" : "CLOSED")
                                + " principal=" + dep.principal()
                                + " rate=" + dep.rate()
                                + " opened=" + dep.openDate()
                                + (dep.closeDate() == null ? "" : " closed=" + dep.closeDate())
                                + (dep.profit() == null ? "" : " profit=" + dep.profit()));
                    }
                }
            }
        }
    }

    //  Input helpers
    private boolean yes() {
        String v = sc.nextLine().trim().toLowerCase();
        return v.startsWith("y") || v.startsWith("д");
    }

    private int readInt(int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.print("Enter a number from " + min + " to " + max + ": ");
        }
    }

    private BigDecimal readBig() {
        while (true) {
            try {
                return new BigDecimal(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Invalid number, try again: ");
            }
        }
    }
}