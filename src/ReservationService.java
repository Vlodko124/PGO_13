import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private List<Student> students;
    private List<Equipment> equipmentList;
    private List<Reservation> reservations;
    private DiscountPolicy discountPolicy;
    private int nextReservationNumber;

    public ReservationService(List<Student> students, List<Equipment> equipmentList, DiscountPolicy discountPolicy) {
        this.students = students;
        this.equipmentList = equipmentList;
        this.discountPolicy = discountPolicy;
        this.reservations = new ArrayList<>();
        this.nextReservationNumber = 1;
    }

    public Reservation createReservation(String studentId, String equipmentId, int days) {
        Student student = findStudentById(studentId);
        Equipment equipment = findEquipmentById(equipmentId);

        if (student == null) {
            throw new IllegalArgumentException("Student " + studentId + " does not exist.");
        }

        if (equipment == null) {
            throw new IllegalArgumentException("Equipment " + equipmentId + " does not exist.");
        }

        if (!equipment.isAvailable()) {
            throw new IllegalArgumentException("Equipment " + equipmentId + " is not available.");
        }

        if (days < 1 || days > 14) {
            throw new IllegalArgumentException("Number of days must be between 1 and 14.");
        }

        String reservationId = String.format("R%03d", nextReservationNumber);
        nextReservationNumber++;

        Reservation reservation = new Reservation(
                reservationId,
                student,
                equipment,
                days,
                discountPolicy
        );

        equipment.setAvailable(false);
        reservations.add(reservation);

        return reservation;
    }

    public int returnEquipment(String reservationId) {
        Reservation reservation = findReservationById(reservationId);

        if (reservation == null) {
            throw new IllegalArgumentException("Reservation " + reservationId + " does not exist.");
        }

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalArgumentException("Reservation " + reservationId + " is not active.");
        }

        reservation.setStatus(ReservationStatus.RETURNED);
        reservation.getEquipment().setAvailable(true);

        int addedPoints = (int) (reservation.getTotalCost() / 10);
        reservation.getStudent().addLoyaltyPoints(addedPoints);

        return addedPoints;
    }

    public List<Equipment> findAvailableEquipment() {
        List<Equipment> availableEquipment = new ArrayList<>();

        for (Equipment equipment : equipmentList) {
            if (equipment.isAvailable()) {
                availableEquipment.add(equipment);
            }
        }

        return availableEquipment;
    }

    public void printStudents() {
        System.out.println("\nStudents:");

        for (Student student : students) {
            System.out.println(student.getDisplayText());
        }
    }

    public void printEquipment() {
        System.out.println("\nEquipment:");

        for (Equipment equipment : equipmentList) {
            System.out.println(equipment.getDisplayText());
        }
    }

    public void printActiveReservations() {
        System.out.println("\nActive reservations:");

        boolean found = false;

        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.ACTIVE) {
                System.out.println(reservation.getDisplayText());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No active reservations.");
        }
    }

    public void printReport() {
        System.out.println("\nCompleted reservations report:");

        boolean foundReturnedReservation = false;
        double totalRevenue = 0;

        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.RETURNED) {
                System.out.println(reservation.getDisplayText());
                totalRevenue += reservation.getTotalCost();
                foundReturnedReservation = true;
            }
        }

        if (!foundReturnedReservation) {
            System.out.println("No completed reservations.");
        }

        System.out.printf("Total revenue: %.2f PLN%n", totalRevenue);

        Student bestStudent = findStudentWithMostLoyaltyPoints();

        if (bestStudent != null) {
            System.out.println("Student with highest loyalty points:");
            System.out.println(bestStudent.getDisplayText());
        }
    }

    private Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }

        return null;
    }

    private Equipment findEquipmentById(String id) {
        for (Equipment equipment : equipmentList) {
            if (equipment.getId().equalsIgnoreCase(id)) {
                return equipment;
            }
        }

        return null;
    }

    private Reservation findReservationById(String id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equalsIgnoreCase(id)) {
                return reservation;
            }
        }

        return null;
    }

    private Student findStudentWithMostLoyaltyPoints() {
        if (students.isEmpty()) {
            return null;
        }

        Student bestStudent = students.get(0);

        for (Student student : students) {
            if (student.getLoyaltyPoints() > bestStudent.getLoyaltyPoints()) {
                bestStudent = student;
            }
        }

        return bestStudent;
    }
}public class ReservationService {
}
