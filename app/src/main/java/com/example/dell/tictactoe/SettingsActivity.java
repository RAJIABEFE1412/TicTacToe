package com.example.dell.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    CheckBox vibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final EditText user1 = findViewById(R.id.user1name);
        final EditText user2 = findViewById(R.id.user2name);
        Button cancel = findViewById(R.id.cancel);
        vibrate = findViewById(R.id.vibrate);
        Button Ok = findViewById(R.id.ok);

        SharedPreferences preferences = getSharedPreferences("userName",MODE_PRIVATE);
        String user1PreferrenceName = preferences.getString("user1","");
        String user2PreferrenceName = preferences.getString("user2","");
        user1.setText(user1PreferrenceName);
        user2.setText(user2PreferrenceName);
        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checked = radioGroup.getCheckedRadioButtonId();
                switch (checked){
                    case R.id.computer:
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                        builder.setMessage("Under Contrustion!!!!");
                        builder.setTitle("Error");
                        builder.setCancelable(true);
                        AlertDialog error = builder.create();
                        error.show();
                    default:

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName1 = user1.getText().toString();
                String userName2 = user2.getText().toString();
                if (!userName1.isEmpty() && !userName2.isEmpty()){
                    SharedPreferences sharedPreferences = getSharedPreferences("userName",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user1",userName1);
                    editor.putString("user2",userName2);
                    editor.apply();
                    editor.commit();
                }

                Intent i = new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

}
