package app.eazyhomebrunei.com.model;

public class my_order {
    private String order_id;
    private String orderId;
    private String Name;
    private String Location;
    private String TotalAmount;
    private String OrderDate;
    private String status_date;
    private String ShippinReference;
    private String Flag;
    public my_order(String order_id, String orderId, String name, String location, String totalAmount, String orderDate, String status_date, String shippinReference, String flag) {
        this.order_id = order_id;
        this.orderId = orderId;
        Name = name;
        Location = location;
        TotalAmount = totalAmount;
        OrderDate = orderDate;
        this.status_date = status_date;
        ShippinReference = shippinReference;
        Flag = flag;
    }
    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getStatus_date() {
        return status_date;
    }

    public void setStatus_date(String status_date) {
        this.status_date = status_date;
    }

    public String getShippinReference() {
        return ShippinReference;
    }

    public void setShippinReference(String shippinReference) {
        ShippinReference = shippinReference;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }


}
