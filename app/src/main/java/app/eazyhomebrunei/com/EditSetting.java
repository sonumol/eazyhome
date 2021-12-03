package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class EditSetting extends AppCompatActivity {
    String lid;
    SharedPreferences prefs;
    TextView address,email,phn;
    ImageView edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setting);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        lid=pref.getString("lid", null);
      //  address=(TextView)findViewById(R.id.adr);
        email=(TextView)findViewById(R.id.email);
        phn=(TextView)findViewById(R.id.phn);
        edit=(ImageView)findViewById(R.id.edit);
    }
}
