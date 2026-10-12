import java.util.*;

class Event {
    int id, seats;
    String name;

    Event(int id, String name, int seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }
}

class Booking {
    int id, userId, eventId, count;
    String status;

    Booking(int id, int userId, int eventId, int count) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.count = count;
        this.status = "CONFIRMED";
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static List<Event> events = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static Map<Integer, String> cache = new HashMap<>();
    static int nextId = 101;

    static void searchEvents() {
        System.out.println("\nAvailable Events:");
        for (Event e : events) {
            cache.put(e.id, e.name);
            System.out.println(e.id + ". " + e.name);
        }
        System.out.println("Event data cached.");
    }

    static void viewSeats() {
        System.out.print("Enter Event ID: ");
        int id = sc.nextInt();

        for (Event e : events) {
            if (e.id == id) {
                System.out.println("Available Seats: " + e.seats);
                return;
            }
        }
        System.out.println("404 - Event Not Found");
    }

    static void reserveSeat() {
        System.out.print("Enter User ID: ");
        int user = sc.nextInt();

        System.out.print("Enter Event ID: ");
        int eventId = sc.nextInt();

        System.out.print("Enter number of seats: ");
        int count = sc.nextInt();

        for (Event e : events) {
            if (e.id == eventId) {
                if (count > 0 && count <= e.seats) {
                    e.seats -= count;

                    Booking b = new Booking(
                        nextId++, user, eventId, count
                    );
                    bookings.add(b);

                    System.out.println("201 - Booking Successful!");
                    System.out.println("Booking ID: " + b.id);
                    System.out.println("Payment Successful!");
                    System.out.println("Ticket Generated!");
                } else {
                    System.out.println("409 - Seats Not Available!");
                }
                return;
            }
        }
        System.out.println("404 - Event Not Found");
    }

    static void bookingDetails() {
        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();

        for (Booking b : bookings) {
            if (b.id == id) {
                System.out.println("Booking ID: " + b.id);
                System.out.println("User ID: " + b.userId);
                System.out.println("Event ID: " + b.eventId);
                System.out.println("Seats: " + b.count);
                System.out.println("Status: " + b.status);
                return;
            }
        }
        System.out.println("404 - Booking Not Found");
    }

    static void cancelTicket() {
        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();

        for (Booking b : bookings) {
            if (b.id == id && b.status.equals("CONFIRMED")) {
                for (Event e : events) {
                    if (e.id == b.eventId) {
                        e.seats += b.count;
                        break;
                    }
                }
                b.status = "CANCELLED";
                System.out.println("200 - Ticket Cancelled Successfully!");
                return;
            }
        }
        System.out.println("404 - Booking Not Found or Already Cancelled");
    }

    static void showArchitecture() {
        System.out.println("\nSYSTEM ARCHITECTURE");
        System.out.println("Users");
        System.out.println("  |");
        System.out.println("Load Balancer");
        System.out.println("  |");
        System.out.println("API Gateway");
        System.out.println("  |");
        System.out.println("Event | Booking | Payment Services");
        System.out.println("  |");
        System.out.println("Database");
        System.out.println("  |");
        System.out.println("Cache and Message Queue");
        System.out.println("\nScalability: Load balancing and seat availability checks");
    }

    public static void main(String[] args) {
        events.add(new Event(1, "Movie Show", 50));
        events.add(new Event(2, "Music Concert", 100));

        int choice;

        do {
            System.out.println("\nONLINE TICKET BOOKING");
            System.out.println("1. Search Events");
            System.out.println("2. View Seats");
            System.out.println("3. Reserve Seat");
            System.out.println("4. Booking Details");
            System.out.println("5. Cancel Ticket");
            System.out.println("6. View System Architecture");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    searchEvents();
                    break;
                case 2:
                    viewSeats();
                    break;
                case 3:
                    reserveSeat();
                    break;
                case 4:
                    bookingDetails();
                    break;
                case 5:
                    cancelTicket();
                    break;
                case 6:
                    showArchitecture();
                    break;
                case 0:
                    System.out.println("Thank You!");
                    break;
                default:
                    System.out.println("400 - Invalid Choice!");
            }
        } while (choice != 0);

        sc.close();
    }
}
