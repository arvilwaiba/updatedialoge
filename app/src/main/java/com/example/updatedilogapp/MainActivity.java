package com.example.updatedilogapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check();

    }

    @Override
    public void OnUpdateCheckListener(final String urlApp) {


        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle("your app on Play Store")
                .setMessage("please update to new version to continue use")
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, ""+urlApp, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "you cancel the the app", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }
}
