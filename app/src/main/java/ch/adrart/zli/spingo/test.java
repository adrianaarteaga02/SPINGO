package ch.adrart.zli.spingo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Random;

public class test extends AppCompatActivity {


    DBHelper dbHelper = new DBHelper(this);
    private int iCategory;

    private TextSwitcher tsSpinning;
    private Button btnSpin;
    Random random = new Random();
    private int iCurID = 0;
    private List<String> elements;
    private TextView tvElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //get intent
        Intent intent = getIntent();
        iCategory = intent.getIntExtra(add_attribute.sEXTRA_NUMBER, 0);
        elements = dbHelper.getAllEntries(1, iCategory);

        tsSpinning = findViewById(R.id.textSwitcher);
        btnSpin = findViewById(R.id.btn_Spin);
        btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                do {
                    iCurID = random.nextInt(elements.size());
                } while (iCurID == 0 || iCurID > elements.size());

                tsSpinning.setText(elements.get(iCurID));

            }
        });

        tsSpinning.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                tvElement = new TextView(test.this);
                tvElement.setTextColor(Color.BLACK);
                tvElement.setTextSize(60);
                tvElement.setGravity(Gravity.CENTER_HORIZONTAL);
                return tvElement;
            }
        });

        tsSpinning.setText(elements.get(iCurID));
    }


}