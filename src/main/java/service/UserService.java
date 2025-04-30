package service;

import model.User;
import jakarta.servlet.ServletContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static String FILE_PATH;
    private List<User> users;

    public UserService(ServletContext context) {
        users = new ArrayList<>();
        // Get the real path of the web application
        String realPath = context.getRealPath("/");
        // Navigate to the correct target directory
        FILE_PATH = realPath + "data" + File.separator + "users.txt";
        File file = new File(FILE_PATH);
        System.out.println("User data file absolute path: " + file.getAbsolutePath());
        loadUsers();
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean register(String username, String password, String email) {
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        // Create new user
        User newUser = new User(username, password, email, false);
        users.add(newUser);
        saveUsers();
        return true;
    }

    public boolean isAdmin(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.isAdmin();
            }
        }
        return false;
    }

    private void loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // Create default admin user if file doesn't exist
            file.getParentFile().mkdirs(); // Create directory if it doesn't exist
            users.add(new User("admin", "admin123", "admin@bookstore.com", true));
            saveUsers();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String username = parts[0];
                    String password = parts[1];
                    String email = parts[2];
                    boolean isAdmin = Boolean.parseBoolean(parts[3]);
                    users.add(new User(username, password, email, isAdmin));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.println(user.getUsername() + "," + user.getPassword() + "," + 
                             user.getEmail() + "," + user.isAdmin());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}