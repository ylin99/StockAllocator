package allocator;

public class Rule {
    private int priority;
    private String ticker;
    private String side;
    private String instrumentType;
    private String region;
    private String account;

    public Rule(int priority, String ticker, String side,
                String instrumentType, String region, String account) {
        this.priority = priority;
        this.ticker = ticker;
        this.side = side;
        this.instrumentType = instrumentType;
        this.region = region;
        this.account = account;
    }

    public boolean matches(Order order) {
        return (ticker.isEmpty() || ticker.equalsIgnoreCase(order.getTicker())) &&
                (side.isEmpty() || side.equalsIgnoreCase(order.getSide())) &&
                (instrumentType.isEmpty() || instrumentType.equalsIgnoreCase(order.getInstrumentType())) &&
                (region.isEmpty() || region.equalsIgnoreCase(order.getRegion()));
    }

    // Getters
    public int getPriority() { return priority; }
    public String getAccount() { return account; }
    public String getTicker() { return ticker; }
    public String getSide() { return side; }
    public String getInstrumentType() { return instrumentType; }
    public String getRegion() { return region; }
}