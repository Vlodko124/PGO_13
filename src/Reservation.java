public class Reservation implements Displayable {
    private String id;
    private Student student;
    private Equipment equipment;
    private int days;
    private ReservationStatus status;
    private double totalCost;

    public Reservation(String id, Student student, Equipment equipment, int days, DiscountPolicy discountPolicy) {
        this.id = id;
        this.student = student;
        this.equipment = equipment;
        this.days = days;
        this.status = ReservationStatus.ACTIVE;
        this.totalCost = calculateTotalCost(discountPolicy);
    }

    public double calculateTotalCost(DiscountPolicy discountPolicy) {
        double priceBeforeDiscount = equipment.calculateDailyPrice() * days;
        return discountPolicy.applyDiscount(student, priceBeforeDiscount);
    }

    @Override
    public String getDisplayText() {
        return String.format(
                "%s | Student: %s | Equipment: %s | Days: %d | Cost: %.2f PLN | Status: %s",
                id,
                student.getFullName(),
                equipment.getName(),
                days,
                totalCost,
                status
        );
    }

    public String getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getDays() {
        return days;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
