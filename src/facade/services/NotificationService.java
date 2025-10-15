package facade.services;
import shop.Customer;

public class NotificationService {
    public void sendEmail(Customer customer,String subject,String content) {
        System.out.println("Email to : " + customer.email() + " | " + subject);
        System.out.println(content);
    }
}
