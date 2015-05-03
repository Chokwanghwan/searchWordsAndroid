package com.kwanggoo.searchword.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kwanggoo.searchword.R;
import com.kwanggoo.searchword.util.UserManager;

public class SignActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStart;
    private EditText mEtEmai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isUserSign())
            startMain();

        setContentView(R.layout.activity_sign);
        mEtEmai = (EditText) findViewById(R.id.et_email);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_start:
                onClickStart();
                break;
        }
    }

    private void onClickStart() {
        String email = mEtEmai.getText().toString();
        if(email.length() <= 0) {
            Toast.makeText(this, "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return ;
        }


        UserManager userManager = UserManager.getInstance(this);
        userManager.saveUserEmail(email);
        startMain();
    }

    private void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        String email = UserManager.getInstance(this).getUserEmail();
        Toast.makeText(this, email + "님 반갑습니다.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    public boolean isUserSign() {
        boolean isUserSign = false;
        UserManager userManager = UserManager.getInstance(this);
        String email = userManager.getUserEmail();
        if(!email.isEmpty())
            isUserSign = true;
        return isUserSign;
    }
}
