package com.example.danielmorales.amore;


import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.System.in;


public class NewUser extends AppCompatActivity {

    private RadioGroup mRadioGroup;
    private String mUserId;
    private TextView mQuestion;
    private DatabaseReference mDatabaseReferance;
    private EditText mName;
    private FirebaseAuth mAuth;
    private LinearLayout[] mlayout;
    private int mCountLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        mName = (EditText) findViewById(R.id.name);
        mQuestion = (TextView) findViewById(R.id.question);
        mAuth = FirebaseAuth.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();
        mCountLayout = 0;
        mlayout = new LinearLayout[] {
                (LinearLayout) findViewById(R.id.genderLayout),
                (LinearLayout) findViewById(R.id.nameLayout)
        };


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("lll", ";llll");
                int selectedGender = mRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedGender);
                String gender = radioButton.getText().toString();
                mDatabaseReferance = FirebaseDatabase.getInstance().getReference().child("Users").child(gender).child(mUserId);
                mlayout[mCountLayout].setVisibility(View.GONE);
                mCountLayout++;
                mlayout[mCountLayout].setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
           String name = mName.getText().toString().trim();
           mDatabaseReferance.child("Name").setValue(name);
           Intent accountIntent = new Intent(getApplicationContext(), MainActivity.class);
           startActivity(accountIntent);

        return true;
        }
        else {
            return false;
        }
    }


}

