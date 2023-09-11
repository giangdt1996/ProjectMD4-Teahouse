package teahouse.projectmd4.model;

import java.util.Date;

public class Order {
    private Long idOrder;
    private Long id_customer;
    private Date order_at;
    private String email;
    private String name;
    private String address;
    private String phone;
    private String note;
    private double total_price;
    private boolean status = true;

    public Order() {
    }

    public Order(Long idOrder, Long id_customer, Date order_at, String email, String name, String address, String phone, String note, float total_price, boolean status) {
        this.idOrder = idOrder;
        this.id_customer = id_customer;
        this.order_at = order_at;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.note = note;
        this.total_price = total_price;
        this.status = status;
    }

    public Order(Long id_customer, String email, String name, String address, String phone, String note, double total_price) {
        this.id_customer = id_customer;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.note = note;
        this.total_price = total_price;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public Date getOrder_at() {
        return order_at;
    }

    public void setOrder_at(Date order_at) {
        this.order_at = order_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
