package app.eazyhomebrunei.com.model;

public class featured_pro {
    private int id;
    private String pname;

    private String offer_price;
    private String price;
    private int flag;
    private String image;
    private  int wishId;
    private int status;

    public featured_pro(int id, String pname, String offer_price, String price, int flag, String image, int wishId, int status) {
        this.id = id;
        this.pname = pname;
        this.offer_price = offer_price;
        this.price = price;
        this.flag = flag;
        this.image = image;
        this.wishId = wishId;
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}