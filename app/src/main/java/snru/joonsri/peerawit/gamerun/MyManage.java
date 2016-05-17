package snru.joonsri.peerawit.gamerun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 17/5/2559.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteOpenHelper;

    public MyManage(Context context) {

        myOpenHelper = new MyOpenHelper(context);
        sqLiteOpenHelper = myOpenHelper.getWritableDatabase();


    } //Constructor


} //Main Class
