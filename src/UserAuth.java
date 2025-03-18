// Hao Phong Le
// N01605830

import java.io.*;
import java.util.Scanner;

public class UserAuth {
    private static final String USER_FILE = "users.txt";

    // Method to register a new user
    public void registerUser() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a new username: ");
        String username = sc.nextLine();

        System.out.print("Enter a new password: ");
        String password = sc.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
            System.out.println("User registered successfully!");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing to user file: " + e.getMessage());
        }
    }

    public void readUserFIle() throws FileNotFoundException {
        FileReader fr = new FileReader(USER_FILE);
        try (BufferedReader br = new BufferedReader(fr)) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to verify user login
    public boolean verifyLogin() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = sc.nextLine();

        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2) {
                    String storedUsername = credentials[0];
                    String storedPassword = credentials[1];

                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
