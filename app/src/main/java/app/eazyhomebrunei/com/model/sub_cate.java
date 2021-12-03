package app.eazyhomebrunei.com.model;

public class sub_cate {
    int catid;
    private int SubinCategoryId;
    private int pr_count;

    private String catagory;
    private String image;
    int subin_count;
    int subcategoryid;

    public sub_cate(int catid, int subinCategoryId, int pr_count, String catagory, String image, int subin_count, int subcategoryid) {
        this.catid = catid;
        SubinCategoryId = subinCategoryId;
        this.pr_count = pr_count;
        this.catagory = catagory;
        this.image = image;
        this.subin_count = subin_count;
        this.subcategoryid = subcategoryid;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getSubinCategoryId() {
        return SubinCategoryId;
    }

    public void setSubinCategoryId(int subinCategoryId) {
        SubinCategoryId = subinCategoryId;
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

    public int getSubin_count() {
        return subin_count;
    }

    public void setSubin_count(int subin_count) {
        this.subin_count = subin_count;
    }

    public int getSubcategoryid() {
        return subcategoryid;
    }

    public void setSubcategoryid(int subcategoryid) {
        this.subcategoryid = subcategoryid;
    }
}