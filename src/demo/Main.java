package demo;
import facade.CheckoutFacade;
import finance.DepositService;
import finance.PGDepositRepository;
import payment.factory.DefaultPaymentFactory;
import shop.CustomerRepositoryPg;

import facade.CheckoutFacade;
import finance.DepositService;
import finance.PGDepositRepository;
import payment.factory.DefaultPaymentFactory;
import payment.factory.PaymentFactory;
import shop.CustomerRepositoryPg;

public class Main {
    public static void main(String[] args) throws Exception {
        var customerRepo = new CustomerRepositoryPg();
        var checkout = new CheckoutFacade(customerRepo);
        var deposits = new DepositService(new PGDepositRepository());
        PaymentFactory factory = new DefaultPaymentFactory();

        var ui = new ConsoleUI(checkout, deposits, factory, customerRepo);
        ui.start();
    }
}