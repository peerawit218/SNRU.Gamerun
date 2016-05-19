package snru.joonsri.peerawit.gamerun;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class Exercise extends AppCompatActivity {

    //Explicit
    private TextView textView;
    private RadioGroup radioGroup;
    private RadioButton choice1RadioButton, choice2RadioButton, choice3RadioButton, choice4RadioButton;
    private String[] questionString, choice1Strings, choice2Strings, choice3Strings, choice4Strings, answerStrings;
    private int timesAnInt = 1, chockseAnInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        bindWidget();

        SynQuestion synQuestion = new SynQuestion();
        synQuestion.execute();

        redioController();
    }//Main Method

    private void redioController() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.radioButton6:
                        chockseAnInt = 1;
                        break;
                    case R.id.radioButton7:
                        chockseAnInt = 2;
                        break;
                    case R.id.radioButton8:
                        chockseAnInt = 3;
                        break;
                    case R.id.radioButton9:
                        chockseAnInt = 4;
                        break;

                }

            }//onChecked
        });
    }


    public void clickAnswer(View view) {

        if (!checkRadio()) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ยังไม่ได้เลือกคำตอบ", "โปรเลือกคำตอบ");
        }



        radioGroup.clearCheck();
    }   // clickAnswer

    private boolean checkRadio() {

        boolean myResult;

        if (choice1RadioButton.isChecked()) {
            myResult = true;
        } else if (choice2RadioButton.isChecked()) {
            myResult = true;
        } else if (choice3RadioButton.isChecked()) {
            myResult = true;
        } else if (choice4RadioButton.isChecked()) {
            myResult = true;
        } else {
            myResult = false;   // Non Check
        }

        return myResult;
    }//check คำตอบ



    public class SynQuestion extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/snru/get_question.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                return null;
            }


           // return null;
        }//doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                questionString = new String[jsonArray.length()];
                choice1Strings = new String[jsonArray.length()];
                choice2Strings = new String[jsonArray.length()];
                choice3Strings = new String[jsonArray.length()];
                choice4Strings = new String[jsonArray.length()];
                answerStrings = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    questionString[i] = jsonObject.getString("Question");
                    choice1Strings[i] = jsonObject.getString("Choice");
                    choice2Strings[i] = jsonObject.getString("Choice");
                    choice3Strings[i] = jsonObject.getString("Choice");
                    choice4Strings[i] = jsonObject.getString("Choice");
                    answerStrings[i] = jsonObject.getString("Answer");

                }//for

                Random random = new Random();
                changView(random.nextInt(jsonArray.length()));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }//onPost
    }//SynQuestion

    private void changView(int index) {

        textView.setText(Integer.toString(timesAnInt) + questionString[index]);
        choice1RadioButton.setText(choice1Strings[index]);
        choice2RadioButton.setText(choice2Strings[index]);
        choice3RadioButton.setText(choice3Strings[index]);
        choice4RadioButton.setText(choice4Strings[index]);


    }


    private void bindWidget() {

        textView = (TextView) findViewById(R.id.textView6);
        radioGroup = (RadioGroup) findViewById(R.id.ragChoice);
        choice1RadioButton = (RadioButton) findViewById(R.id.radioButton6);
        choice2RadioButton = (RadioButton) findViewById(R.id.radioButton7);
        choice3RadioButton = (RadioButton) findViewById(R.id.radioButton8);
        choice4RadioButton = (RadioButton) findViewById(R.id.radioButton9);

    }
}//Main Class
