package com.example.smsreceiver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView mess;
    private static final int MY_PERMISIONS_INFO  = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         //Check the requestcode
        switch(requestCode){
            case MY_PERMISIONS_INFO: {
                //Check whether lenght of grantresuls>0 / == Permisions_granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //New BroadCast servic wil work in background
                    Toast.makeText(this, "Thank you for permiting",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this,"Well, i cant do anything past that",Toast.LENGTH_LONG).show();
                }
            }
            }
        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mess = findViewById(R.id.message);

        //Check permisions not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            // If the permision i not granted check if user has denied
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS)){
                //Do nothing
            }
            else{
                //Show pop up asking user to grant permisions
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},MY_PERMISIONS_INFO);
            }
        }





    } // OnCreate

}
