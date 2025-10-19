package shop;
import java.util.Optional;

public interface CustomerRepository {
    void save(Customer c) throws Exception;
    Optional<Customer> findByEmail(String email) throws Exception;
}
