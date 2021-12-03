package app.eazyhomebrunei.com.model;

public class Product {
    private  int CategoryId;
    private  int sub_count;
    private  int pr_count;

    private String catagory;
    private String image;

    public Product(int categoryId, int sub_count, int pr_count, String catagory, String image) {
        CategoryId = categoryId;
        this.sub_count = sub_count;
        this.pr_count = pr_count;
        this.catagory = catagory;
        this.image = image;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getSub_count() {
        return sub_count;
    }

    public void setSub_count(int sub_count) {
        this.sub_count = sub_count;
    }

    public int getPr_count() {
        return pr_count;
    }

    public void setPr_count(int pr_count) {
        this.pr_count = pr_count;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
