package snru.joonsri.peerawit.gamerun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myManage = new MyManage(MainActivity.this);


    }//Main Method


    public void clickSignUpMain(View view) { //ทำให้ปุ่มเปิดไปหน้าที่เราต้องการ
        startActivity(new Intent(MainActivity.this, SignUp.class));
    }


}//Main Class นี่คือคลาสหลัก
