import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Student> students = createSampleStudents();
        List<Equipment> equipmentList = createSampleEquipment();

        DiscountPolicy discountPolicy = new LoyaltyDiscountPolicy();
        ReservationService reservationService = new ReservationService(
                students,
                equipmentList,
                discountPolicy
        );

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            printMenu();

            int choice = readInt(scanner, "Choice: ");

            switch (choice) {
                case 1:
                    reservationService.printStudents();
                    break;

                case 2:
                    reservationService.printEquipment();
                    break;

                case 3:
                    createReservation(scanner, reservationService);
                    break;

                case 4:
                    returnEquipment(scanner, reservationService);
                    break;

                case 5:
                    reservationService.printActiveReservations();
                    break;

                case 6:
                    reservationService.printReport();
                    break;

                case 0:
                    running = false;
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static List<Student> createSampleStudents() {
        List<Student> students = new ArrayList<>();

        students.add(new Student("S001", "Anna Kowalska", "12c", 120));
        students.add(new Student("S002", "Marek Nowak", "12c", 40));
        students.add(new Student("S003", "Julia Zielinska", "13a", 0));

        return students;
    }

    private static List<Equipment> createSampleEquipment() {
        List<Equipment> equipmentList = new ArrayList<>();

        equipmentList.add(new LaptopSet("E001", "Lenovo ThinkPad Lab", 80, 32, true));
        equipmentList.add(new LaptopSet("E002", "Dell XPS Demo", 100, 16, false));
        equipmentList.add(new CameraKit("E003", "Sony Content Kit", 90, 3, true));
        equipmentList.add(new CameraKit("E004", "Canon Interview Kit", 70, 1, true));

        return equipmentList;
    }

    private static void printMenu() {
        System.out.println("\n===== Equipment Reservation System =====");
        System.out.println("1. Display the list of students");
        System.out.println("2. Display the list of equipment");
        System.out.println("3. Create a new reservation");
        System.out.println("4. Return equipment");
        System.out.println("5. Display active reservations");
        System.out.println("6. Display report");
        System.out.println("0. Exit");
    }

    private static void createReservation(Scanner scanner, ReservationService reservationService) {
        System.out.print("Enter student id: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter equipment id: ");
        String equipmentId = scanner.nextLine();

        int days = readInt(scanner, "Enter number of days: ");

        try {
            Reservation reservation = reservationService.createReservation(studentId, equipmentId, days);

            System.out.println("\nReservation " + reservation.getId() + " created.");
            System.out.println("Equipment: " + reservation.getEquipment().getName());
            System.out.printf("Cost: %.2f PLN%n", reservation.getTotalCost());
            System.out.println("Status: " + reservation.getStatus());
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void returnEquipment(Scanner scanner, ReservationService reservationService) {
        System.out.print("Enter reservation id: ");
        String reservationId = scanner.nextLine();

        try {
            int addedPoints = reservationService.returnEquipment(reservationId);
            System.out.println("Equipment returned. The student received " + addedPoints + " loyalty points.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
