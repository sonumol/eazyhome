package app.eazyhomebrunei.com.model;

public class catgeory {
  int  wishId,pid;
  String  pname,Image,Price,OfferPrice,difference,discount;
  int Status;
  String Flag;

    public catgeory(int wishId, int pid, String pname, String image, String price, String offerPrice, String difference, String discount, int status, String flag) {
        this.wishId = wishId;
        this.pid = pid;
        this.pname = pname;
        Image = image;
        Price = price;
        OfferPrice = offerPrice;
        this.difference = difference;
        this.discount = discount;
        Status = status;
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }
}