package model;

public class User_Bean implements User {
    private int id;
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String mobile;
    private int userRoleId;
    private String branch;

    // Implement User interface methods
    @Override
    public String getUserName() {
        return email;
    }
    
    @Override
    public String getFname() {
        return fname;
    }
    
    @Override
    public String getLname() {
        return lname;
    }
    
    @Override
    public String getEmail() {
        return email;
    }
    
    @Override
    public int getRoleId() {
        return userRoleId;
    }
    
    @Override
    public boolean hasPermission(String permission) {
        // Simple permission logic
        return true;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public void setFname(String fname) { this.fname = fname; }
    public void setLname(String lname) { this.lname = lname; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setUserRoleId(int userRoleId) { this.userRoleId = userRoleId; }
}