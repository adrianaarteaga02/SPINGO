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
import java.util.concurrent.TimeUnit;

public class spin extends AppCompatActivity {

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
        setContentView(R.layout.activity_spin);

        //get intent
        Intent intent = getIntent();
        iCategory = intent.getIntExtra(add_attribute.sEXTRA_NUMBER, 0);
        elements = dbHelper.getAllEntries(1, iCategory);

        //let elements spin
        tsSpinning = findViewById(R.id.ts_SpinningElement);
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
                tvElement = new TextView(spin.this);
                tvElement.setTextColor(Color.BLACK);
                tvElement.setTextSize(60);
                tvElement.setGravity(Gravity.CENTER_HORIZONTAL);
                return tvElement;
            }
        });

        tsSpinning.setText(elements.get(iCurID));
    }

    public void jumpBack(View view) {
        Intent intent = new Intent(this, elements.class);
        startActivity(intent);
    }

}
