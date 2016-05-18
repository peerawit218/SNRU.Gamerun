package snru.joonsri.peerawit.gamerun;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private ImageView imageView;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] userStrings;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind widget
        imageView = (ImageView) findViewById(R.id.imageView6);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);

        myManage = new MyManage(MainActivity.this);

        //Test Add user
       // myManage.addUser("พีรวิชญ์", "soft1", "1234", "3");

        //Delete All SQLite
        deleteAllSQLite();

        //Synchronize
        MySynchronize mySynchronize = new MySynchronize();
        mySynchronize.execute();

        //Show Logo
        Picasso.with(MainActivity.this).load("http://swiftcodingthai.com/snru/image/logogamerun.png").into(imageView);


    }//Main Method

    public void clickSignIn(View view) {

        Log.d("test", "click");

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {

          /*  Toast.makeText(MainActivity.this, "Error กรุณากรอก user pass", Toast.LENGTH_SHORT).show();*/
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกให้ครบ");

        } else {

            checkUser();

        }

    }

    private void checkUser() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString + "'", null);
            cursor.moveToFirst();
            userStrings = new String[cursor.getColumnCount()];

            for (int i = 0;i < cursor.getColumnCount(); i++) {
                userStrings[i] = cursor.getString(i);
            }

            //Check Password
            if (passwordString.equals(userStrings[3])) {

                Toast.makeText(this, "ยินดีต้อนรับ" + userStrings[1], Toast.LENGTH_SHORT).show();


            } else {

                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this,"Password False", "Please Try Again Password False");

            }



        } catch (Exception e) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ไม่มี User นี้", "ไม่มี " + userString + " ในฐานข้อมูลของดรา");
        }

    } //checkUser

    //Create Inner Class
    public class MySynchronize extends AsyncTask<Void, Void, String> {

        //ประกาศ Class ซ้อน Class
        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/snru/get_user.php").build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();

            } catch (Exception e) {

                return null;

            }

            //return null;
        } //doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("Snru", "JSON == " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {

                    //ดึงข้อมูลมาโชว์จากฐานข้อมูลทั้งหมด
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strName = jsonObject.getString(myManage.column_name);
                    String strUser = jsonObject.getString(myManage.column_user);
                    String strPassword = jsonObject.getString(myManage.column_password);
                    String strAvata = jsonObject.getString(myManage.column_avata);

                    myManage.addUser(strName, strUser, strPassword, strAvata);

                } //For

            } catch (Exception e) {

                e.printStackTrace();

            }

        } //onPost



    } //MySyn Class



    //Delete All SQLite
    private void deleteAllSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);
    }

    public void clickSignUpMain(View view) { //ทำให้ปุ่มเปิดไปหน้าที่เราต้องการ
        startActivity(new Intent(MainActivity.this, SignUp.class));
    }


}//Main Class นี่คือคลาสหลัก
