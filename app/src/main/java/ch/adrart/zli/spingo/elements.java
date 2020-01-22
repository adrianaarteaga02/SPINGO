package ch.adrart.zli.spingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class elements extends AppCompatActivity {

    private static final String TAG = "elements";
    DatabaseHelper mDatabaseHelper;

    public static final String sEXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements);

        //fill ListView with already existing elements
        this.addElementsToList();

        //onclick add element and switching to add_attribute
        Button btnAdd = findViewById(R.id.btn_AddElement);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAddElements();
            }
        });
    }

    private void addElementsToList() {

        Log.d(TAG, "addElementsToList: Displaying data in the ListView");

        //get the data and append to the list
        Cursor data = mDatabaseHelper.getData("element");

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));
        }

        ListView elementList = findViewById(R.id.lv_ElementList);
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        elementList.setAdapter(adapter);
    }

    public void openActivityAddElements() {
        String text = "elements;spin";

        Intent intent = new Intent(this, add_attribute.class);
        intent.putExtra(sEXTRA_TEXT, text);
        startActivity(intent);
    }
}
