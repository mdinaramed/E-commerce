package finance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DepositService {
    private final DepositRepository repo;
    private final Calculator calc;

    public DepositService(DepositRepository repo) {
        this.repo = repo;
        this.calc = new Calculator();
    }
    public int open(String email, BigDecimal amount, BigDecimal rate) throws Exception {
        return repo.insert(email, amount, rate, LocalDate.now());
    }

    public BigDecimal previewBonusForDays(BigDecimal amount, BigDecimal annualRate, int days) {
        return calc.forDays(amount, annualRate, days);
    }

    public BigDecimal previewBonusTillMonthEnd(BigDecimal amount, BigDecimal annualRate, LocalDate openDate) {
        return calc.tillMonthEnd(amount, annualRate, openDate);
    }

    public BigDecimal closeAndGetTotal(int depositId) throws Exception {
        Deposit d = repo.findById(depositId).orElseThrow();
        LocalDate closeDate = LocalDate.now();
        BigDecimal bonus = calc.fromTo(d.principal(), d.rate(), d.openDate(), closeDate.plusDays(1));
        repo.close(depositId, closeDate, bonus);
        return d.principal().add(bonus).setScale(2);
    }

    public List<Deposit> list(String email) throws Exception {
        return repo.findByCustomer(email);
    }

}
