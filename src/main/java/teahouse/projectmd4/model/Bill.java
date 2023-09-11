package teahouse.projectmd4.model;

public class Bill {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String note;

    public Bill() {
    }

    public Bill(int id, String name, String address, String phone, String email, String note) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.note = note;
    }

    public Bill(int id, String name, String address, String phone, String note) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.note = note;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
