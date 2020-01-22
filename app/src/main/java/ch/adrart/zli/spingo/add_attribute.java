package ch.adrart.zli.spingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_attribute extends AppCompatActivity {

    private DBHelper db = new DBHelper(this);

    public static final String sEXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public static final String sEXTRA_NUMBER = "com.example.application.example.EXTRA_NUMBER";
    public int iCategoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attribute);

        //define buttons and get parameters
        Button btnCancel = findViewById(R.id.btn_Cancel);
        Button btnSave = findViewById(R.id.btn_Add);
        final EditText lblName = findViewById(R.id.lbl_Name);

        //get intent
        Intent intent = getIntent();
        final String sCategory = intent.getStringExtra(category.sEXTRA_TEXT);
        final String sElement = intent.getStringExtra(elements.sEXTRA_TEXT);

        //check which button was clicked
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jump to activity
                if (sCategory.length() > 0) {
                    jumpToOtherActivity(sCategory, 1);
                } else {
                    jumpToOtherActivity(sElement, 1);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add new entry in database
                String sEntry = lblName.getText().toString();
                String[] separate = sCategory.split(";"); // 0 -->

                if (sEntry.length() != 0) {
                    //add category
                    if (sCategory.length() > 0) {
                        addData(sEntry, 1, 0);
                        iCategoryID = db.getIDFromSelectedCategory(sEntry);
                        jumpToOtherActivity(sCategory, 2);

                        //add element
                    } else {
                        int iID = Integer.parseInt(separate[2]);

                        addData(sEntry, 2, iID);
                        jumpToOtherActivity(sElement, 2);

                    }

                } else {
                    toastMessage("You must put something in the text field");
                }

            }
        });
    }

    public void jumpToOtherActivity(String form, int mode) {
        Intent intent = new Intent(this, add_attribute.class);
        intent.putExtra(sEXTRA_TEXT, form);

        String[] separate = form.split(";");

        switch (mode) {
            //cancel / back
            case 1:
                if (separate[0].equals("elements")) {
                    intent = new Intent(this, elements.class);
                    intent.putExtra(sEXTRA_NUMBER, iCategoryID);

                } else if (separate[0].equals("spin")) {
                    intent = new Intent(this, spin.class);
                    intent.putExtra(sEXTRA_NUMBER, iCategoryID);

                }
                break;

            //save
            case 2:
                if (separate[1].equals("elements")) {
                    intent = new Intent(this, elements.class);
                    intent.putExtra(sEXTRA_NUMBER, iCategoryID);

                } else if (separate[1].equals("spin")) {
                    intent = new Intent(this, spin.class);
                    intent.putExtra(sEXTRA_NUMBER, iCategoryID);

                } else if (separate[1].equals("category")) {
                    intent = new Intent(this, category.class);

                } else if (separate[1].equals("stay")) {
                    intent = new Intent(this, elements.class);
                    intent.putExtra(sEXTRA_NUMBER, iCategoryID);

                }
                break;
        }
        startActivity(intent);
    }

    public void addData(String sEntry, int iTable, int iCategory) {
        boolean bInsertData = true;

        bInsertData = db.insertEntry(iTable, sEntry, iCategory);

        if (bInsertData) {
            toastMessage("Data successfully inserted");

        } else {
            toastMessage("Something went wrong");

        }
    }

    private void toastMessage(String sMessage) {
        Toast.makeText(this, sMessage, Toast.LENGTH_SHORT).show();
    }
}
