package service;

import model.User;
import jakarta.servlet.ServletContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static String FILE_PATH;
    private List<User> users;
    private int nextId = 1; // For assigning new IDs

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
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        User newUser = new User(nextId++, username, password, email, false); // Assign new ID
        users.add(newUser);
        saveUsers();
        return true;
    }

    public boolean changePassword(int userId, String currentPassword, String newPassword) {
        for (User user : users) {
            if (user.getId() == userId) {
                if (user.getPassword().equals(currentPassword)) {
                    user.setPassword(newPassword);
                    saveUsers();
                    return true;
                } else {
                    return false; // Current password incorrect
                }
            }
        }
        return false; // User not found
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
            file.getParentFile().mkdirs();
            // Add default admin with ID 1
            users.add(new User(nextId++, "admin", "admin123", "admin@bookstore.com", true));
            saveUsers();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int maxId = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) { // id,username,password,email,isAdmin
                    int id = Integer.parseInt(parts[0]);
                    String username = parts[1];
                    String password = parts[2];
                    String email = parts[3];
                    boolean isAdmin = Boolean.parseBoolean(parts[4]);
                    users.add(new User(id, username, password, email, isAdmin));
                    if (id > maxId) {
                        maxId = id;
                    }
                } else if (parts.length == 4) { // Legacy: username,password,email,isAdmin
                    // Assign a new ID for old format for forward compatibility,
                    // though this means IDs might change on first load if mixed formats exist.
                    // Best to migrate users.txt fully.
                    int id = nextId++; // Or some other temporary ID assignment strategy
                    String username = parts[0];
                    String password = parts[1];
                    String email = parts[2];
                    boolean isAdmin = Boolean.parseBoolean(parts[3]);
                    users.add(new User(id, username, password, email, isAdmin));
                    if (id > maxId) { // Update maxId for the newly assigned ID
                        maxId = id;
                    }
                }
            }
            nextId = maxId + 1; // Ensure nextId is greater than any loaded ID
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            // Consider what to do if file is corrupt or unreadable
            // For now, if loading fails, we might start with an empty list or default admin
            if (users.isEmpty()) {
                if (file.exists()) file.delete(); // Attempt to delete corrupted file to allow recreation
                file.getParentFile().mkdirs();
                users.clear(); // Clear any partially loaded users
                nextId = 1; // Reset nextId
                users.add(new User(nextId++, "admin", "admin123", "admin@bookstore.com", true));
                saveUsers();
            }
        }
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.println(user.getId() + "," + user.getUsername() + "," +
                        user.getPassword() + "," + user.getEmail() + "," + user.isAdmin());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}