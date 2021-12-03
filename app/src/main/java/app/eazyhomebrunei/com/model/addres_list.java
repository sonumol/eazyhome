package app.eazyhomebrunei.com.model;

public class addres_list {
    private  int id;
    private  int customer_id;
    private  String name;
    private  String phone;
    private  String email;
    private  String address;
    private  String state;
    private  String city;
    private  String postal_code;
    private  String status;
    private  String date;

    public addres_list(int id, int customer_id, String name, String phone, String email, String address, String state, String city, String postal_code, String status, String date) {
        this.id = id;
        this.customer_id = customer_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.state = state;
        this.city = city;
        this.postal_code = postal_code;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
