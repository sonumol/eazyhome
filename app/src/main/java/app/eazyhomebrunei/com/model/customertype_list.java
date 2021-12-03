package app.eazyhomebrunei.com.model;

import java.util.ArrayList;
import java.util.Iterator;



public class customertype_list {
    private static customertype_list _instance;
    private customertype category;
    private ArrayList<customertype> list = new ArrayList<>();

    private customertype_list() {
    }

    public static customertype_list getInstance() {
        if (_instance == null) {
            _instance = new customertype_list();
        }
        return _instance;
    }

    public void add(customertype customertype) {
        this.list.add(customertype);
    }

    public ArrayList<customertype> getList() {
        return this.list;
    }

    public ArrayList<String> getcustomertype() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(0, "Select Address");

        Iterator it = this.list.iterator();
        while (it.hasNext()) {
            arrayList.add(((customertype) it.next()).getName());
        }
        return arrayList;
    }

    public int getcustomertypeID(String str) {
        Iterator it = this.list.iterator();
        while (it.hasNext()) {
            customertype customertype = (customertype) it.next();
            if (customertype.getName().equals(str)) {
                return customertype.getId();
            }
        }
        return -1;
    }


    public customertype getSelectedDistrict() {
        return this.category;
    }

    public void setSelectedCategory(customertype customertype) {
        this.category = customertype;
    }

    public void clearList() {
        if (this.list.size() > 0) {
            this.list.clear();
        }
    }
}
