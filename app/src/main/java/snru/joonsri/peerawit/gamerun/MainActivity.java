package snru.joonsri.peerawit.gamerun;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myManage = new MyManage(MainActivity.this);

        //Test Add user
       // myManage.addUser("พีรวิชญ์", "soft1", "1234", "3");

        //Delete All SQLite
        deleteAllSQLite();

        //Synchronize
        MySynchronize mySynchronize = new MySynchronize();
        mySynchronize.execute();


    }//Main Method

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
