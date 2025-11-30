import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean appRunning = true;

        while (appRunning) {
            System.out.println("\n=== LRU Cache Application ===");
            System.out.println("Choose mode:");
            System.out.println("  1. Hardcoded Demo (from project proposal)");
            System.out.println("  2. Interactive Mode");
            System.out.println("  3. Exit");

            int choice = 0;
            while (choice < 1 || choice > 3) {
                System.out.print("Enter your choice (1-3): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // consume bad input
                }
                scanner.nextLine(); // consume newline
            }

            switch (choice) {
                case 1:
                    runHardcodedDemo();
                    break;
                case 2:
                    runInteractiveMode(scanner); // Pass scanner to avoid re-creating
                    break;
                case 3:
                    System.out.println("Exiting application.");
                    appRunning = false;
                    break;
            }
        }
        scanner.close(); // Close scanner at the end of main
    }

    private static void runHardcodedDemo() {
        System.out.println("\n--- Running Hardcoded Demo ---");
        // Initialize LRU Cache with capacity 4 as per proposal
        LRUCache<Integer, String> cache = new LRUCache<>(4);

        System.out.println("Input Operations:");
        
        System.out.println("put(key=1, value=A)");
        cache.put(1, "A");
        
        System.out.println("put(key=2, value=B)");
        cache.put(2, "B");
        
        System.out.println("get(key=1)");
        cache.get(1);
        
        System.out.println("put(key=3, value=C)");
        cache.put(3, "C");
        
        System.out.println("put(key=4, value=D)");
        cache.put(4, "D");
        
        System.out.println("put(key=5, value=E)");
        cache.put(5, "E");
        
        System.out.println("get(key=3)");
        cache.get(3);

        System.out.println("\nOutput:");
        System.out.println("--------------------------------------------------");
        cache.printCache();
        System.out.println("--------------------------------------------------");
        System.out.println("--- Hardcoded Demo Finished ---\n\n\n\n\n");
    }

    private static void runInteractiveMode(Scanner scanner) {
        System.out.println("\n--- Entering Interactive Mode ---");
        // Get Capacity from user
        int capacity = 0;
        while (capacity <= 0) {
             System.out.print("Enter cache capacity (positive integer): ");
             if (scanner.hasNextInt()) {
                 capacity = scanner.nextInt();
             } else {
                 System.out.println("Invalid input. Please enter a number.");
                 scanner.next(); // consume bad input
             }
        }
        scanner.nextLine(); // consume the remaining newline character

        // Initialize Cache with Integer keys and String values
        LRUCache<Integer, String> cache = new LRUCache<>(capacity);
        System.out.println("Cache initialized with capacity: " + capacity);
        System.out.println("\nAvailable Commands:");
        System.out.println("  put <key> <value>   -> e.g., 'put 1 A'");
        System.out.println("  get <key>           -> e.g., 'get 1'");
        System.out.println("  print               -> Displays the current cache state");
        System.out.println("  exit                -> Quits interactive mode");

        // Command Loop
        boolean running = true;
        while (running) {
            System.out.print("\n> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "put":
                        if (parts.length < 3) {
                            System.out.println("Error: Usage is 'put <integer_key> <string_value>'");
                        } else {
                            int key = Integer.parseInt(parts[1]);
                            String value = parts[2];
                            cache.put(key, value);
                            System.out.println("OK. Put (" + key + ", " + value + ")");
                        }
                        break;

                    case "get":
                        if (parts.length < 2) {
                            System.out.println("Error: Usage is 'get <integer_key>'");
                        } else {
                            int key = Integer.parseInt(parts[1]);
                            String result = cache.get(key);
                            if (result == null) {
                                System.out.println("null (Key not found)");
                            } else {
                                System.out.println("Result: " + result);
                            }
                        }
                        break;

                    case "print":
                        System.out.println("--- Current Cache State ---");
                        cache.printCache();
                        System.out.println("---------------------------");
                        break;

                    case "exit":
                    case "quit":
                        running = false;
                        System.out.println("Exiting interactive mode.");
                        break;

                    default:
                        System.out.println("Unknown command. Try 'put', 'get', 'print', or 'exit'.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Key must be an integer.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("--- Interactive Mode Finished ---");
    }
}