package app.eazyhomebrunei.com.model;

public class cart_pro {
    private int cart_id;
    private  int id;
    private  String pname;

    private String image;
    private String quantity;
    private String price;
    private String total_price;
    private String reference;
    String offer_price;

    public cart_pro(int cart_id, int id, String pname, String image, String quantity, String price, String total_price, String reference, String offer_price) {
        this.cart_id = cart_id;
        this.id = id;
        this.pname = pname;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.total_price = total_price;
        this.reference = reference;
        this.offer_price = offer_price;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }
}
