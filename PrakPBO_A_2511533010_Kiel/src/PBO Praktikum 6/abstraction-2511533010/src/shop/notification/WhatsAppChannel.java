package shop.notification;

public class WhatsAppChannel extends AbstractChannel {
    private static final String PREFIX = "[Toko UMKM] ";

    @Override
    public String channelName() {
        return "WhatsApp";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String phone = contact.getPhone();
        boolean supportedPrefix = phone.startsWith("08") || phone.startsWith("+62");
        return supportedPrefix && phone.length() >= 10 ? phone : null;
    }

    @Override
    protected String prepare(String message) {
        return PREFIX + message;
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[WA to " + recipient + "] " + message);
        return true;
    }
}