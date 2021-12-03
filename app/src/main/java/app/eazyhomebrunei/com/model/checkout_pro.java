package app.eazyhomebrunei.com.model;

public class checkout_pro {
    private int cart_id;
    private  int id;
    private  String pname;

    private String image;
    private int quantity;
    private int price;
    private int total_price;

    public checkout_pro(int cart_id, int id, String pname, String image, int quantity, int price, int total_price) {
        this.cart_id = cart_id;
        this.id = id;
        this.pname = pname;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.total_price = total_price;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
}
