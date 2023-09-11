package teahouse.projectmd4.model;

public class Product {
    private Long product_id;
    private String product_name;
    private double price;
    private int stock;
    private String description;
    private String image;
    private boolean status;

    public Product() {
    }

    public Product(Long product_id, String product_name, double price, int stock, String description, String image, boolean status) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.image = image;
        this.status = status;
    }

    public Product(Long product_id, String product_name, double price, int stock, String description, String image) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.image = image;
    }

    public Product(String product_name, double price, int stock, String description, String image) {
        this.product_name = product_name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
