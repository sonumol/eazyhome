package app.eazyhomebrunei.com.model;

public class latest_products {
    private int id;
    private String pname;

    private String offer_price;
    private String price;
    private String flag;
    private String image;
    private  int wishid;
    private int status;

    public latest_products(int id, String pname, String offer_price, String price, String flag, String image, int wishid, int status) {
        this.id = id;
        this.pname = pname;
        this.offer_price = offer_price;
        this.price = price;
        this.flag = flag;
        this.image = image;
        this.wishid = wishid;
        this.status = status;
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

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getWishid() {
        return wishid;
    }

    public void setWishid(int wishid) {
        this.wishid = wishid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}