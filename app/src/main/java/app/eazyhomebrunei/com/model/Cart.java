package app.eazyhomebrunei.com.model;

public class Cart {
    private int cartId;
    private String image;
    private int pr_id;
    private String pr_name;
    private String pr_ref;
    private String price;
    private int quantity;
    private String total_price;
    private String variance;

    public Cart(int cartId2, int pr_id2, String pr_ref2, String pr_name2, String image2, String variance2, int quantity2, String price2, String total_price2) {
        this.cartId = cartId2;
        this.pr_id = pr_id2;
        this.pr_ref = pr_ref2;
        this.pr_name = pr_name2;
        this.image = image2;
        this.variance = variance2;
        this.quantity = quantity2;
        this.price = price2;
        this.total_price = total_price2;
    }

    public int getCartId() {
        return this.cartId;
    }

    public void setCartId(int cartId2) {
        this.cartId = cartId2;
    }

    public int getPr_id() {
        return this.pr_id;
    }

    public void setPr_id(int pr_id2) {
        this.pr_id = pr_id2;
    }

    public String getPr_ref() {
        return this.pr_ref;
    }

    public void setPr_ref(String pr_ref2) {
        this.pr_ref = pr_ref2;
    }

    public String getPr_name() {
        return this.pr_name;
    }

    public void setPr_name(String pr_name2) {
        this.pr_name = pr_name2;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image2) {
        this.image = image2;
    }

    public String getVariance() {
        return this.variance;
    }

    public void setVariance(String variance2) {
        this.variance = variance2;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity2) {
        this.quantity = quantity2;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price2) {
        this.price = price2;
    }

    public String getTotal_price() {
        return this.total_price;
    }

    public void setTotal_price(String total_price2) {
        this.total_price = total_price2;
    }
}
