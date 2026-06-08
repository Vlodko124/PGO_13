public class CameraKit extends Equipment {
    private int lensCount;
    private boolean hasTripod;

    public CameraKit(String id, String name, double baseDailyPrice, int lensCount, boolean hasTripod) {
        super(id, name, baseDailyPrice);
        this.lensCount = lensCount;
        this.hasTripod = hasTripod;
    }

    @Override
    public double calculateDailyPrice() {
        double price = getBaseDailyPrice();

        price += lensCount * 10;

        if (hasTripod) {
            price += 15;
        }

        return price;
    }

    @Override
    public String getDetails() {
        return lensCount + " lens/lenses, " + (hasTripod ? "tripod" : "no tripod");
    }

    public int getLensCount() {
        return lensCount;
    }

    public boolean hasTripod() {
        return hasTripod;
    }
}
