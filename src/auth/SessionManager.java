package auth;

import model.User;

public class SessionManager {
    private static User currentUser = null;
    private static String sessionEmail = null; // Store email separately as backup
    private static long sessionStartTime = 0;
    
    private SessionManager() {} // Prevent instantiation
    
    public static void setCurrentUser(User user) {
        currentUser = user;
        sessionStartTime = System.currentTimeMillis();
        
        // Store email separately as backup
        if (user != null) {
            try {
                sessionEmail = user.getEmail();
                System.out.println("Session started for: " + sessionEmail);
            } catch (Exception e) {
                System.out.println("Error getting email from user: " + e.getMessage());
                sessionEmail = null;
            }
        }
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }
    
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public static String getEmail() {
        // First try to get email from separate storage
        if (sessionEmail != null) {
            return sessionEmail;
        }
        
        // Fallback to getting from user object
        if (currentUser != null) {
            try {
                return currentUser.getEmail();
            } catch (Exception e) {
                System.out.println("Error getting email from user object: " + e.getMessage());
            }
        }
        
        return null;
    }
    
    public static void clearSession() {
        if (currentUser != null) {
            System.out.println("Session ended for: " + getEmail());
        }
        currentUser = null;
        sessionEmail = null;
        sessionStartTime = 0;
    }
    
    public static int getRoleId() {
        if (currentUser == null) return -1;
        return currentUser.getRoleId();
    }
    
    public static int getId() {
        if (currentUser == null) return -1;
        return currentUser.getId();
    }
    
    public static boolean hasRole(int roleId) {
        if (currentUser == null) return false;
        return currentUser.getRoleId() == roleId;
    }
    
    // Debug method
    public static void debugSession() {
        System.out.println("=== SESSION DEBUG ===");
        System.out.println("currentUser: " + currentUser);
        System.out.println("sessionEmail: " + sessionEmail);
        System.out.println("isLoggedIn(): " + isLoggedIn());
        System.out.println("getEmail(): " + getEmail());
        System.out.println("=====================");
    }
}