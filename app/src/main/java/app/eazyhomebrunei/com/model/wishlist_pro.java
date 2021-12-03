package app.eazyhomebrunei.com.model;

public class wishlist_pro {
    private int wishlist_id;
    private  int id;
    private  String pname;

    private String image;
    private String price;
    private String offer_price;
    private int status;
    private  int flag;

    public wishlist_pro(int wishlist_id, int id, String pname, String image, String price, String offer_price, int status, int flag) {
        this.wishlist_id = wishlist_id;
        this.id = id;
        this.pname = pname;
        this.image = image;
        this.price = price;
        this.offer_price = offer_price;
        this.status = status;
        this.flag = flag;
    }

    public int getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
