package ch.adrart.zli.spingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class category extends AppCompatActivity {

    private static final String TAG = "category";
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);

    public static final String sEXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //fill ListView with already existing elements
        this.addCategoriesToList();

        //onclick add element and switching to add_attribute
        Button btnAdd = (Button) findViewById(R.id.btn_AddElement);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAddElements();
            }
        });

    }

    private void addCategoriesToList() {
        Log.d(TAG, "addCategoriesToList: Displaying data in the ListView");

        //get the data and append to the list
        Cursor cursor = mDatabaseHelper.getData("category");
        ArrayList<String> listData = new ArrayList<>();

        if (!cursor.moveToNext()){
            toastMessage("Here are currently no data ");
        }else{

            while (cursor.moveToNext()){
                listData.add(cursor.getString(1));
            }

            ListView elementList = findViewById(R.id.lv_ElementList);
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
            elementList.setAdapter(adapter);
        }
    }

    public void openActivityAddElements() {
        String text = "category;elements";

        Intent intent = new Intent(this, add_attribute.class);
        intent.putExtra(sEXTRA_TEXT, text);
        startActivity(intent);
    }

    private void toastMessage(String sMessage){
        Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
    }

}
