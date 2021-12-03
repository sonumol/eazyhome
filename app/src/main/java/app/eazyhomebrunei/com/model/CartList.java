package app.eazyhomebrunei.com.model;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public class CartList {
    private static CartList _instance = null;
    private cart_pro cart;
    private ArrayList<cart_pro> list = new ArrayList<>();

    private CartList() {
    }

    public static CartList getInstance() {
        if (_instance == null) {
            _instance = new CartList();
        }
        return _instance;
    }

    public void add(cart_pro forumsModel) {
        this.list.add(forumsModel);
    }

    public ArrayList<cart_pro> getList() {
        return this.list;
    }

    public int getCartID(String location) {
        Iterator it = this.list.iterator();
        while (it.hasNext()) {
            Cart forumsModel = (Cart) it.next();
            if (forumsModel.getPr_name().equals(location)) {
                return forumsModel.getCartId();
            }
        }
        return -1;
    }

//    public float get_grand_total() {
//        float grandTotal = 0.0f;
//        Iterator it = this.list.iterator();
//        while (it.hasNext()) {
//            cart_pro cart2 = (cart_pro) it.next();
//        //    float quantity = (float) cart2.getQuantity();
//            int price = (int) Integer.parseInt(String.valueOf(cart2.getPrice()));
//            StringBuilder sb = new StringBuilder();
//            sb.append("cart list get_grand_total: quantity = ");
//            sb.append(quantity);
//            sb.append(" price = ");
//            sb.append(price);
//            String str = "TAG";
//            Log.d(str, sb.toString());
//            grandTotal += price * quantity;
//            StringBuilder sb2 = new StringBuilder();
//            sb2.append("cart list get_grand_total: total ");
//            sb2.append(grandTotal);
//            Log.d(str, sb2.toString());
//        }
//        return grandTotal;
//    }

    public cart_pro getSelectedCategory() {
        return this.cart;
    }

    public void setSelectedCategory(cart_pro cart2) {
        this.cart = cart2;
    }

    public void clearList() {
        if (this.list.size() > 0) {
            this.list.clear();
        }
    }
}
