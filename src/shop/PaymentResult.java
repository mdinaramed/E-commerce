package shop;
import java.math.BigDecimal;

public record PaymentResult (boolean success, String txnId, BigDecimal charged, String message){}
