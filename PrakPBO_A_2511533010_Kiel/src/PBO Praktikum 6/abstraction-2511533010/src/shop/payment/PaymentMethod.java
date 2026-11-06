package shop.payment;

/** Contract for every payment method. */
public interface PaymentMethod {
    String getName();

    default long adminFee(long amount) {
        return 0;
    }

    /** @return true if the payment succeeds */
    boolean pay(long amount);
}
