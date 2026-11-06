package review.fixed;

import java.util.ArrayList;
import java.util.List;

public class BadShopRefactored {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));
        System.out.println("Total cart: " + cart.total());

        System.out.println("Ongkir Ekspres 2 kg: " + Courier.EXPRESS.cost(2));

        Address home = new Address("Jl. Sudirman 10", "Padang");
        SimpleOrder first = new SimpleOrder("ORD-1", home);
        SimpleOrder second = new SimpleOrder("ORD-2", new Address("Jl. Sudirman 10", "Bukittinggi"));
        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount();
        System.out.println("Harga flash sale: " + flashSale.apply(100_000));

        PaymentMethod voucher = new GiftVoucher();
        System.out.println(voucher.pay(30_000) + " | " + voucher.description());
    }

    private interface PaymentMethod {
        boolean pay(long amount);

        String description();
    }

    private static final class GiftVoucher implements PaymentMethod {
        private long balance = 100_000;

        @Override
        public boolean pay(long amount) {
            if (amount <= 0 || amount > balance) {
                return false;
            }
            balance -= amount;
            return true;
        }

        @Override
        public String description() {
            return "sisa Rp" + balance;
        }
    }

    private enum Courier {
        REGULAR(10_000),
        EXPRESS(18_000),
        SAME_DAY(30_000);

        private final long ratePerKilogram;

        Courier(long ratePerKilogram) {
            this.ratePerKilogram = ratePerKilogram;
        }

        long cost(int kilograms) {
            if (kilograms < 0) {
                throw new IllegalArgumentException("Weight must not be negative");
            }
            return ratePerKilogram * kilograms;
        }
    }

    private static final class Cart {
        private final List<Item> items = new ArrayList<>();

        private static final class Item {
            private final String name;
            private final long price;
            private final int quantity;

            private Item(String name, long price, int quantity) {
                if (name == null || name.isBlank() || price < 0 || quantity <= 0) {
                    throw new IllegalArgumentException("Invalid cart item");
                }
                this.name = name;
                this.price = price;
                this.quantity = quantity;
            }

            private long subtotal() {
                try {
                    return Math.multiplyExact(price, quantity);
                } catch (ArithmeticException overflow) {
                    throw new ArithmeticException("Subtotal overflow for " + name);
                }
            }
        }

        void add(Item item) {
            items.add(item);
        }

        long total() {
            long total = 0;
            for (Item item : items) {
                total += item.subtotal();
            }
            return total;
        }
    }

    private static final class Address {
        private final String street;
        private final String city;

        private Address(String street, String city) {
            if (street == null || street.isBlank() || city == null || city.isBlank()) {
                throw new IllegalArgumentException("Address fields must not be blank");
            }
            this.street = street;
            this.city = city;
        }

        @Override
        public String toString() {
            return street + ", " + city;
        }
    }

    private static final class SimpleOrder {
        private final String id;
        private final Address address;

        private SimpleOrder(String id, Address address) {
            if (id == null || id.isBlank() || address == null) {
                throw new IllegalArgumentException("Order ID and address are required");
            }
            this.id = id;
            this.address = address;
        }

        @Override
        public String toString() {
            return id + " -> " + address;
        }
    }

    private interface Discount {
        long apply(long price);
    }

    private static final class FlashSaleDiscount implements Discount {
        @Override
        public long apply(long price) {
            return price - price * 30 / 100;
        }
    }
}