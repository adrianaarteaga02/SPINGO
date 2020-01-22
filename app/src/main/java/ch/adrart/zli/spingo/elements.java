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
import java.util.List;

public class elements extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);
    public static final String sEXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public static final String sEXTRA_NUMBER = "com.example.application.example.EXTRA_NUMBER";
    public int iCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements);

        //get intent
        Intent intent = getIntent();
        final int iCategory = intent.getIntExtra(add_attribute.sEXTRA_NUMBER, 0);

        //onclick add element and switching to add_attribute
        Button btnAdd = findViewById(R.id.btn_AddElement);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAddElements(iCategory);
            }
        });

        //fill ListView with already existing elements
        this.addElementsToList(iCategory);
    }

    public void addElementsToList(int iCategory) {

        Log.d("elements", "addElementsToList: Displaying data in the ListView");

        //get the data and append to the list
        List<String> entries = dbHelper.getAllEntries(2, iCategory);
        ArrayList<String> listData = new ArrayList<>();

        if (entries.size() <= 0) {
            toastMessage("Here are currently no data ");
        } else {
            ListView elementList = findViewById(R.id.lv_ElementList);
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entries);
            elementList.setAdapter(adapter);
        }
    }

    public void openActivityAddElements(int iCategory) {
        String text = "elements;elements;" + iCategory;

        Intent intent = new Intent(this, add_attribute.class);
        intent.putExtra(sEXTRA_TEXT, text);
        startActivity(intent);
    }

    //show tast message
    private void toastMessage(String sMessage) {
        Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
    }

    //open activity spin
    public void openActivitySpin(View view) {
        Intent intent = new Intent(this, spin.class);
        intent.putExtra(sEXTRA_NUMBER, iCategory);
        startActivity(intent);
    }

    //jump back
    public void jumpBack(View view) {
        Intent intent = new Intent(this, category.class);
        startActivity(intent);
    }
}
