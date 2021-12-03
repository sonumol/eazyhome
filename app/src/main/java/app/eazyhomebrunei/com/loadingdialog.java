package app.eazyhomebrunei.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;


public class loadingdialog {
   private Activity activity;
  private   AlertDialog dialog;

    loadingdialog(Activity myactivity) {
        activity = myactivity;
    }
     void startDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
         LayoutInflater inflater=activity.getLayoutInflater();
         builder.setView(inflater.inflate(R.layout.custom_dialog,null));
         builder.setCancelable(true);
         dialog=builder.create();
         dialog.show();
     }
     void dismissDilaog(){
        dialog.dismiss();
     }
}
