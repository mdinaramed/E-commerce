package finance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DepositRepository {
    int insert(String email, BigDecimal principal, BigDecimal rate, LocalDate openDate) throws Exception;
    void close(int id, LocalDate closeDate, BigDecimal profit) throws Exception;

    Optional<Deposit> findById(int id) throws Exception;
    List<Deposit> findByCustomer(String email) throws Exception;
}
