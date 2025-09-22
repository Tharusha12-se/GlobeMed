package model;

public interface User {
    String getUserName();
    String getFname();
    String getLname();
    String getEmail(); // Add this method
    int getRoleId();
    int getId();
    boolean hasPermission(String permission);
}