package app.eazyhomebrunei.com.model;

public class coupon_model {
    private  String reference;
    private String startdate;
    private String StartTime;
    private String EndDate;
    private String EndTime;
    private String title;
    private String description;
    private String image;
    private String code;
    private String value;

    public coupon_model(String reference, String startdate, String startTime, String endDate, String endTime, String title, String description, String image, String code, String value) {
        this.reference = reference;
        this.startdate = startdate;
        StartTime = startTime;
        EndDate = endDate;
        EndTime = endTime;
        this.title = title;
        this.description = description;
        this.image = image;
        this.code = code;
        this.value = value;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
