package finance;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Calculator {
    private final int dayBase;
    public Calculator() {
        this(365); }
    public Calculator(int dayBase) {
        this.dayBase = dayBase <= 0 ? 365 : dayBase;
    }

    public BigDecimal forDays(BigDecimal principal, BigDecimal annualRate, int days) {
        BigDecimal fraction = new BigDecimal(days).divide(new BigDecimal(dayBase), 10, RoundingMode.HALF_UP);
        return principal.multiply(annualRate).multiply(fraction).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal fromTo(BigDecimal principal, BigDecimal annualRate, LocalDate from, LocalDate toExclusive) {
        int days = (int) ChronoUnit.DAYS.between(from, toExclusive);
        return forDays(principal, annualRate, Math.max(days, 0));
    }

    public BigDecimal tillMonthEnd(BigDecimal principal, BigDecimal annualRate, LocalDate startInclusive) {
        LocalDate monthEndPlusOne = startInclusive.withDayOfMonth(startInclusive.lengthOfMonth()).plusDays(1);
        return fromTo(principal, annualRate, startInclusive, monthEndPlusOne);
    }
}
