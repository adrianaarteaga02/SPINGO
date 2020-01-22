package ch.adrart.zli.spingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_attribute extends AppCompatActivity {

    private static final String TAG = "category";
    DatabaseHelper mDatabaseHelper;

    public static final String sEXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public static final String sEXTRA_NUMBER = "com.example.application.example.EXTRA_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attribute);

        //define buttons and get parameters
        Button btnCancel = findViewById(R.id.btn_Add);
        Button btnSave = findViewById(R.id.btn_Cancel);
        final EditText lblName = findViewById(R.id.lbl_Name);

        Intent intent = getIntent();
        final String sCategory = intent.getStringExtra(category.sEXTRA_TEXT);
        final String sElement = intent.getStringExtra(elements.sEXTRA_TEXT);

        //check which button was clicked
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jump to origin activity
                if (sCategory.isEmpty()) {
                    jumpToOtherActivity(sCategory, 1);
                } else {
                    jumpToOtherActivity(sElement, 1);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add in database
                String sEntry = lblName.getText().toString();
                if(sEntry.length() != 0){
                    addData(sEntry);
                }else{
                    toastMessage("You must put something in the text field");
                }

                //jump to next activity
                if (sCategory.isEmpty()) {
                    jumpToOtherActivity(sCategory, 2);
                } else {
                    jumpToOtherActivity(sElement, 2);
                }
            }
        });
    }

    public void jumpToOtherActivity(String form, int mode) {
        Intent intent = new Intent(this, category.class);
        intent.putExtra(sEXTRA_TEXT, form);

        String[] separate = form.split(";");

        switch (mode) {
            case 1:
                if (separate[0].equals("category")) {
                    intent = new Intent(this, category.class);
                } else {
                    intent = new Intent(this, elements.class);
                }

            case 2:
                if (separate[1].equals("elements")) {
                    intent = new Intent(this, elements.class);
                } else {
                    intent = new Intent(this, spin.class);
                }
        }
        startActivity(intent);
    }

    public void addData(String sEntry){
        boolean insertData = mDatabaseHelper.addData(sEntry, "element");

        if(insertData){
            toastMessage("Data successfully inserted");
        }else{
            toastMessage("Something went wrong");
        }
    }


    private void toastMessage(String sMessage){
        Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
    }
}
