package app.eazyhomebrunei.com;

public class catgeory1 {
    int wishId,pid,Status;


   String pname,Image,Price,OfferPrice,difference,discount,Flag;

    public catgeory1(int wishId, int pid, int status, String pname, String image, String price, String offerPrice, String difference, String discount, String flag) {
        this.wishId = wishId;
        this.pid = pid;
        Status = status;
        this.pname = pname;
        Image = image;
        Price = price;
        OfferPrice = offerPrice;
        this.difference = difference;
        this.discount = discount;
        Flag = flag;
    }

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getOfferPrice() {
        return OfferPrice;
    }

    public void setOfferPrice(String offerPrice) {
        OfferPrice = offerPrice;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }
}
