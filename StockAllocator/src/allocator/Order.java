package allocator;

public class Order {
    private String ticker;
    private String side;
    private String instrumentType;
    private String region;
    private String account;

    public Order(String ticker, String side, String instrumentType) {
        this.ticker = ticker.trim().toUpperCase();
        this.side = side.trim().toLowerCase();
        this.instrumentType = instrumentType.trim().toLowerCase();
        this.region = SecurityMaster.getRegion(this.ticker);
    }

    // Getters
    public String getTicker() { return ticker; }
    public String getSide() { return side; }
    public String getInstrumentType() { return instrumentType; }
    public String getRegion() { return region; }
    public String getAccount() { return account; }

    // Setter
    public void setAccount(String account) { this.account = account; }
}