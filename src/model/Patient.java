package model;

public class Patient {
    private int id;
    private String name;
    private String mobile;
    private String address;
    private String age;
    private String nic; // CHANGED from double to String
    private int branch_id;

    public int getBranch_id() {
        return branch_id;
    }

    public Patient(int id, int branch_id, String nic, String age, String mobile, String address, String name) {
        this.id = id;
        this.branch_id = branch_id;
        this.nic = nic;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public Patient() {
    }
}