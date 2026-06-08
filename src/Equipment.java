public abstract class Equipment implements Displayable {
    private String id;
    private String name;
    private double baseDailyPrice;
    private boolean available;

    public Equipment(String id, String name, double baseDailyPrice) {
        this.id = id;
        this.name = name;
        this.baseDailyPrice = baseDailyPrice;
        this.available = true;
    }

    public abstract double calculateDailyPrice();

    public abstract String getDetails();

    public String getEquipmentType() {
        return getClass().getSimpleName();
    }

    @Override
    public String getDisplayText() {
        return String.format(
                "%s | %s | Type: %s | Price: %.2f PLN/day | %s | %s",
                id,
                name,
                getEquipmentType(),
                calculateDailyPrice(),
                available ? "Available" : "Not available",
                getDetails()
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBaseDailyPrice() {
        return baseDailyPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
