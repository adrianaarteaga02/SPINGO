package ch.adrart.zli.spingo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class category extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);
    public static final String sEXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public static final String sEXTRA_NUMBER = "com.example.application.example.EXTRA_NUMBER";
    public int iCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //fill ListView with already existing elements
        this.addCategoriesToList();

        //get clicked category
        ListView lv = findViewById(R.id.lv_CategoryList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String sSelectedName = (String) parent.getItemAtPosition(position);
                iCategory = dbHelper.getIDFromSelectedCategory(sSelectedName);

                Intent intent = new Intent(category.this, elements.class);
                intent.putExtra(sEXTRA_NUMBER, iCategory);
                startActivity(intent);
            }
        });
    }

    //get data from database and add to ListView
    private void addCategoriesToList() {
        Log.d("category", "addCategoriesToList: Displaying data in the ListView");

        //get the data and append to the list
        List<String> entries = dbHelper.getAllEntries(1, 0);

        if (entries.size() <= 0) {
            toastMessage("Here are currently no data ");
        } else {
            ListView elementList = findViewById(R.id.lv_CategoryList);
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entries);
            elementList.setAdapter(adapter);
        }
    }

    //open activity add_attribute
    public void openActivityAddElements(View view) {
        String text = "category;category"; //String text = "category;elements";

        Intent intent = new Intent(this, add_attribute.class);
        intent.putExtra(sEXTRA_TEXT, text);
        startActivity(intent);
    }

    //show toast message
    private void toastMessage(String sMessage) {
        Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
    }


    ////########## elements
    //open activity spin
    public void openActivitySpin(View view) {
        Intent intent = new Intent(this, spin.class);
        intent.putExtra(sEXTRA_NUMBER, iCategory);
        startActivity(intent);
    }

    public void jumpBack(View view) {
        Intent intent = new Intent(this, category.class);
        startActivity(intent);
    }

}
