package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class otp extends AppCompatActivity {
    private  long backpressed;
    private  Toast backtoast;

    LinearLayout lnr1;
    EditText phn;
    CountryCodePicker cpp;
    String fullnumber;
    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        lnr1 =(LinearLayout)findViewById(R.id.otp1);
        cpp=(CountryCodePicker) findViewById(R.id.ccp);
        phn=(EditText)findViewById(R.id.phn) ;

        lnr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = phn.getText().toString();
                String MobilePattern = "[0-9]{10}";

                    String phonenumber = "+" + cpp.getFullNumber() + number;
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("phn", phonenumber);
                    editor.putString("number",number);

                    editor.apply();


                    Intent intent = new Intent(getApplicationContext(), otp_generate.class);
                    intent.putExtra("phonenumber", phonenumber);
                    //Toast.makeText(getApplicationContext(), "otp" + phonenumber, Toast.LENGTH_LONG).show();
                    startActivity(intent);

            }


        });
        cpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnumber();//get country code

            }
        });
    }


    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    ///////////////get country code//////////////////////////
    private void getnumber() {
        fullnumber=cpp.getFullNumber()+phn.getText().toString();
        phn.setText(fullnumber);
    }

}
